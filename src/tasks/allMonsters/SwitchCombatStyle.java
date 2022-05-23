package tasks.allMonsters;

import methods.CombatMethods;
import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.combat.Combat;
import org.dreambot.api.methods.combat.CombatStyle;
import org.dreambot.api.methods.skills.Skill;
import org.dreambot.api.methods.skills.Skills;
import org.dreambot.api.methods.tabs.Tab;
import org.dreambot.api.methods.tabs.Tabs;
import tasks.AbstractTask;

public class SwitchCombatStyle extends AbstractTask {

    private CombatMethods cm = new CombatMethods();

    // TODO- adjust so it always checks, based on all different level combinations,
    @Override
    public boolean accept() {
        return config.isMajorLevelJustReached() // increments of 10
                || (Skills.getRealLevel(Skill.STRENGTH) % 10 != 0 && Combat.getCombatStyle() != CombatStyle.STRENGTH); // for account initialization
    }

    @Override
    public int execute() {
        log("[T] Switch Combat Style");
        config.setStatus("Switch Combat Style");
        if(Skills.getRealLevel(Skill.STRENGTH) % 10 != 0 || Skills.getRealLevel(Skill.STRENGTH) > 40) {
            cm.SwitchCombatStyle(CombatStyle.STRENGTH);
        }
        else if(Skills.getRealLevel(Skill.STRENGTH) > Skills.getRealLevel(Skill.ATTACK)) {
            cm.SwitchCombatStyle(CombatStyle.ATTACK);
        }
        else if (Skills.getRealLevel(Skill.ATTACK) > Skills.getRealLevel(Skill.DEFENCE)) {
            cm.SwitchCombatStyle(CombatStyle.DEFENCE);
        }
        else {
            cm.SwitchCombatStyle(CombatStyle.STRENGTH);
        }
        config.setMajorLevelJustReached(false);
        return 0;
    }

}
