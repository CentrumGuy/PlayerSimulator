package com.CentrumGuy.PlayerSimulator.GUI;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;

import com.CentrumGuy.PlayerSimulator.Bot;
import com.CentrumGuy.PlayerSimulator.Main;

public abstract class BotMenu implements Listener {
	public static ArrayList<BotMenu> botMenus = new ArrayList<BotMenu>();
	private static HashMap<Bot, ArrayList<BotMenu>> privateMenus = new HashMap<Bot, ArrayList<BotMenu>>();
	private Bot bot;
	private MenuType type;

	protected BotMenu(MenuType type, Bot bot) {
		Bukkit.getPluginManager().registerEvents(this, Main.getPlugin());
		this.type = type;
		this.bot = bot;
		if (!privateMenus.containsKey(bot)) privateMenus.put(bot, new ArrayList<BotMenu>());
		privateMenus.get(bot).add(this);
		
		botMenus.add(this);
	}
	
	public enum MenuType {
		MAIN, INVENTORY, ACTIONS, ATTRIBUTES
	}
	
	public Bot getBot() {
		return this.bot;
	}
	
	public Player getPlayer() {
		return getBot().getBot();
	}
	
	public void setBot(Bot bot) {
		this.bot = bot;
	}
	
	public abstract Inventory getInv();
	public abstract void open(Player p);
	
	public MenuType getType() {
		return this.type;
	}
	
	public static boolean isBotMenu(Inventory inv) {
		for (BotMenu bm : botMenus) {
			if (bm.getInv().equals(inv)) return true;
		}
		
		return false;
	}
	
	public static BotMenu getBotMenu(Inventory inv) {
		for (BotMenu bm : botMenus) {
			if (bm.getInv().equals(inv)) return bm;
		}
		
		return null;
	}
	
	public static ArrayList<BotMenu> getMenus(Bot bot) {
		return privateMenus.get(bot);
	}
	
	public static void removeBotMenus(Bot bot) {
		privateMenus.remove(bot);
	}
	
	public static MainMenu getMainMenu(Bot bot) {
		for (BotMenu bm : getMenus(bot)) {
			if (bm instanceof MainMenu) return (MainMenu) bm;
		}
		
		return null;
	}
	
	public static InvMenu getInvMenu(Bot bot) {
		for (BotMenu bm : getMenus(bot)) {
			if (bm instanceof InvMenu) return (InvMenu) bm;
		}
		
		return null;
	}
	
	public static ActionsMenu getActionsMenu(Bot bot) {
		for (BotMenu bm : getMenus(bot)) {
			if (bm instanceof ActionsMenu) return (ActionsMenu) bm;
		}
		
		return null;
	}
}
