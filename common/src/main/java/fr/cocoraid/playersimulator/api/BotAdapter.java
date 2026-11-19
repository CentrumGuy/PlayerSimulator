package fr.cocoraid.playersimulator.api;

import org.bukkit.profile.PlayerProfile;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public interface BotAdapter {
    Player spawn(PlayerProfile profile, Location location) throws Exception;

    void disconnect(Player bot);
}
