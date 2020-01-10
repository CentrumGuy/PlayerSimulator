package com.CentrumGuy.PlayerSimulator.GUI;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.CentrumGuy.PlayerSimulator.Bot;
import com.CentrumGuy.PlayerSimulator.Utils.Items;

import net.minecraft.server.v1_12_R1.EnumItemSlot;
import net.minecraft.server.v1_12_R1.PacketPlayOutEntityEquipment;

public class InvMenu extends BotMenu {
	public static ItemStack helmet = Items.createItem(Material.LEATHER_HELMET, 1, 0, "§a§lHelmet", "§7Place below");
	public static ItemStack chestplate = Items.createItem(Material.LEATHER_CHESTPLATE, 1, 0, "§a§lChestplate", "§7Place below");
	public static ItemStack leggings = Items.createItem(Material.LEATHER_LEGGINGS, 1, 0, "§a§lLeggings", "§7Place below");
	public static ItemStack boots = Items.createItem(Material.LEATHER_BOOTS, 1, 0, "§a§lBoots", "§7Place below");
	public static ItemStack invTitle = Items.createItem(Material.CHEST, 1, 0, "§a§lInventory", "§7Fill inventory below");
	private Inventory inv;
	
	public InvMenu(Bot bot) {
		super(MenuType.INVENTORY, bot);
		this.inv = Bukkit.createInventory(bot.getBot(), 54, "Inventory Contents - " + bot.getBot().getName());
		
		update();
	}

	@Override
	public Inventory getInv() {
		return this.inv;
	}
	
	public void update() {
		this.inv.setItem(1, helmet);
		this.inv.setItem(10, this.getBot().getBot().getInventory().getHelmet());
		this.inv.setItem(3, chestplate);
		this.inv.setItem(12, this.getBot().getBot().getInventory().getChestplate());
		this.inv.setItem(5, leggings);
		this.inv.setItem(14, this.getBot().getBot().getInventory().getLeggings());
		this.inv.setItem(7, boots);
		this.inv.setItem(16, this.getBot().getBot().getInventory().getBoots());
		this.inv.setItem(13, invTitle);
		
		for (int i = 18 ; i < 54 ; i++) {
			this.inv.setItem(i, this.getBot().getBot().getInventory().getItem(i - 18));
		}
	}

	@Override
	public void open(Player p) {
		update();
		p.openInventory(getInv());
	}
	
	@EventHandler
	public void onInteract(InventoryClickEvent e) {
		if (!e.getInventory().equals(this.inv)) return;
		
		if (e.getRawSlot() == 10) {
			this.getBot().getBot().getInventory().setHelmet(e.getCursor());
			
			for (Player p : Bukkit.getOnlinePlayers()) {
				PacketPlayOutEntityEquipment helmet = new PacketPlayOutEntityEquipment(this.getBot().getBot().getEntityId(), EnumItemSlot.HEAD, CraftItemStack.asNMSCopy(this.getBot().getBot().getInventory().getHelmet()));
				((CraftPlayer) p).getHandle().playerConnection.sendPacket(helmet);
			}
		} else if (e.getRawSlot() == 12) {
			this.getBot().getBot().getInventory().setChestplate(e.getCursor());
			
			for (Player p : Bukkit.getOnlinePlayers()) {
				PacketPlayOutEntityEquipment chest = new PacketPlayOutEntityEquipment(this.getBot().getBot().getEntityId(), EnumItemSlot.CHEST, CraftItemStack.asNMSCopy(this.getBot().getBot().getInventory().getChestplate()));
				((CraftPlayer) p).getHandle().playerConnection.sendPacket(chest);
			}
		} else if (e.getRawSlot() == 14) {
			this.getBot().getBot().getInventory().setLeggings(e.getCursor());

			for (Player p : Bukkit.getOnlinePlayers()) {
				PacketPlayOutEntityEquipment pants = new PacketPlayOutEntityEquipment(this.getBot().getBot().getEntityId(), EnumItemSlot.LEGS, CraftItemStack.asNMSCopy(this.getBot().getBot().getInventory().getLeggings()));
				((CraftPlayer) p).getHandle().playerConnection.sendPacket(pants);
			}
		} else if (e.getRawSlot() == 16) {
			this.getBot().getBot().getInventory().setBoots(e.getCursor());
			
			for (Player p : Bukkit.getOnlinePlayers()) {
				PacketPlayOutEntityEquipment boots = new PacketPlayOutEntityEquipment(this.getBot().getBot().getEntityId(), EnumItemSlot.FEET, CraftItemStack.asNMSCopy(this.getBot().getBot().getInventory().getBoots()));
				((CraftPlayer) p).getHandle().playerConnection.sendPacket(boots);
			}
		} else if (e.getRawSlot() <= 17) {
			e.setCancelled(true);
		} else if (e.getRawSlot() > 17 && e.getRawSlot() < 54) {
			this.getBot().getBot().getInventory().setItem(e.getRawSlot() - 18, e.getCursor());
			
			for (Player p : Bukkit.getOnlinePlayers()) {
				PacketPlayOutEntityEquipment mainH = new PacketPlayOutEntityEquipment(this.getBot().getBot().getEntityId(), EnumItemSlot.MAINHAND, CraftItemStack.asNMSCopy(this.getBot().getBot().getInventory().getItemInMainHand()));
				((CraftPlayer) p).getHandle().playerConnection.sendPacket(mainH);
				
				PacketPlayOutEntityEquipment offH = new PacketPlayOutEntityEquipment(this.getBot().getBot().getEntityId(), EnumItemSlot.OFFHAND, CraftItemStack.asNMSCopy(this.getBot().getBot().getInventory().getItemInOffHand()));
				((CraftPlayer) p).getHandle().playerConnection.sendPacket(offH);
			}
		}
	}
}
