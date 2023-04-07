package methods;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.container.impl.bank.Bank;
import org.dreambot.api.methods.container.impl.equipment.Equipment;
import org.dreambot.api.methods.grandexchange.GrandExchange;
import org.dreambot.api.wrappers.items.Item;

import static org.dreambot.api.methods.MethodProvider.log;
import static org.dreambot.api.methods.MethodProvider.sleepUntil;

public class GrandExchangeMethods extends AbstractMethod {

    private int price;

    // TODO- sloppy
    public void BuyGearIfNecessarySingleItem(String item){
        log("[m] Buying Gear If Necessary Single Item - " + item);
        // check if you need to but the item
        if((!Bank.contains(item) && !Inventory.contains(item) && !Equipment.contains(item))){
            // check if you need to collect
            CollectGE();

            // determine the price
            // TODO- this method sucks, probably should just rework to not use buyItem and do it manually instead
            if(Inventory.count(config.coins) > 100000){
                price = 100000;
            }
            else{
                price = Inventory.count(config.coins);
            }
            // buy item
            if (GrandExchange.buyItem(item, 1, price)) {
                log("yes");
                sleepUntil(GrandExchange::isReadyToCollect, Calculations.random(10000, 20000));
                GrandExchange.collect();
                sleepUntil(() -> !GrandExchange.isReadyToCollect(), Calculations.random(Calculations.random(2000, 3000)));
            }
        }
    }


    public void BuyGearIfNecessaryMultipleOfItem(String item, int whenToBuy, int howManyToBuy){
        log("[m] Buying Gear If Necessary Mulitple of Item - " + item);

        // buy item is combined count of item is too low
        if(Bank.count(item) + Inventory.count(item) + Equipment.count(item) < whenToBuy){
            // check if you need to collect
            CollectGE();

            price = Inventory.count(config.coins)/howManyToBuy;
            if (GrandExchange.buyItem(item, howManyToBuy, price)) {
                log("yes");
                sleepUntil(GrandExchange::isReadyToCollect, Calculations.random(10000, 20000));
                GrandExchange.collect();
                sleepUntil(() -> !GrandExchange.isReadyToCollect(), Calculations.random(Calculations.random(2000, 3000)));
            }
        }
    }

    public boolean BuyItem(String item, int amount){
        CollectGE();
        if(GrandExchange.openBuyScreen(Calculations.random(1,3))){
            sleepUntil(GrandExchange::isBuyOpen,Calculations.random(2000,3000));

        }
        return false;

    }

    public boolean ButItems(String[] items, int[] amounts){
        return false;
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
        while(!GrandExchange.isOpen()) {
            if (GrandExchange.isOpen() || GrandExchange.open()) {
                sleepUntil(GrandExchange::isOpen, Calculations.random(2000, 3000));
            }
        }
    }
    public boolean CloseGE(){
        log("[m] Close GE");
        if(!GrandExchange.isOpen() || GrandExchange.close()){
            sleepUntil(() -> !GrandExchange.isOpen(), Calculations.random(2000,3000));
        }
        return !GrandExchange.isOpen();
    }

    public boolean CollectGE(){
        log("[m] Collect GE");
        if(GrandExchange.isReadyToCollect()){
            if(GrandExchange.collect()) {
                sleepUntil(() -> !GrandExchange.isReadyToCollect(), Calculations.random(Calculations.random(2000, 3000)));
            }
        }
        return !GrandExchange.isReadyToCollect();
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
