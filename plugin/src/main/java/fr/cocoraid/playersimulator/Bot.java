package fr.cocoraid.playersimulator;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.util.RayTraceResult;

public final class Bot {
    private final Player player;
    private final String name;

    Bot(Player player) {
        this.player = player;
        this.name = player.getName();
    }

    public Player getBot() { return player; }
    public String getName() { return name; }
    public boolean isOnline() { return player.isOnline(); }

    public void leftClick() {
        player.swingMainHand();
        RayTraceResult r = player.getWorld().rayTraceEntities(
                player.getEyeLocation(), player.getEyeLocation().getDirection(), 3.0, 0.1, e -> !e.equals(player));
        if (r != null && r.getHitEntity() != null) {
            Entity target = r.getHitEntity();
            player.attack(target);
        }
    }

    public void rightClick() {
        player.swingOffHand();
    }

    public void jump() {
        player.setVelocity(player.getVelocity().setY(0.42));
    }

    public void respawn() {
        if (player.isDead()) player.spigot().respawn();
    }

    public void disconnect() {
        Main.get().getManager().disconnect(this);
    }
}
