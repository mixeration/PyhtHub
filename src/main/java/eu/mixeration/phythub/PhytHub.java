package eu.mixeration.phythub;

import eu.mixeration.phythub.commands.PhytHubManager;
import eu.mixeration.phythub.config.PluginConfig;
import eu.mixeration.phythub.data.PhytHubDataHolder;
import eu.mixeration.phythub.data.PhytHubUserDataHolder;
import eu.mixeration.phythub.events.PlayerToolsLoader;
import eu.mixeration.phythub.events.SimpleCommandCreator;
import eu.mixeration.phythub.events.YAMLUserDataHolder;
import eu.mixeration.phythub.mysql.MySqlConnector;
import eu.mixeration.phythub.utils.PhytServer;
import eu.mixeration.phythub.utils.tools.PlayerVision;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import static eu.mixeration.phythub.utils.PhytID.getPhytID;
import static eu.mixeration.phythub.utils.PhytID.loadPhytID;

public final class PhytHub extends JavaPlugin {
    private static boolean isSql;
    public static PhytHub phytHub;
    public synchronized static PhytHub get(){return phytHub;}
    public void setPhytHub(PhytHub $phytHub) {phytHub=$phytHub;}
    public PluginConfig pluginConfig = new PluginConfig(this, "config.yml");
    public PhytHubDataHolder yamlDataHolder = new PhytHubDataHolder(this, "phythub.yml", "storage");
    public PluginManager pluginManager = Bukkit.getPluginManager();
    public MySqlConnector mySqlConnector = new MySqlConnector(this);
    public PhytServer phytServer = new PhytServer();

    void loader() {
        pluginConfig.create();
        if(PluginConfig.getConfig().getString("phythub.storage").equals("yaml")) {
            isSql = false;
            yamlDataHolder.create();
        } else {
            isSql = true;
            mySqlConnector.setupSource();
        }
        loadPhytID();
        getCommand("PhytHub").setExecutor(new PhytHubManager(this));
        pluginManager.registerEvents(new YAMLUserDataHolder(this), this);
        pluginManager.registerEvents(new PlayerToolsLoader(this), this);
        pluginManager.registerEvents(new SimpleCommandCreator(this), this);
        pluginManager.registerEvents(new PlayerVision(this), this);


    }

    @Override
    public void onEnable() {
        setPhytHub(this);
        loader();
        Bukkit.getLogger().warning("[@PhytServer]: Server version - " + phytServer.getServerVersion());
        Bukkit.getLogger().warning("[@PhytServer]: Current phytID - " + getPhytID());

    }

    @Override
    public void onDisable() {
        if(isSQL()) {
            Bukkit.getLogger().warning("[@PhytServer]: Saving users data into phytSql...");
            for (Player player : Bukkit.getOnlinePlayers()) {
                mySqlConnector.saveData(player);
                Bukkit.getLogger().warning(String.format("[@PhytServer]: Saved (%s) user data", Bukkit.getOnlinePlayers().size()));
            }
        } else {
            Bukkit.getLogger().warning("[@PhytServer]: Saving users data into user-data.");
            for (Player player : Bukkit.getOnlinePlayers()) {
                PhytHubUserDataHolder.saveConfig(player);
                Bukkit.getLogger().warning(String.format("[@PhytServer]: Saved (%s) user data", Bukkit.getOnlinePlayers().size()));
            }
        }
    }

    public static boolean isSQL(){
        return isSql;
    }

}
