package tasks.states;

import config.Config;
import org.dreambot.api.methods.skills.Skill;
import org.dreambot.api.methods.skills.Skills;
import tasks.AbstractTask;

public class ChangeFightingStyle extends AbstractTask {
    @Override
    public boolean accept() {
        if(config.getCurFightingStyle() == null){
            return true;
        }
        if(Skills.getRealLevel(Skill.DEFENCE) < 40 && config.getCurFightingStyle() != Config.FightingStyle.MELEE && config.isTrainingMelee()){
            return true;
        }
        // if you have all base 40 melee, then you train to 60 range no matter what
        if(Skills.getRealLevel(Skill.DEFENCE) >= 40 && Skills.getRealLevel(Skill.RANGED) < 60 && config.getCurFightingStyle() != Config.FightingStyle.RANGED && config.isTrainingRanged()){
            return true;
        }
        // check if need switch back to training melee
        if(config.isBryophytaMelee() && Skills.getRealLevel(Skill.RANGED) >= 60 && config.getCurFightingStyle() != Config.FightingStyle.MELEE){
            return true;
        }
        // already have the reqs for ranged
        if(config.isBryophytaRanged() && Skills.getRealLevel(Skill.RANGED) >= 60 && config.getCurFightingStyle() != Config.FightingStyle.RANGED){
            return true;
        }
        // already have the reqs for melee
        if(config.isBryophytaMelee() && Skills.getRealLevel(Skill.DEFENCE) >= 50 && config.getCurFightingStyle() != Config.FightingStyle.MELEE){
            return true;
        }

        return false;
    }

    @Override
    public int execute() {
        log("[T] Changing Fighting Style...");
        // only melee - will never change - gets set when fight style is still null
        if(config.isMeleeSelected() && !config.isRangedSelected()){
            log("melee");
            config.setCurFightingStyle(Config.FightingStyle.MELEE);
        }
        // only range - will never change - gets set when fight style is still null
        else if(!config.isMeleeSelected() && config.isRangedSelected()){
            log("ranged");
            config.setCurFightingStyle(Config.FightingStyle.RANGED);
        }
        // both selected
        else{
            // no matter what you're fighting bryophtya with, if you are training both you got to all base 40 melee first
            if(Skills.getRealLevel(Skill.DEFENCE) < 40){
                log("melee");
                config.setCurFightingStyle(Config.FightingStyle.MELEE);
            }
            // if you have all base 40 melee, then you train to 60 range no matter what
            else if(Skills.getRealLevel(Skill.DEFENCE) >= 40 && Skills.getRealLevel(Skill.RANGED) < 60){
                log("ranged");
                config.setCurFightingStyle(Config.FightingStyle.RANGED);
            }
            // check if need switch back to training melee
            else if(config.isBryophytaMelee() && Skills.getRealLevel(Skill.RANGED) >= 60){
                log("melee");
                config.setCurFightingStyle(Config.FightingStyle.MELEE);
            }
            // already have the reqs for ranged
            else if(config.isBryophytaRanged() && Skills.getRealLevel(Skill.RANGED) >= 60){
                log("ranged");
                config.setCurFightingStyle(Config.FightingStyle.RANGED);
            }
            // already have the reqs for melee
            else if(config.isBryophytaMelee() && Skills.getRealLevel(Skill.DEFENCE) >= 50){
                log("melee");
                config.setCurFightingStyle(Config.FightingStyle.MELEE);
            }
            else{
                log("Should not get here");
            }
        }

        return 0;
    }

}
