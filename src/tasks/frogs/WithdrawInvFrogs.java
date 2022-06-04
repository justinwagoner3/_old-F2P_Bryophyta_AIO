package tasks.frogs;

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

public class WithdrawInvFrogs extends AbstractTask {
    BankingMethods bm = new BankingMethods();

    @Override
    public boolean accept() {
        if(config.lumbridgeBank.contains(Players.localPlayer()) && config.getState() == Config.State.FROGS){
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

        log("[T] Withdrawing inv Frogs");
        config.setStatus("Withdrawing inv Frogs");
        // create list to withdraw
        List<nameQuantity> inv = new ArrayList<>();
        if(config.getCurFightingStyle() == Config.FightingStyle.MELEE) {
            inv.add(new nameQuantity(config.lobster, 14));
            if (Skills.getRealLevel(Skill.ATTACK) >= 30 && Skills.getRealLevel(Skill.ATTACK) < 40) {
                inv.add(new nameQuantity(config.runeScimitar, 1));
                inv.add(new nameQuantity(config.strPot4, 12));
            } else if (Skills.getRealLevel(Skill.ATTACK) >= 20 && Skills.getRealLevel(Skill.ATTACK) < 30) {
                inv.add(new nameQuantity(config.addyScimitar, 1));
                inv.add(new nameQuantity(config.runeScimitar, 1));
                inv.add(new nameQuantity(config.strPot4, 11));
            } else {
                log("THIS IS WEIRD - SHOULD NOT HIT THIS");
                inv.add(new nameQuantity(config.strPot4, 11));
            }
        }
        if(config.getCurFightingStyle() == Config.FightingStyle.RANGED) {
            if(Skills.getRealLevel(Skill.RANGED) < 30){
                inv.add(new nameQuantity(config.mapleShortbow, 1));
            }
            if(Skills.getRealLevel(Skill.DEFENCE) >= 40){
                inv.add(new nameQuantity(config.lobster, 10));
            }
            else{
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
