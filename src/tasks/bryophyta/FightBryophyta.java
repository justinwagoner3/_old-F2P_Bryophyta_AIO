package tasks.bryophyta;

import config.Config;
import methods.CombatMethods;
import org.dreambot.api.input.Mouse;
import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.container.impl.equipment.Equipment;
import org.dreambot.api.methods.container.impl.equipment.EquipmentSlot;
import org.dreambot.api.methods.dialogues.Dialogues;
import org.dreambot.api.methods.input.Camera;
import org.dreambot.api.methods.interactive.GameObjects;
import org.dreambot.api.methods.interactive.NPCs;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.methods.map.Tile;
import org.dreambot.api.methods.prayer.Prayer;
import org.dreambot.api.methods.prayer.Prayers;
import org.dreambot.api.methods.skills.Skill;
import org.dreambot.api.methods.skills.Skills;
import org.dreambot.api.methods.walking.impl.Walking;
import org.dreambot.api.wrappers.interactive.Entity;
import org.dreambot.api.wrappers.interactive.GameObject;
import org.dreambot.api.wrappers.interactive.NPC;
import org.dreambot.api.wrappers.interactive.Character;
import tasks.AbstractTask;

import javax.xml.stream.events.Characters;

public class FightBryophyta extends AbstractTask {

    private CombatMethods cm = new CombatMethods();
    private Character curGrowthling = null;


    @Override
    public boolean accept() {
        return config.bryophytaLairEntrance.contains(getLocalPlayer())
                && Inventory.count(config.swordfish) > 20
                && Inventory.containsAll(config.bronzeAxe,config.mossyKey)
                && !getLocalPlayer().isInCombat() && !getLocalPlayer().isMoving();
    }

