package eu.mixeration.phythub.mysql.manager;

import eu.mixeration.phythub.PhytHub;
import eu.mixeration.phythub.mysql.MySqlConnector;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class PhytPlayer {
    public static MySqlConnector mySqlConnector = new MySqlConnector(PhytHub.get());

    public static int getPlayerBalance(Player player) {
        UUID uuid = player.getUniqueId();
        try {
            PreparedStatement statement = mySqlConnector.getConnection()
                    .prepareStatement("SELECT * FROM phytSql WHERE uuid=?");
            statement.setString(1, uuid.toString());
            ResultSet results = statement.executeQuery();
            results.next();

            return (results.getInt("balance"));
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static int getPlayedMatches(Player player) {
        UUID uuid = player.getUniqueId();
        try {
            PreparedStatement statement = mySqlConnector.getConnection()
                    .prepareStatement("SELECT * FROM phytSql WHERE uuid=?");
            statement.setString(1, uuid.toString());
            ResultSet results = statement.executeQuery();
            results.next();

            return (results.getInt("playedmatches"));
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static int getPlayerCoin(Player player) {
        UUID uuid = player.getUniqueId();
        try {
            PreparedStatement statement = mySqlConnector.getConnection()
                    .prepareStatement("SELECT * FROM phytSql WHERE uuid=?");
            statement.setString(1, uuid.toString());
            ResultSet results = statement.executeQuery();
            results.next();

            return (results.getInt("coin"));
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

}
