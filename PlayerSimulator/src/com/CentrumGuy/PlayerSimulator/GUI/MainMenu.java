package com.CentrumGuy.PlayerSimulator.GUI;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.CentrumGuy.PlayerSimulator.Bot;
import com.CentrumGuy.PlayerSimulator.Utils.Items;

public class MainMenu extends BotMenu {
	public static ItemStack inventory = Items.createItem(Material.IRON_HELMET, 1, 0, "§a§lInventory Contents");
	public static ItemStack actions = Items.createItem(Material.DIAMOND_SWORD, 1, 0, "§a§lActions");
	public static ItemStack attributes = Items.createItem(Material.NAME_TAG, 1, 0, "§a§lAttributes");
	private Inventory inv;
	
	public MainMenu(Bot bot) {
		super(MenuType.MAIN, bot);
		
		this.inv = Bukkit.createInventory(bot.getBot(), 54, "Main Menu - " + bot.getBot().getName());
		this.inv.setItem(19, inventory);
		this.inv.setItem(22, actions);
		this.inv.setItem(25, attributes);
		
		new InvMenu(bot);
		new ActionsMenu(bot);
	}

	@Override
	public Inventory getInv() {
		return this.inv;
	}

	@Override
	public void open(Player p) {
		p.openInventory(getInv());
	}
	
	@EventHandler
	public void onInteract(InventoryClickEvent e) {
		if (!(e.getWhoClicked() instanceof Player)) return;
		if (!e.getInventory().equals(this.inv)) return;
		e.setCancelled(true);
		
		if (e.getCurrentItem().equals(inventory)) {
			BotMenu.getInvMenu(this.getBot()).open((Player) e.getWhoClicked());
		} else if (e.getCurrentItem().equals(actions)) {
			BotMenu.getActionsMenu(this.getBot()).open((Player) e.getWhoClicked());
		} else if (e.getCurrentItem().equals(attributes)) {
			
		}
	}
}
