package fr.cocoraid.playersimulator.gui;

import fr.cocoraid.playersimulator.Bot;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public abstract class BotMenu implements InventoryHolder {
    protected final Bot bot;
    protected final Inventory inv;

    protected BotMenu(Bot bot, int size, String title) {
        this.bot = bot;
        this.inv = Bukkit.createInventory(this, size, Component.text(title));
    }

    public Bot getBot() { return bot; }

    @Override
    public Inventory getInventory() { return inv; }

    public void open(Player viewer) {
        update();
        viewer.openInventory(inv);
    }

    protected void update() { }

    public abstract void onClick(InventoryClickEvent e);
}
