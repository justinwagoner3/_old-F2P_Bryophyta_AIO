package tasks.shared_rats_frogs;

import config.Config;
import methods.WalkingMethods;
import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.container.impl.bank.BankLocation;
import org.dreambot.api.methods.interactive.Players;
import tasks.AbstractTask;

public class TravelToLumbridgeBank extends AbstractTask {

    private WalkingMethods wm = new WalkingMethods();

    @Override
    public boolean accept() {
        if(config.getState() == Config.State.RATS || config.getState() == Config.State.FROGS){
            if(!config.lumbridgeBank.contains(Players.localPlayer())){
                if(!Inventory.contains(config.lobster)){
                    return true;
                }
                if(config.getCurFightingStyle() == Config.FightingStyle.MELEE){
                    if(!Inventory.contains(config.strPot4,config.strPot3,config.strPot2,config.strPot1));
                }
            }
        }

        return false;
    }

    @Override
    public int execute() {
        log("[T] Traveling to Lumbridge bank");
        config.setStatus("Traveling to Lumbridge bank");
        if(config.giantFrogArea.contains(getLocalPlayer()) || config.largeGiantRatArea.contains(getLocalPlayer())){
            wm.Walk(config.lumbridgeGraveyard,"lumb graveyard");
            wm.Walk(BankLocation.LUMBRIDGE.getArea(20), "Lumbridge bank");
        }
        else {
            wm.TeleHome();
            wm.Walk(BankLocation.LUMBRIDGE.getArea(20), "Lumbridge bank");
        }
        return Calculations.random(600,1200);

    }

}
