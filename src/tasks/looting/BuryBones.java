package tasks.looting;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.wrappers.items.Item;
import tasks.AbstractTask;

public class BuryBones extends AbstractTask {
    @Override
    public boolean accept() {
        return Inventory.isFull()
                && (Inventory.contains(config.bigBones) || Inventory.contains(config.bones));
    }

    @Override
    public int execute() {
        log("[T] Burying bones");
        config.setStatus("Burying bones");
        for (Item i: Inventory.all()) {
            if (i != null && i.hasAction("Bury")) {
                if (i.interact("Bury")) {
                    sleep(Calculations.random(500,700));
                }
            }
        }

        return 0;
    }

}
