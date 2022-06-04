package tasks.frogs;

import config.Config;
import methods.WalkingMethods;
import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.interactive.Players;
import tasks.AbstractTask;

public class TravelToFrogs extends AbstractTask {

    public WalkingMethods wm = new WalkingMethods();

    @Override
    public boolean accept() {
        if(!config.giantFrogArea.contains(Players.localPlayer()) && config.getState() == Config.State.FROGS && !getLocalPlayer().isInCombat()){
            if(config.getCurFightingStyle() == Config.FightingStyle.MELEE){
                if(Inventory.containsAll(config.strPot4,config.lobster)){
                    return true;
                }
            }
            if(config.getCurFightingStyle() == Config.FightingStyle.RANGED){
                if(Inventory.contains(config.lobster)){
                    return true;
                }
            }
        }

        return false;
    }

    public int execute() {
        log("[T] Traveling to giant frogs");
        config.setStatus("Traveling to giant frogs");
        // teleport home, if far away from home
        wm.TeleHome();
        wm.Walk(config.giantFrogArea, "Giant Frogs");
        return Calculations.random(600,1200);
    }

}
