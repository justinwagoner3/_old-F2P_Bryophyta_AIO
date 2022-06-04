package tasks.rats;

import config.Config;
import dataStructures.nameQuantity;
import methods.BankingMethods;
import methods.CombatMethods;
import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.container.impl.equipment.Equipment;
import tasks.AbstractTask;

import java.util.ArrayList;
import java.util.List;

public class EquipGearRats extends AbstractTask {

    BankingMethods bm = new BankingMethods();
    CombatMethods cm = new CombatMethods();

    // TODO - does not include scimitar; adding just the iron scimitar cause script to fail when reaching lvl 10, need if statement based on attack level for what should be wearing
    @Override
    public boolean accept() {
        if(config.getState() == Config.State.RATS){
            // shared, always should be on
            if(!Equipment.containsAll(config.leatherBoots,config.amuletOfPower,config.blackCape)){
                return true;
            }

            // melee gear
            if(config.getCurFightingStyle() == Config.FightingStyle.MELEE){
                if(!Equipment.containsAll(config.leatherGloves,config.ironFullHelm,config.ironKiteshield,config.ironPlatebody,config.ironPlatelegs)){
                    return true;
                }
            }

            // range gear
            if(config.getCurFightingStyle() == Config.FightingStyle.RANGED) {
                if(!Equipment.containsAll(config.leatherCowl,config.leatherBody,config.leatherChaps,config.ironArrow,config.leatherVambraces)){
                    return true;
                }
            }
        }
        return false;

    }

    @Override
    public int execute() {
        log("[T] Equip Gear Rats");
        config.setStatus("Equip Gear Rats");

        // shared gear
        List<nameQuantity> gear = new ArrayList<>();
        if(!Equipment.contains(config.amuletOfPower)) {
            gear.add(new nameQuantity(config.amuletOfPower,1));
        }
        if(!Equipment.contains(config.blackCape)) {
            gear.add(new nameQuantity(config.blackCape,1));
        }
        if(!Equipment.contains(config.leatherBoots)) {
            gear.add(new nameQuantity(config.leatherBoots,1));
        }

        // melee gear
        if(config.getCurFightingStyle() == Config.FightingStyle.MELEE) {
            if (!Equipment.contains(config.ironFullHelm)) {
                gear.add(new nameQuantity(config.ironFullHelm, 1));
            }
            if (!Equipment.contains(config.ironScimitar)) {
                gear.add(new nameQuantity(config.ironScimitar, 1));
            }
            if (!Equipment.contains(config.ironPlatebody)) {
                gear.add(new nameQuantity(config.ironPlatebody, 1));
            }
            if (!Equipment.contains(config.ironKiteshield)) {
                gear.add(new nameQuantity(config.ironKiteshield, 1));
            }
            if (!Equipment.contains(config.ironPlatelegs)) {
                gear.add(new nameQuantity(config.ironPlatelegs, 1));
            }
            if (!Equipment.contains(config.leatherGloves)) {
                gear.add(new nameQuantity(config.leatherGloves, 1));
            }
        }
        // range gear
        if(config.getCurFightingStyle() == Config.FightingStyle.RANGED){
            if (!Equipment.contains(config.leatherCowl)) {
                gear.add(new nameQuantity(config.leatherCowl, 1));
            }
            if (!Equipment.contains(config.leatherBody)) {
                gear.add(new nameQuantity(config.leatherBody, 1));
            }
            if (!Equipment.contains(config.leatherChaps)) {
                gear.add(new nameQuantity(config.leatherChaps, 1));
            }
            if (!Equipment.contains(config.ironArrow)) {
                gear.add(new nameQuantity(config.ironArrow, 5000));
            }
            if (!Equipment.contains(config.leatherVambraces)) {
                gear.add(new nameQuantity(config.leatherVambraces, 1));
            }
            if (!Equipment.contains(config.shortbow)) {
                gear.add(new nameQuantity(config.shortbow, 1));
            }
        }

        // withdraw all gear
        bm.OpenBank();
        bm.DepositAllInventory();
        bm.WithdrawXItemsRandom(gear);
        bm.CloseBank();

        // equip the gear
        cm.EquipAllItemsInInv();

        return Calculations.random(600,1200);
    }

}

