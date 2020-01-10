package com.CentrumGuy.PlayerSimulator;

import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;

import net.minecraft.server.v1_12_R1.EntityPlayer;
import net.minecraft.server.v1_12_R1.PacketPlayInClientCommand;
import net.minecraft.server.v1_12_R1.PacketPlayInClientCommand.EnumClientCommand;

public class Listeners implements Listener {
    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
    	if (!Bot.isBot(e.getEntity())) return;
    	if (!(Main.autoRespawn)) return;
    	
    	BukkitRunnable br = new BukkitRunnable() {
    		public void run() {
    			PacketPlayInClientCommand in = new PacketPlayInClientCommand(EnumClientCommand.PERFORM_RESPAWN); // Gets the packet class
    	        EntityPlayer cPlayer = ((CraftPlayer) e.getEntity()).getHandle(); // Gets the EntityPlayer class
    	        cPlayer.playerConnection.a(in); // Handles the rest of it
    		}
    	};
    	
    	br.runTaskLaterAsynchronously(Main.getPlugin(), 10L);
    }
    
    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        if (!Bot.isBot(e.getPlayer())) return;
        
       /* NetworkManager network = ((CraftPlayer) e.getPlayer()).getHandle().playerConnection.networkManager;
        BukkitRunnable chan = new BukkitRunnable() {
        	int time = 0;
        	public void run() {
        		time++;
        		if (network.channel.isActive()) Bukkit.broadcastMessage("§a" + network.channel + time);
        		else Bukkit.broadcastMessage("§c" + network.channel + time);
        	}
        };
        
        chan.runTaskTimer(Main.getPlugin(), 20, 20);*/
        
    	if (!e.getPlayer().isDead()) return;
    	if (!(Main.autoRespawn)) return;
    	
    	BukkitRunnable br = new BukkitRunnable() {
    		public void run() {
    			PacketPlayInClientCommand in = new PacketPlayInClientCommand(EnumClientCommand.PERFORM_RESPAWN); // Gets the packet class
    	        EntityPlayer cPlayer = ((CraftPlayer) e.getPlayer()).getHandle(); // Gets the EntityPlayer class
    	        cPlayer.playerConnection.a(in); // Handles the rest of it
    		}
    	};
    	
    	br.runTaskLaterAsynchronously(Main.getPlugin(), 10L);
    }
}
