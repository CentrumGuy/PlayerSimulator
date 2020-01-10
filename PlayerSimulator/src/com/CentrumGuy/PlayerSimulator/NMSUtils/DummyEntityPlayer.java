package com.CentrumGuy.PlayerSimulator.NMSUtils;

import com.mojang.authlib.GameProfile;

import net.minecraft.server.v1_12_R1.EntityPlayer;
import net.minecraft.server.v1_12_R1.MinecraftServer;
import net.minecraft.server.v1_12_R1.PlayerInteractManager;
import net.minecraft.server.v1_12_R1.WorldServer;

public class DummyEntityPlayer extends EntityPlayer {
	public DummyEntityPlayer(MinecraftServer minecraftserver, WorldServer worldserver, GameProfile gameprofile,
			PlayerInteractManager playerinteractmanager) {
		super(minecraftserver, worldserver, gameprofile, playerinteractmanager);
	}
	
	public DummyCraftPlayer getBukkitEntity() {
		if (bukkitEntity == null) {
            bukkitEntity = new DummyCraftPlayer(server.server, (EntityPlayer) this);
        }
		
		return (DummyCraftPlayer) bukkitEntity;
	}
}
