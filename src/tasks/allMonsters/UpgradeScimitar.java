package tasks.allMonsters;

import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.skills.Skill;
import org.dreambot.api.methods.skills.Skills;
import tasks.AbstractTask;

public class UpgradeScimitar extends AbstractTask {
    @Override
    public boolean accept() {
        return (Skills.getRealLevel(Skill.ATTACK) == 10 && Inventory.contains(config.blackScimitar))
                || (Skills.getRealLevel(Skill.ATTACK) == 20 && Inventory.contains(config.mithScimitar))
                || (Skills.getRealLevel(Skill.ATTACK) == 30 && Inventory.contains(config.addyScimitar))
                || (Skills.getRealLevel(Skill.ATTACK) == 40 && Inventory.contains(config.runeScimitar));
    }

    @Override
    // TODO - turn weilding into a method
    public int execute() {
        log("[T] Upgrading scimitar too...");
        if(Skills.getRealLevel(Skill.ATTACK) == 10) {
            log("Black");
            if (Inventory.interact(config.blackScimitar, "Wield")) {
                sleepUntil(() -> Inventory.contains(config.ironScimitar), 4000);
            }
        }
        if(Skills.getRealLevel(Skill.ATTACK) == 20) {
            log("Mith");
            if (Inventory.interact(config.mithScimitar, "Wield")) {
                sleepUntil(() -> Inventory.contains(config.blackScimitar), 4000);
            }
        }
        if(Skills.getRealLevel(Skill.ATTACK) == 30) {
            log("Addy");
            if (Inventory.interact(config.addyScimitar, "Wield")) {
                sleepUntil(() -> Inventory.contains(config.mithScimitar), 4000);
            }
        }
        if(Skills.getRealLevel(Skill.ATTACK) == 40) {
            log("Rune");
            if (Inventory.interact(config.runeScimitar, "Wield")) {
                sleepUntil(() -> Inventory.contains(config.addyScimitar), 4000);
            }
        }

        return 0;
    }
}
