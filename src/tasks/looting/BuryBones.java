package tasks.looting;

import methods.BankingMethods;
import methods.LootingMethods;
import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.container.impl.bank.Bank;
import tasks.AbstractTask;

public class BuryBones extends AbstractTask {

    private LootingMethods lm = new LootingMethods();
    private BankingMethods bm = new BankingMethods();


    @Override
    public boolean accept() {
        return (Inventory.isFull() && (Inventory.contains(config.bigBones))) // bury when fighting and inv is full
                || (config.feroxEnclave.contains(getLocalPlayer()) && config.isInitBankOpened() && Bank.count(config.bigBones) >= 28); // bury if you have a full inv
    }


    @Override
    public int execute() {
        log("[T] Burying bones");
        config.setStatus("Burying bones");
        if(config.feroxEnclave.contains(getLocalPlayer())){
            bm.OpenBank();
            bm.DepositAllInventory();
            bm.WithdrawAllOfOneItem(config.bigBones);
            bm.CloseBank();
        }
        lm.BuryBonesInInv();
        return Calculations.random(600,1200);
    }

}
