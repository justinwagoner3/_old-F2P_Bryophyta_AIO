package methods;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.container.impl.bank.Bank;
import org.dreambot.api.methods.container.impl.equipment.Equipment;
import org.dreambot.api.methods.grandexchange.GrandExchange;
import org.dreambot.api.wrappers.items.Item;

import java.util.ArrayList;

import static org.dreambot.api.methods.MethodProvider.log;
import static org.dreambot.api.methods.MethodProvider.sleepUntil;

public class GrandExchangeMethods extends AbstractMethod {

    private int price;

    public void BuyGearIfNecessary(String item, int amount){
        log("[m] Buying Gear If Necessary - " + item);
        // check if you need to but the item
        if(!Bank.contains(item) && !Inventory.contains(item) && !Equipment.contains(item)) {
            // check if you need to collect
            if(GrandExchange.isReadyToCollect()){
                GrandExchange.collect();
                sleepUntil(() -> !GrandExchange.isReadyToCollect(), Calculations.random(Calculations.random(2000, 3000)));
            }

            // buy the item
            if(amount == 1){
                if(Inventory.count(config.coins) > 100000){
                    price = 100000;
                }
            }
            else{
                price = Inventory.count(config.coins)/amount;
            }
            if (GrandExchange.buyItem(item, amount, price)) {
                log("yes");
                sleepUntil(GrandExchange::isReadyToCollect, Calculations.random(10000, 20000));
                GrandExchange.collect();
                sleepUntil(() -> Inventory.contains(item), Calculations.random(Calculations.random(2000, 3000)));
            }
        }
    }

    /*
    // TODO- assumes you only need to buy one thing
    // for now only used with ranged gear
    // TODO- BuyGear half of it uses this function and other half the one above
    public void BuyGearIfNecessary(ArrayList<String> items){
        log("[m] Buying Gear If Necessary (list)");
        // check if you need to but the item
        for(String item : items) {
            if (!Bank.contains(item) && !Inventory.contains(item) && !Equipment.contains(item)) {
                // check if you need to collect
                if (GrandExchange.isReadyToCollect()) {
                    GrandExchange.collect();
                    sleepUntil(() -> !GrandExchange.isReadyToCollect(), Calculations.random(Calculations.random(2000, 3000)));
                }

                // buy the item
                if (GrandExchange.buyItem(item, 1, Inventory.count(config.coins))) {
                    sleepUntil(GrandExchange::isReadyToCollect, Calculations.random(100000, 200000));
                    GrandExchange.collect();
                    sleepUntil(() -> Inventory.contains(item), Calculations.random(Calculations.random(2000, 3000)));
                }
            }
        }
    }


     */
    public void OpenGE(){
        log("[m] Open GE");
        while(!GrandExchange.isOpen()){
            GrandExchange.open();
            sleepUntil(GrandExchange::isOpen, Calculations.random(2000,3000));
        }
    }
    public void CloseGE(){
        log("[m] Close GE");
        while(GrandExchange.isOpen()){
            GrandExchange.close();
            sleepUntil(() -> !GrandExchange.isOpen(), Calculations.random(2000,3000));
        }
    }

    // TODO - sloppy
    public void SellAllItemsInInv(){
        if(GrandExchange.isReadyToCollect()){
            GrandExchange.collect();
            sleepUntil(() -> !GrandExchange.isReadyToCollect(),4000);
        }
        for(Item i : Inventory.all()){
            if (i != null) {
                if(GrandExchange.sellItem(i.getName(),i.getAmount(),1)) {
                    log("Selling " + i.getName());
                    // TODO - long wait, should decrease price if fails
                    sleepUntil(GrandExchange::isReadyToCollect, Calculations.random(100000, 200000));
                    GrandExchange.collect();
                    sleepUntil(() -> !Inventory.contains(i), Calculations.random(Calculations.random(2000, 3000)));
                }
            }
        }
    }
}
