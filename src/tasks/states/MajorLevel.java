package tasks.states;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.skills.Skill;
import org.dreambot.api.methods.skills.Skills;
import tasks.AbstractTask;

// TODO- use this class to control the states
// TODO- use onStart to load config values based on starting conditions then update config values using this
public class MajorLevel extends AbstractTask {
    @Override
    public boolean accept() {
        return (config.getCurAttackLevel() != Skills.getRealLevel(Skill.ATTACK) && Skills.getRealLevel(Skill.ATTACK) % 10 == 0 && Skills.getRealLevel(Skill.ATTACK) <= 50)
                || (config.getCurStrengthLevel() != Skills.getRealLevel(Skill.STRENGTH) && Skills.getRealLevel(Skill.STRENGTH) % 10 == 0 && Skills.getRealLevel(Skill.STRENGTH) <= 50)
                || (config.getCurDefenceLevel() != Skills.getRealLevel(Skill.DEFENCE) && Skills.getRealLevel(Skill.DEFENCE) % 10 == 0 && Skills.getRealLevel(Skill.DEFENCE) <= 50)
                || (config.getCurDefenceLevel() != Skills.getRealLevel(Skill.DEFENCE) && Skills.getRealLevel(Skill.PRAYER) == 37);
    }

    @Override
    public int execute() {
        log("[T] Major Level Reached");
        config.setCurAttackLevel(Skills.getRealLevel(Skill.ATTACK));
        config.setCurStrengthLevel(Skills.getRealLevel(Skill.STRENGTH));
        config.setCurDefenceLevel(Skills.getRealLevel(Skill.DEFENCE));
        config.setCurPrayerLevel(Skills.getRealLevel(Skill.PRAYER));

        // TODO - this is handled weirdly, not sure what to do about this whole class honestly
        if(Skills.getRealLevel(Skill.PRAYER) == 37){
            config.setCurLootItems(config.getLootItemsNoBones());
            config.setCurLootItemPrices(config.getLootItemsPricesNoBones());
        }


        config.setMajorLevelJustReached(true);
        return Calculations.random(600,1200);
    }
}