package tasks.mossGiants;

import config.Config;
import dataStructures.nameQuantity;
import methods.BankingMethods;
import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.interactive.Players;
import tasks.AbstractTask;

import java.util.ArrayList;
import java.util.List;

public class WithdrawInvMossGiants extends AbstractTask {
    BankingMethods bm = new BankingMethods();

    @Override
    public boolean accept() {
        if(config.feroxEnclaveBank.contains(Players.localPlayer()) && config.getState() == Config.State.MOSSGIANTS){
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

        log("[T] Withdrawing inv Moss Giants");
        config.setStatus("Withdrawing inv Moss Giants");
        // create list to withdraw
        // TODO - grab mithril armor, and in SwitchToFrogs maybe check if necessary to go back to bank or not
        List<nameQuantity> inv = new ArrayList<>();
        if(config.getCurFightingStyle() == Config.FightingStyle.MELEE) {
            inv.add(new nameQuantity(config.lobster, 22));
            inv.add(new nameQuantity(config.strPot4, 3));
        }
        if(config.getCurFightingStyle() == Config.FightingStyle.RANGED){
            inv.add(new nameQuantity(config.lobster, 25));
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