    // TODO - look into prayer flicking
    @Override
    public int execute() {
        log("[T] Fight Bryophyta");
        config.setStatus("Fighting Bryophyta");

        // drink potion
        if(config.getCurFightingStyle() == Config.FightingStyle.MELEE) {
            log("drinking potion");
            cm.DrinkPotion(config.strPot4, config.strPot3, config.strPot2, config.strPot1);
        }

        // turn run on
        if(!Walking.isRunEnabled() && Walking.getRunEnergy() >= 1) {
            Walking.toggleRun();
        }

        if(Camera.getZoom() != 181) {
            Camera.setZoom(181);
        }
        if(Camera.getPitch() != 383) {
            Camera.keyboardRotateToPitch(383);
        }

        // turn prayer on // TODO - turn this on once you enter lair, but be careful to not have to constantly open prayers to check its on
        cm.TurnPrayerOn(Prayer.PROTECT_FROM_MAGIC);

        // enter the lair
        GameObject gate = GameObjects.closest(o -> o != null && o.getName().equals("Gate"));
        if(gate != null){
            gate.interact();
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
        Character curMonsterAttacking = null;
        while(brophyta.getHealthPercent() != 0 && Inventory.contains(config.swordfish)) {

            // save who we are currently attacking
            if(getLocalPlayer().getInteractingCharacter() != null) {
                curMonsterAttacking = getLocalPlayer().getInteractingCharacter();
            }

            // if accidentally equip the bronze axe, re-equip weapon
            if(Inventory.contains(config.runeScimitar)){
                Equipment.equip(EquipmentSlot.WEAPON,config.runeScimitar);
            }

            // using configs and methods, check and eat
            if(Players.localPlayer().getHealthPercent() <= config.getNextEatAtPercentage()) {
                log("eating");
                cm.EatFood(config.swordfish);
                if(curMonsterAttacking != null && curMonsterAttacking.getName().equals(config.bryophyta)){
                    curMonsterAttacking.interact();
                }
            }

            // check if need to move away from bryo
            boolean moveLeft = false;
            boolean moveBack = false;
            if(brophyta.getTile().distance(getLocalPlayer().getTile()) <= 3){
                // calculate if we should move left or right / up or down
                if(brophyta.getX() > getLocalPlayer().getX()){
                    moveLeft = true;
                }
                if(brophyta.getY() > getLocalPlayer().getY()){
                    moveBack = true;
                }
                // use that to create which tile to move to
                Tile moveToTile = (getLocalPlayer().getTile());
                if(moveLeft){
                    moveToTile.setX(moveToTile.getX()-Calculations.random(1,4));
                }
                else{
                    moveToTile.setX(moveToTile.getX()+Calculations.random(1,4));
                }
                if(moveBack){
                    moveToTile.setY(moveToTile.getY()-Calculations.random(1,4));
                }
                else{
                    moveToTile.setY(moveToTile.getY()+Calculations.random(1,4));
                }
                Walking.walkOnScreen(moveToTile);
                sleep(Calculations.random(300,500));
            }

            // check if any of the little monsters can be axed
            NPC anyGrowthlingWithZeroHealth = NPCs.closest(n -> n != null && n.getHealthPercent() <= 10 && n.getName().equals(config.growthling));
            if(anyGrowthlingWithZeroHealth != null){
                curGrowthling = anyGrowthlingWithZeroHealth;
                while(curGrowthling.exists()) {
                    log("axinggg");
                    if (!Inventory.isItemSelected()) {
                        Inventory.interact(config.bronzeAxe, "Use");
                    }
                    sleepUntil(Inventory::isItemSelected, Calculations.random(700, 1100));
                    if (Inventory.isItemSelected()) {
                        if (curGrowthling.interact("Use")) {
                            sleepUntil(() -> !curGrowthling.exists(), Calculations.random(2000, 3000)); // TODO- this could be better, should maybe check to attack something else?
                        }
                    }
                }
                continue;
            }
            if(getLocalPlayer().getInteractingCharacter() != null && getLocalPlayer().getInteractingCharacter().getName().equals(config.growthling) && getLocalPlayer().getInteractingCharacter().getHealthPercent() <= 10){
                curGrowthling = getLocalPlayer().getInteractingCharacter();
                while(curGrowthling.exists()) {
                    log("axinggg");
                    if (!Inventory.isItemSelected()) {
                        Inventory.interact(config.bronzeAxe, "Use");
                    }
                    sleepUntil(Inventory::isItemSelected, Calculations.random(700, 1100));
                    if (Inventory.isItemSelected()) {
                        if (curGrowthling.interact("Use")) {
                            sleepUntil(() -> !curGrowthling.exists(), Calculations.random(2000, 3000)); // TODO- this could be better, should maybe check to attack something else?
                        }
                    }
                }
                continue;
            }


            // check if any of the little monsters are alive, and attack them if not already attacking one
            NPC anyGrowthlingWithoutZeroHealth = NPCs.closest(n -> n != null && n.getHealthPercent() > 0 && n.getName().equals(config.growthling));
            if(anyGrowthlingWithoutZeroHealth != null && getLocalPlayer().getInteractingCharacter() != null && !getLocalPlayer().getInteractingCharacter().getName().equals(config.growthling)){
                log("attack growthling");
                if(Inventory.isItemSelected()){
                    Inventory.deselect();
                }
                anyGrowthlingWithoutZeroHealth.interact();
                sleepUntil(() -> getLocalPlayer().getInteractingCharacter() != null && getLocalPlayer().getInteractingCharacter().getName().equals(config.growthling),Calculations.random(1000,2000));
            }

            // pre-select axe and hover monster when in combat with a growthling and have enough health
            if(curMonsterAttacking != null && curMonsterAttacking.getName().equals(config.growthling) && getLocalPlayer().getHealthPercent() >= 80){
                if(!Inventory.isItemSelected()) {
                    if(Inventory.interact(config.bronzeAxe, "Use")){
                        Mouse.move(curMonsterAttacking.getTile());
                        continue;
                    }
                }
            }

            // attack bryophyta when there are no growliths alive
            anyGrowthlingWithoutZeroHealth = NPCs.closest(n -> n != null && n.getHealthPercent() > 0 && n.getName().equals(config.growthling));
            if(anyGrowthlingWithoutZeroHealth == null && getLocalPlayer().getInteractingCharacter() != null && !getLocalPlayer().getInteractingCharacter().getName().equals(config.bryophyta)){
                log("attack bryyophyta");
                brophyta.interact("Attack");
            }
        }
        // wait it out a little longer without food to hopefully kill it
        if(brophyta.getHealthPercent() <= 10) {
            return Calculations.random(7000, 10000);
        }
        else{
            return 0;
        }
    }

}
