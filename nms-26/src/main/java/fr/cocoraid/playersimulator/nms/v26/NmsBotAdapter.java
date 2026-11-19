package fr.cocoraid.playersimulator.nms.v26;

import com.destroystokyo.paper.profile.PlayerProfile;
import com.mojang.authlib.GameProfile;
import fr.cocoraid.playersimulator.api.BotAdapter;
import net.minecraft.network.Connection;
import net.minecraft.network.DisconnectionDetails;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ClientInformation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.CommonListenerCookie;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.craftbukkit.CraftWorld;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.craftbukkit.profile.CraftPlayerProfile;
import org.bukkit.entity.Player;

public final class NmsBotAdapter implements BotAdapter {
    @Override
    public Player spawn(PlayerProfile profile, Location location) {
        MinecraftServer server = ((CraftServer) Bukkit.getServer()).getServer();
        ServerLevel level = ((CraftWorld) location.getWorld()).getHandle();
        GameProfile gameProfile = ((CraftPlayerProfile) profile).buildGameProfile();

        ClientInformation info = ClientInformation.createDefault();
        ServerPlayer player = new BotPlayer(server, level, gameProfile, info);
        Connection connection = new FakeConnection();
        CommonListenerCookie cookie = CommonListenerCookie.createInitial(gameProfile, false);

        server.getPlayerList().placeNewPlayer(connection, player, cookie);

        Player bukkit = player.getBukkitEntity();
        bukkit.teleport(location);
        return bukkit;
    }

    @Override
    public void disconnect(Player bot) {
        ServerPlayer handle = ((CraftPlayer) bot).getHandle();

        handle.connection.onDisconnect(new DisconnectionDetails(Component.literal("Bot disconnected")));
    }
}
