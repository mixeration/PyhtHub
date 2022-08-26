package eu.mixeration.phythub.utils;

import eu.mixeration.phythub.config.PluginConfig;

public class PhytStatus {

    public static String getStatus(String path) {
        return PluginConfig.getConfig().getString("phythub.status." +path);
    }

}
