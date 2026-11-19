package fr.cocoraid.playersimulator.gui;

import fr.cocoraid.playersimulator.Bot;
import fr.cocoraid.playersimulator.util.Items;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

public final class ActionsMenu extends BotMenu {
    public ActionsMenu(Bot bot) {
        super(bot, 9, "Actions - " + bot.getName());
    }

    @Override
    protected void update() {
        inv.setItem(1, Items.named(Material.WOODEN_SWORD, "§a§lLeft Click"));
        inv.setItem(2, Items.named(Material.WOODEN_HOE, "§a§lRight Click"));
        inv.setItem(4, Items.named(Material.FEATHER, "§a§lJump"));
        inv.setItem(6, Items.named(Material.PLAYER_HEAD, "§a§lRespawn"));
        inv.setItem(7, Items.named(Material.BARRIER, "§a§lDisconnect"));
    }

    @Override
    public void onClick(InventoryClickEvent e) {
        e.setCancelled(true);
        Player viewer = (Player) e.getWhoClicked();
        switch (e.getSlot()) {
            case 1 -> bot.leftClick();
            case 2 -> bot.rightClick();
            case 4 -> bot.jump();
            case 6 -> bot.respawn();
            case 7 -> { viewer.closeInventory(); bot.disconnect(); }
            default -> { }
        }
    }
}
