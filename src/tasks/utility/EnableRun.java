package tasks.utility;

import methods.WalkingMethods;
import org.dreambot.api.methods.walking.impl.Walking;
import tasks.AbstractTask;

public class EnableRun extends AbstractTask {

    private WalkingMethods wm = new WalkingMethods();

    @Override
    public boolean accept() {
        if(!Walking.isRunEnabled()){
            if (Walking.getRunEnergy() > config.getNextRunEnergyPercentage()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int execute() {
        log("[T] Enable Run");
        wm.EnableRun();
        return 0;
    }
}
