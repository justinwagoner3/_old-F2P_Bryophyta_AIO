package tasks.bryophyta;

import methods.CombatMethods;
import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.dialogues.Dialogues;
import org.dreambot.api.methods.interactive.GameObjects;
import org.dreambot.api.methods.interactive.NPCs;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.methods.map.Tile;
import org.dreambot.api.methods.prayer.Prayer;
import org.dreambot.api.methods.prayer.Prayers;
import org.dreambot.api.methods.skills.Skill;
import org.dreambot.api.methods.skills.Skills;
import org.dreambot.api.methods.walking.impl.Walking;
import org.dreambot.api.wrappers.interactive.GameObject;
import org.dreambot.api.wrappers.interactive.NPC;
import org.dreambot.api.wrappers.interactive.Character;
import org.dreambot.api.wrappers.interactive.Player;
import tasks.AbstractTask;

public class FightBryophyta extends AbstractTask {

    private CombatMethods cm = new CombatMethods();
    private Character curGrowthling = null;


    @Override
    public boolean accept() {
        return config.bryophytaLairEntrance.contains(getLocalPlayer())
                && Inventory.count(config.swordfish) > 14 // TODO - this needs make advanced logic based on levels (or make it user configurable)
                && Inventory.containsAll(config.bronzeAxe,config.mossyKey);
    }

    // TODO - the problem is once the growthling has zero health and needs axed it is return null
    // TODO - gonna have to put a ton into logs and figure out what's happening
    @Override
    public int execute() {
        log("[T] Fight Bryophyta");
        config.setStatus("Fighting Bryophyta");

        // drink potion
        cm.DrinkPotion(config.strPot4,config.strPot3,config.strPot2,config.strPot1);

        // turn on prayer if high enough level
        if(Skills.getRealLevel(Skill.PRAYER) >= 37){
            if (!Prayers.isActive(Prayer.PROTECT_FROM_MAGIC)) {
                Prayers.toggle(true,Prayer.PROTECT_FROM_MAGIC);
            }
        }

        // turn run on
        if(!Walking.isRunEnabled() && Walking.getRunEnergy() >= 1) {
            Walking.toggleRun();
        }


        // enter the lair
        GameObject gate = GameObjects.closest(o -> o != null && o.getName().equals("Gate"));
        if(gate != null){
            gate.interact();
            //sleepUntil(() -> config.bryophytaLairExit.contains(getLocalPlayer()), Calculations.random(4000,5000));
            sleepUntil(Dialogues::canContinue,Calculations.random(5000,6000));
            if(Dialogues.clickContinue()){
                sleepUntil(Dialogues::canEnterInput,Calculations.random(5000,6000));
                    Dialogues.chooseOption(1);
                    sleepUntil(() -> NPCs.closest(config.bryophyta) != null && NPCs.closest(config.bryophyta).getName().equals(config.bryophyta),Calculations.random(10000,12000));
                    log("bryophyta not null");
            }
        }

        // while bryophyta health is greater than zero
        NPC brophyta = NPCs.closest(config.bryophyta);
        while(brophyta.getHealthPercent() != 0) {
            // using configs and methods, check and eat
            if(Players.localPlayer().getHealthPercent() <= config.getNextEatAtPercentage()) {
                log("eating");
                cm.EatFood(config.swordfish);
            }


            // check if any of the little monsters can be axed
            if(getLocalPlayer().getInteractingCharacter() != null && getLocalPlayer().getInteractingCharacter().getHealthPercent() <= 0){
                curGrowthling = getLocalPlayer().getInteractingCharacter();
            //NPC anyGrowthlingWithZeroHealth = NPCs.closest(n -> n != null && n.getHealthPercent() == 0);
//            if(anyGrowthlingWithZeroHealth != null){
            //if(curGrowthling != null){
                log("axinggg");
                Inventory.interact(config.bronzeAxe,"Use");
                sleepUntil(Inventory::isItemSelected,Calculations.random(700,1100));
                curGrowthling.interact("Use");
                //sleepUntil(() -> !anyGrowthlingWithZeroHealth.exists(),Calculations.random(400,800));
                sleepUntil(() -> !curGrowthling.exists(),Calculations.random(400,800));
            }

            // check if any of the little monsters are alive, and attack them if not already attacking one
            NPC anyGrowthlingWithoutZeroHealth = NPCs.closest(n -> n != null && n.getHealthPercent() > 0 && n.getName().equals(config.growthling));
            if(anyGrowthlingWithoutZeroHealth != null && getLocalPlayer().getInteractingCharacter() != null && !getLocalPlayer().getInteractingCharacter().getName().equals(config.growthling)){
                log("attack growthling");
                anyGrowthlingWithoutZeroHealth.interact();
                sleepUntil(() -> getLocalPlayer().getInteractingCharacter().getName() != null && getLocalPlayer().getInteractingCharacter().getName().equals(config.growthling),Calculations.random(1000,2000));
                if(getLocalPlayer().getInteractingCharacter().getName() != null && getLocalPlayer().getInteractingCharacter().getName().equals(config.growthling)){
                    curGrowthling = getLocalPlayer().getInteractingCharacter();
                    log("in combat with growthling");
                    sleepUntil(() -> curGrowthling.getHealthPercent() <= 0,Calculations.random(4000,5000));
                    if(curGrowthling.getHealthPercent() <= 0){
                        log("health <= 0");
                        Inventory.interact(config.bronzeAxe,"Use");
                        sleepUntil(Inventory::isItemSelected,Calculations.random(700,1100));
                        curGrowthling.interact("Use");
                        sleep(Calculations.random(400,700)); // TODO - maybe make this better
                    }
                }
            }

            // attack bryophyta when there are no growliths on the alive
            anyGrowthlingWithoutZeroHealth = NPCs.closest(n -> n != null && n.getHealthPercent() > 0 && n.getName().equals(config.growthling));
            if(anyGrowthlingWithoutZeroHealth == null && getLocalPlayer().getInteractingCharacter() != null && !getLocalPlayer().getInteractingCharacter().getName().equals(config.bryophyta)){
                log("attack bryyophyta");
                brophyta.interact("Attack");
            }
            sleep(300);

        }
        return 0;
    }

}
