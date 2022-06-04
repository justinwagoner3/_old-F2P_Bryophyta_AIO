package tasks.rats;

import config.Config;
import dataStructures.nameQuantity;
import methods.BankingMethods;
import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.methods.skills.Skill;
import org.dreambot.api.methods.skills.Skills;
import tasks.AbstractTask;

import java.util.ArrayList;
import java.util.List;

public class WithdrawInvRats extends AbstractTask {

    BankingMethods bm = new BankingMethods();

    @Override
    public boolean accept() {
        if(config.lumbridgeBank.contains(Players.localPlayer()) && config.getState() == Config.State.RATS){
            if(config.getCurFightingStyle() == Config.FightingStyle.MELEE){
                if(!Inventory.containsAll(config.strPot4,config.lobster)){
                    return true;
                }
            }
            if(config.getCurFightingStyle() == Config.FightingStyle.RANGED){
                if(!Inventory.contains(config.lobster)){
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public int execute() {

        log("[T] Withdrawing inv Rats");
        config.setStatus("Withdrawing inv Rats");
        // create list to withdraw
        // TODO - maybe grab mithril armor, and in SwitchToFrogs maybe check if necessary to go back to bank or not
        List<nameQuantity> inv = new ArrayList<>();
        if(config.getCurFightingStyle() == Config.FightingStyle.MELEE) {
            inv.add(new nameQuantity("Lobster", 15));
            if (Skills.getRealLevel(Skill.ATTACK) < 10) {
                inv.add(new nameQuantity(config.blackScimitar, 1));
                inv.add(new nameQuantity(config.mithScimitar, 1));
                inv.add(new nameQuantity(config.strPot4, 11));
            } else if (Skills.getRealLevel(Skill.ATTACK) < 20) {
                inv.add(new nameQuantity(config.mithScimitar, 1));
                inv.add(new nameQuantity(config.strPot4, 12));
            } else {
                inv.add(new nameQuantity(config.strPot4, 13));
            }
        }
        if(config.getCurFightingStyle() == Config.FightingStyle.RANGED) {
            if (Skills.getRealLevel(Skill.RANGED) < 5) {
                inv.add(new nameQuantity(config.coif, 1));
                inv.add(new nameQuantity(config.mithArrow, 5000));
                inv.add(new nameQuantity(config.studdedChaps, 1));
                inv.add(new nameQuantity(config.oakShortbow, 1));
                inv.add(new nameQuantity(config.willowShortbow, 1));
                inv.add(new nameQuantity(config.lobster, 23));
            } else if (Skills.getRealLevel(Skill.RANGED) < 20) {
                inv.add(new nameQuantity(config.coif, 1));
                inv.add(new nameQuantity(config.mithArrow, 5000));
                inv.add(new nameQuantity(config.studdedChaps, 1));
                inv.add(new nameQuantity(config.willowShortbow, 1));
                inv.add(new nameQuantity(config.lobster, 24));
            } else {
                inv.add(new nameQuantity(config.lobster, 28));
            }
        }


            // open bank, deposit everything
        bm.OpenBank();
        bm.DepositAllInventory();


        // withdraw items
        bm.WithdrawXItemsRandom(inv);
        bm.CloseBank();
        return Calculations.random(600,1200);
    }
}
