package methods;

import dataStructures.nameQuantity;
import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.container.impl.bank.Bank;
import org.dreambot.api.methods.container.impl.bank.BankMode;
import org.dreambot.api.methods.container.impl.equipment.Equipment;

import java.util.ArrayList;
import java.util.List;

import static org.dreambot.api.methods.MethodProvider.*;

public class BankingMethods extends AbstractMethod {

    // TODO - the last withdraw should be a withdraw all
    public void WithdrawXItemsRandom(List<nameQuantity> items) {
        log("[m] Withdrawing x inv at random");

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
        log("[m] Withdrawing x inv at random");

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
            WithAllItemNoted(curItem);
            thisItems.remove(randomIdx);
            log(thisItems);
        }
    }

    public void WithdrawXItem(String name, int quantity){
        log("withdraw " + name);
        while(!Inventory.contains(name)) {
            if(Bank.withdraw(name,quantity)) {
                sleepUntil(() -> Inventory.contains(name), Calculations.random(5000, 6000));
            }
        }
    }

    public void WithAllItemNoted(String name){
        while(!Inventory.contains(name) && Bank.contains(name)) {
            log("Grabbing " + name);
            if(Bank.setWithdrawMode(BankMode.NOTE)) {
                if (Bank.withdrawAll(name)) {
                    sleepUntil(() -> Inventory.contains(name), Calculations.random(5000, 6000));
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
