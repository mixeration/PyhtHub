package eu.mixeration.phythub.utils;

import eu.mixeration.phythub.config.PluginConfig;
import eu.mixeration.phythub.mysql.manager.PhytData;
import org.bukkit.entity.Player;

public class PhytID {
    public static PhytData phytData = new PhytData();

    static int ID = 0;
    static String lastPID;

    public static void loadPhytID(){
        ID = phytData.getPhytID(PluginConfig.getConfig().getString("phythub.main-spawn"));
    }

    public static int getPhytID(){
        return ID;
    }

    public static String getLastPID(){
        return lastPID;
    }

    public static int addPhyt(Player player) {
        lastPID = player.getName();
        return ID++;
    }


}
