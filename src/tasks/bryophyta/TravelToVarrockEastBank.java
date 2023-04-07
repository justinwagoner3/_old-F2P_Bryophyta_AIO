package tasks.bryophyta;

import config.Config;
import methods.WalkingMethods;
import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.interactive.GameObjects;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.wrappers.interactive.GameObject;
import tasks.AbstractTask;

public class TravelToVarrockEastBank extends AbstractTask {
    private WalkingMethods wm = new WalkingMethods();


    @Override
    public boolean accept() {
        return !config.varrockEastBank.contains(Players.localPlayer()) //
                && config.getState() == Config.State.BRYOPHYTA
                && (Inventory.count(config.swordfish) < 20 || !Inventory.contains(config.mossyKey)); //
    }

    @Override
    public int execute() {
        log("[T] Traveling to Varrock E Bank");
        config.setStatus("Traveling to Varrock E Bank");
        if(!config.bryophytaLairEntrance.contains(getLocalPlayer())) {
            GameObject rockPile = GameObjects.closest(o -> o != null && o.getName().equals("Rock Pile"));
            if (rockPile != null && rockPile.isOnScreen()) {
                log("found rock pile");
                while (!rockPile.interact("Clamber")) {
                    sleepUntil(() -> config.bryophytaLairEntrance.contains(getLocalPlayer()), 5000);
                }
            }
        }
        wm.Walk(config.varrockSewerSouthLadderArea,"Sewer south ladder");
        wm.GoUpLadder();
        wm.Walk(config.varrockEastBank,"Varrock E Bank");
        return 0;
    }

}
