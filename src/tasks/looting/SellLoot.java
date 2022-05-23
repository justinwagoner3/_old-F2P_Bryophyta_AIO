package tasks.looting;

import methods.BankMethods;
import methods.GrandExchangeMethods;
import methods.WalkingMethods;
import org.dreambot.api.methods.container.impl.bank.Bank;
import tasks.AbstractTask;

public class SellLoot extends AbstractTask {
    private WalkingMethods wm = new WalkingMethods();
    private BankMethods bm = new BankMethods();
    private GrandExchangeMethods gem = new GrandExchangeMethods();

    @Override
    public boolean accept() {
        return config.isInitBankOpened() && Bank.count(config.coins) < 50000;
    }

    @Override
    public int execute() {
        wm.Walk(config.grandExchangeArea,"GE");

        // open and deposit
        bm.OpenBank();
        bm.DepositAllInventory();

        // from list of things looted
        bm.WithdrawAllItemsNotedRandom(config.getLootItemsNoBones());

        return 0;
    }

}
