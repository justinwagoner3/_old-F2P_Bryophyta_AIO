package tasks.bryophyta;

import config.Config;
import dataStructures.nameQuantity;
import methods.BankingMethods;
import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.container.impl.bank.Bank;
import org.dreambot.api.methods.container.impl.bank.BankMode;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.methods.skills.Skill;
import org.dreambot.api.methods.skills.Skills;
import tasks.AbstractTask;

import java.util.ArrayList;
import java.util.List;

public class WithdrawInvBryophyta extends AbstractTask {

    private BankingMethods bm = new BankingMethods();

    @Override
    public boolean accept() {
        return (config.varrockEastBank.contains(Players.localPlayer()) || config.grandExchangeArea.contains(getLocalPlayer()))
                && (!Inventory.contains(config.swordfish) || !Inventory.contains(config.strPot4) || !Inventory.contains(config.bronzeAxe) || !Inventory.contains(config.mossyKey))
                && config.getState() == Config.State.BRYOPHYTA; // Low enough stats to be fighting rats

    }

    @Override
    public int execute() {
        log("[T] Withdrawing inv Bryophyta");
        config.setStatus("Withdrawing inv Bryophyta");
        // create list to withdraw
        // TODO - grab mithril armor, and in SwitchToFrogs maybe check if necessary to go back to bank or not
        List<nameQuantity> inv = new ArrayList<>();
        inv.add(new nameQuantity(config.swordfish,25));
        inv.add(new nameQuantity(config.bronzeAxe,1));
        inv.add(new nameQuantity(config.mossyKey,2));
        inv.add(new nameQuantity(config.strPot4,1));


        // open bank, deposit everything
        bm.OpenBank();
        bm.DepositAllInventory();


        // withdraw items
        bm.WithdrawXItemsRandom(inv);
        bm.CloseBank();
        return Calculations.random(600,1200);
    }

}
