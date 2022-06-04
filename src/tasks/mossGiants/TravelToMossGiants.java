package tasks.mossGiants;

import config.Config;
import methods.WalkingMethods;
import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.interactive.GameObjects;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.methods.skills.Skill;
import org.dreambot.api.methods.skills.Skills;
import org.dreambot.api.methods.walking.impl.Walking;
import org.dreambot.api.wrappers.interactive.GameObject;
import tasks.AbstractTask;

public class TravelToMossGiants extends AbstractTask {
    public WalkingMethods wm = new WalkingMethods();

    @Override
    public boolean accept() {
        if(!config.mossGiantWildernessArea.contains(Players.localPlayer()) && config.getState() == Config.State.MOSSGIANTS && !getLocalPlayer().isInCombat()){
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
        log("[T] Traveling to moss giants");
        config.setStatus("Traveling to moss giants");
        // drink from pool if in ferox enclave; TODO - should be function
        if(config.feroxEnclave.contains(getLocalPlayer())){
            GameObject poolOfRefreshment = GameObjects.closest(o -> o != null && o.getName().equals(config.poolOfRefreshment));
            if(poolOfRefreshment != null){
                poolOfRefreshment.interact("Drink");
                sleepUntil(() -> Walking.getRunEnergy() == 100, Calculations.random(10000,12000));
            }
        }
        wm.WalkToWildy(config.mossGiantWildernessArea, "Moss Giants");
        return Calculations.random(600,1200);
    }

}
