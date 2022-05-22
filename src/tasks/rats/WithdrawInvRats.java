package tasks.rats;

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

public class WithdrawInvRats extends AbstractTask {

    BankMethods bm = new BankMethods();

    @Override
    public boolean accept() {
        return config.lumbridgeBank.contains(Players.localPlayer())
                && (!Inventory.contains("Lobster") || !Inventory.contains("Strength potion(4)"))
                && Skills.getRealLevel(Skill.DEFENCE) < 20; // Low enough stats to be fighting rats
    }

    @Override
    public int execute() {

        log("[T] Withdrawing inv Rats");
        // create list to withdraw
        // TODO - maybe grab mithril armor, and in SwitchToFrogs maybe check if necessary to go back to bank or not
        List<nameQuantity> inv = new ArrayList<>();
        inv.add(new nameQuantity("Lobster",15));
        if(Skills.getRealLevel(Skill.ATTACK) < 10){
            inv.add(new nameQuantity(config.blackScimitar,1));
            inv.add(new nameQuantity(config.mithScimitar,1));
            inv.add(new nameQuantity(config.strPot4,11));
        }
        else if(Skills.getRealLevel(Skill.ATTACK) < 20){
            inv.add(new nameQuantity(config.mithScimitar,1));
            inv.add(new nameQuantity(config.strPot4,12));
        }
        else{
            inv.add(new nameQuantity("Strength potion(4)",13));
        }


        // open bank, deposit everything
        bm.OpenBank();
        bm.DepositInventory();


        // withdraw items
        bm.WithdrawItemsRandom(inv);
        return Calculations.random(500,900);
    }
}
