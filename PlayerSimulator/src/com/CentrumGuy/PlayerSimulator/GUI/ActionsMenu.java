package com.CentrumGuy.PlayerSimulator.GUI;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import com.CentrumGuy.PlayerSimulator.Bot;
import com.CentrumGuy.PlayerSimulator.Utils.Items;

public class ActionsMenu extends BotMenu {
	public static ItemStack left_click = Items.createItem(Material.WOOD_SWORD, 1, 0, "§a§lLeft Click");
	public static ItemStack right_click = Items.createItem(Material.WOOD_HOE, 1, 0, "§a§lRight Click");
	public static ItemStack jump = Items.createItem(Material.FEATHER, 1, 0, "§a§lJump");
	public static ItemStack respawn = Items.createItem(Material.SKULL_ITEM, 1, 0, "§a§lRespawn");
	public static ItemStack disconnect = Items.createItem(Material.BARRIER, 1, 0, "§a§lDisconnect");
	private Inventory inv;
	
	public ActionsMenu(Bot bot) {
		super(MenuType.ACTIONS, bot);
		this.inv = Bukkit.createInventory(bot.getBot(), 54, "Actions - " + bot.getBot().getName());
		
		update();
	}

	@Override
	public Inventory getInv() {
		return this.inv;
	}
	
	public void update() {
		this.inv.setItem(19, jump);
		this.inv.setItem(22, respawn);
		this.inv.setItem(25, disconnect);
		this.inv.setItem(38, left_click);
		this.inv.setItem(42, right_click);
	}

	@Override
	public void open(Player p) {
		p.openInventory(getInv());
	}
	
	@EventHandler
	public void onInteract(InventoryClickEvent e) {
		if (!e.getInventory().equals(this.inv)) return;
		e.setCancelled(true);
		
		if (e.getCurrentItem() == null) return;
		
		if (e.getCurrentItem().equals(jump)) {
			this.getBot().getBot().setVelocity(new Vector(0, 1, 0));
		}
	}
}
