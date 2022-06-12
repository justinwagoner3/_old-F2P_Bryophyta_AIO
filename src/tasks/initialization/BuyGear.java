package tasks.initialization;

import methods.BankingMethods;
import methods.GrandExchangeMethods;
import methods.WalkingMethods;
import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.container.impl.bank.Bank;
import org.dreambot.api.methods.container.impl.equipment.Equipment;
import tasks.AbstractTask;

public class BuyGear extends AbstractTask {

    private WalkingMethods wm = new WalkingMethods();
    private BankingMethods bm = new BankingMethods();
    private GrandExchangeMethods gem = new GrandExchangeMethods();


    @Override
    public boolean accept() {
        // TODO- prolly don't even need the first if statement anymore
        // TODO - this breaks if brand new player stops script before purchasing gear
        if(Equipment.isEmpty() && config.lumbridgeTeleArea.contains(getLocalPlayer())){ // just died or started
            return true;
        }
        else{
            if(config.isInitBankOpened()) {
                for (int i = 0; i < config.gearList.size(); i++) {
                    // lobster and swordfish need to be above 25 to do things
                    if(config.gearList.get(i).equals(config.lobster)) {
                        if (Bank.count(config.lobster) + Inventory.count(config.lobster) + Equipment.count(config.lobster) < 25) {
                            log("Needs to buy: " + config.gearList.get(i));
                            return true;
                        }
                    }
                    if(config.gearList.get(i).equals(config.swordfish)) {
                        if (Bank.count(config.swordfish) + Inventory.count(config.swordfish) + Equipment.count(config.swordfish) < 25) {
                            log("Needs to buy: " + config.gearList.get(i));
                            return true;
                        }
                    }
                    // mith arrows needs to be above 1000 to do things
                    if(config.gearList.get(i).equals(config.mithArrow)) {
                        if (Bank.count(config.mithArrow) + Inventory.count(config.mithArrow) + Equipment.count(config.mithArrow) < 1000) {
                            log("Needs to buy: " + config.gearList.get(i));
                            return true;
                        }
                    }
                    // addy arrows needs to be above 500 to do things
                    else if(config.gearList.get(i).equals(config.addyArrow)){
                        if (Bank.count(config.addyArrow) + Inventory.count(config.addyArrow) + Equipment.count(config.addyArrow) < 500) {
                            log("Needs to buy: " + config.gearList.get(i));
                            return true;
                        }
                    }
                    // everything else is just a single check
                    else {
                        if (!Bank.contains(config.gearList.get(i)) && !Inventory.contains(config.gearList.get(i)) && !Equipment.contains(config.gearList.get(i))) {
                            log("Needs to buy: " + config.gearList.get(i));
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    @Override
    public int execute() {
        log("[T] Buying Gear");
        config.setStatus("Buying Gear");
        // travel to the GE
        wm.Walk(config.grandExchangeArea,"GE");

        // Open bank, deposit all inv and gear, withdraw cash stack
        bm.OpenBank();
        bm.DepositAllInventory();
        bm.DepositAllGear();
        bm.WithdrawOnlyCoins();
        bm.CloseBank();

        // open ge, buy gear, deposit it all in bank
        gem.OpenGE();

        // TODO - fix how i buy items so can just pass an array to this function and can randomize order bought
        // shared
        gem.BuyGearIfNecessary(config.amuletOfPower, 1);
        gem.BuyGearIfNecessary(config.blackCape, 1);
        gem.BuyGearIfNecessary(config.leatherBoots, 1);
        gem.BuyGearIfNecessary(config.lobster, Calculations.random(200,300));
        gem.BuyGearIfNecessary(config.swordfish, Calculations.random(100,150));

        // melee
        if(config.isMeleeSelected()) {
            gem.BuyGearIfNecessary(config.ironPlatebody, 1);
            gem.BuyGearIfNecessary(config.ironPlatelegs, 1);
            gem.BuyGearIfNecessary(config.ironFullHelm, 1);
            gem.BuyGearIfNecessary(config.mithPlatebody, 1);
            gem.BuyGearIfNecessary(config.mithPlatelegs, 1);
            gem.BuyGearIfNecessary(config.mithFullHelm, 1);
            gem.BuyGearIfNecessary(config.runeChainbody, 1);
            gem.BuyGearIfNecessary(config.runePlatelegs, 1);
            gem.BuyGearIfNecessary(config.runeFullHelm, 1);
            gem.BuyGearIfNecessary(config.ironKiteshield, 1);
            gem.BuyGearIfNecessary(config.mithKiteshield, 1);
            gem.BuyGearIfNecessary(config.runeKiteshield, 1);
            gem.BuyGearIfNecessary(config.ironScimitar, 1);
            gem.BuyGearIfNecessary(config.blackScimitar, 1);
            gem.BuyGearIfNecessary(config.mithScimitar, 1);
            gem.BuyGearIfNecessary(config.addyScimitar, 1);
            gem.BuyGearIfNecessary(config.runeScimitar, 1);
            gem.BuyGearIfNecessary(config.leatherGloves, 1);
            gem.BuyGearIfNecessary(config.strPot4, Calculations.random(100, 200));
        }

        // ranged
        if(config.isRangedSelected()) {
            gem.BuyGearIfNecessary(config.leatherCowl,1);
            gem.BuyGearIfNecessary(config.coif,1);
            gem.BuyGearIfNecessary(config.studdedBody,1);
            gem.BuyGearIfNecessary(config.hardleatherBody,1);
            gem.BuyGearIfNecessary(config.leatherBody,1);
            gem.BuyGearIfNecessary(config.greendHideChaps,1);
            gem.BuyGearIfNecessary(config.studdedChaps,1);
            gem.BuyGearIfNecessary(config.leatherChaps,1);
            gem.BuyGearIfNecessary(config.mapleShortbow,1);
            gem.BuyGearIfNecessary(config.willowShortbow,1);
            gem.BuyGearIfNecessary(config.oakShortbow,1);
            gem.BuyGearIfNecessary(config.shortbow,1);
            gem.BuyGearIfNecessary(config.addyArrow,Calculations.random(1000,2000));
            gem.BuyGearIfNecessary(config.mithArrow,Calculations.random(5000,7000));
            gem.BuyGearIfNecessary(config.ironArrow,Calculations.random(1000,2000));
            gem.BuyGearIfNecessary(config.greendHideVambraces,1);
            gem.BuyGearIfNecessary(config.leatherVambraces,1);
        }


        gem.CloseGE();

        // deposit everything
        bm.OpenBank();
        bm.DepositAllInventory();
        bm.CloseBank();


        return Calculations.random(600,1200);
    }
}
