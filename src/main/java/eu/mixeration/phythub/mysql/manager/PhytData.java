package eu.mixeration.phythub.mysql.manager;

import eu.mixeration.phythub.PhytHub;
import eu.mixeration.phythub.config.PluginConfig;
import eu.mixeration.phythub.data.PhytHubDataHolder;
import eu.mixeration.phythub.mysql.MySqlConnector;
import eu.mixeration.phythub.utils.PhytString;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static eu.mixeration.phythub.PhytHub.isSQL;

public class PhytData {

    public static MySqlConnector mySqlConnector = new MySqlConnector(PhytHub.get());
    public PhytString phytString = new PhytString();

    public String getWorld(String world) {
        if(isSQL()) {
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
        } else {
            return PhytHubDataHolder.getConfig().getString("phythub.spawn.world");
        }
    }

    public int getPhytID(String world) {
        if(isSQL()) {
            try {
                PreparedStatement statement = mySqlConnector.getConnection()
                        .prepareStatement("SELECT * FROM phytData WHERE world=?");
                statement.setString(1, world);
                ResultSet results = statement.executeQuery();
                results.next();

                return (results.getInt("phytid"));
            } catch (SQLException e) {
                e.printStackTrace();
                return 0;
            }
        } else {
            return PhytHubDataHolder.getConfig().getInt("phythub.phytID$");
        }
    }

    public double getX(String world) {
        if(isSQL()) {
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
        } else {
            return PhytHubDataHolder.getConfig().getDouble("phythub.spawn.x");
        }
    }

    public double getY(String world) {
        if(isSQL()) {
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
        } else {
            return PhytHubDataHolder.getConfig().getDouble("phythub.spawn.y");
        }
    }

    public double getZ(String world) {
        if(isSQL()) {
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
        } else {
            return PhytHubDataHolder.getConfig().getDouble("phythub.spawn.z");
        }
    }

    public float getYaw(String world) {
        if(isSQL()) {
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
        } else {
            return PhytHubDataHolder.getConfig().getLong("phythub.spawn.yaw");
        }
    }

    public float getPitch(String world) {
        if(isSQL()) {
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
        } else {
            return PhytHubDataHolder.getConfig().getLong("phythub.spawn.pitch");
        }
    }

    public boolean locExists(String world) {
        if(isSQL()) {
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
        } else {
            if (PhytHubDataHolder.getConfig().getString("phythub.spawn") == null) {
                return false;
            } else {
                return true;
            }
        }
    }

    public void setLocation(Player player, final String world, double x, double y, double z, float yaw, float pitch) {
        if(isSQL()) {
            try {
                PreparedStatement statement = MySqlConnector.getConnection()
                        .prepareStatement("SELECT * FROM phytData WHERE world=?");
                statement.setString(1, world);
                ResultSet results = statement.executeQuery();
                results.next();
                System.out.print(1);
                if (locExists(world) != true) {
                    PreparedStatement insert = MySqlConnector.getConnection()
                            .prepareStatement("INSERT INTO phytData (world,x,y,z,yaw,pitch,phytid) VALUES (?,?,?,?,?,?,?)");
                    insert.setString(1, world);
                    insert.setDouble(2, x);
                    insert.setDouble(3, y);
                    insert.setDouble(4, z);
                    insert.setFloat(5, yaw);
                    insert.setFloat(6, pitch);
                    insert.setInt(7, 0);
                    insert.executeUpdate();
                    phytString.send(player, "spawn-selected");
                    PluginConfig.getConfig().set("phythub.main-spawn", world);
                    PluginConfig.saveConfig();
                } else {
                    phytString.send(player, "spawn-already-selected");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            if(PhytHubDataHolder.getConfig().getString("phythub.spawn") == null) {
                PhytHubDataHolder.getConfig().set("phythub.spawn.world", world);
                PhytHubDataHolder.getConfig().set("phythub.spawn.x", x);
                PhytHubDataHolder.getConfig().set("phythub.spawn.y", y);
                PhytHubDataHolder.getConfig().set("phythub.spawn.z", z);
                PhytHubDataHolder.getConfig().set("phythub.spawn.pitch", pitch);
                PhytHubDataHolder.getConfig().set("phythub.spawn.yaw", yaw);
                PhytHubDataHolder.saveConfig();
                phytString.send(player, "spawn-selected");
                PluginConfig.getConfig().set("phythub.main-spawn", world);
                PluginConfig.saveConfig();
            } else {
                phytString.send(player, "spawn-already-selected");
            }
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
            loc.setPitch(pitch);
            player.teleport(loc);
        } else {
            phytString.send(player, "spawn-not-found");
        }
    }

}
