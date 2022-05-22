package tasks.combat;

import methods.CombatMethods;
import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.interactive.Players;
import tasks.AbstractTask;

public class EatFood extends AbstractTask {

    private CombatMethods cm = new CombatMethods();
    @Override
    public boolean accept() {
        return Players.localPlayer().getHealthPercent() <= config.getNextEatAtPercentage();
    }

    @Override
    public int execute() {
        log("[T] Eating food");
        cm.EatFood(config.lobster);
        return Calculations.random(800,1400);
    }

}
