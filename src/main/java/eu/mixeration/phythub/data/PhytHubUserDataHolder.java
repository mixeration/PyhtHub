package eu.mixeration.phythub.data;

import eu.mixeration.phythub.PhytHub;
import eu.mixeration.phythub.utils.PhytID;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.*;

public class PhytHubUserDataHolder {
    private static PhytHub plugin;
    private static String path;
    private static String folderpath;
    private static File file;
    private static FileConfiguration config;

    public PhytHubUserDataHolder(PhytHub plugin, String folderpath) {
        this.plugin = plugin;
        this.path = path;
        file = null;
        config = null;
    }

    public static void create(Player player) {
        file = new File(plugin.getDataFolder() + File.separator + folderpath + File.separator, player.getName()+player.getUniqueId().toString()+".yml");
        if (!file.exists()) {
            getConfig(player).options().copyDefaults(true);
            getConfig(player).set("phythub.username", player.getName());
            getConfig(player).set("phythub.uuid", player.getUniqueId().toString());
            getConfig(player).set("phythub.phytID", "#"+player.getName()+";"+PhytID.addPhyt(player));
            getConfig(player).set("phythub.address", player.getAddress().getAddress().getHostAddress().toString());
            saveConfig(player);
        }

    }

    public static FileConfiguration getConfig(Player player) {
        if (config == null) {
            reloadConfig(player);
        }

        return config;
    }

    public static void reloadConfig(Player player) {
        if (config == null) {
            file = new File(plugin.getDataFolder() + File.separator + folderpath + File.separator, player.getName()+player.getUniqueId().toString()+".yml");

        }

        config = YamlConfiguration.loadConfiguration(file);

        try {
            Reader defaultConfigStream = new InputStreamReader(plugin.getResource(path), "UTF8");
            if (defaultConfigStream != null) {
                YamlConfiguration defaultConfig = YamlConfiguration.loadConfiguration(defaultConfigStream);
                config.setDefaults(defaultConfig);
            }
        } catch (UnsupportedEncodingException var2) {
            var2.printStackTrace();
        } catch (NullPointerException var3) {
            var3.printStackTrace();
        }

    }

    public static void saveConfig(Player player) {
        file = new File(plugin.getDataFolder() + File.separator + folderpath + File.separator, player.getName()+player.getUniqueId().toString()+".yml");
        try {
            config.save(file);
        } catch (IOException var1) {
            var1.printStackTrace();
        }

    }

    public void saveDefaultConfig(Player player) {
        if (file == null) {
            file = new File(plugin.getDataFolder() + File.separator + folderpath + File.separator, player.getName()+player.getUniqueId().toString()+".yml");

        }

        if (!file.exists()) {
            plugin.saveResource(path, false);
        }

    }

    public String getPath() {
        return path;
    }

    public String getFolderpath() {
        return folderpath;
    }
}