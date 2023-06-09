package tasks.combat;

import methods.CombatMethods;
import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.methods.skills.Skill;
import org.dreambot.api.methods.skills.Skills;
import tasks.AbstractTask;

public class DrinkPotion extends AbstractTask {

    private CombatMethods cm = new CombatMethods();

    @Override
    public boolean accept() {
        return (config.largeGiantRatArea.contains(Players.localPlayer()) || config.giantFrogArea.contains(getLocalPlayer()) || config.mossGiantWildernessArea.contains(Players.localPlayer()))
                && Skills.getBoostedLevels(Skill.STRENGTH) - Skills.getRealLevel(Skill.STRENGTH) <= config.getNextDrinkPotionBoostedRealDiff()
                && Inventory.contains(config.strPot1,config.strPot2,config.strPot3,config.strPot4);
    }

    // TODO - should maybe set a var and let some time pass so not drinking exactly when levels change
    @Override
    public int execute() {
        log("[T] Drinking potion at " + Skills.getBoostedLevels(Skill.STRENGTH) + "/" + Skills.getRealLevel(Skill.STRENGTH));
        config.setStatus("Drinking potion at " + Skills.getBoostedLevels(Skill.STRENGTH) + "/" + Skills.getRealLevel(Skill.STRENGTH));
        cm.DrinkPotion(config.strPot1,config.strPot2,config.strPot3,config.strPot4);
        if(Skills.getRealLevel(Skill.STRENGTH) >= 30){
            config.setNextDrinkPotionBoostedRealDiff(Calculations.random(0,3));
        }
        if(Skills.getRealLevel(Skill.STRENGTH) >= 50){
            config.setNextDrinkPotionBoostedRealDiff(Calculations.random(0,4));
        }
        if(Skills.getRealLevel(Skill.STRENGTH) >= 70){
            config.setNextDrinkPotionBoostedRealDiff(Calculations.random(0,5));
        }
        return Calculations.random(600,1200);
    }

}
