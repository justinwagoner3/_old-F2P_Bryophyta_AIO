package tasks.bryophyta;

import config.Config;
import methods.WalkingMethods;
import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.interactive.GameObjects;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.methods.skills.Skill;
import org.dreambot.api.methods.skills.Skills;
import org.dreambot.api.wrappers.interactive.GameObject;
import tasks.AbstractTask;

public class TravelToBryophyta extends AbstractTask {

    private WalkingMethods wm = new WalkingMethods();

    @Override
    public boolean accept() {
        if(!config.bryophytaLairEntrance.contains(Players.localPlayer()) && config.getState() == Config.State.BRYOPHYTA){
            if(config.getCurFightingStyle() == Config.FightingStyle.MELEE){
                if(Inventory.containsAll(config.strPot4,config.mossyKey,config.bronzeAxe)  && Inventory.count(config.swordfish) > 20){
                    return true;
                }
            }
            if(config.getCurFightingStyle() == Config.FightingStyle.RANGED){
                if(Inventory.containsAll(config.knife,config.mossyKey,config.bronzeAxe) && Inventory.count(config.swordfish) > 20){
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
        while(Skills.getBoostedLevels(Skill.PRAYER) != Skills.getRealLevel(Skill.PRAYER)){
            wm.Walk(config.varrockChurch,"Varrock Church");
            GameObject altar = GameObjects.closest(o -> o != null && o.getName().equals("Altar"));
            if(altar != null){
                altar.interact();
                sleepUntil(() -> Skills.getBoostedLevels(Skill.PRAYER) == Skills.getRealLevel(Skill.PRAYER), Calculations.random(4000,8000));
            }
        }
        wm.Walk(config.varrockSewersManholeArea,"Sewer Manhole");
        wm.GoDownManhole();
        wm.Walk(config.bryophytaLairEntrance,"Bryophyta lair entrance");
        return 0;
    }

}
