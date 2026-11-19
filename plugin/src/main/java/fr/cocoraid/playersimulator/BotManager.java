package fr.cocoraid.playersimulator;

import fr.cocoraid.playersimulator.api.BotAdapter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.profile.PlayerProfile;

import java.nio.charset.StandardCharsets;
import java.util.*;

public final class BotManager {

    private final Main plugin;
    private final BotAdapter adapter;
    private final Map<UUID, Bot> bots = new LinkedHashMap<>();
    private final Set<UUID> joining = new HashSet<>();
    private final Set<String> reservedNames = new HashSet<>();
    private int botNum = 0;

    BotManager(Main plugin, BotAdapter adapter) {
        this.plugin = plugin;
        this.adapter = adapter;
    }

    public Collection<Bot> getBots() {
        return new ArrayList<>(bots.values());
    }

    public boolean isBot(Player p) {
        return bots.containsKey(p.getUniqueId()) || joining.contains(p.getUniqueId());
    }

    public Bot find(String name) {
        for (Bot b : bots.values()) if (b.getName().equalsIgnoreCase(name)) return b;
        return null;
    }

    public boolean isNameTaken(String name) {
        return Bukkit.getPlayerExact(name) != null || reservedNames.contains(name.toLowerCase());
    }

    public String nextName() {
        String n;
        do {
            n = "Bot" + (++botNum);
        } while (isNameTaken(n));
        return n;
    }

    public void spawn(String name, Location location, CommandSender feedback) {
        reservedNames.add(name.toLowerCase());
        PlayerProfile base = Bukkit.createPlayerProfile(name);
        base.update().whenComplete((res, ex) -> Bukkit.getScheduler().runTask(plugin, () -> {
            try {
                PlayerProfile resolved = (ex == null && res != null && res.getUniqueId() != null)
                        ? res
                        : Bukkit.createPlayerProfile(offlineUuid(name), name);
                PlayerProfile profile = resolved.getName() == null
                        ? Bukkit.createPlayerProfile(resolved.getUniqueId(), name)
                        : resolved;
                UUID id = profile.getUniqueId();
                if (Bukkit.getPlayer(id) != null) {
                    if (feedback != null) Main.send(feedback, "Player is already online");
                    return;
                }
                joining.add(id);
                Player p;
                try {
                    p = adapter.spawn(profile, location);
                } catch (Throwable t) {
                    joining.remove(id);
                    throw t;
                }
                joining.remove(id);
                bots.put(p.getUniqueId(), new Bot(p));
            } catch (Throwable t) {
                plugin.getLogger().severe("Bot '" + name + "' konnte nicht gespawnt werden: " + t);
                t.printStackTrace();
                if (feedback != null) Main.send(feedback, "§cCould not spawn bot §e" + name + "§c (see console)");
            } finally {
                reservedNames.remove(name.toLowerCase());
            }
        }));
    }

    public void disconnect(Bot bot) {
        BotMenus.remove(bot);
        bots.remove(bot.getBot().getUniqueId());
        if (bot.getBot().isOnline()) {
            try {
                adapter.disconnect(bot.getBot());
            } catch (Throwable t) {
                plugin.getLogger().warning("Disconnect fehlgeschlagen: " + t);
            }
        }
    }

    public void disconnectAll() {
        for (Bot b : getBots()) disconnect(b);
        bots.clear();
    }

    void forget(UUID id) {
        Bot b = bots.remove(id);
        if (b != null) BotMenus.remove(b);
    }

    private static UUID offlineUuid(String name) {
        return UUID.nameUUIDFromBytes(("OfflinePlayer:" + name).getBytes(StandardCharsets.UTF_8));
    }
}
