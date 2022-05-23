package tasks.frogs;

import dataStructures.nameQuantity;
import methods.BankMethods;
import methods.CombatMethods;
import methods.WalkingMethods;
import org.dreambot.api.methods.container.impl.equipment.Equipment;
import org.dreambot.api.methods.skills.Skill;
import org.dreambot.api.methods.skills.Skills;
import tasks.AbstractTask;

import java.util.ArrayList;
import java.util.List;

public class EquipGearInvFrogs extends AbstractTask {

    private WalkingMethods wm = new WalkingMethods();
    private BankMethods bm = new BankMethods();
    private CombatMethods cm = new CombatMethods();

    @Override
    public boolean accept() {
        return ((Skills.getRealLevel(Skill.ATTACK) >= 20 && Skills.getRealLevel(Skill.ATTACK) < 40) // attack between 20-39
                && (Skills.getRealLevel(Skill.STRENGTH) >= 20 && Skills.getRealLevel(Skill.STRENGTH) < 40) // attack between 20-39
                && (Skills.getRealLevel(Skill.DEFENCE) >= 20 && Skills.getRealLevel(Skill.DEFENCE) < 40)) // attack between 20-39

                && !Equipment.containsAll(config.mithFullHelm,config.mithKiteshield,config.mithPlatebody,config.mithPlatelegs); // not all wearing the right equipment
        // TODO - might want use equipnent. to put gear on too
    }

    @Override
    public int execute() {
        log("[T] Equipping gear and inv frogs");
        config.setStatus("Equipping gear and inv frogs");
        // set frogs as monster
        config.setCurMonster(config.giantFrog);

        // Travel to bank
        wm.Walk(config.lumbridgeBank,"Lumbridge bank");

        // Deposit everything
        bm.OpenBank();
        bm.DepositAllInventory();

        // Withdraw new gear
        List<nameQuantity> gear = new ArrayList<>();
        gear.add(new nameQuantity(config.mithFullHelm,1));
        gear.add(new nameQuantity(config.mithPlatebody,1));
        gear.add(new nameQuantity(config.mithKiteshield,1));
        gear.add(new nameQuantity(config.mithPlatelegs,1));

        bm.WithdrawXItemsRandom(gear);
        bm.CloseBank();

        // Equip new gear
        cm.EquipItemsRandom(gear);

        // Prepare inventory
        List<nameQuantity> inv = new ArrayList<>();
        inv.add(new nameQuantity(config.addyScimitar,1));
        inv.add(new nameQuantity(config.runeScimitar,1));
        inv.add(new nameQuantity(config.lobster,14));
        inv.add(new nameQuantity(config.strPot4,13));


        bm.OpenBank();
        bm.DepositAllInventory();


        bm.OpenBank();
        bm.WithdrawXItemsRandom(inv);
        bm.CloseBank();

        return 0;
    }

}
