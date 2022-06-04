package tasks.states;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.skills.Skill;
import org.dreambot.api.methods.skills.Skills;
import tasks.AbstractTask;

public class UpgradeShortbowAndCoif extends AbstractTask {
    @Override
    public boolean accept() {

        return (Skills.getRealLevel(Skill.RANGED) == 5 && Inventory.contains(config.oakShortbow))
                || (Skills.getRealLevel(Skill.RANGED) == 20 && Inventory.contains(config.willowShortbow))
                || (Skills.getRealLevel(Skill.RANGED) == 30 && Inventory.contains(config.mapleShortbow));

    }

    @Override
    public int execute() {

        log("[T] Upgrading shortbow to...");
        config.setStatus("Upgrading shortbow");
        if(Skills.getRealLevel(Skill.RANGED) == 5) {
            log("Oak");
            if (Inventory.interact(config.oakShortbow, "Wield")) {
                sleepUntil(() -> Inventory.contains(config.shortbow), 4000);
            }
        }
        if(Skills.getRealLevel(Skill.RANGED) == 20) {
            log("Willow");
            if (Inventory.interact(config.willowShortbow, "Wield")) {
                sleepUntil(() -> Inventory.contains(config.oakShortbow), 4000);
            }
            log("coif");
            if (Inventory.interact(config.coif, "Wear")) {
                sleepUntil(() -> Inventory.contains(config.leatherCowl), 4000);
            }
            log("studded chaps");
            if (Inventory.interact(config.studdedChaps, "Wear")) {
                sleepUntil(() -> Inventory.contains(config.leatherChaps), 4000);
            }
            log("mithril arrows");
            if (Inventory.interact(config.mithArrow, "Wield")) {
                sleepUntil(() -> Inventory.contains(config.ironArrow), 4000);
            }
        }
        if(Skills.getRealLevel(Skill.RANGED) == 30) {
            log("Maple");
            if (Inventory.interact(config.mapleShortbow, "Wield")) {
                sleepUntil(() -> Inventory.contains(config.willowShortbow), 4000);
            }
        }

        return Calculations.random(600,1200);
    }
}
