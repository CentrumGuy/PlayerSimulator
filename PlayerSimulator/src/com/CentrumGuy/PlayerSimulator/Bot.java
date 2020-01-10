package com.CentrumGuy.PlayerSimulator;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.craftbukkit.v1_12_R1.CraftServer;
import org.bukkit.craftbukkit.v1_12_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import com.CentrumGuy.PlayerSimulator.GUI.BotMenu;
import com.CentrumGuy.PlayerSimulator.GUI.MainMenu;
import com.CentrumGuy.PlayerSimulator.NMSUtils.DummyEntityPlayer;
import com.CentrumGuy.PlayerSimulator.NMSUtils.DummyPlayerConnection;
import com.CentrumGuy.PlayerSimulator.NMSUtils.ServerConnectionChannel;
import com.CentrumGuy.PlayerSimulator.Utils.ProfileLoader;
import com.mojang.authlib.GameProfile;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import net.minecraft.server.v1_12_R1.ChunkRegionLoader;
import net.minecraft.server.v1_12_R1.DedicatedPlayerList;
import net.minecraft.server.v1_12_R1.Entity;
import net.minecraft.server.v1_12_R1.EntityPlayer;
import net.minecraft.server.v1_12_R1.EnumProtocolDirection;
import net.minecraft.server.v1_12_R1.LocaleI18n;
import net.minecraft.server.v1_12_R1.MobEffect;
import net.minecraft.server.v1_12_R1.NBTTagCompound;
import net.minecraft.server.v1_12_R1.NetworkManager;
import net.minecraft.server.v1_12_R1.PacketDataSerializer;
import net.minecraft.server.v1_12_R1.PacketPlayOutAbilities;
import net.minecraft.server.v1_12_R1.PacketPlayOutCustomPayload;
import net.minecraft.server.v1_12_R1.PacketPlayOutEntityEffect;
import net.minecraft.server.v1_12_R1.PacketPlayOutHeldItemSlot;
import net.minecraft.server.v1_12_R1.PacketPlayOutLogin;
import net.minecraft.server.v1_12_R1.PacketPlayOutServerDifficulty;
import net.minecraft.server.v1_12_R1.PlayerConnection;
import net.minecraft.server.v1_12_R1.PlayerInteractManager;
import net.minecraft.server.v1_12_R1.PlayerList;
import net.minecraft.server.v1_12_R1.ScoreboardServer;
import net.minecraft.server.v1_12_R1.ServerConnection;
import net.minecraft.server.v1_12_R1.UserCache;
import net.minecraft.server.v1_12_R1.WorldData;
import net.minecraft.server.v1_12_R1.WorldServer;

public class Bot {
	private static int botNum = 0;
	public static ArrayList<Bot> bots = new ArrayList<Bot>();
	
	public static ArrayList<Bot> getBots() {
		return bots;
	}
	
	private String name;
	private Player spawner;
	private Player bot;
	private EntityPlayer botEntity;
	private boolean spawned = false;
	private MainMenu mainMenu = null;
	
	public Bot(Player spawner) {
		botNum++;
		this.name = "Bot" + botNum;
		this.spawner = spawner;
		bots.add(this);
	}
	
	public Bot(Player spawner, String botName) {
		botNum++;
		this.name = botName;
		this.spawner = spawner;
		bots.add(this);
	}
	
	public Player getSpawner() {
		return this.spawner;
	}
	
	public Player getBot() {
		return this.bot;
	}
	
