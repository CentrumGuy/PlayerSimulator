package fr.cocoraid.playersimulator.nms.v1_21_11;

import io.netty.channel.ChannelFutureListener;
import io.netty.channel.embedded.EmbeddedChannel;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.PacketFlow;
import org.jetbrains.annotations.Nullable;

import java.net.InetAddress;
import java.net.InetSocketAddress;

final class FakeConnection extends Connection {
    FakeConnection() {
        super(PacketFlow.SERVERBOUND);
        this.channel = new EmbeddedChannel();
        this.address = new InetSocketAddress(InetAddress.getLoopbackAddress(), 0);
    }

    @Override public void send(Packet<?> packet) { }
    @Override public void send(Packet<?> packet, @Nullable ChannelFutureListener listener) { }
    @Override public void send(Packet<?> packet, @Nullable ChannelFutureListener listener, boolean flush) { }
    @Override public void flushChannel() { }
    @Override public boolean isConnected() { return true; }
    @Override public void setReadOnly() { }
    @Override public void handleDisconnection() { }
}
