package eu.mixeration.phythub.mysql.manager;

import eu.mixeration.phythub.mysql.MySqlConnector;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.UUID;

public class PhytSQL {

    public static boolean playerExists(UUID uuid) {
        try {
            PreparedStatement statement = MySqlConnector.getConnection()
                    .prepareStatement("SELECT * FROM phytSql WHERE UUID=?");
            statement.setString(1, uuid.toString());

            ResultSet results = statement.executeQuery();
            if (results.next()) {
                Bukkit.getLogger().warning("[phytSql] Unknow player data.");
                return true;
            }
            Bukkit.getLogger().warning("[phytSql] Unknow player data.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Deprecated
    public static void createPlayer(final UUID uuid, Player player) {
        try {
            PreparedStatement statement = MySqlConnector.getConnection()
                    .prepareStatement("SELECT * FROM phytSql WHERE uuid=?");
            statement.setString(1, uuid.toString());
            ResultSet results = statement.executeQuery();
            results.next();
            System.out.print(1);
            Date date = new Date();
            String toDay = date.getMinutes()+":"+date.getHours()+":"+date.getDay()+"/"+date.getMonth()+"/"+date.getYear();
            if (playerExists(uuid) != true) {
                PreparedStatement insert = MySqlConnector.getConnection()
                        .prepareStatement("INSERT INTO phytSql (uuid,player,coin,lastplay,firstplay) VALUES (?,?,?,?,?)");
                insert.setString(1, uuid.toString());
                insert.setString(2, player.getName());
                insert.setInt(3, 0);
                insert.setString(4, toDay);
                insert.setString(5, toDay);
                insert.executeUpdate();

                Bukkit.getLogger().warning("Data created for " + player.getName());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void resetCoin(UUID uuid) {
        try {
            PreparedStatement statement = MySqlConnector.getConnection()
                    .prepareStatement("UPDATE phytSql SET coin=? WHERE uuid=?");
            statement.setInt(1, 0);
            statement.setString(2, uuid.toString());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void resetBalance(UUID uuid) {
        try {
            PreparedStatement statement = MySqlConnector.getConnection()
                    .prepareStatement("UPDATE phytSql SET balance=? WHERE uuid=?");
            statement.setInt(1, 0);
            statement.setString(2, uuid.toString());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void getCoins(UUID uuid) {
        try {
            PreparedStatement statement = MySqlConnector.getConnection()
                    .prepareStatement("SELECT * FROM phytSql WHERE uuid=?");
            statement.setString(1, uuid.toString());
            ResultSet results = statement.executeQuery();
            results.next();

            System.out.print(results.getInt("coin"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void addCoin(Player player, int val) {
        try {
            PreparedStatement statement = MySqlConnector.getConnection()
                    .prepareStatement("UPDATE phytSql SET coin=? WHERE uuid=?");
            statement.setInt(1, (PhytPlayer.getPlayerCoin(player) + val));
            statement.setString(2, player.getUniqueId().toString());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void removeCoin(Player player, int val) {
        try {
            PreparedStatement statement = MySqlConnector.getConnection()
                    .prepareStatement("UPDATE phytSql SET coin=? WHERE uuid=?");
            statement.setInt(1, (PhytPlayer.getPlayerCoin(player) - val));
            statement.setString(2, player.getUniqueId().toString());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void addBalance(Player player, int val) {
        try {
            PreparedStatement statement = MySqlConnector.getConnection()
                    .prepareStatement("UPDATE phytSql SET balance=? WHERE uuid=?");
            statement.setInt(1, (PhytPlayer.getPlayerBalance(player) + val));
            statement.setString(2, player.getUniqueId().toString());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void removeBalance(Player player, int val) {
        try {
            PreparedStatement statement = MySqlConnector.getConnection()
                    .prepareStatement("UPDATE phytSql SET balance=? WHERE uuid=?");
            statement.setInt(1, (PhytPlayer.getPlayerBalance(player) - val));
            statement.setString(2, player.getUniqueId().toString());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


}
