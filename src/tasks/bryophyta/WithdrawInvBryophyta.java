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
        if((config.varrockEastBank.contains(Players.localPlayer()) || config.grandExchangeArea.contains(getLocalPlayer())) && config.getState() == Config.State.BRYOPHYTA){
            if(config.getCurFightingStyle() == Config.FightingStyle.MELEE){
                if(!Inventory.containsAll(config.strPot4,config.swordfish,config.bronzeAxe,config.mossyKey)){
                    return true;
                }
            }
            if(config.getCurFightingStyle() == Config.FightingStyle.RANGED){
                if(!Inventory.contains(config.mossyKey,config.bronzeAxe,config.ironScimitar) || Inventory.count(config.swordfish) < 20){
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public int execute() {
        log("[T] Withdrawing inv Bryophyta");
        config.setStatus("Withdrawing inv Bryophyta");
        // TODO- maybe put this somewhere else
        // check if we need to eat first
        if(getLocalPlayer().getHealthPercent() <= 95){
            log("Eating before withdrawing");
            bm.OpenBank();
            bm.WithdrawXItem(config.lobster,10);
            bm.CloseBank();
            while(getLocalPlayer().getHealthPercent() <= 95){
                Inventory.interact(config.lobster,"Eat");
                sleep(Calculations.random(800,1400));
            }
        }

        // create list to withdraw
        // TODO - grab mithril armor, and in SwitchToFrogs maybe check if necessary to go back to bank or not
        List<nameQuantity> inv = new ArrayList<>();
        if(config.getCurFightingStyle() == Config.FightingStyle.MELEE) {
            inv.add(new nameQuantity(config.swordfish, 25));
            inv.add(new nameQuantity(config.bronzeAxe, 1));
            inv.add(new nameQuantity(config.mossyKey, 3));
            inv.add(new nameQuantity(config.strPot4, 1));
        }
        if(config.getCurFightingStyle() == Config.FightingStyle.RANGED){
            inv.add(new nameQuantity(config.swordfish, 25));
            inv.add(new nameQuantity(config.bronzeAxe, 1));
            inv.add(new nameQuantity(config.mossyKey, 3));
            inv.add(new nameQuantity(config.knife, 1));
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
