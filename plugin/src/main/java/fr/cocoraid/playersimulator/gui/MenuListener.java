package fr.cocoraid.playersimulator.gui;

import fr.cocoraid.playersimulator.Main;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;

public final class MenuListener implements Listener {
    public MenuListener(Main plugin) { }

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        if (e.getView().getTopInventory().getHolder() instanceof BotMenu menu) {
            if (e.getClickedInventory() != e.getView().getTopInventory()) {
                if (e.isShiftClick()) e.setCancelled(true);
                return;
            }
            menu.onClick(e);
        }
    }

    @EventHandler
    public void onDrag(InventoryDragEvent e) {
        if (e.getView().getTopInventory().getHolder() instanceof BotMenu) {
            int top = e.getView().getTopInventory().getSize();
            if (e.getRawSlots().stream().anyMatch(s -> s < top)) e.setCancelled(true);
        }
    }
}
