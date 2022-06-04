package tasks.looting;

import methods.LootingMethods;
import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.container.impl.Inventory;
import tasks.AbstractTask;

public class Loot extends AbstractTask {

    public LootingMethods lm = new LootingMethods();

    // TODO - should loot item if stackable and already in inv
    @Override
    public boolean accept() {

        if(lm.ShouldLootMultipleItemsExceptSingleItem(config.getCurLootItems(),config.chaosRune)){// check if there are items on the ground to loot that aren't single chaos runes
            if(lm.itemToPickUp.getName().equals(config.mossyKey)){ // if it's a key, get it right away
                return true;
            }
            else{
                if(!Inventory.isFull()){ // TODO- update to check if stackable and inv is full with that item in a slot already
                    if(config.mossGiantWildernessArea.contains(getLocalPlayer()) || config.giantFrogArea.contains(getLocalPlayer())) { // don't loot unless you are in monster areas; TODO - this should be loot when loot is within x distance of me
                        //if(!getLocalPlayer().isInCombat()){ // don't leave combat
                        //    log("looting because not in combat");
                        //    return true;
                        //}
                        //if (getLocalPlayer().getCharacterInteractingWithMe() != null && getLocalPlayer().getCharacterInteractingWithMe().getHealthPercent() == 0) { // can leave once they have 0 health
                        //if (getLocalPlayer().getInteractingCharacter() == null || (getLocalPlayer().getCharacterInteractingWithMe() != null && getLocalPlayer().getCharacterInteractingWithMe().getHealthPercent() == 0)) { // can leave once they have 0 health
                        if(!getLocalPlayer().isInCombat() && !getLocalPlayer().isMoving()){
                            return true;
                        }
                        if (getLocalPlayer().isInCombat() && getLocalPlayer().getCharacterInteractingWithMe() == null && !getLocalPlayer().isMoving()) {
                            return true;
                        }
                    }
                }

            }
        }
        return false;
    }

    @Override
    public int execute() {
        log("[T] Loot");
        config.setStatus("Looting " + lm.itemToPickUp.getName());
        lm.EatIfNecessary(lm.itemToPickUp,config.mossyKey);
        lm.PickLootUp(lm.itemToPickUp);
        return Calculations.random(400,800);
    }

}
