package tasks.looting;

import methods.LootingMethods;
import org.dreambot.api.methods.container.impl.Inventory;
import tasks.AbstractTask;

public class Loot extends AbstractTask {

    public LootingMethods lm = new LootingMethods();

    @Override
    public boolean accept() {
        return lm.ShouldLootMultipleItemsExceptSingleChaos(config.getLootItemsWithBones())
            && (!Inventory.isFull() || lm.itemToPickUp.getName().equals(config.mossyKey))
            && !getLocalPlayer().isInCombat();
    }

    @Override
    public int execute() {
        log("[T] Loot");
        config.setStatus("Looting " + lm.itemToPickUp.getName());
        lm.EatIfNecessary(lm.itemToPickUp,config.mossyKey);
        lm.PickLootUp(lm.itemToPickUp);
        return 0;
    }

}
