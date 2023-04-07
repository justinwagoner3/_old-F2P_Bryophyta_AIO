package tasks.combat;

import config.Config;
import methods.CombatMethods;
import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.combat.Combat;
import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.methods.walking.impl.Walking;
import tasks.AbstractTask;

public class AttackMonster extends AbstractTask {

    private CombatMethods cm = new CombatMethods();

    @Override
    public boolean accept() {
        if((config.largeGiantRatArea.contains(getLocalPlayer()) && config.getState().equals(Config.State.RATS)) || (config.giantFrogArea.contains(getLocalPlayer()) && config.getState().equals(Config.State.FROGS)) || (config.mossGiantWildernessArea.contains(getLocalPlayer()) && config.getState().equals(Config.State.MOSSGIANTS))){ // in combat area
            if(Inventory.contains(config.lobster)){ // with heals
                if(config.getCurFightingStyle() != Config.FightingStyle.MELEE || Inventory.contains(config.strPot1, config.strPot2, config.strPot3, config.strPot4)) { // and str pots if melee
                    if (!getLocalPlayer().isInCombat() && !getLocalPlayer().isMoving()) { // not fighting or about to fight
                        return true;
                    }
                    // this fixes leveling up while attacking error
                    if(getLocalPlayer().isInCombat() && !getLocalPlayer().isHealthBarVisible()){
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public int execute() {
        log("[T] Attacking monster... ");
        config.setStatus("Attacking " + config.getCurMonster());
        // walk to monster if too far away
        cm.AttackMonster(config.getCurMonster());

        // TODO- probably should not exist here
        // turn auto retailiate on if necessary
        if(!Combat.isAutoRetaliateOn()){
            Combat.toggleAutoRetaliate(true);
        }
        return Calculations.random(400,1000);
    }

}
