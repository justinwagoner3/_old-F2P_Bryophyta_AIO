package tasks.rats;

import dataStructures.nameQuantity;
import methods.BankMethods;
import methods.CombatMethods;
import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.container.impl.bank.Bank;
import org.dreambot.api.methods.container.impl.equipment.Equipment;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.methods.skills.Skill;
import org.dreambot.api.methods.skills.Skills;
import tasks.AbstractTask;

import java.util.ArrayList;
import java.util.List;

public class EquipGearInvRats extends AbstractTask {

    BankMethods bm = new BankMethods();
    CombatMethods cm = new CombatMethods();

    // TODO - set this to all equipment
    @Override
    public boolean accept() {
        return ((Skills.getRealLevel(Skill.ATTACK) >= 1 && Skills.getRealLevel(Skill.ATTACK) < 20) // attack between 20-39
                || (Skills.getRealLevel(Skill.STRENGTH) >= 1 && Skills.getRealLevel(Skill.STRENGTH) < 20) // attack between 20-39
                || (Skills.getRealLevel(Skill.DEFENCE) >= 1 && Skills.getRealLevel(Skill.DEFENCE) < 20)) // attack between 20-39

                && !Equipment.containsAll(config.ironFullHelm,config.ironKiteshield,config.ironPlatebody,config.ironPlatelegs); // not all wearing the right equipment
    }

    @Override
    public int execute() {
        log("[T] Equip Gear and Inv Rats");

        // all gear
        List<nameQuantity> gear = new ArrayList<>();
        gear.add(new nameQuantity(config.ironFullHelm,1));
        gear.add(new nameQuantity(config.amuletOfPower,1));
        gear.add(new nameQuantity(config.blackCape,1));
        gear.add(new nameQuantity(config.ironScimitar,1));
        gear.add(new nameQuantity(config.ironPlatebody,1));
        gear.add(new nameQuantity(config.ironKiteshield,1));
        gear.add(new nameQuantity(config.ironPlatelegs,1));
        gear.add(new nameQuantity(config.leatherBoots,1));
        gear.add(new nameQuantity(config.leatherGloves,1));

        // withdraw all gear
        bm.OpenBank();
        bm.WithdrawItemsRandom(gear);
        bm.CloseBank();

        // equip the gear
        cm.EquipItemsRandom(gear);

        // all inv
        List<nameQuantity> inv = new ArrayList<>();
        inv.add(new nameQuantity(config.blackScimitar,1));
        inv.add(new nameQuantity(config.mithScimitar,1));
        inv.add(new nameQuantity(config.lobster,18));
        inv.add(new nameQuantity(config.strPot4,8));

        // withdraw all inv
        // TODO - use methods
        if(Bank.isOpen() || Bank.open()) {
            sleepUntil(Bank::isOpen, Calculations.random(2000, 3000));
            bm.WithdrawItemsRandom(inv);
            if(Bank.close()){
                sleepUntil(this::bankIsClosed,Calculations.random(2000,3000));
            }
        }

        return 0;
    }

    // TODO- can be deleted once methods are used above
    private boolean bankIsClosed(){
        return !Bank.isOpen();
    }

}

