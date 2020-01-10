package com.CentrumGuy.PlayerSimulator.NMSUtils;

import net.minecraft.server.v1_12_R1.EnumProtocolDirection;
import net.minecraft.server.v1_12_R1.NetworkManager;

public class DummyNetworkManager extends NetworkManager implements Cloneable {
    //private IChatBaseComponent ichatbasecomponent;
	
	public DummyNetworkManager() {
        super(EnumProtocolDirection.SERVERBOUND);
	}
	
    /*public void channelActive(ChannelHandlerContext channelhandlercontext) throws Exception {
    }

    public void a(EnumProtocol enumprotocol) {
    }

    public void channelInactive(ChannelHandlerContext channelhandlercontext) {
    }

    public void exceptionCaught(ChannelHandlerContext channelhandlercontext, Throwable throwable) {
    }

    @SuppressWarnings("rawtypes")
	protected void a(ChannelHandlerContext channelhandlercontext, Packet packet) {
    }

    public void a(PacketListener packetlistener) {
        super.setPacketListener(packetlistener);
    }

    @SuppressWarnings("rawtypes")
	public void handle(Packet packet, GenericFutureListener... agenericfuturelistener) {
    }

    @SuppressWarnings({ "unused", "rawtypes" })
	private void b(Packet packet, GenericFutureListener[] agenericfuturelistener) {
    }

    public void a() {
    }

    @SuppressWarnings("serial")
	public SocketAddress getSocketAddress() {
        return new SocketAddress() {
        };
    }

    public void a(IChatBaseComponent ichatbasecomponent) {
        this.ichatbasecomponent = ichatbasecomponent;
    }

    public boolean c() {
        return false;
    }

    public void a(SecretKey secretkey) {
    }

    public boolean d() {
        return true;
    }

    public PacketListener getPacketListener() {
        return super.i();
    }

    public IChatBaseComponent f() {
        return this.ichatbasecomponent;
    }

    public void g() {
    }*/
}
