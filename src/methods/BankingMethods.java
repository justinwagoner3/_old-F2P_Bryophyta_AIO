package methods;

import dataStructures.nameQuantity;
import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.container.impl.bank.Bank;
import org.dreambot.api.methods.container.impl.bank.BankLocation;
import org.dreambot.api.methods.container.impl.bank.BankMode;
import org.dreambot.api.methods.container.impl.equipment.Equipment;

import java.util.ArrayList;
import java.util.List;

import static org.dreambot.api.methods.MethodProvider.*;

public class BankingMethods extends AbstractMethod {

    // TODO - the last withdraw should be a withdraw all
    public void WithdrawXItemsRandom(List<nameQuantity> items) {
        log("[m] Withdraw x Items Random");

        List<nameQuantity> thisItems = new ArrayList<>(items);

        // randomly grab gear
        for (int i = 0; i < thisItems.size(); i = 0) {
            int randomIdx = 0;
            if(thisItems.size() != 1) {
                randomIdx = Calculations.random(0, thisItems.size());
            }
            String curItem = thisItems.get(randomIdx).name;
            int quantity = thisItems.get(randomIdx).quantity;
            WithdrawXItem(curItem,quantity);
            thisItems.remove(randomIdx);
        }
    }

    public void WithdrawAllItemsRandom(String[] items){
        log("[m] Withdrawing All Items Random");

        List<String> thisItems = new ArrayList<>();
        for(String item : items){
            thisItems.add(item);
        }

        // randomly grab
        for (int i = 0; i < thisItems.size(); i = 0) {
            int randomIdx = 0;
            if(thisItems.size() != 1) {
                randomIdx = Calculations.random(0, thisItems.size());
            }
            String curItem = thisItems.get(randomIdx);
            WithdrawAllItemNoted(curItem);
            thisItems.remove(randomIdx);
        }
    }

    public void WithdrawAllButOneItemsRandom(String[] items){
        log("[m] Withdrawing All But One Items Random");

        List<String> thisItems = new ArrayList<>();
        for(String item : items){
            thisItems.add(item);
        }

        // randomly grab
        for (int i = 0; i < thisItems.size(); i = 0) {
            int randomIdx = 0;
            if(thisItems.size() != 1) {
                randomIdx = Calculations.random(0, thisItems.size());
            }
            String curItem = thisItems.get(randomIdx);
            WithdrawAllButOneItemNoted(curItem);
            thisItems.remove(randomIdx);
            log(thisItems);
        }
    }

    public void WithdrawXItem(String name, int quantity){
        log("withdraw " + name);
        if(Bank.contains(name)) {
            while (!Inventory.contains(name)) {
                if (Bank.withdraw(name, quantity)) {
                    sleepUntil(() -> Inventory.contains(name), Calculations.random(5000, 6000));
                }
            }
        }
    }

    public void WithdrawAllItemNoted(String name){
        while(!Inventory.contains(name) && Bank.contains(name)) {
            log("Grabbing " + name);
            if(Bank.setWithdrawMode(BankMode.NOTE)) {
                if (Bank.withdrawAll(name)) {
                    sleepUntil(() -> Inventory.contains(name), Calculations.random(5000, 6000));
                }
            }
        }
    }

    public void WithdrawAllButOneItemNoted(String name){
        if(Bank.count(name) > 1) {
            log("thinks there's more than one of " + name);
            while (!Inventory.contains(name) && Bank.contains(name)) {
                log("Grabbing " + name);
                if (Bank.setWithdrawMode(BankMode.NOTE)) {
                    if (Bank.withdrawAll(name)) {
                        sleepUntil(() -> Inventory.contains(name), Calculations.random(5000, 6000));
                    }
                    if (Bank.deposit(name, 1)) {
                        sleepUntil(() -> Bank.contains(name), Calculations.random(5000, 6000));
                    }
                }
            }
        }
    }

    public void OpenBank() {
        log("[m] Opening Bank");
        while(!Bank.isOpen()){
            if(Bank.open()) {
                log("Bank open");
                sleepUntil(Bank::isOpen, Calculations.random(3000, 4000));
            }
            else sleep(Calculations.random(3000,4000));
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

    public void DepositAllInventory(){
        log("[m] Deposit All Inventory");
        while(!Inventory.isEmpty()){
            if(Bank.depositAllItems()){
                sleepUntil(() -> Inventory.isEmpty(),Calculations.random(2000,3000));
            }
        }
    }

    public void DepositAllGear(){
        log("[m] Deposit All Gear");
        while(!Equipment.isEmpty()){
            if(Bank.depositAllEquipment()){
                sleepUntil(() -> Equipment.isEmpty(),Calculations.random(2000,3000));
            }
        }
    }

    public void WithdrawOnlyCoins(){
        OpenBank();
        if(Inventory.onlyContains(config.coins)){
            CloseBank();
        }
        while(!Inventory.onlyContains(config.coins)) {
            if (!Inventory.isEmpty()) {
                DepositAllInventory();
            }
            if (Inventory.isEmpty()) {
                if (Bank.withdrawAll(config.coins)) {
                    sleepUntil(() -> !Inventory.isEmpty(), Calculations.random(2000, 3000));
                }
            }
        }
    }

    public void WithdrawAllOfOneItem(String name){
        while(!Inventory.contains(name)) {
            if(Bank.setWithdrawMode(BankMode.ITEM)) {
                if (Bank.withdrawAll(name)) {
                    sleepUntil(() -> Inventory.contains(name), Calculations.random(5000, 6000));
                }
            }
        }
    }

}
