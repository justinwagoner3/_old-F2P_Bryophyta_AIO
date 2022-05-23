package tasks.combat;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.interactive.NPCs;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.methods.walking.impl.Walking;
import org.dreambot.api.wrappers.interactive.NPC;
import tasks.AbstractTask;

public class AttackMonster extends AbstractTask {
    @Override
    public boolean accept() {
        // TODO- update to check when enemy health hits 0
        return (config.largeGiantRatArea.contains(Players.localPlayer()) || config.mossGiantWildernessArea.contains(Players.localPlayer()))
                && (!Players.localPlayer().isInCombat() || (getLocalPlayer().getInteractingCharacter() != null && getLocalPlayer().getInteractingCharacter().getHealthPercent() == 0));
    }

    @Override
    public int execute() {
        log("[T] Attacking monster... ");
        config.setStatus("Attacking " + config.getCurMonster());
        String monster = config.getCurMonster();
        NPC currentNpc = NPCs.closest(npc -> npc != null && npc.getName() != null && npc.getName().equals(monster) && !npc.isInCombat() && npc.getInteractingCharacter() == null);
        if(currentNpc != null) { //does the npc exist?
            if (currentNpc.interact("Attack")) { //currentNpc will return true if we succesfully attacked the rat, if that happens we want to wait a bit to make sure we are in combat
                log(currentNpc.getName());
                sleepUntil(() -> getLocalPlayer().isInCombat() || getLocalPlayer().getInteractingCharacter() != null, Calculations.random(2000,3000)); //Wait a max of 2 seconds or until we are in combat
            }
        }
        // check if run needs to be enabled
        // TODO - turn into function
        if(!Walking.isRunEnabled()){
            if (Walking.getRunEnergy() > config.getNextRunEnergyPercentage()) {
                if(Walking.toggleRun()){
                    config.setNextRunEnergyPercentage(Calculations.random(10,50));
                    sleepUntil(Walking::isRunEnabled,Calculations.random(500));
                }
            }
        }
        //if(Players.localPlayer())
        return Calculations.random(400,1300);
    }

}
