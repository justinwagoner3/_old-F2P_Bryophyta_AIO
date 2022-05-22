package tasks.mossGiants;

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

public class EquipGearInvMossGiants extends AbstractTask {
    private WalkingMethods wm = new WalkingMethods();
    private BankMethods bm = new BankMethods();
    private CombatMethods cm = new CombatMethods();

    @Override
    public boolean accept() {
        return Skills.getRealLevel(Skill.ATTACK) == 40
                && Skills.getRealLevel(Skill.STRENGTH) == 40
                && Skills.getRealLevel(Skill.DEFENCE) == 40
                && !Equipment.containsAll(config.runeFullHelm,config.runeKiteshield,config.runeChainbody,config.runePlatelegs);
        // TODO - might want use equipnent. to put gear on too
    }

    @Override
    public int execute() {
        log("[T] Equipping gear and inv moss giants");
        // set frogs as monster
        config.setCurMonster(config.mossGiant);

        // Travel to bank
        wm.Walk(config.edgevilleBank,"Edgeville bank");

        // Deposit everything
        bm.OpenBank();
        bm.DepositInventory();

        // Withdraw new gear
        List<nameQuantity> gear = new ArrayList<>();
        gear.add(new nameQuantity(config.runeFullHelm,1));
        gear.add(new nameQuantity(config.runeChainbody,1));
        gear.add(new nameQuantity(config.runeKiteshield,1));
        gear.add(new nameQuantity(config.runePlatelegs,1));

        bm.WithdrawItemsRandom(gear);
        bm.CloseBank();

        // Equip new gear
        cm.EquipItemsRandom(gear);

        // Prepare inventory
        List<nameQuantity> inv = new ArrayList<>();
        inv.add(new nameQuantity(config.lobster,23));
        inv.add(new nameQuantity(config.strPot4,4));


        bm.OpenBank();
        bm.DepositInventory();


        bm.OpenBank();
        bm.WithdrawItemsRandom(inv);
        bm.CloseBank();

        return 0;
    }

}