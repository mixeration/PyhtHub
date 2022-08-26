package eu.mixeration.phythub.commands;

import eu.mixeration.phythub.PhytHub;
import eu.mixeration.phythub.config.PluginConfig;
import eu.mixeration.phythub.mysql.manager.PhytData;
import eu.mixeration.phythub.utils.PhytString;
import jdk.tools.jlink.plugin.Plugin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PhytHubManager implements CommandExecutor {
    public PhytString phytString = new PhytString();
    public PhytData phytData = new PhytData();

    public PhytHubManager(PhytHub phytHub) {}

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player) {
            Player player = ((Player) sender).getPlayer();
            if(player.hasPermission("phythub.administrator") || player.isOp()) {
                if(args.length == 0 || args[0].equalsIgnoreCase("help")) {
                    phytString.sendHelp(player, "management");
                } else if (args.length == 1) {
                    if(args[0].equalsIgnoreCase("reload")) {
                        PluginConfig.reloadConfig();
                        PluginConfig.saveConfig();
                        phytString.send(player, "plugin-reloaded");
                    } else if (args[0].equalsIgnoreCase("setspawn")) {
                        phytData.setLocation(player, player.getWorld().getName(), player.getLocation().getX(), player.getLocation().getY(), player.getLocation().getZ(), player.getLocation().getYaw(), player.getLocation().getPitch());
                    } else if (args[0].equalsIgnoreCase("spawn")) {
                        phytData.teleportToSpawn(player, phytData.getWorld(PluginConfig.getConfig().getString("phythub.main-spawn")));
                    } else {
                        phytString.sendHelp(player, "management");
                    }
                } else {
                    phytString.sendHelp(player, "management");
                }
            } else {
                phytString.send(player, "no-permission");
            }
        } else {
            phytString.console(phytString.get("phythub.messages.console"));
        }

        return false;
    }
}
