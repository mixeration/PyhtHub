package eu.mixeration.phythub.events;

import eu.mixeration.phythub.PhytHub;
import eu.mixeration.phythub.config.PluginConfig;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class PlayerToolsLoader implements Listener {
    public PlayerToolsLoader(PhytHub phytHub) {}

    @EventHandler
    public void tools(PlayerJoinEvent event) {
        ItemStack playervision = new ItemStack(Material.getMaterial(PluginConfig.getConfig().getString("phythub.tools.player-visibility.material")));
        ItemMeta playerVisionMeta = playervision.getItemMeta();
        playerVisionMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', PluginConfig.getConfig().getString("phythub.tools.player-visibility.display-name")));
        List<String> lores = PluginConfig.getConfig().getStringList("phythub.tools.player-visibility.lore");
        for (int i = 0; i < lores.size(); ++i) {
            lores.set(i, ChatColor.translateAlternateColorCodes('&', lores.get(i)));
        }
        playerVisionMeta.setLore(lores);
        playervision.setItemMeta(playerVisionMeta);
        if(!event.getPlayer().getInventory().contains(playervision)) {
            event.getPlayer().getPlayer().getInventory().setItem(PluginConfig.getConfig().getInt("phythub.tools.player-visibility.slot"), playervision);
        }
    }

}
