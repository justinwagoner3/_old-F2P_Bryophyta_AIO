package tasks.bryophyta;

import config.Config;
import methods.WalkingMethods;
import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.interactive.Players;
import tasks.AbstractTask;

public class TravelToBryophyta extends AbstractTask {

    private WalkingMethods wm = new WalkingMethods();

    @Override
    public boolean accept() {
        return !config.bryophytaLairEntrance.contains(Players.localPlayer()) // Not in the Giant Rat Area
                && config.getState() == Config.State.BRYOPHYTA
                && Inventory.containsAll(config.swordfish,config.strPot4,config.mossyKey,config.bronzeAxe); // Inventory has supplies
    }

    @Override
    public int execute() {
        log("[T] Traveling to Bryophyta");
        config.setStatus("Traveling to Bryophyta");
        wm.Walk(config.varrockSewersManholeArea,"Sewer Manhole");
        wm.GoDownManhole();
        wm.Walk(config.bryophytaLairEntrance,"Bryophyta lair entrance");
        return 0;
    }

}
