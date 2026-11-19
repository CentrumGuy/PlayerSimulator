package fr.cocoraid.playersimulator;

import fr.cocoraid.playersimulator.gui.MainMenu;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.Locale;
import java.util.concurrent.ThreadLocalRandom;

public final class MainCommand implements CommandExecutor {
    private final BotManager manager;

    MainCommand(BotManager manager) { this.manager = manager; }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String n = command.getName().toLowerCase(Locale.ROOT);
        String perm = switch (n) {
            case "spawnbot" -> "playersim.spawnbot";
            case "killbots" -> "playersim.killbots";
            case "killbot" -> "playersim.killbot";
            case "simdebug" -> "playersim.debug";
            case "editbot" -> "playersim.editbot";
            case "command" -> "playersim.command";
            default -> null;
        };
        if (perm == null) return false;
        if (!sender.hasPermission(perm)) {
            Main.send(sender, "You don't have permission to perform this command");
            return true;
        }

        switch (n) {
            case "spawnbot" -> spawn(sender, args);
            case "simdebug" -> {
                World w = Bukkit.getWorlds().get(0);
                double tps = Math.min(20.0, Bukkit.getTPS()[0]);
                Main.send(sender, "TPS: " + String.format(Locale.ROOT, "%.1f", tps)
                        + " Loaded chunks: " + w.getLoadedChunks().length
                        + " Entities: " + w.getEntities().size());
            }
            case "killbots" -> {
                manager.disconnectAll();
                Main.send(sender, "Disconnected all bots");
            }
            case "killbot" -> {
                if (args.length < 1) { Main.send(sender, "Must specify §eBot Name"); return true; }
                Bot b = manager.find(args[0]);
                if (b == null) { Main.send(sender, "Could not find bot §e" + args[0]); return true; }
                manager.disconnect(b);
                Main.send(sender, "Disconnected§e " + args[0]);
            }
            case "editbot" -> {
                if (!(sender instanceof Player p)) { Main.send(sender, "You must be a player to perform this command"); return true; }
                if (args.length < 1) { Main.send(sender, "Must specify §eBot Name"); return true; }
                Bot b = manager.find(args[0]);
                if (b == null) { Main.send(sender, "Could not find bot §e" + args[0]); return true; }
                BotMenus.main(b).open(p);
            }
            case "command" -> {
                if (args.length < 2) { Main.send(sender, "Usage: /command <bot> <command...>"); return true; }
                Bot b = manager.find(args[0]);
                if (b == null) { Main.send(sender, "Could not find bot §e" + args[0]); return true; }
                String cmd = String.join(" ", Arrays.copyOfRange(args, 1, args.length));
                Main.send(sender, "executing command " + cmd);
                boolean ok = Bukkit.dispatchCommand(b.getBot(), cmd);
                Main.send(sender, ok ? "Command is a success" : "Command failed !");
            }
        }
        return true;
    }

    private void spawn(CommandSender sender, String[] args) {
        if (!(sender instanceof Player p)) {
            Main.send(sender, "You must be a player to perform this command");
            return;
        }
        int count = 1, radius = 0;
        String name = null;
        if (args.length >= 1) {
            try { count = Integer.parseInt(args[0]); } catch (NumberFormatException e) { name = args[0]; }
        }
        if (args.length >= 2) {
            try { radius = Integer.parseInt(args[1]); } catch (NumberFormatException ignored) { }
        }
        if (name != null) {
            if (manager.isNameTaken(name)) { Main.send(sender, "Player is already online"); return; }
            manager.spawn(name, target(p, radius), sender);
            return;
        }
        for (int i = 0; i < Math.max(1, count); i++) {
            manager.spawn(manager.nextName(), target(p, radius), sender);
        }
    }

    private static Location target(Player p, int radius) {
        Location base = p.getLocation();
        if (radius <= 0) return base;
        ThreadLocalRandom r = ThreadLocalRandom.current();
        double x = base.getX() + r.nextDouble(-radius, radius);
        double z = base.getZ() + r.nextDouble(-radius, radius);
        World w = base.getWorld();
        return new Location(w, x, w.getHighestBlockYAt((int) x, (int) z) + 1, z, base.getYaw(), base.getPitch());
    }
}
