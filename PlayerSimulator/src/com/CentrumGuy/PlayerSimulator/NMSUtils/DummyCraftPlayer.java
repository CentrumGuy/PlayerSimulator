package com.CentrumGuy.PlayerSimulator.NMSUtils;

import org.bukkit.craftbukkit.v1_12_R1.CraftServer;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.scoreboard.Scoreboard;

import net.minecraft.server.v1_12_R1.EntityPlayer;

public class DummyCraftPlayer extends CraftPlayer {
	public DummyCraftPlayer(CraftServer server, EntityPlayer entity) {
		super(server, entity);
	}

	public void setScoreboard(Scoreboard scoreboard) {
		super.setScoreboard(scoreboard);
		/*Validate.notNull(scoreboard, "Scoreboard cannot be null");
        PlayerConnection playerConnection = getHandle().playerConnection;
        if (playerConnection == null) {
            throw new IllegalStateException("Cannot set scoreboard yet");
        }
        if (playerConnection.isDisconnected()) {
            throw new IllegalStateException("Cannot set scoreboard for invalid CraftPlayer");
        }

        this.server.getScoreboardManager().setPlayerBoard(this, scoreboard);*/
	}
}
