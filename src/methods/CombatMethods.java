package methods;

import dataStructures.nameQuantity;
import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.combat.Combat;
import org.dreambot.api.methods.combat.CombatStyle;
import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.skills.Skill;
import org.dreambot.api.methods.skills.Skills;

import java.util.ArrayList;
import java.util.List;

import static org.dreambot.api.Client.getLocalPlayer;
import static org.dreambot.api.methods.MethodProvider.*;

public class CombatMethods extends AbstractMethod {

    // TODO- should be bool
    public void EatFood(String food){
        log("[m] Eating... " + food);
        if(Inventory.interact(food,"Eat")) {
            sleepUntil(() -> getLocalPlayer().getHealthPercent() > config.getNextEatAtPercentage(),2000);
            if(Skills.getRealLevel(Skill.HITPOINTS) < 20) {
                config.setNextEatAtPercentage(Calculations.random(20, 40));
            }
            else{
                config.setNextEatAtPercentage(Calculations.random(40, 70));
            }
        }

    }

    // TODO- randomize this and turn this into a method
    // TODO- might be better to sleep until potion is one dose lower
    public void DrinkPotion(String pot1, String pot2, String pot3, String pot4){
        log("[m] Drinking potion...");
        int curBoostedLevel = Skills.getBoostedLevels(Skill.STRENGTH);
        if(Inventory.contains(pot1)){
            if(Inventory.interact(pot1,"Drink")){
                log(pot1);
                sleepUntil(() -> Inventory.contains(config.vial),Calculations.random(2000,3000));
                if(Inventory.contains(config.vial)){
                    Inventory.dropAll(config.vial);
                    sleepUntil(() -> !Inventory.contains(config.vial),Calculations.random(2000,3000));
                }
            }
        }
        else if(Inventory.contains(pot2)){
            Inventory.interact(pot2,"Drink");
            log(pot2);
        }
        else if(Inventory.contains(pot3)){
            Inventory.interact(pot3,"Drink");
            log(pot3);
        }
        else{
            Inventory.interact(pot4,"Drink");
            log(pot4);
        }

        sleepUntil(() -> curBoostedLevel != Skills.getBoostedLevels(Skill.STRENGTH), Calculations.random(2000,3000));
    }
    public void SwitchCombatStyle(CombatStyle style){
        log("[m] Switching Combat Style to... ");
        log(style);
        Combat.setCombatStyle(style);
        sleep(Calculations.random(1000, 2000));
        if (Combat.getCombatStyle() != style) {
            Combat.setCombatStyle(style);
        }
        return;
    }

    // TODO - sloppy code
    public void EquipItemsRandom(List<nameQuantity> items) {
        log("[m] starting equip items");
        // save the original list so we can double check
        List<nameQuantity> origItems = new ArrayList<>(items);
        List<nameQuantity> thisItems = new ArrayList<>(items);

        // randomly grab gear
        for (int i = 0; i < thisItems.size(); i = 0) {
            int randomIdx = 0;
            if(thisItems.size() != 1) {
                randomIdx = Calculations.random(0, thisItems.size());
            }
            String curItem = thisItems.get(randomIdx).name;
            if(Inventory.contains(curItem)) {
                if(Inventory.interact(curItem,"Wear")) {
                    sleepUntil(() -> !Inventory.contains(curItem), Calculations.random(5000, 6000));
                }
                else{
                    if(Inventory.interact(curItem,"Wield")) {
                        sleepUntil(() -> !Inventory.contains(curItem), Calculations.random(5000, 6000));
                    }
                }
            }
            thisItems.remove(randomIdx);
        }

        // double check all gear has been grabbed
        for (int i = 0; i < origItems.size(); i++) {
            String curItem = origItems.get(i).name;
            if(Inventory.interact(curItem,"Wear")) {
                sleepUntil(() -> !Inventory.contains(curItem), Calculations.random(5000, 6000));
            }
            else{
                if(Inventory.interact(curItem,"Wield")) {
                    sleepUntil(() -> !Inventory.contains(curItem), Calculations.random(5000, 6000));
                }
            }
        }

        // triple check all gear has been grabbed
        for (int i = 0; i < origItems.size(); i++) {
            String curItem = origItems.get(i).name;
            if(Inventory.interact(curItem,"Wear")) {
                sleepUntil(() -> !Inventory.contains(curItem), Calculations.random(5000, 6000));
            }
            else{
                if(Inventory.interact(curItem,"Wield")) {
                    sleepUntil(() -> !Inventory.contains(curItem), Calculations.random(5000, 6000));
                }
            }
        }
    }
}
