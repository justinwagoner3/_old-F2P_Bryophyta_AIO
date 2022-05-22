package methods;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.container.impl.bank.Bank;
import org.dreambot.api.methods.container.impl.equipment.Equipment;
import org.dreambot.api.methods.grandexchange.GrandExchange;

import static org.dreambot.api.methods.MethodProvider.log;
import static org.dreambot.api.methods.MethodProvider.sleepUntil;

public class GrandExchangeMethods extends AbstractMethod {
    public void BuyGearIfNecessary(String item, int amount, int price){
        log("[m] Buying Gear If Necessary - " + item);
        if(!Bank.contains(item) && !Inventory.contains(item) && !Equipment.contains(item)) {
            // TODO - always buys from slot 1 then 2 then 1 then 2
            if (GrandExchange.buyItem(item, amount, price)) {
                log("yes");
                // TODO - long wait, should increase price if fails
                sleepUntil(GrandExchange::isReadyToCollect, Calculations.random(100000, 200000));
                GrandExchange.collect();
                sleepUntil(() -> Inventory.contains(item), Calculations.random(Calculations.random(2000, 3000)));
            }
        }
    }

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

}
