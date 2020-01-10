package com.CentrumGuy.PlayerSimulator.Utils;

import java.util.ArrayList;
import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Items {
	public static ItemStack createItem(Material M, int Ammount, Byte Data, String Name, ArrayList<String> Lore) {
		if (Data == null) Data = (byte) 0;
		ItemStack item = new ItemStack(M, Ammount, Data);
		ItemMeta itemMeta = item.getItemMeta();
		if (Name != null) itemMeta.setDisplayName(Name);
		if (Lore != null) itemMeta.setLore(Lore);
		item.setItemMeta(itemMeta);
		
		return item;
	}
	
	public static ItemStack createItem(Material M, int Ammount, int Data, String Name, ArrayList<String> Lore) {
		ItemStack item = new ItemStack(M, Ammount, (byte) Data);
		ItemMeta itemMeta = item.getItemMeta();
		if (Name != null) itemMeta.setDisplayName(Name);
		if (Lore != null) itemMeta.setLore(Lore);
		item.setItemMeta(itemMeta);
		
		return item;
	}
	
	public static ItemStack createItem(Material M, int Ammount, Byte Data, String Name, String... Lore) {
		if (Data == null) Data = (byte) 0;
		ItemStack item = new ItemStack(M, Ammount, Data);
		ItemMeta itemMeta = item.getItemMeta();
		if (Name != null) itemMeta.setDisplayName(Name);
		
		if (Lore != null) {
			ArrayList<String> loreList = new ArrayList<String>();
			for (String index : Lore) {
				loreList.add(index);
			}
			
			itemMeta.setLore(loreList);
		}
		
		item.setItemMeta(itemMeta);
		
		return item;
	}
	
	public static ItemStack createItem(Material M, int Ammount, int Data, String Name, String... Lore) {
		ItemStack item = new ItemStack(M, Ammount, (byte) Data);
		ItemMeta itemMeta = item.getItemMeta();
		if (Name != null) itemMeta.setDisplayName(Name);
		if (Lore != null) {
			ArrayList<String> loreList = new ArrayList<String>();
			for (String index : Lore) {
				loreList.add(index);
			}
			
			itemMeta.setLore(loreList);
		}
		
		item.setItemMeta(itemMeta);
		
		return item;
	}
	
	public static ItemStack createItem(Material M, int Ammount, Byte Data, String Name) {
		if (Data == null) Data = (byte) 0;
		ItemStack item = new ItemStack(M, Ammount, Data);
		ItemMeta itemMeta = item.getItemMeta();
		if (Name != null) itemMeta.setDisplayName(Name);
		item.setItemMeta(itemMeta);
		
		return item;
	}
	
	public static ItemStack createItem(Material M, int Ammount, Byte Data, String Name, String loreString) {
		if (Data == null) Data = (byte) 0;
		ItemStack item = new ItemStack(M, Ammount, Data);
		
		ArrayList<String> lore = new ArrayList<String>();
    	lore.addAll(Arrays.asList(loreString.split("\n")));
    	
    	for (int i = 0 ; i < lore.size() ; i++) {
    		String s = lore.get(i);
    		//s = "§a" + s;
    		lore.set(i, s);
    	}
		
		ItemMeta itemMeta = item.getItemMeta();
		itemMeta.setLore(lore);
		if (Name != null) itemMeta.setDisplayName(Name);
		item.setItemMeta(itemMeta);
		
		return item;
	}
	
	public static ItemStack createItem(Material M, int Ammount, int Data, String Name) {
		ItemStack item = new ItemStack(M, Ammount, (byte) Data);
		ItemMeta itemMeta = item.getItemMeta();
		if (Name != null) itemMeta.setDisplayName(Name);
		item.setItemMeta(itemMeta);
		
		return item;
	}
	
	public static ItemStack createItem(Material M, int Ammount, int Data, String Name, String loreString) {
		ItemStack item = new ItemStack(M, Ammount, (byte) Data);
		
		ArrayList<String> lore = new ArrayList<String>();
	    	lore.addAll(Arrays.asList(loreString.split("\n")));
	    	
	    	for (int i = 0 ; i < lore.size() ; i++) {
	    		String s = lore.get(i);
	    		//s = "§a" + s;
	    		lore.set(i, s);
	    	}
		
		ItemMeta itemMeta = item.getItemMeta();
		itemMeta.setLore(lore);
		if (Name != null) itemMeta.setDisplayName(Name);
		item.setItemMeta(itemMeta);
		
		return item;
	}
	
	public static void setItem(Player p, ItemStack item, int Space) {
		p.getInventory().setItem(Space, item);
	}
	
	public static void addItem(Player p, ItemStack item) {
		p.getInventory().addItem(item);
	}
	
	public static void addEnchantment(ItemStack item, Enchantment enchantment, int level, boolean ignoreRestriction) {
		ItemMeta meta = item.getItemMeta();
		meta.addEnchant(enchantment, level, ignoreRestriction);
		item.setItemMeta(meta);
	}
	
	public static ItemStack unbreakable(ItemStack item) {
		/*net.minecraft.server.v1_12_R1.ItemStack is = CraftItemStack.asNMSCopy(item);
        NBTTagCompound tag = is.hasTag() ? is.getTag() : new NBTTagCompound();
        tag.setInt("Unbreakable", 1);
        is.setTag(tag);
        
        return CraftItemStack.asBukkitCopy(is);*/
		
		ItemMeta meta = item.getItemMeta();
		meta.setUnbreakable(true);
		item.setItemMeta(meta);
		return item;
    }
}