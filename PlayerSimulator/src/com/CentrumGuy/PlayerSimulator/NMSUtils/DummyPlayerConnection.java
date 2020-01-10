package com.CentrumGuy.PlayerSimulator.NMSUtils;

import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;

import net.minecraft.server.v1_12_R1.EntityPlayer;
import net.minecraft.server.v1_12_R1.MinecraftServer;
import net.minecraft.server.v1_12_R1.NetworkManager;
import net.minecraft.server.v1_12_R1.Packet;
import net.minecraft.server.v1_12_R1.PlayerConnection;

public class DummyPlayerConnection extends PlayerConnection {
    @SuppressWarnings("unused")
	private boolean disconnected = false;

    public DummyPlayerConnection(MinecraftServer minecraftserver, NetworkManager networkmanager, EntityPlayer entityplayer) {
        super(minecraftserver, networkmanager, entityplayer);
        this.player.joining = true;
    }

    @Override
    public CraftPlayer getPlayer() {
        return (this.player == null) ? null : this.player.getBukkitEntity();
    }

    public NetworkManager b() {
        return this.networkManager;
    }

    /*public void disconnect(java.lang.String s) {
        WorldServer worldserver = player.x();

        worldserver.kill(player);
        worldserver.getPlayerChunkMap().removePlayer(player);
        ((CraftServer) Bukkit.getServer()).getHandle().players.remove(player);
    }*/
    
    public void sendPacket(Packet<?> packet) {
    	//if (packet instanceof PacketPlayOutUpdateTime) super.sendPacket(packet);
    }

    /*public void a(PacketPlayInSteerVehicle packetplayinsteervehicle) {
    	Bukkit.broadcastMessage("test2");
    }

    public void a(PacketPlayInFlying packetplayinflying) {
    	Bukkit.broadcastMessage("test3");
    }

    public void a(double d0, double d1, double d2, float f, float f1) {
    	Bukkit.broadcastMessage("test4");
    }

    public void teleport(Location dest) {
    	Bukkit.broadcastMessage("test5");
    }

    public void a(PacketPlayInBlockDig packetplayinblockdig) {
    	Bukkit.broadcastMessage("test6");
    }

    public void a(PacketPlayInBlockPlace packetplayinblockplace) {
    	Bukkit.broadcastMessage("test7");
    }

    public void a(IChatBaseComponent ichatbasecomponent) {
    	Bukkit.broadcastMessage("test8");
    }
    
    public void a(PacketPlayInHeldItemSlot packetplayinhelditemslot) {
    	Bukkit.broadcastMessage("test9");
    }

    public void a(PacketPlayInChat packetplayinchat) {
    	Bukkit.broadcastMessage("test10");
    }

    public void chat(String s, boolean async) {
    	Bukkit.broadcastMessage("test11");
    }

    public void a(PacketPlayInArmAnimation packetplayinarmanimation) {
    	Bukkit.broadcastMessage("test12");
    }

    public void a(PacketPlayInEntityAction packetplayinentityaction) {
    	Bukkit.broadcastMessage("test13");
    }

    public void a(PacketPlayInUseEntity packetplayinuseentity) {
    	Bukkit.broadcastMessage("test14");
    }

    public void a(PacketPlayInClientCommand packetplayinclientcommand) {
    	Bukkit.broadcastMessage("test15");
    }

    public void a(PacketPlayInCloseWindow packetplayinclosewindow) {
    	Bukkit.broadcastMessage("test16");
    }

    public void a(PacketPlayInWindowClick packetplayinwindowclick) {
    	Bukkit.broadcastMessage("test17");
    }

    public void a(PacketPlayInEnchantItem packetplayinenchantitem) {
    	Bukkit.broadcastMessage("test18");
    }

    public void a(PacketPlayInSetCreativeSlot packetplayinsetcreativeslot) {
    	Bukkit.broadcastMessage("test19");
    }

    public void a(PacketPlayInTransaction packetplayintransaction) {
    	Bukkit.broadcastMessage("test20");
    }

    public void a(PacketPlayInUpdateSign packetplayinupdatesign) {
    	Bukkit.broadcastMessage("test21");
    }

    public void a(PacketPlayInKeepAlive packetplayinkeepalive) {
    	Bukkit.broadcastMessage("test22");
    }

    public void a(PacketPlayInAbilities packetplayinabilities) {
    	Bukkit.broadcastMessage("test23");
    }

    public void a(PacketPlayInTabComplete packetplayintabcomplete) {
    	Bukkit.broadcastMessage("test24");
    }

    public void a(PacketPlayInSettings packetplayinsettings) {
    	Bukkit.broadcastMessage("test25");
    }

    public void a(PacketPlayInCustomPayload packetplayincustompayload) {
    	Bukkit.broadcastMessage("test26");
    }

    public void a(EnumProtocol enumprotocol, EnumProtocol enumprotocol1) {
    	Bukkit.broadcastMessage("test27");
    }*/
}
