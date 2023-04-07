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
                    // str pot needs to be above 20 to do things
                    if(config.gearList.get(i).equals(config.strPot4)) {
                        if (Bank.count(config.strPot4) + Inventory.count(config.strPot4) + Equipment.count(config.strPot4) < 20) {
                            log("Needs to buy: " + config.gearList.get(i));
                            return true;
                        }
                    }
                    // mith arrows needs to be above 1000 to do things
                    if(config.gearList.get(i).equals(config.mithArrow)) {
                        if ((Bank.count(config.mithArrow) + Inventory.count(config.mithArrow) + Equipment.count(config.mithArrow) < 1000) && !config.mossGiantWildernessArea.getCenter().getArea(100).contains(getLocalPlayer())) {
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

    // TODO - in order to get away from using while loops, will have to break apart some of this logic into different tasks
    // if it fails at certain points can cause weird things to happen
    @Override
    public int execute() {
        log("[T] Buying Gear");
        config.setStatus("Buying Gear");
        // travel to the GE
        wm.Walk(config.grandExchangeArea,"GE");

        // Open bank, deposit all inv if necessary, withdraw cash stack if necessary
        if(Bank.contains(config.coins) || !Inventory.onlyContains(config.coins)) {
            bm.OpenBank();
            if (!Inventory.onlyContains(config.coins)) {
                bm.DepositAllInventory();
            }
            if (Bank.contains(config.coins)) {
                bm.WithdrawAllOfOneItem(config.coins);
            }
            bm.CloseBank();
        }

        // open ge, buy gear, deposit it all in bank
        gem.OpenGE();

        // TODO - fix how i buy items so can just pass an array to this function and can randomize order bought
        // shared
        gem.BuyGearIfNecessarySingleItem(config.amuletOfPower);
        gem.BuyGearIfNecessarySingleItem(config.blackCape);
        gem.BuyGearIfNecessarySingleItem(config.leatherBoots);
        gem.BuyGearIfNecessaryMultipleOfItem(config.lobster,25, Calculations.random(200,300));
        gem.BuyGearIfNecessaryMultipleOfItem(config.swordfish,25, Calculations.random(100,150));

        // melee
        if(config.isMeleeSelected()) {
            gem.BuyGearIfNecessarySingleItem(config.ironPlatebody);
            gem.BuyGearIfNecessarySingleItem(config.ironPlatelegs);
            gem.BuyGearIfNecessarySingleItem(config.ironFullHelm);
            gem.BuyGearIfNecessarySingleItem(config.mithPlatebody);
            gem.BuyGearIfNecessarySingleItem(config.mithPlatelegs);
            gem.BuyGearIfNecessarySingleItem(config.mithFullHelm);
            gem.BuyGearIfNecessarySingleItem(config.runeChainbody);
            gem.BuyGearIfNecessarySingleItem(config.runePlatelegs);
            gem.BuyGearIfNecessarySingleItem(config.runeFullHelm);
            gem.BuyGearIfNecessarySingleItem(config.ironKiteshield);
            gem.BuyGearIfNecessarySingleItem(config.mithKiteshield);
            gem.BuyGearIfNecessarySingleItem(config.runeKiteshield);
            gem.BuyGearIfNecessarySingleItem(config.ironScimitar);
            gem.BuyGearIfNecessarySingleItem(config.blackScimitar);
            gem.BuyGearIfNecessarySingleItem(config.mithScimitar);
            gem.BuyGearIfNecessarySingleItem(config.addyScimitar);
            gem.BuyGearIfNecessarySingleItem(config.runeScimitar);
            gem.BuyGearIfNecessarySingleItem(config.leatherGloves);
            gem.BuyGearIfNecessaryMultipleOfItem(config.strPot4,20,Calculations.random(100, 200));
        }

        // ranged
        if(config.isRangedSelected()) {
            gem.BuyGearIfNecessarySingleItem(config.leatherCowl);
            gem.BuyGearIfNecessarySingleItem(config.coif);
            gem.BuyGearIfNecessarySingleItem(config.studdedBody);
            gem.BuyGearIfNecessarySingleItem(config.hardleatherBody);
            gem.BuyGearIfNecessarySingleItem(config.leatherBody);
            gem.BuyGearIfNecessarySingleItem(config.greendHideChaps);
            gem.BuyGearIfNecessarySingleItem(config.studdedChaps);
            gem.BuyGearIfNecessarySingleItem(config.leatherChaps);
            gem.BuyGearIfNecessarySingleItem(config.mapleShortbow);
            gem.BuyGearIfNecessarySingleItem(config.willowShortbow);
            gem.BuyGearIfNecessarySingleItem(config.oakShortbow);
            gem.BuyGearIfNecessarySingleItem(config.shortbow);
            gem.BuyGearIfNecessarySingleItem(config.knife);
            gem.BuyGearIfNecessaryMultipleOfItem(config.addyArrow,500,Calculations.random(1000,2000));
            gem.BuyGearIfNecessaryMultipleOfItem(config.mithArrow,1000,7000);
            gem.BuyGearIfNecessaryMultipleOfItem(config.ironArrow,1,Calculations.random(1500,2500));
            gem.BuyGearIfNecessarySingleItem(config.greendHideVambraces);
            gem.BuyGearIfNecessarySingleItem(config.leatherVambraces);
        }


        gem.CloseGE();

        // deposit everything
        bm.OpenBank();
        bm.DepositAllInventory();
        bm.CloseBank();




        return Calculations.random(600,1200);
    }
}
