package tasks.mossGiants;

import dataStructures.nameQuantity;
import methods.BankMethods;
import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.methods.skills.Skill;
import org.dreambot.api.methods.skills.Skills;
import tasks.AbstractTask;

import java.util.ArrayList;
import java.util.List;

public class WithdrawInvMossGiants extends AbstractTask {
    BankMethods bm = new BankMethods();

    @Override
    public boolean accept() {
        return config.feroxEnclaveBank.contains(Players.localPlayer())
                && (!Inventory.contains(config.lobster) || !Inventory.contains(config.strPot4))
                && Skills.getRealLevel(Skill.DEFENCE) >= 40; // Low enough stats to be fighting rats
    }

    @Override
    public int execute() {

        log("[T] Withdrawing inv Moss Giants");
        // create list to withdraw
        // TODO - grab mithril armor, and in SwitchToFrogs maybe check if necessary to go back to bank or not
        List<nameQuantity> inv = new ArrayList<>();
        inv.add(new nameQuantity(config.lobster,23));
        inv.add(new nameQuantity(config.strPot4,4));


        // open bank, deposit everything
        bm.OpenBank();
        bm.DepositAllInventory();


        // withdraw items
        bm.WithdrawItemsRandom(inv);
        bm.CloseBank();
        return Calculations.random(500,900);
    }

}
