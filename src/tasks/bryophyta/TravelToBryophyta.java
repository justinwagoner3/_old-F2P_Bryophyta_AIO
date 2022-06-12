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
        if(!config.bryophytaLairEntrance.contains(Players.localPlayer()) && config.getState() == Config.State.BRYOPHYTA){
            if(config.getCurFightingStyle() == Config.FightingStyle.MELEE){
                if(Inventory.containsAll(config.swordfish,config.strPot4,config.mossyKey,config.bronzeAxe)){
                    return true;
                }
            }
            if(config.getCurFightingStyle() == Config.FightingStyle.RANGED){
                if(Inventory.containsAll(config.swordfish,config.ironScimitar,config.mossyKey,config.bronzeAxe)){
                    return true;
                }
            }
        }
        return false;
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
