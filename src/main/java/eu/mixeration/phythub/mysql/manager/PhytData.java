package eu.mixeration.phythub.mysql.manager;

import eu.mixeration.phythub.PhytHub;
import eu.mixeration.phythub.mysql.MySqlConnector;
import eu.mixeration.phythub.utils.PhytString;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class PhytData {

    public static MySqlConnector mySqlConnector = new MySqlConnector(PhytHub.get());
    public PhytString phytString = new PhytString();

    public String getWorld(String world) {
        try {
            PreparedStatement statement = mySqlConnector.getConnection()
                    .prepareStatement("SELECT * FROM phytData WHERE world=?");
            statement.setString(1, world);
            ResultSet results = statement.executeQuery();
            results.next();

            return (results.getString("world"));
        } catch (SQLException e) {
            e.printStackTrace();
            return "unknow";
        }
    }

    public double getX(String world) {
        try {
            PreparedStatement statement = mySqlConnector.getConnection()
                    .prepareStatement("SELECT * FROM phytData WHERE world=?");
            statement.setString(1, world);
            ResultSet results = statement.executeQuery();
            results.next();

            return (results.getDouble("x"));
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public double getY(String world) {
        try {
            PreparedStatement statement = mySqlConnector.getConnection()
                    .prepareStatement("SELECT * FROM phytData WHERE world=?");
            statement.setString(1, world);
            ResultSet results = statement.executeQuery();
            results.next();

            return (results.getDouble("y"));
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public double getZ(String world) {
        try {
            PreparedStatement statement = mySqlConnector.getConnection()
                    .prepareStatement("SELECT * FROM phytData WHERE world=?");
            statement.setString(1, world);
            ResultSet results = statement.executeQuery();
            results.next();

            return (results.getDouble("z"));
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public float getYaw(String world) {
        try {
            PreparedStatement statement = mySqlConnector.getConnection()
                    .prepareStatement("SELECT * FROM phytData WHERE world=?");
            statement.setString(1, world);
            ResultSet results = statement.executeQuery();
            results.next();

            return (results.getFloat("yaw"));
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public float getPitch(String world) {
        try {
            PreparedStatement statement = mySqlConnector.getConnection()
                    .prepareStatement("SELECT * FROM phytData WHERE world=?");
            statement.setString(1, world);
            ResultSet results = statement.executeQuery();
            results.next();

            return (results.getFloat("pitch"));
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public boolean locExists(String world) {
        try {
            PreparedStatement statement = MySqlConnector.getConnection()
                    .prepareStatement("SELECT * FROM phytData WHERE world=?");
            statement.setString(1, world);

            ResultSet results = statement.executeQuery();
            if (results.next()) {
                Bukkit.getLogger().warning("[@PhytHub] Unknow player data.");
                return true;
            }
            Bukkit.getLogger().warning("[@PhytHub] Unknow player data.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void setLocation(Player player, final String world, double x, double y, double z, float yaw, float pitch) {
        try {
            PreparedStatement statement = MySqlConnector.getConnection()
                    .prepareStatement("SELECT * FROM phytData WHERE world=?");
            statement.setString(1, world);
            ResultSet results = statement.executeQuery();
            results.next();
            System.out.print(1);
            if (locExists(world) != true) {
                PreparedStatement insert = MySqlConnector.getConnection()
                        .prepareStatement("INSERT INTO phytData (world,x,y,z,yaw,pitch) VALUES (?,?,?,?,?,?)");
                insert.setString(1, world);
                insert.setDouble(2, x);
                insert.setDouble(3, y);
                insert.setDouble(4, z);
                insert.setFloat(5, yaw);
                insert.setFloat(6, pitch);
                insert.executeUpdate();
                phytString.send(player, "spawn-selected");
            } else {
                phytString.send(player, "spawn-already-selected");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void teleportToSpawn(Player player, String world) {
        if(locExists(world)) {
            String worldName = getWorld(world);
            World spawnWorld = Bukkit.getServer().getWorld(worldName);
            double x = getX(world);
            double y = getY(world);
            double z = getZ(world);
            float yaw = getYaw(world);
            float pitch = getPitch(world);
            Location loc = new Location(spawnWorld, x, y, z);
            loc.setYaw(yaw);
            player.getLocation().setPitch(pitch);
            player.teleport(loc);
        } else {
            phytString.send(player, "spawn-not-found");
        }
    }

}
