package tasks.frogs;

import config.Config;
import dataStructures.nameQuantity;
import methods.BankingMethods;
import methods.CombatMethods;
import methods.WalkingMethods;
import org.dreambot.api.methods.container.impl.equipment.Equipment;
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
            if(config.getCurFightingStyle() == Config.FightingStyle.MELEE){
                if(!Equipment.containsAll(config.leatherGloves,config.mithFullHelm,config.mithKiteshield,config.mithPlatebody,config.mithPlatelegs)){
                    return true;
                }
            }
            if(config.getCurFightingStyle() == Config.FightingStyle.RANGED){
                if(!Equipment.containsAll(config.coif,config.leatherVambraces,config.studdedBody,config.studdedChaps)){
                    return true;
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
        // Deposit everything
        bm.OpenBank();
        bm.DepositAllInventory();

        // Withdraw new gear
        List<nameQuantity> gear = new ArrayList<>();
        if(config.getCurFightingStyle() == Config.FightingStyle.MELEE) {
            gear.add(new nameQuantity(config.mithFullHelm, 1));
            gear.add(new nameQuantity(config.mithPlatebody, 1));
            gear.add(new nameQuantity(config.mithKiteshield, 1));
            gear.add(new nameQuantity(config.mithPlatelegs, 1));
        }
        if(config.getCurFightingStyle() == Config.FightingStyle.RANGED){
            gear.add(new nameQuantity(config.studdedBody, 1));
        }

        bm.WithdrawXItemsRandom(gear);
        bm.CloseBank();

        // Equip new gear
        cm.EquipAllItemsInInv();

        return 0;
    }

}
