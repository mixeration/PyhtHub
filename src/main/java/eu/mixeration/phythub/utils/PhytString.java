package eu.mixeration.phythub.utils;

import eu.mixeration.phythub.config.PluginConfig;
import eu.mixeration.phythub.interfaces.PhytMessenger;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class PhytString implements PhytMessenger {

    public String getPrefix(){return ChatColor.translateAlternateColorCodes('&', PluginConfig.getConfig().getString("phythub.prefix"));}

    @Override
    public void send(Player player, String message) {
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', get("phythub.messages." + message)).replace("<prefix>", getPrefix()));
    }

    @Override
    public void sendHelp(Player player, String message) {
        for(String help : PluginConfig.getConfig().getStringList("phythub.messages.helps." + message)) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', help));
        }
    }

    @Override
    public void console(String message) {
        Bukkit.getLogger().warning("[@PhytHub]: " + message);
    }
    @Override
    public String get(String path) {return PluginConfig.getConfig().getString(path);}
}
