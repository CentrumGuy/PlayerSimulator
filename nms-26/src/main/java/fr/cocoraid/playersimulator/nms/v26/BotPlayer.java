package fr.cocoraid.playersimulator.nms.v26;

import com.mojang.authlib.GameProfile;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ClientInformation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;

final class BotPlayer extends ServerPlayer {
    BotPlayer(MinecraftServer server, ServerLevel level, GameProfile profile, ClientInformation info) {
        super(server, level, profile, info);
    }

    @Override
    public void tick() {
        super.tick();
        this.doTick();
    }
}
