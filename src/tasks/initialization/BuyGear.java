package tasks.initialization;

import methods.BankMethods;
import methods.GrandExchangeMethods;
import methods.WalkingMethods;
import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.container.impl.bank.Bank;
import org.dreambot.api.methods.container.impl.equipment.Equipment;
import tasks.AbstractTask;

public class BuyGear extends AbstractTask {

    private WalkingMethods wm = new WalkingMethods();
    private BankMethods bm = new BankMethods();

    private GrandExchangeMethods gem = new GrandExchangeMethods();


    @Override
    public boolean accept() {
        if(Equipment.isEmpty() && config.lumbridgeTeleArea.contains(getLocalPlayer())){ // just died or started
            return true;
        }
        else{
            if(config.isInitBankOpened()){
                if(!Bank.contains(config.strPot4) || !Bank.contains(config.lobster)){
                    return true;
                }
                else{
                    for(int i = 0; i < config.getGearList().length; i++){
                        if(!Bank.contains(config.getGearList()[i]) && !Inventory.contains(config.getGearList()[i]) && !Equipment.contains(config.getGearList()[i])){
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

        gem.BuyGearIfNecessary(config.amuletOfPower, 1, Calculations.random(5, 10) * 10000);
        gem.BuyGearIfNecessary(config.blackCape, 1, Calculations.random(1, 10) * 10000);
        gem.BuyGearIfNecessary(config.leatherBoots, 1, Calculations.random(1, 10) * 10000);
        gem.BuyGearIfNecessary(config.ironPlatebody, 1, Calculations.random(1, 10) * 10000);
        gem.BuyGearIfNecessary(config.ironPlatelegs, 1, Calculations.random(1, 10) * 10000);
        gem.BuyGearIfNecessary(config.ironFullHelm, 1, Calculations.random(1, 10) * 10000);
        gem.BuyGearIfNecessary(config.mithPlatebody, 1, Calculations.random(1, 10) * 10000);
        gem.BuyGearIfNecessary(config.mithPlatelegs, 1, Calculations.random(1, 10) * 10000);
        gem.BuyGearIfNecessary(config.mithFullHelm, 1, Calculations.random(1, 10) * 10000);
        gem.BuyGearIfNecessary(config.runeChainbody, 1, Calculations.random(4, 10) * 10000);
        gem.BuyGearIfNecessary(config.runePlatelegs, 1, Calculations.random(4, 10) * 10000);
        gem.BuyGearIfNecessary(config.runeFullHelm, 1, Calculations.random(4, 10) * 10000);
        gem.BuyGearIfNecessary(config.ironKiteshield, 1, Calculations.random(1, 10) * 10000);
        gem.BuyGearIfNecessary(config.mithKiteshield, 1, Calculations.random(1, 10) * 10000);
        gem.BuyGearIfNecessary(config.runeKiteshield, 1, Calculations.random(4, 10) * 10000);
        gem.BuyGearIfNecessary(config.ironScimitar, 1, Calculations.random(1, 10) * 10000);
        gem.BuyGearIfNecessary(config.blackScimitar, 1, Calculations.random(1, 10) * 10000);
        gem.BuyGearIfNecessary(config.mithScimitar, 1, Calculations.random(1, 10) * 10000);
        gem.BuyGearIfNecessary(config.addyScimitar, 1, Calculations.random(1, 10) * 10000);
        gem.BuyGearIfNecessary(config.runeScimitar, 1, Calculations.random(4, 10) * 10000);
        gem.BuyGearIfNecessary(config.leatherGloves, 1, Calculations.random(1, 10) * 10000);
        gem.BuyGearIfNecessary(config.strPot4, 300, Calculations.random(5, 10) * 100);
        gem.BuyGearIfNecessary(config.lobster, 1000, Calculations.random(5, 10) * 100);

        gem.CloseGE();

        // deposit everything
        bm.OpenBank();
        bm.DepositAllInventory();


        return 0;
    }
}
