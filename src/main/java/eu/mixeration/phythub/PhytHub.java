package eu.mixeration.phythub;

import eu.mixeration.phythub.commands.PhytHubManager;
import eu.mixeration.phythub.config.PluginConfig;
import eu.mixeration.phythub.events.PlayerToolsLoader;
import eu.mixeration.phythub.events.SimpleCommandCreator;
import eu.mixeration.phythub.mysql.MySqlConnector;
import eu.mixeration.phythub.mysql.manager.PhytSQL;
import eu.mixeration.phythub.utils.PhytServer;
import eu.mixeration.phythub.utils.PhytString;
import eu.mixeration.phythub.utils.tools.PlayerVision;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class PhytHub extends JavaPlugin {
    public static PhytHub phytHub;
    public synchronized static PhytHub get(){return phytHub;}
    public void setPhytHub(PhytHub $phytHub) {phytHub=$phytHub;}
    public PluginConfig pluginConfig = new PluginConfig(this, "config.yml");
    public PluginManager pluginManager = Bukkit.getPluginManager();
    public MySqlConnector mySqlConnector = new MySqlConnector(this);
    public PhytServer phytServer = new PhytServer();

    void loader() {
        pluginConfig.create();
        getCommand("PhytHub").setExecutor(new PhytHubManager(this));
        pluginManager.registerEvents(new PlayerToolsLoader(this), this);
        pluginManager.registerEvents(new SimpleCommandCreator(this), this);
        pluginManager.registerEvents(new PlayerVision(this), this);


    }

    @Override
    public void onEnable() {
        setPhytHub(this);
        loader();
        mySqlConnector.setupSource();
        Bukkit.getLogger().warning("[@PhytServer]: Server version - " + phytServer.getServerVersion());

    }

    @Override
    public void onDisable() {
        Bukkit.getLogger().warning("[@PhytServer]: Saving users data into phytSql...");
        for(Player player : Bukkit.getOnlinePlayers()) {
            mySqlConnector.saveData(player);
            Bukkit.getLogger().warning(String.format("[@PhytServer]: Saved (%s) user data", Bukkit.getOnlinePlayers().size()));
        }
    }
}
