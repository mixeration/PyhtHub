package eu.mixeration.phythub.mysql;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import eu.mixeration.phythub.PhytHub;
import eu.mixeration.phythub.config.PluginConfig;
import eu.mixeration.phythub.mysql.interfaces.DataAdapter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.sql.*;
import java.util.UUID;

public class MySqlConnector extends DataAdapter {
    private final PhytHub plugin;
    private static HikariDataSource hikariDataSource;

    public MySqlConnector(PhytHub plugin) {
        this.plugin = plugin;
    }


    @Override
    public void setupSource() {
        this.setupConnection();
        this.checkTables();
    }

    @Override
    public void saveData(Player player) {
        try(Connection con = this.getConnection()){
            String sql = "INSERT INTO phytSql(uuid, player, balance, coin,ingame,playedmatches) VALUES (?,?,?,?,?,?) ON DUPLICATE KEY UPDATE player=player,balance=balance,ingame=ingame,playedmatches=playedmatches;";
            PreparedStatement statement = con.prepareStatement(sql);

            statement.setString(1,player.getUniqueId().toString());
            statement.setString(2, player.getName().toString());
            statement.setInt(3,0);
            statement.setInt(4,0);
            statement.setInt(5,0);
            statement.setInt(6,0);

            statement.executeUpdate();
        }catch (Exception exception){
            exception.printStackTrace();
        }
    }

    @Override
    public void removeData(Player player) {
        try(Connection con = this.getConnection()){
            PreparedStatement statement = con.prepareStatement("DELETE FROM phytSql WHERE id=?");
            statement.setString(1,player.getUniqueId().toString());
            statement.executeUpdate();
        }catch (Exception exception){
            exception.printStackTrace();
        }
    }


    public static Connection getConnection() throws SQLException {
        return hikariDataSource.getConnection();
    }


    private void setupConnection(){
        HikariConfig dbConfig = new HikariConfig();
        String host = PluginConfig.getConfig().getString("phythub.$mysql.address");
        String database = PluginConfig.getConfig().getString("phythub.$mysql.database");

        // Address
        dbConfig.setJdbcUrl("jdbc:mysql://" + host + "/" + database);

        dbConfig.setUsername(PluginConfig.getConfig().getString("phythub.$mysql.username"));
        dbConfig.setPassword(PluginConfig.getConfig().getString("phythub.$mysql.password"));
        dbConfig.setDriverClassName(PluginConfig.getConfig().getString("phythub.$mysql.driver"));
        dbConfig.setPoolName("PhytHub@Mixeration");

        // Encoding
        dbConfig.addDataSourceProperty("characterEncoding", "utf8");
        dbConfig.addDataSourceProperty("encoding", "UTF-8");
        dbConfig.addDataSourceProperty("useUnicode", "true");

        // Request mysql over SSL
        dbConfig.addDataSourceProperty("useSSL", PluginConfig.getConfig().getString("phythub.$mysql.useSSL"));

        this.hikariDataSource = new HikariDataSource(dbConfig);

        plugin.getLogger().info("Mysql Datasource ready!");
    }

    private void checkTables(){
        try(Connection con = this.getConnection(); Statement statement = con.createStatement()){
            String $0sql = "CREATE TABLE IF NOT EXISTS phytSql (\n" +
                    "    uuid VARCHAR(255) NOT NULL PRIMARY KEY,\n" +
                    "    player VARCHAR(255) NOT NULL,\n" +
                    "    firstjoin VARCHAR(255) NOT NULL,\n" +
                    "    lastjoin VARCHAR(255) NOT NULL,\n" +
                    "    ismod INT" +
                    ")  ENGINE=INNODB;";
            String $1sql = "CREATE TABLE IF NOT EXISTS phytData (\n" +
                    "    world VARCHAR(255) NOT NULL PRIMARY KEY,\n" +
                    "    x VARCHAR(255) NOT NULL,\n" +
                    "    y VARCHAR(255) NOT NULL,\n" +
                    "    z VARCHAR(255) NOT NULL,\n" +
                    "    yaw VARCHAR(255),\n" +
                    "    pitch VARCHAR(255) NOT NULL" +
                    ")  ENGINE=INNODB;";
            statement.executeUpdate($0sql);
            statement.executeUpdate($1sql);
        }catch (Exception exception){
            exception.printStackTrace();
        }
    }

    public static boolean playerExists(UUID uuid) {
        try {
            PreparedStatement statement = hikariDataSource.getConnection()
                    .prepareStatement("SELECT * FROM phytSql WHERE UUID=?");
            statement.setString(1, uuid.toString());

            ResultSet results = statement.executeQuery();
            if (results.next()) {
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void createNewData(final UUID uuid, Player player) {
        try {
            PreparedStatement statement = hikariDataSource.getConnection()
                    .prepareStatement("SELECT * FROM phytSql WHERE uuid=?");
            statement.setString(1, uuid.toString());
            ResultSet results = statement.executeQuery();
            results.next();
            System.out.print(1);
            if (playerExists(uuid) != true) {
                PreparedStatement insert = hikariDataSource.getConnection()
                        .prepareStatement("INSERT INTO phytSql (uuid,player,balance,coin,ingame,playedmatches) VALUES (?,?,?,?,?,?)");
                insert.setString(1, uuid.toString());
                insert.setString(2, player.getName());
                insert.setInt(3, 0);
                insert.setInt(4, 0);
                insert.setInt(5, 0);
                insert.setInt(6, 0);
                insert.executeUpdate();

                Bukkit.getLogger().warning("Data created for " + player.getName());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
