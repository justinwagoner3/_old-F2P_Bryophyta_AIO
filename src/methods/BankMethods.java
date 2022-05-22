package methods;

import dataStructures.nameQuantity;
import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.container.impl.bank.Bank;

import java.util.ArrayList;
import java.util.List;

import static org.dreambot.api.methods.MethodProvider.log;
import static org.dreambot.api.methods.MethodProvider.sleepUntil;

public class BankMethods extends AbstractMethod {

    // TODO - the last withdraw should be a withdraw all
    public void WithdrawItemsRandom(List<nameQuantity> items) {
        log("[m] Withdrawing inv at random");

        // save the original list so we can double check
        List<nameQuantity> origItems = new ArrayList<>(items);
        List<nameQuantity> thisItems = new ArrayList<>(items);


        // randomly grab gear
        for (int i = 0; i < thisItems.size(); i = 0) {
            int randomIdx = 0;
            if(thisItems.size() != 1) {
                randomIdx = Calculations.random(0, thisItems.size());
            }
            String curItem = thisItems.get(randomIdx).name;
            int quantity = thisItems.get(randomIdx).quantity;
            if(!Inventory.contains(curItem)) {
                if(Bank.withdraw(curItem,quantity)) {
                    sleepUntil(() -> Inventory.contains(curItem), Calculations.random(5000, 6000));
                }
            }
            thisItems.remove(randomIdx);
        }

        // double check all gear has been grabbed
        for (int i = 0; i < origItems.size(); i++) {
            String curItem = origItems.get(i).name;
            int quantity = origItems.get(i).quantity;
            if (!Inventory.contains(curItem)) {
                if(Bank.withdraw(curItem,quantity)) {
                    sleepUntil(() -> Inventory.contains(curItem), Calculations.random(5000, 6000));
                }
            }
        }

        // triple check all gear has been grabbed
        for (int i = 0; i < origItems.size(); i++) {
            String curItem = origItems.get(i).name;
            int quantity = origItems.get(i).quantity;
            if (!Inventory.contains(curItem)) {
                if(Bank.withdraw(curItem,quantity)) {
                    sleepUntil(() -> Inventory.contains(curItem), Calculations.random(5000, 6000));
                }
            }
        }
    }

    public void OpenBank() {
        log("[m] Opening Bank");
        while(!Bank.isOpen()){
            if(Bank.open()) {
                sleepUntil(Bank::isOpen, Calculations.random(1000, 3000));
            }
        }
    }

    public void CloseBank() {
        log("[m] Closing Bank");
        while(Bank.isOpen()){
            if(Bank.close()) {
                sleepUntil(() -> !Bank.isOpen(), Calculations.random(1000, 3000));
            }
        }
    }

    public void DepositInventory(){
        log("[m] Deposit Inventory");
        while(!Inventory.isEmpty()){
            if(Bank.depositAllItems()){
                sleepUntil(() -> Inventory.isEmpty(),Calculations.random(2000,3000));
            }
        }
    }


}
