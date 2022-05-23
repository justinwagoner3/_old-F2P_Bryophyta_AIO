package tasks.allMonsters;

import config.Config;
import org.dreambot.api.methods.skills.Skill;
import org.dreambot.api.methods.skills.Skills;
import tasks.AbstractTask;

// TODO- use this class to control the states
// TODO- use onStart to load config values based on starting conditions then update config values using this
public class MajorLevel extends AbstractTask {
    @Override
    public boolean accept() {
        return (config.getCurAttLvl() != Skills.getRealLevel(Skill.ATTACK) && Skills.getRealLevel(Skill.ATTACK) % 10 == 0)
                || (config.getCurStrLvl() != Skills.getRealLevel(Skill.STRENGTH) && Skills.getRealLevel(Skill.STRENGTH) % 10 == 0)
                || (config.getCurDefLvl() != Skills.getRealLevel(Skill.DEFENCE) && Skills.getRealLevel(Skill.DEFENCE) % 10 == 0)
                || (Skills.getRealLevel(Skill.PRAYER) == 43);
    }

    @Override
    public int execute() {
        log("[T] Major Level Reached");
        config.setCurAttLvl(Skills.getRealLevel(Skill.ATTACK));
        config.setCurStrLvl(Skills.getRealLevel(Skill.STRENGTH));
        config.setCurDefLvl(Skills.getRealLevel(Skill.DEFENCE));



        config.setMajorLevelJustReached(true);
        return 0;
    }
}