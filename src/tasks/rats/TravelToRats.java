package tasks.rats;

import config.Config;
import methods.WalkingMethods;
import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.methods.skills.Skill;
import org.dreambot.api.methods.skills.Skills;
import tasks.AbstractTask;

public class TravelToRats extends AbstractTask {

    private WalkingMethods wm = new WalkingMethods();

    @Override
    public boolean accept() {
        if(!config.smallGiantRatArea.contains(Players.localPlayer()) && config.getState() == Config.State.RATS && !getLocalPlayer().isInCombat()){
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

    @Override
    public int execute() {
        log("[T] Traveling to rats");
        config.setStatus("Traveling to rats");
        // teleport home, if far away from home
        wm.TeleHome();
        wm.Walk(config.smallGiantRatArea, "Rats");
        return Calculations.random(600,1200);
    }
}
