package fr.cocoraid.playersimulator.gui;

import fr.cocoraid.playersimulator.Bot;
import fr.cocoraid.playersimulator.Main;
import fr.cocoraid.playersimulator.util.Items;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public final class InvMenu extends BotMenu {
    private static final int HELMET = 0, CHEST = 1, LEGS = 2, BOOTS = 3, MAIN = 5, OFF = 6;
    private static final int[] EQUIP = {HELMET, CHEST, LEGS, BOOTS, MAIN, OFF};

    public InvMenu(Bot bot) {
        super(bot, 9, "Inventory Contents - " + bot.getName());
    }

    private static boolean isEquipSlot(int slot) {
        for (int s : EQUIP) if (s == slot) return true;
        return false;
    }

    @Override
    protected void update() {
        ItemStack filler = Items.named(Material.GRAY_STAINED_GLASS_PANE, " ");
        for (int i = 0; i < inv.getSize(); i++) if (!isEquipSlot(i)) inv.setItem(i, filler);
        PlayerInventory pi = bot.getBot().getInventory();
        inv.setItem(HELMET, pi.getHelmet());
        inv.setItem(CHEST, pi.getChestplate());
        inv.setItem(LEGS, pi.getLeggings());
        inv.setItem(BOOTS, pi.getBoots());
        inv.setItem(MAIN, pi.getItemInMainHand());
        inv.setItem(OFF, pi.getItemInOffHand());
    }

    @Override
    public void onClick(InventoryClickEvent e) {
        if (!isEquipSlot(e.getSlot()) || e.isShiftClick()) {
            e.setCancelled(true);
            return;
        }

        Bukkit.getScheduler().runTask(Main.get(), this::apply);
    }

    private void apply() {
        Player p = bot.getBot();
        if (!p.isOnline()) return;
        PlayerInventory pi = p.getInventory();
        pi.setHelmet(inv.getItem(HELMET));
        pi.setChestplate(inv.getItem(CHEST));
        pi.setLeggings(inv.getItem(LEGS));
        pi.setBoots(inv.getItem(BOOTS));
        pi.setItemInMainHand(inv.getItem(MAIN));
        pi.setItemInOffHand(inv.getItem(OFF));
    }
}
