package tasks.frogs;

import config.Config;
import dataStructures.nameQuantity;
import methods.BankingMethods;
import methods.CombatMethods;
import methods.WalkingMethods;
import org.dreambot.api.methods.container.impl.equipment.Equipment;
import org.dreambot.api.methods.skills.Skill;
import org.dreambot.api.methods.skills.Skills;
import org.dreambot.api.methods.tabs.Tab;
import org.dreambot.api.methods.tabs.Tabs;
import tasks.AbstractTask;

import java.util.ArrayList;
import java.util.List;

public class EquipGearFrogs extends AbstractTask {

    private WalkingMethods wm = new WalkingMethods();
    private BankingMethods bm = new BankingMethods();
    private CombatMethods cm = new CombatMethods();

    // TODO - might want use equipnent. to put gear on too

    @Override
    public boolean accept() {
        if(config.getState() == Config.State.FROGS){
            // shared
            if(!Equipment.containsAll(config.leatherBoots,config.amuletOfPower,config.blackCape)){
                return true;
            }
            // melee
            if(config.getCurFightingStyle() == Config.FightingStyle.MELEE){
                if(!Equipment.containsAll(config.leatherGloves,config.mithFullHelm,config.mithKiteshield,config.mithPlatebody,config.mithPlatelegs)){
                    return true;
                }
            }
            // range
            if(config.getCurFightingStyle() == Config.FightingStyle.RANGED){
                if(!Equipment.containsAll(config.coif,config.leatherVambraces,config.studdedBody,config.studdedChaps,config.mithArrow)){
                    return true;
                }
                if(Skills.getRealLevel(Skill.RANGED) < 30){
                    if(!Equipment.contains(config.willowShortbow)) {
                        return true;
                    }
                }
                else{
                    if(!Equipment.contains(config.mapleShortbow)) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    @Override
    public int execute() {
        log("[T] Equipping Gear Frogs");
        config.setStatus("Equipping Gear Frogs");

        // Travel to bank
        if(config.giantFrogArea.contains(getLocalPlayer()) || config.largeGiantRatArea.contains(getLocalPlayer())){
            wm.Walk(config.lumbridgeGraveyard,"lumb graveyard");
            wm.Walk(config.lumbridgeBank, "Lumbridge bank");
        }
        else {
            wm.TeleHome();
            wm.Walk(config.lumbridgeBank, "Lumbridge bank");
        }

        // check equipment
        cm.OpenTab(Tab.EQUIPMENT);

        // Deposit everything
        bm.OpenBank();
        bm.DepositAllInventory();

        // Withdraw new gear
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
        if(config.getCurFightingStyle() == Config.FightingStyle.MELEE) {
            if(!Equipment.contains(config.mithFullHelm)) {
                gear.add(new nameQuantity(config.mithFullHelm, 1));
            }
            if(!Equipment.contains(config.leatherGloves)) {
                gear.add(new nameQuantity(config.leatherGloves, 1));
            }
            if(!Equipment.contains(config.mithPlatebody)) {
                gear.add(new nameQuantity(config.mithPlatebody, 1));
            }
            if(!Equipment.contains(config.mithKiteshield)) {
                gear.add(new nameQuantity(config.mithKiteshield, 1));
            }
            if(!Equipment.contains(config.mithPlatelegs)) {
                gear.add(new nameQuantity(config.mithPlatelegs, 1));
            }
        }
        if(config.getCurFightingStyle() == Config.FightingStyle.RANGED){
            if(!Equipment.contains(config.coif)) {
                gear.add(new nameQuantity(config.coif, 1));
            }
            if(!Equipment.contains(config.leatherVambraces)) {
                gear.add(new nameQuantity(config.leatherVambraces, 1));
            }
            if(!Equipment.contains(config.studdedBody)) {
                gear.add(new nameQuantity(config.studdedBody, 1));
            }
            if(!Equipment.contains(config.studdedChaps)) {
                gear.add(new nameQuantity(config.studdedChaps, 1));
            }
            if(!Equipment.contains(config.mithArrow)) {
                gear.add(new nameQuantity(config.mithArrow, 10000));
            }
            if(Skills.getRealLevel(Skill.RANGED) < 30) {
                if (!Equipment.contains(config.willowShortbow)) {
                    gear.add(new nameQuantity(config.willowShortbow, 1));
                }
            }
            else{
                if (!Equipment.contains(config.mapleShortbow)) {
                    gear.add(new nameQuantity(config.mapleShortbow, 1));
                }
            }
        }

        bm.WithdrawXItemsRandom(gear);
        bm.CloseBank();

        // Equip new gear
        cm.EquipAllItemsInInv();

        return 0;
    }

}
