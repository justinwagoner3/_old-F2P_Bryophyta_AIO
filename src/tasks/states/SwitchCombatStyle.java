package tasks.states;

import config.Config;
import methods.CombatMethods;
import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.combat.Combat;
import org.dreambot.api.methods.combat.CombatStyle;
import org.dreambot.api.methods.container.impl.equipment.Equipment;
import org.dreambot.api.methods.skills.Skill;
import org.dreambot.api.methods.skills.Skills;
import tasks.AbstractTask;

public class SwitchCombatStyle extends AbstractTask {

    private CombatMethods cm = new CombatMethods();

    // TODO- sloppy
    @Override
    public boolean accept() {
        if(config.getCurFightingStyle() == Config.FightingStyle.MELEE) {
            // quick false return since will be the case most of the script
            if (Combat.getCombatStyle() == CombatStyle.STRENGTH && Skills.getRealLevel(Skill.ATTACK) >= 50 && Skills.getRealLevel(Skill.STRENGTH) >= 50 && Skills.getRealLevel(Skill.DEFENCE) >= 50) {
                return false;
            }
            // most common return true
            if (Combat.getCombatStyle() != CombatStyle.STRENGTH && Skills.getRealLevel(Skill.DEFENCE) >= 50) {
                // set strength
                return true;
            }

            if (Combat.getCombatStyle() != CombatStyle.STRENGTH && Skills.getRealLevel(Skill.STRENGTH) < 20) {
                // set strength
                return true;
            }
            if (Combat.getCombatStyle() != CombatStyle.ATTACK && Skills.getRealLevel(Skill.STRENGTH) >= 20 && Skills.getRealLevel(Skill.ATTACK) < 20) {
                // set attack
                return true;
            }
            if (Combat.getCombatStyle() != CombatStyle.DEFENCE && Skills.getRealLevel(Skill.STRENGTH) >= 20 && Skills.getRealLevel(Skill.ATTACK) >= 20 && Skills.getRealLevel(Skill.DEFENCE) < 20) {
                // set defence
                return true;
            }
            if (Combat.getCombatStyle() != CombatStyle.STRENGTH && Skills.getRealLevel(Skill.DEFENCE) >= 20 && Skills.getRealLevel(Skill.ATTACK) >= 20 && Skills.getRealLevel(Skill.STRENGTH) < 40) {
                // set strength
                return true;
            }
            if (Combat.getCombatStyle() != CombatStyle.ATTACK && Skills.getRealLevel(Skill.DEFENCE) >= 20 && Skills.getRealLevel(Skill.STRENGTH) >= 40 && Skills.getRealLevel(Skill.ATTACK) < 40) {
                // set attack
                return true;
            }
            if (Combat.getCombatStyle() != CombatStyle.DEFENCE && Skills.getRealLevel(Skill.ATTACK) >= 40 && Skills.getRealLevel(Skill.STRENGTH) >= 40 && Skills.getRealLevel(Skill.DEFENCE) < 40) {
                // set defence
                return true;
            }
            if (Combat.getCombatStyle() != CombatStyle.STRENGTH && Skills.getRealLevel(Skill.ATTACK) >= 40 && Skills.getRealLevel(Skill.DEFENCE) >= 40 && Skills.getRealLevel(Skill.STRENGTH) < 50) {
                // set strength
                return true;
            }
            if (Combat.getCombatStyle() != CombatStyle.ATTACK && Skills.getRealLevel(Skill.STRENGTH) >= 50 && Skills.getRealLevel(Skill.DEFENCE) >= 40 && Skills.getRealLevel(Skill.ATTACK) < 50) {
                // set attack
                return true;
            }
            if (Combat.getCombatStyle() != CombatStyle.DEFENCE && Skills.getRealLevel(Skill.STRENGTH) >= 50 && Skills.getRealLevel(Skill.ATTACK) >= 50 && Skills.getRealLevel(Skill.DEFENCE) < 50) {
                // set defence
                return true;
            }
        }
        if(config.getCurFightingStyle() == Config.FightingStyle.RANGED && Equipment.contains(config.shortbow,config.oakShortbow,config.willowShortbow,config.mapleShortbow)) {
            // most common return true
            if (Combat.getCombatStyle() != CombatStyle.RANGED_RAPID && Skills.getRealLevel(Skill.DEFENCE) >= 40) {
                // set rapid
                return true;
            }
            if (Combat.getCombatStyle() != CombatStyle.RANGED_RAPID && Skills.getRealLevel(Skill.RANGED) < 20) {
                // set rapid
                return true;
            }
            if (Combat.getCombatStyle() != CombatStyle.RANGED_DEFENCE && Skills.getRealLevel(Skill.RANGED) >= 20 && Skills.getRealLevel(Skill.DEFENCE) < 20) {
                // set defence
                return true;
            }
            if (Combat.getCombatStyle() != CombatStyle.RANGED_RAPID && Skills.getRealLevel(Skill.DEFENCE) >= 20 && Skills.getRealLevel(Skill.RANGED) < 40) {
                // set rapid
                return true;
            }
            if (Combat.getCombatStyle() != CombatStyle.RANGED_DEFENCE && Skills.getRealLevel(Skill.RANGED) >= 40 && Skills.getRealLevel(Skill.DEFENCE) < 40) {
                // set defense
                return true;
            }
        }
        return false;
    }

