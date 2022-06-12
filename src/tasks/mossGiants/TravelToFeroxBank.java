package tasks.mossGiants;

import config.Config;
import methods.WalkingMethods;
import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.methods.skills.Skill;
import org.dreambot.api.methods.skills.Skills;
import tasks.AbstractTask;

public class TravelToFeroxBank extends AbstractTask {
    private WalkingMethods wm = new WalkingMethods();

    @Override
    public boolean accept() {
        if(config.getState() == Config.State.MOSSGIANTS && !config.feroxEnclaveBank.contains(Players.localPlayer())){
            if(config.getCurFightingStyle() == Config.FightingStyle.MELEE){
                if(!Inventory.containsAll(config.strPot4,config.lobster)){
                    return true;
                }
            }
            if(config.getCurFightingStyle() == Config.FightingStyle.RANGED){
                if(!Inventory.contains(config.lobster)){
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public int execute() {
        log("[T] Traveling to Ferox bank");
        config.setStatus("Traveling to Ferox bank");
        wm.WalkToWildy(config.feroxEnclaveBank, "Ferox Bank");


        return Calculations.random(600,1200);

    }

}
