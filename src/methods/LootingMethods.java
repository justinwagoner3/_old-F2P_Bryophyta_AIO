package methods;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.item.GroundItems;
import org.dreambot.api.wrappers.items.GroundItem;

import java.util.List;

import static org.dreambot.api.methods.MethodProvider.log;
import static org.dreambot.api.methods.MethodProvider.sleepUntil;

public class LootingMethods extends AbstractMethod {
    public GroundItem itemToPickUp;
    List<GroundItem> allGroundItems;

    public WalkingMethods wm = new WalkingMethods();
    public CombatMethods cm = new CombatMethods();

    // TODO should be bool
    public void LootSingleItem(int itemID) {
        GroundItem groundItem = GroundItems.closest(o -> o != null && o.getID() == itemID);
        PickLootUp(groundItem);
    }

    // Returns true if anything on the ground is something we want to loot
    // Sets the value of the item we want to loot
    public boolean ShouldLootMultipleItems(String[] items) {
        allGroundItems = GroundItems.all(o -> o != null);
        for(int i = 0; i < allGroundItems.size(); i++){
            for(int j = 0; j < items.length; j++){
                if(allGroundItems.get(i).getName().equals(items[j])){
                    itemToPickUp = allGroundItems.get(i);
                    return true;
                }
            }
        }
        return false;
    }



    public boolean ShouldLootMultipleItemsExceptSingleChaos(String[] items) {
        allGroundItems = GroundItems.all(o -> o != null);
        for(int i = 0; i < allGroundItems.size(); i++){
            for(int j = 0; j < items.length; j++){
                if(allGroundItems.get(i).getName().equals(items[j])){
                    if(allGroundItems.get(i).getAmount() > 1 || !allGroundItems.get(i).getName().equals(config.chaosRune)){
                            itemToPickUp = allGroundItems.get(i);
                            return true;
                    }
                }
            }
        }
        return false;
    }

    // TODO - should be bool
    public void PickLootUp(GroundItem groundItem){
        if (groundItem != null) {
            log("[m] Pick Loot Up - " + groundItem.getName());
            if(!groundItem.isOnScreen()){
                wm.Walk(groundItem.getSurroundingArea(5), groundItem.getName());
                sleepUntil(() -> groundItem.isOnScreen(), Calculations.random(4000,5000));
            }
            if (groundItem.interact()) {
                sleepUntil(() -> !groundItem.exists(), 4000);
            }
        }
    }

    // TODO- should be bool
    public void EatIfNecessary(GroundItem item, String itemCheckAgainst){
        if(item != null && item.getName().equals(itemCheckAgainst) && Inventory.isFull()){
            log("[m] Eat If Necessary..." + itemCheckAgainst);
            cm.EatFood(config.lobster);
        }
    }
}
