package tasks.bryophyta;

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

public class EquipGearBryophyta extends AbstractTask {

    private WalkingMethods wm = new WalkingMethods();
    private BankingMethods bm = new BankingMethods();
    private CombatMethods cm = new CombatMethods();

    @Override
    public boolean accept() {

        return config.getState() == Config.State.BRYOPHYTA
                && !Equipment.containsAll(config.runeScimitar,config.runeFullHelm,config.runeKiteshield,config.runeChainbody,config.runePlatelegs,config.amuletOfPower,config.leatherBoots,config.leatherBoots,config.blackCape);
    }

    @Override
    public int execute() {


        log("[T] Equipping gear Bryophyta");
        config.setStatus("Equipping gear Bryophyta");

        // Travel to bank
        wm.Walk(config.grandExchangeArea,"GE");

        // Deposit everything
        bm.OpenBank();
        bm.DepositAllInventory();

        // Withdraw new gear; TODO-turn to function
        List<nameQuantity> gear = new ArrayList<>();
        if(!Equipment.contains(config.amuletOfPower)) {
            gear.add(new nameQuantity(config.amuletOfPower, 1));
        }
        if(!Equipment.contains(config.blackCape)) {
            gear.add(new nameQuantity(config.blackCape, 1));
        }
        if(!Equipment.contains(config.leatherBoots)) {
            gear.add(new nameQuantity(config.leatherBoots, 1));
        }
        if(!Equipment.contains(config.leatherGloves)) {
            gear.add(new nameQuantity(config.leatherGloves, 1));
        }
        if(!Equipment.contains(config.runeFullHelm)) {
            gear.add(new nameQuantity(config.runeFullHelm, 1));
        }
        if(!Equipment.contains(config.runeChainbody)) {
            gear.add(new nameQuantity(config.runeChainbody, 1));
        }
        if(!Equipment.contains(config.runeKiteshield)) {
            gear.add(new nameQuantity(config.runeKiteshield, 1));
        }
        if(!Equipment.contains(config.runePlatelegs)) {
            gear.add(new nameQuantity(config.runePlatelegs, 1));
        }
        if(!Equipment.contains(config.runeScimitar)) {
            gear.add(new nameQuantity(config.runeScimitar, 1));
        }
        bm.WithdrawXItemsRandom(gear);
        bm.CloseBank();

        // Equip new gear
        cm.EquipAllItemsInInv();

        return Calculations.random(600,1200);
    }

}
