package fr.cocoraid.playersimulator;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public final class Listeners implements Listener {
    private final BotManager manager;

    Listeners(BotManager manager) { this.manager = manager; }

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        Player p = e.getEntity();
        if (!manager.isBot(p) || !Main.autoRespawn) return;
        Bukkit.getScheduler().runTaskLater(Main.get(), () -> {
            if (p.isOnline() && p.isDead()) p.spigot().respawn();
        }, 20L);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        if (!manager.isBot(p)) return;

        Bukkit.getScheduler().runTask(Main.get(), () -> {
            if (p.isOnline() && p.isDead()) p.spigot().respawn();
        });
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        manager.forget(e.getPlayer().getUniqueId());
    }
}
