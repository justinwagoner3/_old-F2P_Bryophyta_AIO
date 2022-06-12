package methods;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.combat.Combat;
import org.dreambot.api.methods.combat.CombatStyle;
import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.container.impl.equipment.Equipment;
import org.dreambot.api.methods.interactive.NPCs;
import org.dreambot.api.methods.prayer.Prayer;
import org.dreambot.api.methods.prayer.Prayers;
import org.dreambot.api.methods.skills.Skill;
import org.dreambot.api.methods.skills.Skills;
import org.dreambot.api.methods.tabs.Tab;
import org.dreambot.api.methods.tabs.Tabs;
import org.dreambot.api.wrappers.interactive.NPC;
import org.dreambot.api.wrappers.items.Item;

import static org.dreambot.api.Client.getLocalPlayer;
import static org.dreambot.api.methods.MethodProvider.*;

public class CombatMethods extends AbstractMethod {

    private WalkingMethods wm = new WalkingMethods();

    public void AttackMonster(String monster){
        NPC currentNpc = NPCs.closest(npc -> npc != null && npc.getName() != null && npc.getName().equals(monster) && !npc.isInCombat() && npc.getInteractingCharacter() == null);
        if(currentNpc != null) { //does the npc exist?
            if(!currentNpc.isOnScreen()){
                wm.Walk(currentNpc.getSurroundingArea(5), currentNpc.getName());
                sleepUntil(() -> currentNpc.isOnScreen(), Calculations.random(4000,5000));
            }
            if (currentNpc.interact("Attack")) { //currentNpc will return true if we succesfully attacked the rat, if that happens we want to wait a bit to make sure we are in combat
                log(currentNpc.getName());
                sleepUntil(() -> getLocalPlayer().getCharacterInteractingWithMe() == currentNpc, Calculations.random(7000,10000)); //Wait a max of 2 seconds or until we are in combat
            }
        }
    }

    // TODO- should be bool
    public void EatFood(String food){
        log("[m] Eating... " + food);
        if(Tabs.isOpen(Tab.INVENTORY) || Tabs.open(Tab.INVENTORY)) {
            if (Inventory.interact(food, "Eat")) {
                sleepUntil(() -> getLocalPlayer().getHealthPercent() > config.getNextEatAtPercentage(), 2000);
                // TODO - this should probably be in the task, not method
                if (Skills.getRealLevel(Skill.HITPOINTS) < 40) {
                    config.setNextEatAtPercentage(Calculations.random(20, 40));
                } else {
                    config.setNextEatAtPercentage(Calculations.random(50, 70));
                }
            }
        }
    }

    // TODO- randomize this
    // TODO- could fail by repeatedly drinking if already at max boost
    public void DrinkPotion(String pot1, String pot2, String pot3, String pot4){
        log("[m] Drinking potion...");
        if(Tabs.isOpen(Tab.INVENTORY) || Tabs.open(Tab.INVENTORY)) {
            int curBoostedLevel = Skills.getBoostedLevels(Skill.STRENGTH);
            while(curBoostedLevel == Skills.getBoostedLevels(Skill.STRENGTH)) {
                if (Inventory.contains(pot1)) {
                    if (Inventory.interact(pot1, "Drink")) {
                        log(pot1);
                        sleepUntil(() -> Inventory.contains(config.vial), Calculations.random(2000, 3000));
                        if (Inventory.contains(config.vial)) {
                            Inventory.dropAll(config.vial);
                            sleepUntil(() -> !Inventory.contains(config.vial), Calculations.random(2000, 3000));
                        }
                    }
                } else if (Inventory.contains(pot2)) {
                    Inventory.interact(pot2, "Drink");
                    log(pot2);
                } else if (Inventory.contains(pot3)) {
                    Inventory.interact(pot3, "Drink");
                    log(pot3);
                } else if (Inventory.contains(pot4)) {
                    Inventory.interact(pot4, "Drink");
                    log(pot4);
                } else {
                    log("no poition to drink");
                }

                sleepUntil(() -> curBoostedLevel != Skills.getBoostedLevels(Skill.STRENGTH), Calculations.random(2000, 3000));
            }
        }
    }

    public void SwitchCombatStyle(CombatStyle style){
        log("[m] Switching Combat Style to... ");
        log(style);
        if(Tabs.isOpen(Tab.COMBAT) || Tabs.open(Tab.COMBAT)) {
            Combat.setCombatStyle(style);
        }
        // TODO - sloppy
        if (Combat.getCombatStyle() != style) {
            Combat.setCombatStyle(style);
        }
        return;
    }

    public void EquipAllItemsInInv() {
        log("[m] Equip All Items in Inv");

        // make sure inventory tab is open
        if(Tabs.isOpen(Tab.INVENTORY) || Tabs.open(Tab.INVENTORY)) {

            // equip gear
            for (Item i : Inventory.all()) {
                while (Inventory.contains(i)) {
                    if (i.interact("Wear")) {
                        sleepUntil(() -> Equipment.contains(i), Calculations.random(5000, 6000));
                    } else {
                        if (i.interact("Wield")) {
                            sleepUntil(() -> Equipment.contains(i), Calculations.random(5000, 6000));
                        }
                    }
                }

            }
        }

    }

    public void TurnPrayerOff(Prayer prayer){
        if (Prayers.isActive(prayer)) {
            Prayers.toggle(false,prayer);
        }
    }

    public void TurnPrayerOn(Prayer prayer){
        if (!Prayers.isActive(prayer)) {
            Prayers.toggle(true,prayer);
        }
    }

    public boolean OpenTab(Tab tab){
        if(Tabs.isOpen(tab) || Tabs.open(tab)){
            sleepUntil(() -> Tabs.isOpen(tab), Calculations.random(2000,3000));
        }
        return Tabs.isOpen(tab);
    }

    public boolean TurnAutoRetaliateOn(){
        if(OpenTab(Tab.COMBAT)) {
            if (!Combat.isAutoRetaliateOn()) {
                Combat.toggleAutoRetaliate(true);
                sleepUntil(Combat::isAutoRetaliateOn,Calculations.random(2000,3000));
            }
        }
        return Combat.isAutoRetaliateOn();
    }

    public boolean TurnAutoRetaliateOff(){
        if(OpenTab(Tab.COMBAT)) {
            if (Combat.isAutoRetaliateOn()) {
                Combat.toggleAutoRetaliate(false);
                sleepUntil(() -> !Combat.isAutoRetaliateOn(),Calculations.random(2000,3000));
            }
        }
        return !Combat.isAutoRetaliateOn();
    }

}
