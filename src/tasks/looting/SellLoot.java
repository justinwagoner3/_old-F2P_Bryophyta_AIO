package tasks.looting;

import methods.BankingMethods;
import methods.GrandExchangeMethods;
import methods.WalkingMethods;
import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.container.impl.bank.Bank;
import tasks.AbstractTask;

public class SellLoot extends AbstractTask {
    private WalkingMethods wm = new WalkingMethods();
    private BankingMethods bm = new BankingMethods();
    private GrandExchangeMethods gem = new GrandExchangeMethods();

    // TODO- change condition later and will need a different array of sellable items that does not include cash, bones, keys
    @Override
    public boolean accept() {
        return config.isInitBankOpened() && Bank.count(config.coins) < 40000 && Inventory.count(config.coins) < 40000;
    }

    @Override
    public int execute() {
        log("[T] Sell Loot");
        config.setStatus("Selling loot");
        wm.Walk(config.grandExchangeArea,"GE");

        // open and deposit
        bm.OpenBank();
        bm.DepositAllInventory();

        // from list of things looted
        bm.WithdrawAllItemsRandom(config.getAllSellableLootList());
        bm.WithdrawAllButOneItemsRandom(config.getAllButOneSellableLootList());

        // sell everything
        gem.OpenGE();
        gem.SellAllItemsInInv();
        gem.CloseGE();

        bm.OpenBank();
        bm.DepositAllInventory();
        bm.CloseBank();

        return Calculations.random(600,1200);
    }

}
