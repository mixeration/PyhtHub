package eu.mixeration.phythub.events;

import eu.mixeration.phythub.PhytHub;
import eu.mixeration.phythub.config.PluginConfig;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.util.Iterator;

public class SimpleCommandCreator implements Listener {
    public SimpleCommandCreator(PhytHub phytHub) {}

    @EventHandler(
            priority = EventPriority.LOWEST
    )
    public void playerCustomCommandEvent(PlayerCommandPreprocessEvent e) {
        Player getUser = e.getPlayer();
        String cmd = e.getMessage().split(" ")[0];
        for (String customCommand : PluginConfig.getConfig().getConfigurationSection("phythub.$commands").getKeys(false)) {
            String mode = PluginConfig.getConfig().getString("phythub.$commands." + customCommand + ".mode");
            String command = PluginConfig.getConfig().getString("phythub.$commands." + customCommand + ".command");
            String permission = PluginConfig.getConfig().getString("phythub.$commands." + customCommand + ".permission");
            boolean askPermission = PluginConfig.getConfig().getBoolean("phythub.$commands." + customCommand + ".ask-permission");
            if (cmd.equalsIgnoreCase("/" + command)) {
                Iterator var10;
                String commands;
                if (mode.equalsIgnoreCase("send-message")) {
                    if (!askPermission) {
                        e.setCancelled(true);
                        var10 = PluginConfig.getConfig().getStringList("phythub.$commands." + customCommand + ".message").iterator();

                        while (var10.hasNext()) {
                            commands = (String) var10.next();
                            getUser.sendMessage(ChatColor.translateAlternateColorCodes('&', commands));
                        }

                        return;
                    }

                    if (getUser.hasPermission(permission)) {
                        e.setCancelled(true);
                        var10 = PluginConfig.getConfig().getStringList("phythub.$commands." + customCommand + ".message").iterator();

                        while (var10.hasNext()) {
                            commands = (String) var10.next();
                            getUser.sendMessage(ChatColor.translateAlternateColorCodes('&', commands));
                        }

                        return;
                    }
                } else if (mode.equalsIgnoreCase("run-command")) {
                    if (!askPermission) {
                        e.setCancelled(true);
                        var10 = PluginConfig.getConfig().getStringList("phythub.$commands." + customCommand + ".commands").iterator();

                        while (var10.hasNext()) {
                            commands = (String) var10.next();
                            Bukkit.getServer().dispatchCommand(getUser.getPlayer(), commands.replace("%fabledplayer_name%", getUser.getName()));
                        }

                        return;
                    }

                    if (getUser.hasPermission(permission)) {
                        e.setCancelled(true);
                        var10 = PluginConfig.getConfig().getStringList("phythub.$commands." + customCommand + ".commands").iterator();

                        while (var10.hasNext()) {
                            commands = (String) var10.next();
                            Bukkit.getServer().dispatchCommand(getUser.getPlayer(), commands.replace("%fabledplayer_name%", getUser.getName()));
                        }

                        return;
                    }
                }
            }
        }

    }
}