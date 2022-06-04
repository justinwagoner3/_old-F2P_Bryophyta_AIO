package tasks.mossGiants;

import config.Config;
import dataStructures.nameQuantity;
import methods.BankingMethods;
import methods.CombatMethods;
import methods.WalkingMethods;
import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.container.impl.equipment.Equipment;
import tasks.AbstractTask;

import java.util.ArrayList;
import java.util.List;

public class EquipGearMossGiants extends AbstractTask {
    private WalkingMethods wm = new WalkingMethods();
    private BankingMethods bm = new BankingMethods();
    private CombatMethods cm = new CombatMethods();

    @Override
    public boolean accept() {
        return config.getState() == Config.State.MOSSGIANTS
                && !Equipment.containsAll(config.runeScimitar,config.runeFullHelm,config.runeKiteshield,config.runeChainbody,config.runePlatelegs,config.amuletOfPower,config.leatherBoots,config.leatherBoots,config.blackCape);
        // TODO - might want use equipnent. to put gear on too
    }

    @Override
    public int execute() {
        log("[T] Equipping gear moss giants");
        config.setStatus("Equipping gear moss giants");

        // Travel to bank
        wm.WalkToWildy(config.feroxEnclaveBank,"Ferox Enclave bank");

        // Deposit everything
        bm.OpenBank();
        bm.DepositAllInventory();

        // Withdraw new gear; TODO-turn to function
        List<nameQuantity> gear = new ArrayList<>();

        // shared
        if(!Equipment.contains(config.amuletOfPower)) {
            gear.add(new nameQuantity(config.amuletOfPower, 1));
        }
        if(!Equipment.contains(config.blackCape)) {
            gear.add(new nameQuantity(config.blackCape, 1));
        }
        if(!Equipment.contains(config.leatherBoots)) {
            gear.add(new nameQuantity(config.leatherBoots, 1));
        }

        // melee
        if(config.getCurFightingStyle() == Config.FightingStyle.MELEE) {
            if (!Equipment.contains(config.leatherGloves)) {
                gear.add(new nameQuantity(config.leatherGloves, 1));
            }
            if (!Equipment.contains(config.runeFullHelm)) {
                gear.add(new nameQuantity(config.runeFullHelm, 1));
            }
            if (!Equipment.contains(config.runeChainbody)) {
                gear.add(new nameQuantity(config.runeChainbody, 1));
            }
            if (!Equipment.contains(config.runeKiteshield)) {
                gear.add(new nameQuantity(config.runeKiteshield, 1));
            }
            if (!Equipment.contains(config.runePlatelegs)) {
                gear.add(new nameQuantity(config.runePlatelegs, 1));
            }
            if (!Equipment.contains(config.runeScimitar)) {
                gear.add(new nameQuantity(config.runeScimitar, 1));
            }
        }
        if(config.getCurFightingStyle() == Config.FightingStyle.RANGED){
            if (!Equipment.contains(config.coif)) {
                gear.add(new nameQuantity(config.coif, 1));
            }
            if (!Equipment.contains(config.studdedBody)) {
                gear.add(new nameQuantity(config.studdedBody, 1));
            }
            if (!Equipment.contains(config.greendHideChaps)) {
                gear.add(new nameQuantity(config.greendHideChaps, 1));
            }
            if (!Equipment.contains(config.mapleShortbow)) {
                gear.add(new nameQuantity(config.mapleShortbow, 1));
            }
            if (Equipment.count(config.mithArrow) < 500) {
                gear.add(new nameQuantity(config.mithArrow, 1000));
            }
            if (!Equipment.contains(config.greendHideVambraces)) {
                gear.add(new nameQuantity(config.greendHideVambraces, 1));
            }
        }


        bm.WithdrawXItemsRandom(gear);
        bm.CloseBank();

        // Equip new gear
        cm.EquipAllItemsInInv();

        return Calculations.random(600,1200);
    }

}
