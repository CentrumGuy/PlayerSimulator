package com.CentrumGuy.PlayerSimulator;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import com.CentrumGuy.PlayerSimulator.Utils.TPSCheck;


public class Main extends JavaPlugin implements Listener {
	public static int bots = 0;
    public static TPSCheck tpsCheck = new TPSCheck();
    public static boolean autoRespawn = true;
    public static String prefix = "§c[PlayerSim]§7 ";

    @Override
    public void onEnable() {
        getServer().getScheduler().scheduleSyncRepeatingTask(this, tpsCheck, 20, 20);
        getServer().getPluginManager().registerEvents(this, this);
        getServer().getPluginManager().registerEvents(new Listeners(), this);
        
        getCommand("spawnbot").setExecutor(new MainCommand());
        getCommand("killbots").setExecutor(new MainCommand());
        getCommand("killbot").setExecutor(new MainCommand());
        getCommand("simdebug").setExecutor(new MainCommand());
        getCommand("editbot").setExecutor(new MainCommand());
    }

    @Override
    public void onDisable() {
        getServer().getScheduler().cancelTasks(this);
        
        if (!Bot.bots.isEmpty()) {
            for (Bot bot : Bot.bots) {
            	bot.disconnect(false);
            }
            
            Bot.bots.clear();
    	}
    }
	
    public static Plugin getPlugin() {
    	return Bukkit.getServer().getPluginManager().getPlugin("PlayerSimulator");
    }
}
