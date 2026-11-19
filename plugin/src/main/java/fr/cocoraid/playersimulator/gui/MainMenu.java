package fr.cocoraid.playersimulator.gui;

import fr.cocoraid.playersimulator.Bot;
import fr.cocoraid.playersimulator.BotMenus;
import fr.cocoraid.playersimulator.util.Items;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

public final class MainMenu extends BotMenu {
    public MainMenu(Bot bot) {
        super(bot, 9, "Bot - " + bot.getName());
    }

    @Override
    protected void update() {
        inv.setItem(2, Items.named(Material.CHEST, "§a§lInventory"));
        inv.setItem(6, Items.named(Material.WOODEN_SWORD, "§a§lActions"));
    }

    @Override
    public void onClick(InventoryClickEvent e) {
        e.setCancelled(true);
        Player viewer = (Player) e.getWhoClicked();
        if (e.getSlot() == 2) BotMenus.inv(bot).open(viewer);
        else if (e.getSlot() == 6) BotMenus.actions(bot).open(viewer);
    }
}
