package methods;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.filter.Filter;
import org.dreambot.api.methods.item.GroundItems;
import org.dreambot.api.methods.tabs.Tab;
import org.dreambot.api.methods.tabs.Tabs;
import org.dreambot.api.wrappers.items.GroundItem;
import org.dreambot.api.wrappers.items.Item;

import java.util.Arrays;
import java.util.List;

import static org.dreambot.api.methods.MethodProvider.*;

public class LootingMethods extends AbstractMethod {
    public GroundItem itemToPickUp;
    List<GroundItem> allGroundItems;

    public WalkingMethods wm = new WalkingMethods();
    public CombatMethods cm = new CombatMethods();

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

    public boolean ShouldLootMultipleItemsExceptSingleItem(String[] items, String itemToNotLoot) {
        /*
        // get a list of all ground object
        allGroundItems = GroundItems.all(o -> o != null);

        // if any in the list match, set that item as the item to pick up
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
        */
/*
        List<String> curLootItemsList = Arrays.asList(config.getCurLootItems());
        itemToPickUp = GroundItems.closest(i -> i != null && curLootItemsList.contains(i.getName()));
        if(itemToPickUp != null){
            return true;
        }
        else{
            return false;
        }
*/

        List<String> curLootItemsList = Arrays.asList(config.getCurLootItems());
        itemToPickUp = GroundItems.closest(new Filter<GroundItem>(){
            public boolean match(GroundItem gi) {
                // null check
                if(gi == null || gi.getName() == null){
                    return false;
                }
                // check if contained in our looting list
                if(curLootItemsList.contains(gi.getName())){
                    if(!gi.getName().equals(itemToNotLoot)){
                        return true;
                    }
                    else{
                        if(gi.getAmount() > 1){
                            return true;
                        }
                    }
                }
                return false;
            }
        });
        if(itemToPickUp != null){
            return true;
        }
        else{
            return false;
        }
    }

    public void PickLootUp(GroundItem groundItem){
        if (groundItem != null) {
            log("[m] Pick Loot Up - " + groundItem.getName());
            // TODO - walks right on top of the loot, should walk to an area around it
            if(!groundItem.isOnScreen()){
                wm.Walk(groundItem.getSurroundingArea(5), groundItem.getName());
                sleepUntil(() -> groundItem.isOnScreen(), Calculations.random(4000,5000));
            }
            if (groundItem.interact()) {
                sleepUntil(() -> !groundItem.exists(), 4000);
            }
        }
    }

    public void EatIfNecessary(GroundItem item, String itemCheckAgainst){
        if(item != null && item.getName().equals(itemCheckAgainst) && Inventory.isFull()){
            log("[m] Eat If Necessary..." + itemCheckAgainst);
            cm.EatFood(config.lobster);
        }
    }

    // TODO - randomize drop pattern - have to be smart about it though - too random is worse than always the same
    public void BuryBonesInInv(){
        if(Tabs.isOpen(Tab.INVENTORY) || Tabs.open(Tab.INVENTORY)) {
            for (Item i : Inventory.all()) {
                if (i != null && i.hasAction("Bury")) {
                    if (i.interact("Bury")) {
                        sleep(Calculations.random(800, 1000));
                    }
                }
            }
        }
    }
}