    @Override
    public int execute() {
        log("[T] Switch Combat Style... ");
        config.setStatus("Switch Combat Style");
        if(config.getCurFightingStyle() == Config.FightingStyle.MELEE) {
            if (Combat.getCombatStyle() != CombatStyle.STRENGTH && Skills.getRealLevel(Skill.DEFENCE) >= 50 && Skills.getRealLevel(Skill.ATTACK) >= 50) {
                // set strength
                log("strength");
                cm.SwitchCombatStyle(CombatStyle.STRENGTH);
            } else if (Combat.getCombatStyle() != CombatStyle.STRENGTH && Skills.getRealLevel(Skill.STRENGTH) < 20) {
                // set strength
                log("strength");
                cm.SwitchCombatStyle(CombatStyle.STRENGTH);
            } else if (Combat.getCombatStyle() != CombatStyle.ATTACK && Skills.getRealLevel(Skill.STRENGTH) >= 20 && Skills.getRealLevel(Skill.ATTACK) < 20) {
                // set attack
                log("attack");
                cm.SwitchCombatStyle(CombatStyle.ATTACK);
            } else if (Combat.getCombatStyle() != CombatStyle.DEFENCE && Skills.getRealLevel(Skill.STRENGTH) >= 20 && Skills.getRealLevel(Skill.ATTACK) >= 20 && Skills.getRealLevel(Skill.DEFENCE) < 20) {
                // set defence
                log("defence");
                cm.SwitchCombatStyle(CombatStyle.DEFENCE);
            } else if (Combat.getCombatStyle() != CombatStyle.STRENGTH && Skills.getRealLevel(Skill.DEFENCE) >= 20 && Skills.getRealLevel(Skill.ATTACK) >= 20 && Skills.getRealLevel(Skill.STRENGTH) < 40) {
                // set strength
                log("strength");
                cm.SwitchCombatStyle(CombatStyle.STRENGTH);
            } else if (Combat.getCombatStyle() != CombatStyle.ATTACK && Skills.getRealLevel(Skill.DEFENCE) >= 20 && Skills.getRealLevel(Skill.STRENGTH) >= 40 && Skills.getRealLevel(Skill.ATTACK) < 40) {
                // set attack
                log("attack");
                cm.SwitchCombatStyle(CombatStyle.ATTACK);
            } else if (Combat.getCombatStyle() != CombatStyle.DEFENCE && Skills.getRealLevel(Skill.ATTACK) >= 40 && Skills.getRealLevel(Skill.STRENGTH) >= 40 && Skills.getRealLevel(Skill.DEFENCE) < 40) {
                // set defence
                log("defence");
                cm.SwitchCombatStyle(CombatStyle.DEFENCE);
            } else if (Combat.getCombatStyle() != CombatStyle.STRENGTH && Skills.getRealLevel(Skill.ATTACK) >= 40 && Skills.getRealLevel(Skill.DEFENCE) >= 40 && Skills.getRealLevel(Skill.STRENGTH) < 50) {
                // set strength
                log("strength");
                cm.SwitchCombatStyle(CombatStyle.STRENGTH);
            } else if (Combat.getCombatStyle() != CombatStyle.ATTACK && Skills.getRealLevel(Skill.STRENGTH) >= 50 && Skills.getRealLevel(Skill.DEFENCE) >= 40 && Skills.getRealLevel(Skill.ATTACK) < 50) {
                // set attack
                log("attack");
                cm.SwitchCombatStyle(CombatStyle.ATTACK);
            } else if (Combat.getCombatStyle() != CombatStyle.DEFENCE && Skills.getRealLevel(Skill.STRENGTH) >= 50 && Skills.getRealLevel(Skill.ATTACK) >= 50 && Skills.getRealLevel(Skill.DEFENCE) < 50) {
                // set defence
                log("defence");
                cm.SwitchCombatStyle(CombatStyle.DEFENCE);
            } else {
                log("[E] Could not determine Switch Combat Style logic");
            }
        }
        if(config.getCurFightingStyle() == Config.FightingStyle.RANGED) {
            // most common return true
            if (Combat.getCombatStyle() != CombatStyle.RANGED_RAPID && Skills.getRealLevel(Skill.DEFENCE) >= 40) {
                // set rapid
                log("rapid");
                cm.SwitchCombatStyle(CombatStyle.RANGED_RAPID);
            }
            else if (Combat.getCombatStyle() != CombatStyle.RANGED_RAPID && Skills.getRealLevel(Skill.RANGED) < 20) {
                // set rapid
                log("rapid");
                cm.SwitchCombatStyle(CombatStyle.RANGED_RAPID);
            }
            else if (Combat.getCombatStyle() != CombatStyle.RANGED_DEFENCE && Skills.getRealLevel(Skill.RANGED) >= 20 && Skills.getRealLevel(Skill.DEFENCE) < 20) {
                // set defence
                log("defence");
                cm.SwitchCombatStyle(CombatStyle.RANGED_DEFENCE);
            }
            else if (Combat.getCombatStyle() != CombatStyle.RANGED_RAPID && Skills.getRealLevel(Skill.DEFENCE) >= 20 && Skills.getRealLevel(Skill.RANGED) < 40) {
                // set rapid
                log("rapid");
                cm.SwitchCombatStyle(CombatStyle.RANGED_RAPID);
            }
            else if (Combat.getCombatStyle() != CombatStyle.RANGED_DEFENCE && Skills.getRealLevel(Skill.RANGED) >= 40 && Skills.getRealLevel(Skill.DEFENCE) < 40) {
                // set defense
                log("defense");
                cm.SwitchCombatStyle(CombatStyle.RANGED_DEFENCE);
            }
            else{
                log("Could not determine ranged switch combat style logic");
            }
        }
        return Calculations.random(600,1200);
    }

}
