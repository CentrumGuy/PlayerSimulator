package fr.cocoraid.playersimulator;

import fr.cocoraid.playersimulator.gui.ActionsMenu;
import fr.cocoraid.playersimulator.gui.InvMenu;
import fr.cocoraid.playersimulator.gui.MainMenu;

import java.util.HashMap;
import java.util.Map;

public final class BotMenus {
    private static final Map<Bot, MainMenu> MAIN = new HashMap<>();
    private static final Map<Bot, InvMenu> INV = new HashMap<>();
    private static final Map<Bot, ActionsMenu> ACTIONS = new HashMap<>();

    private BotMenus() { }

    public static MainMenu main(Bot b) { return MAIN.computeIfAbsent(b, MainMenu::new); }
    public static InvMenu inv(Bot b) { return INV.computeIfAbsent(b, InvMenu::new); }
    public static ActionsMenu actions(Bot b) { return ACTIONS.computeIfAbsent(b, ActionsMenu::new); }

    public static void remove(Bot b) {
        MAIN.remove(b); INV.remove(b); ACTIONS.remove(b);
    }
}
