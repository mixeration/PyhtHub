package eu.mixeration.phythub.interfaces;

import org.bukkit.entity.Player;

public interface PhytMessenger {

    void send(Player player, String message);
    void sendHelp(Player player, String message);

    void console(String message);

    String get(String path);

}
