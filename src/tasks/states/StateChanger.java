package tasks.states;

import config.Config;
import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.container.impl.bank.Bank;
import org.dreambot.api.methods.skills.Skill;
import org.dreambot.api.methods.skills.Skills;
import tasks.AbstractTask;

public class StateChanger extends AbstractTask {
    @Override
    public boolean accept() {
        if(config.getCurFightingStyle() == Config.FightingStyle.MELEE) {
            // if any stat is below 20, we are still fighting rats
            if (Skills.getRealLevel(Skill.ATTACK) < 20 || Skills.getRealLevel(Skill.STRENGTH) < 20 || Skills.getRealLevel(Skill.DEFENCE) < 20) {
                if (config.getState() != Config.State.RATS) {
                    return true;
                }
            }
            // if all stats are 20 or above, but any is below 40, we are fighting frogs
            if (Skills.getRealLevel(Skill.ATTACK) >= 20 && Skills.getRealLevel(Skill.STRENGTH) >= 20 && Skills.getRealLevel(Skill.DEFENCE) >= 20) {
                if (Skills.getRealLevel(Skill.ATTACK) < 40 || Skills.getRealLevel(Skill.STRENGTH) < 40 || Skills.getRealLevel(Skill.DEFENCE) < 40) {
                    if (config.getState() != Config.State.FROGS) {
                        return true;
                    }
                }
            }
            // if all stats are 40 or above, but any is under 50, or we do not have enough keys, we are fighting moss giants
            if (Skills.getRealLevel(Skill.ATTACK) >= 40 && Skills.getRealLevel(Skill.STRENGTH) >= 40 && Skills.getRealLevel(Skill.DEFENCE) >= 40) {
                if (Skills.getRealLevel(Skill.ATTACK) < 50 || Skills.getRealLevel(Skill.STRENGTH) < 50 || Skills.getRealLevel(Skill.DEFENCE) < 50) {
                    if (config.getState() != Config.State.MOSSGIANTS) {
                        return true;
                    }
                }
                if (Bank.count(config.mossyKey)+Inventory.count(config.mossyKey) < 3) {
                    if (config.getState() != Config.State.MOSSGIANTS) {
                        return true;
                    }
                }

            }
            // if we are above 50 in all stats, and we have enough keys, or our inventory is already loaded for bryophyta, or we are standing outside his lair, we are fighting bryophyta
            if (Skills.getRealLevel(Skill.ATTACK) >= 50 && Skills.getRealLevel(Skill.STRENGTH) >= 50 && Skills.getRealLevel(Skill.DEFENCE) >= 50) {
                if (Bank.count(config.mossyKey)+Inventory.count(config.mossyKey) >= 3) {
                    if (config.getState() != Config.State.BRYOPHYTA) {
                        return true;
                    }
                }
                if (Inventory.containsAll(config.mossyKey, config.swordfish) || config.bryophytaLairEntrance.contains(getLocalPlayer())) {
                    if (config.getState() != Config.State.BRYOPHYTA) {
                        return true;
                    }
                }
            }
        }
        if(config.getCurFightingStyle() == Config.FightingStyle.RANGED){
            // if any stat is below 20, we are still fighting rats
            if (Skills.getRealLevel(Skill.RANGED) < 20 || Skills.getRealLevel(Skill.DEFENCE) < 20) {
                if (config.getState() != Config.State.RATS) {
                    return true;
                }
            }
            // if all stats are 20 or above, but any is below 40, we are fighting frogs
            if (Skills.getRealLevel(Skill.RANGED) >= 20 && Skills.getRealLevel(Skill.DEFENCE) >= 20) {
                if (Skills.getRealLevel(Skill.RANGED) < 40 || Skills.getRealLevel(Skill.DEFENCE) < 40) {
                    if (config.getState() != Config.State.FROGS) {
                        return true;
                    }
                }
            }
            // if all stats are 40 or above, but range is under 60, or we do not have enough keys, we are fighting moss giants
            if (Skills.getRealLevel(Skill.RANGED) >= 40 && Skills.getRealLevel(Skill.DEFENCE) >= 40) {
                if (Skills.getRealLevel(Skill.RANGED) < 60) {
                    if (config.getState() != Config.State.MOSSGIANTS) {
                        return true;
                    }
                }
                if (Bank.count(config.mossyKey)+Inventory.count(config.mossyKey) < 3) {
                    if (config.getState() != Config.State.MOSSGIANTS) {
                        return true;
                    }
                }

            }
            // if we are range/def is 60/40, and we have enough keys, or our inventory is already loaded for bryophyta, or we are standing outside his lair, we are fighting bryophyta
            if (Skills.getRealLevel(Skill.RANGED) >= 60 && Skills.getRealLevel(Skill.DEFENCE) >= 40) {
                if (Bank.count(config.mossyKey)+Inventory.count(config.mossyKey) >= 3) {
                    if (config.getState() != Config.State.BRYOPHYTA) {
                        return true;
                    }
                }
                if (Inventory.containsAll(config.mossyKey, config.swordfish) || config.bryophytaLairEntrance.contains(getLocalPlayer())) {
                    if (config.getState() != Config.State.BRYOPHYTA) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    // TODO - think should should probably be an if-else so i can have the else set as an error message
    @Override
    public int execute() {
        log("[S] State set to...");
        if(config.getCurFightingStyle() == Config.FightingStyle.MELEE) {

            // if any stat is below 20, we are still fighting rats
            if (Skills.getRealLevel(Skill.ATTACK) < 20 || Skills.getRealLevel(Skill.STRENGTH) < 20 || Skills.getRealLevel(Skill.DEFENCE) < 20) {
                if (config.getState() != Config.State.RATS) {
                    log("RATS");
                    config.setState(Config.State.RATS);
                    config.setCurMonster(config.giantRat);
                }
            }
            // if all stats are 20 or above, but any is below 40, we are fighting frogs
            if (Skills.getRealLevel(Skill.ATTACK) >= 20 && Skills.getRealLevel(Skill.STRENGTH) >= 20 && Skills.getRealLevel(Skill.DEFENCE) >= 20) {
                if (Skills.getRealLevel(Skill.ATTACK) < 40 || Skills.getRealLevel(Skill.STRENGTH) < 40 || Skills.getRealLevel(Skill.DEFENCE) < 40) {
                    if (config.getState() != Config.State.FROGS) {
                        log("FROGS");
                        config.setState(Config.State.FROGS);
                        config.setCurMonster(config.giantFrog);
                    }
                }
            }
            // if all stats are 40 or above, but any is under 50, or we do not have enough keys, we are fighting moss giants
            if (Skills.getRealLevel(Skill.ATTACK) >= 40 && Skills.getRealLevel(Skill.STRENGTH) >= 40 && Skills.getRealLevel(Skill.DEFENCE) >= 40) {
                if (Skills.getRealLevel(Skill.ATTACK) < 50 || Skills.getRealLevel(Skill.STRENGTH) < 50 || Skills.getRealLevel(Skill.DEFENCE) < 50) {
                    if (config.getState() != Config.State.MOSSGIANTS) {
                        log("MOSS GIANTS");
                        config.setState(Config.State.MOSSGIANTS);
                        config.setCurMonster(config.mossGiant);
                    }
                }
                if (Bank.count(config.mossyKey)+Inventory.count(config.mossyKey) < 3) {
                    if (config.getState() != Config.State.MOSSGIANTS) {
                        log("MOSS GIANTS");
                        config.setState(Config.State.MOSSGIANTS);
                        config.setCurMonster(config.mossGiant);
                    }
                }
            }
            // if we are above 50 in all stats, and we have enough keys, or our inventory is already loaded for bryophyta, we are fighting bryophyta
            if (Skills.getRealLevel(Skill.ATTACK) >= 50 && Skills.getRealLevel(Skill.STRENGTH) >= 50 && Skills.getRealLevel(Skill.DEFENCE) >= 50) {
                if (Bank.count(config.mossyKey)+Inventory.count(config.mossyKey) >= 3) {
                    if (config.getState() != Config.State.BRYOPHYTA) {
                        log("BRYOPHYTA");
                        config.setState(Config.State.BRYOPHYTA);
                        config.setCurMonster(config.bryophyta);
                    }
                }
                if (Inventory.containsAll(config.mossyKey, config.swordfish) || config.bryophytaLairEntrance.contains(getLocalPlayer())) { // TODO- update this to be entire dungeon area
                    if (config.getState() != Config.State.BRYOPHYTA) {
                        log("BRYOPHYTA");
                        config.setState(Config.State.BRYOPHYTA);
                        config.setCurMonster(config.bryophyta);
                    }
                }
            }
        }
        if(config.getCurFightingStyle() == Config.FightingStyle.RANGED){
            // if any stat is below 20, we are still fighting rats
            if (Skills.getRealLevel(Skill.RANGED) < 20 || Skills.getRealLevel(Skill.DEFENCE) < 20) {
                if (config.getState() != Config.State.RATS) {
                    log("RATS");
                    config.setState(Config.State.RATS);
                    config.setCurMonster(config.giantRat);
                }
            }
            // if all stats are 20 or above, but any is below 40, we are fighting frogs
            if (Skills.getRealLevel(Skill.RANGED) >= 20 && Skills.getRealLevel(Skill.DEFENCE) >= 20) {
                if (Skills.getRealLevel(Skill.RANGED) < 40 || Skills.getRealLevel(Skill.DEFENCE) < 40) {
                    if (config.getState() != Config.State.FROGS) {
                        log("FROGS");
                        config.setState(Config.State.FROGS);
                        config.setCurMonster(config.giantFrog);
                    }
                }
            }
            // if all stats are 40 or above, but range is under 60, or we do not have enough keys, we are fighting moss giants
            if (Skills.getRealLevel(Skill.RANGED) >= 40 && Skills.getRealLevel(Skill.DEFENCE) >= 40) {
                if (Skills.getRealLevel(Skill.RANGED) < 60) {
                    if (config.getState() != Config.State.MOSSGIANTS) {
                        log("MOSS GIANTS");
                        config.setState(Config.State.MOSSGIANTS);
                        config.setCurMonster(config.mossGiant);
                    }
                }
                if (Bank.count(config.mossyKey)+Inventory.count(config.mossyKey) < 3) {
                    if (config.getState() != Config.State.MOSSGIANTS) {
                        log("MOSS GIANTS");
                        config.setState(Config.State.MOSSGIANTS);
                        config.setCurMonster(config.mossGiant);
                    }
                }

            }
            // if we are range/def is 60/40, and we have enough keys, or our inventory is already loaded for bryophyta, or we are standing outside his lair, we are fighting bryophyta
            if (Skills.getRealLevel(Skill.RANGED) >= 60 && Skills.getRealLevel(Skill.DEFENCE) >= 40) {
                if (Bank.count(config.mossyKey)+Inventory.count(config.mossyKey) >= 3) {
                    if (config.getState() != Config.State.BRYOPHYTA) {
                        log("BRYOPHYTA");
                        config.setState(Config.State.BRYOPHYTA);
                        config.setCurMonster(config.bryophyta);
                    }
                }
                if (Inventory.containsAll(config.mossyKey, config.swordfish) || config.bryophytaLairEntrance.contains(getLocalPlayer())) {
                    if (config.getState() != Config.State.BRYOPHYTA) {
                        log("BRYOPHYTA");
                        config.setState(Config.State.BRYOPHYTA);
                        config.setCurMonster(config.bryophyta);
                    }
                }
            }
        }
        return Calculations.random(2000,3000);
    }
}