	@SuppressWarnings("deprecation")
	public void spawn(Location loc) {
        DedicatedPlayerList playerList = ((CraftServer) Bukkit.getServer()).getHandle();
        OfflinePlayer op = Bukkit.getOfflinePlayer(name);
        UUID uuid = op.getUniqueId();
        
        //GameProfile gameProfile = new GameProfile(uuid, name);
        GameProfile gameProfile = (new ProfileLoader(uuid.toString(), name)).loadProfile();
        
        NetworkManager network = new NetworkManager(EnumProtocolDirection.SERVERBOUND);
        InetSocketAddress address = (InetSocketAddress) ((CraftPlayer) spawner).getHandle().playerConnection.networkManager.channel.localAddress(); //new InetSocketAddress(Bukkit.getServer().getIp(), Bukkit.getServer().getPort());
        
        try {
            Bootstrap b = new Bootstrap();
            b.option(ChannelOption.TCP_NODELAY, true);
            b.option(ChannelOption.SO_KEEPALIVE, true);
            b.group(new NioEventLoopGroup());
            b.channel(NioSocketChannel.class);
            ServerConnectionChannel scc = new ServerConnectionChannel(new ServerConnection(((CraftServer) Bukkit.getServer()).getHandle().getServer()));
            b.handler(scc);
            
            ChannelFuture f = b.connect(address.getAddress(), address.getPort()).sync(); // (5)
            //network.channel = f.channel();
            network.channelActive(f.channel().pipeline().lastContext());
        } catch (Exception e) {
        	e.printStackTrace();
        }
        
        /*try {
            Bootstrap b = new Bootstrap();
            b.option(ChannelOption.TCP_NODELAY, true);
            b.option(ChannelOption.SO_KEEPALIVE, true);
            b.group(new NioEventLoopGroup());
            b.channel(NioSocketChannel.class);
            b.handler(new ReadTimeoutHandler(50));
            
            InetSocketAddress sa = new InetSocketAddress(Bukkit.getServer().getIp(), Bukkit.getServer().getPort());
            ChannelFuture f = b.connect(sa.getAddress(), sa.getPort()).sync(); // (5)
            Channel chan = f.channel();
            
            BukkitRunnable check = new BukkitRunnable() {
            	int time = 0;
            	public void run() {
            		time++;
            		if (chan.isActive()) Bukkit.broadcastMessage("§a" + chan + time);
            		else Bukkit.broadcastMessage("§c" + chan + time);
            	}
            };
            
            check.runTaskTimer(Main.getPlugin(), 20, 20);
        } catch (Exception e) {
        	e.printStackTrace();
        }*/

        WorldServer world = ((CraftWorld) loc.getWorld()).getHandle();
        botEntity = new DummyEntityPlayer(((CraftServer) Bukkit.getServer()).getServer(), world, gameProfile, new PlayerInteractManager(world));
        bot = botEntity.getBukkitEntity();
        
        //playerList.a(network, botEntity);
        a(network, botEntity, playerList);

        mainMenu = new MainMenu(this);
        this.spawned = true;
	}
	
	public void disconnect(boolean removeBot) {        
        //for (Player p : Bukkit.getOnlinePlayers()) p.sendMessage(((CraftServer) Bukkit.getServer()).getHandle().disconnect(botEntity));
        this.bot.kickPlayer("");
        BotMenu.removeBotMenus(this);
		
        if (removeBot) bots.remove(this);
        botNum--;
	}
	
	public boolean spawned() {
		return this.spawned;
	}

	public boolean isOnline(Player p) {
        return Bukkit.getServer().getPlayer(p.getUniqueId()) != null;
    }
	
