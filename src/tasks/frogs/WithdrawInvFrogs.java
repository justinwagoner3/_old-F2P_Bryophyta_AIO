package tasks.frogs;

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

public class WithdrawInvFrogs extends AbstractTask {
    BankMethods bm = new BankMethods();

    @Override
    public boolean accept() {
        return config.lumbridgeBank.contains(Players.localPlayer())
                && (!Inventory.contains(config.lobster) || !Inventory.contains(config.strPot4))
                && Skills.getRealLevel(Skill.DEFENCE) < 40; // Low enough stats to be fighting rats
    }

    @Override
    public int execute() {

        log("[T] Withdrawing inv Frogs");
        config.setStatus("Withdrawing inv Frogs");
        // create list to withdraw
        // TODO - grab mithril armor, and in SwitchToFrogs maybe check if necessary to go back to bank or not
        List<nameQuantity> inv = new ArrayList<>();
        inv.add(new nameQuantity(config.lobster,14));
        if(Skills.getRealLevel(Skill.ATTACK) >= 30 && Skills.getRealLevel(Skill.ATTACK) < 40){
            inv.add(new nameQuantity(config.runeScimitar, 1));
            inv.add(new nameQuantity(config.strPot4,13));
        }
        else if(Skills.getRealLevel(Skill.ATTACK) >= 20 && Skills.getRealLevel(Skill.ATTACK) < 30) {
            inv.add(new nameQuantity(config.addyScimitar, 1));
            inv.add(new nameQuantity(config.runeScimitar, 1));
            inv.add(new nameQuantity(config.strPot4,12));
        }
        else{
            log("THIS IS WEIRD - SHOULD NOT HIT THIS");
            inv.add(new nameQuantity(config.strPot4,12));
        }


        // open bank, deposit everything
        bm.OpenBank();
        bm.DepositAllInventory();


        // withdraw items
        bm.WithdrawXItemsRandom(inv);
        bm.CloseBank();
        return Calculations.random(500,900);
    }

}
