package eu.mixeration.phythub.events;

import eu.mixeration.phythub.PhytHub;
import eu.mixeration.phythub.data.PhytHubUserDataHolder;
import eu.mixeration.phythub.mysql.manager.PhytData;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import static eu.mixeration.phythub.PhytHub.isSQL;
import static eu.mixeration.phythub.mysql.manager.PhytSQL.createPlayer;

public class YAMLUserDataHolder implements Listener {
    public YAMLUserDataHolder(PhytHub phytHub) {}

    @Deprecated
    @EventHandler
    public void  registerNewUser(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (isSQL()) {
            createPlayer(player.getUniqueId(), player);
        } else {
            PhytHubUserDataHolder userData = new PhytHubUserDataHolder(PhytHub.get(), "user-data");
            userData.create(player);
        }
    }

}