	public void a(NetworkManager networkmanager, EntityPlayer entityplayer, PlayerList pl) {
        GameProfile gameprofile = entityplayer.getProfile();
        UserCache usercache = pl.getServer().getUserCache();
        GameProfile gameprofile1 = usercache.a(gameprofile.getId());
        String s = gameprofile1 == null ? gameprofile.getName() : gameprofile1.getName();

        usercache.a(gameprofile);
        NBTTagCompound nbttagcompound = pl.a(entityplayer);
        // CraftBukkit start - Better rename detection
        if (nbttagcompound != null && nbttagcompound.hasKey("bukkit")) {
            NBTTagCompound bukkit = nbttagcompound.getCompound("bukkit");
            s = bukkit.hasKeyOfType("lastKnownName", 8) ? bukkit.getString("lastKnownName") : s;
        }
        // CraftBukkit end

        entityplayer.spawnIn(pl.getServer().getWorldServer(entityplayer.dimension));
        entityplayer.playerInteractManager.a((WorldServer) entityplayer.world);
        String s1 = "local";

        if (networkmanager.getSocketAddress() != null) {
            s1 = networkmanager.getSocketAddress().toString();
        }

        // CraftBukkit - Moved message to after join
        // PlayerList.f.info("{}[{}] logged in with entity id {} at ({}, {}, {})", entityplayer.getName(), s1, Integer.valueOf(entityplayer.getId()), Double.valueOf(entityplayer.locX), Double.valueOf(entityplayer.locY), Double.valueOf(entityplayer.locZ));
        WorldServer worldserver = pl.getServer().getWorldServer(entityplayer.dimension);
        WorldData worlddata = worldserver.getWorldData();

        //pl.a(entityplayer, (EntityPlayer) null, worldserver);
        PlayerConnection playerconnection = new DummyPlayerConnection(pl.getServer(), networkmanager, entityplayer);

        playerconnection.sendPacket(new PacketPlayOutLogin(entityplayer.getId(), entityplayer.playerInteractManager.getGameMode(), worlddata.isHardcore(), worldserver.worldProvider.getDimensionManager().getDimensionID(), worldserver.getDifficulty(), pl.getMaxPlayers(), worlddata.getType(), worldserver.getGameRules().getBoolean("reducedDebugInfo")));
        entityplayer.getBukkitEntity().sendSupportedChannels(); // CraftBukkit
        playerconnection.sendPacket(new PacketPlayOutCustomPayload("MC|Brand", (new PacketDataSerializer(Unpooled.buffer())).a(pl.getServer().getServerModName())));
        playerconnection.sendPacket(new PacketPlayOutServerDifficulty(worlddata.getDifficulty(), worlddata.isDifficultyLocked()));
        playerconnection.sendPacket(new PacketPlayOutAbilities(entityplayer.abilities));
        playerconnection.sendPacket(new PacketPlayOutHeldItemSlot(entityplayer.inventory.itemInHandIndex));
        pl.f(entityplayer);
        entityplayer.getStatisticManager().c();
        entityplayer.F().a(entityplayer);
        pl.sendScoreboard((ScoreboardServer) worldserver.getScoreboard(), entityplayer);
        pl.getServer().aD();
        // CraftBukkit start - login message is handled in the event
        // ChatMessage chatmessage;

        String joinMessage;
        if (entityplayer.getName().equalsIgnoreCase(s)) {
            // chatmessage = new ChatMessage("multiplayer.player.joined", new Object[] { entityplayer.getScoreboardDisplayName()});
            joinMessage = "\u00A7e" + LocaleI18n.a("multiplayer.player.joined", entityplayer.getName());
        } else {
            // chatmessage = new ChatMessage("multiplayer.player.joined.renamed", new Object[] { entityplayer.getScoreboardDisplayName(), s});
            joinMessage = "\u00A7e" + LocaleI18n.a("multiplayer.player.joined.renamed", entityplayer.getName(), s);
        }

        // chatmessage.getChatModifier().setColor(EnumChatFormat.YELLOW);
        // this.sendMessage(chatmessage);
        pl.onPlayerJoin(entityplayer, joinMessage);
        // CraftBukkit end
        worldserver = pl.getServer().getWorldServer(entityplayer.dimension);  // CraftBukkit - Update in case join event changed it
        playerconnection.a(entityplayer.locX, entityplayer.locY, entityplayer.locZ, entityplayer.yaw, entityplayer.pitch);
        pl.b(entityplayer, worldserver);
        if (!pl.getServer().getResourcePack().isEmpty()) {
            entityplayer.setResourcePack(pl.getServer().getResourcePack(), pl.getServer().getResourcePackHash());
        }

        Iterator<?> iterator = entityplayer.getEffects().iterator();

        while (iterator.hasNext()) {
            MobEffect mobeffect = (MobEffect) iterator.next();

            playerconnection.sendPacket(new PacketPlayOutEntityEffect(entityplayer.getId(), mobeffect));
        }

        if (nbttagcompound != null && nbttagcompound.hasKeyOfType("RootVehicle", 10)) {
            NBTTagCompound nbttagcompound1 = nbttagcompound.getCompound("RootVehicle");
            Entity entity = ChunkRegionLoader.a(nbttagcompound1.getCompound("Entity"), worldserver, true);

            if (entity != null) {
                UUID uuid = nbttagcompound1.a("Attach");
                Iterator<?> iterator1;
                Entity entity1;

                if (entity.getUniqueID().equals(uuid)) {
                    entityplayer.a(entity, true);
                } else {
                    iterator1 = entity.bG().iterator();

                    while (iterator1.hasNext()) {
                        entity1 = (Entity) iterator1.next();
                        if (entity1.getUniqueID().equals(uuid)) {
                            entityplayer.a(entity1, true);
                            break;
                        }
                    }
                }

                if (!entityplayer.isPassenger()) {
                	LogManager.getLogger().warn("Couldn\'t reattach entity to player");
                    worldserver.removeEntity(entity);
                    iterator1 = entity.bG().iterator();

                    while (iterator1.hasNext()) {
                        entity1 = (Entity) iterator1.next();
                        worldserver.removeEntity(entity1);
                    }
                }
            }
        }

        entityplayer.syncInventory();
        // CraftBukkit - Moved from above, added world
        LogManager.getLogger().info(entityplayer.getName() + "[" + s1 + "] logged in with entity id " + entityplayer.getId() + " at ([" + entityplayer.world.worldData.getName() + "]" + entityplayer.locX + ", " + entityplayer.locY + ", " + entityplayer.locZ + ")");
    }
	
	public MainMenu getMainMenu() {
		return this.mainMenu;
	}
	
	public static boolean isBot(Player p) {
		for (Bot b : Bot.getBots()) {
			if (b.getBot().equals(p)) return true;
		}
		
		return false;
	}
}
