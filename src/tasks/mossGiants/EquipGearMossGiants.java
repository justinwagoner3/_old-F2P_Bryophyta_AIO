package tasks.mossGiants;

import config.Config;
import dataStructures.nameQuantity;
import methods.BankingMethods;
import methods.CombatMethods;
import methods.WalkingMethods;
import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.container.impl.equipment.Equipment;
import org.dreambot.api.methods.container.impl.equipment.EquipmentSlot;
import tasks.AbstractTask;

import java.util.ArrayList;
import java.util.List;

public class EquipGearMossGiants extends AbstractTask {
    private WalkingMethods wm = new WalkingMethods();
    private BankingMethods bm = new BankingMethods();
    private CombatMethods cm = new CombatMethods();

    @Override
    public boolean accept() {
        if(config.getState() == Config.State.MOSSGIANTS){
            // shared
            if(!Equipment.containsAll(config.leatherBoots,config.amuletOfPower,config.blackCape)){
                return true;
            }
            // melee
            if(config.getCurFightingStyle() == Config.FightingStyle.MELEE){
                if(!Equipment.containsAll(config.leatherGloves,config.runeFullHelm,config.runeKiteshield,config.runeChainbody,config.runePlatelegs,config.runeScimitar)){
                    return true;
                }
            }
            // range
            if(config.getCurFightingStyle() == Config.FightingStyle.RANGED){
                if(!Equipment.containsAll(config.coif,config.greendHideVambraces,config.studdedBody,config.greendHideChaps,config.mithArrow,config.mapleShortbow)){
                    return true;
                }
                if(Equipment.count(config.mithArrow) > 2000){
                    return true;
                }
                if(Equipment.count(config.mithArrow) < 1000 && config.feroxEnclave.contains(getLocalPlayer())){
                    return true;
                }
            }
        }

        return false;
        // TODO - might want use equipment. to put gear on too
    }

    @Override
    public int execute() {
        log("[T] Equipping gear moss giants");
        config.setStatus("Equipping gear moss giants");

        // Travel to bank
        if(!config.feroxEnclave.contains(getLocalPlayer())) {
            wm.WalkToWildy(config.feroxEnclaveBank, "Ferox Enclave bank");
        }

        // Check if need to not use so many arrows
        if(Equipment.count(config.mithArrow) > 2000){
            Equipment.unequip(EquipmentSlot.ARROWS);
        }


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
            if (Equipment.count(config.mithArrow) < 1000) {
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
