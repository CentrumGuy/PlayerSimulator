package fr.cocoraid.playersimulator;

import fr.cocoraid.playersimulator.api.BotAdapter;
import fr.cocoraid.playersimulator.gui.MenuListener;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {
    public static final String PREFIX = "§c[PlayerSim]§7 ";

    public static boolean autoRespawn = true;

    private static Main instance;
    private BotManager manager;

    public static Main get() { return instance; }
    public BotManager getManager() { return manager; }

    public static void send(CommandSender to, String legacyText) {
        to.sendMessage(LegacyComponentSerializer.legacySection().deserialize(PREFIX + legacyText));
    }

    @Override
    public void onEnable() {
        instance = this;
        BotAdapter adapter;
        try {
            adapter = loadAdapter();
        } catch (Throwable t) {
            getLogger().severe("Minecraft " + Bukkit.getMinecraftVersion() + " wird nicht unterstuetzt (1.21.11 bis 26.x): " + t);
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        manager = new BotManager(this, adapter);

        getServer().getPluginManager().registerEvents(new Listeners(manager), this);
        getServer().getPluginManager().registerEvents(new MenuListener(this), this);

        MainCommand executor = new MainCommand(manager);
        for (String c : new String[]{"spawnbot", "killbots", "killbot", "simdebug", "editbot", "command"}) {
            getCommand(c).setExecutor(executor);
        }
    }

    @Override
    public void onDisable() {
        if (manager != null) manager.disconnectAll();
        Bukkit.getScheduler().cancelTasks(this);
    }

    private BotAdapter loadAdapter() throws Exception {
        String v = Bukkit.getMinecraftVersion();
        int major = Integer.parseInt(v.split("\\.")[0]);
        String cls;
        if (major >= 26) {
            cls = "fr.cocoraid.playersimulator.nms.v26.NmsBotAdapter";
        } else if (v.equals("1.21.11")) {
            cls = "fr.cocoraid.playersimulator.nms.v1_21_11.NmsBotAdapter";
        } else {
            throw new IllegalStateException("keine Implementierung fuer " + v);
        }
        getLogger().info("Minecraft " + v + " -> " + cls);
        return (BotAdapter) Class.forName(cls).getDeclaredConstructor().newInstance();
    }
}
