package eu.mixeration.phythub.utils.tools;

import eu.mixeration.phythub.PhytHub;
import eu.mixeration.phythub.config.PluginConfig;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.HashMap;
import java.util.UUID;

public class PlayerVision implements Listener {
    public HashMap<UUID, Boolean> canSee = new HashMap<>();
    public PlayerVision(PhytHub phytHub) {}

    @EventHandler
    public void visionItemClickEvent(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if(event.getAction() == Action.RIGHT_CLICK_AIR && event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if(event.getItem().getType() == Material.getMaterial(PluginConfig.getConfig().getString("phythub.tools.player-visibility.material"))) {
                if(event.getItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.translateAlternateColorCodes('&',PluginConfig.getConfig().getString("phythub.tools.player-visibility.display-name")))) {
                    if(canSee.get(player.getUniqueId()) == null && !canSee.get(player.getUniqueId())) {
                        canSee.put(player.getUniqueId(), true);
                        for(Player onlines : Bukkit.getOnlinePlayers()) {
                            player.canSee(onlines);
                            onlines.canSee(player);
                        }
                    } else {
                        canSee.put(player.getUniqueId(), false);
                        for(Player onlines : Bukkit.getOnlinePlayers()) {
                            player.canSee(onlines);
                            onlines.canSee(player);
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void visionDropEvent(PlayerDropItemEvent event) {
        Player player = event.getPlayer();
        if (event.getItemDrop().getItemStack().getType() == Material.getMaterial(PluginConfig.getConfig().getString("phythub.tools.player-visibility.material"))) {
            if(event.getItemDrop().getItemStack().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.translateAlternateColorCodes('&',PluginConfig.getConfig().getString("phythub.tools.player-visibility.display-name")))) {
                event.setCancelled(true);
            } else {
                return;
            }
        } else {
            return;
        }
    }

}
