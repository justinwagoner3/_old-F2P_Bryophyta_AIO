package tasks.initialization;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.container.impl.bank.Bank;
import org.dreambot.api.methods.grandexchange.GrandExchange;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.methods.skills.Skill;
import org.dreambot.api.methods.skills.Skills;
import tasks.AbstractTask;

public class BuyGear extends AbstractTask {
    @Override
    public boolean accept() {
        // TODO - update this for all items
        return Skills.getRealLevel(Skill.STRENGTH) == 1 && config.grandExchangeArea.contains(Players.localPlayer()) && !Bank.contains("Rune chainbody");
    }

    @Override
    public int execute() {
        log("[T] Buying Gear");
        /*
        Bank.open();
        sleep(400);
        Bank.close();
        if(Bank.contains("Rune chainbody")){
            log("Contains");
        }

        this works for checking if bank contains - HAVE TO HAVE OPENED IT ONCE
         */

        // open bank and deposit everything, withdraw cash stack
        // TODO - update using new methods
        if(Bank.isOpen() || Bank.open()) {
            sleepUntil(this::bankIsOpen, Calculations.random(2000, 3000));
            if(Inventory.onlyContains("Coins")){
                if(Bank.close()){
                    sleepUntil(this::bankIsClosed,Calculations.random(2000,3000));
                }
            }
            if(!Inventory.isEmpty()) {
                if (Bank.depositAllItems()) {
                    sleepUntil(this::invIsEmpty, Calculations.random(2000, 3000));
                }
            }
            if(Inventory.isEmpty()){
                if(Bank.withdrawAll("Coins")){
                    sleepUntil(this::invIsNotEmpty,Calculations.random(2000,3000));
                }
            }
        }

        // open ge, buy gear, deposit it all in bank
        if(Inventory.onlyContains("Coins")) {
            if (GrandExchange.open()) {
                sleepUntil(GrandExchange::isOpen, Calculations.random(2000, 3000));
                if(GrandExchange.buyItem(config.amuletOfPower, 1, Calculations.random(5, 10) * 10000)){
                    sleepUntil(GrandExchange::isReadyToCollect,Calculations.random(100000,200000));
                    GrandExchange.collect();
                }
                if(GrandExchange.buyItem(config.blackCape, 1, Calculations.random(1, 10) * 10000)){
                    sleepUntil(GrandExchange::isReadyToCollect,Calculations.random(100000,200000));
                    GrandExchange.collect();
                }
                if(GrandExchange.buyItem(config.leatherBoots, 1, Calculations.random(1, 10) * 10000)){
                    sleepUntil(GrandExchange::isReadyToCollect,Calculations.random(100000,200000));
                    GrandExchange.collect();
                }
                if(GrandExchange.buyItem(config.ironPlatebody, 1, Calculations.random(1, 10) * 10000)){
                    sleepUntil(GrandExchange::isReadyToCollect,Calculations.random(100000,200000));
                    GrandExchange.collect();
                }
                if(GrandExchange.buyItem(config.ironPlatelegs, 1, Calculations.random(1, 10) * 10000)){
                    sleepUntil(GrandExchange::isReadyToCollect,Calculations.random(100000,200000));
                    GrandExchange.collect();
                }
                if(GrandExchange.buyItem(config.ironFullHelm, 1, Calculations.random(1, 10) * 10000)){
                    sleepUntil(GrandExchange::isReadyToCollect,Calculations.random(100000,200000));
                    GrandExchange.collect();
                }
                if(GrandExchange.buyItem(config.mithPlatebody, 1, Calculations.random(1, 10) * 10000)){
                    sleepUntil(GrandExchange::isReadyToCollect,Calculations.random(100000,200000));
                    GrandExchange.collect();
                }
                if(GrandExchange.buyItem(config.mithPlatelegs, 1, Calculations.random(1, 10) * 10000)){
                    sleepUntil(GrandExchange::isReadyToCollect,Calculations.random(100000,200000));
                    GrandExchange.collect();
                }
                if(GrandExchange.buyItem(config.mithFullHelm, 1, Calculations.random(1, 10) * 10000)){
                    sleepUntil(GrandExchange::isReadyToCollect,Calculations.random(100000,200000));
                    GrandExchange.collect();
                }
                if(GrandExchange.buyItem(config.runeChainbody, 1, Calculations.random(4, 10) * 10000)){
                    sleepUntil(GrandExchange::isReadyToCollect,Calculations.random(100000,200000));
                    GrandExchange.collect();
                }
                if(GrandExchange.buyItem(config.runePlatelegs, 1, Calculations.random(4, 10) * 10000)){
                    sleepUntil(GrandExchange::isReadyToCollect,Calculations.random(100000,200000));
                    GrandExchange.collect();
                }
                if(GrandExchange.buyItem(config.runeFullHelm, 1, Calculations.random(4, 10) * 10000)){
                    sleepUntil(GrandExchange::isReadyToCollect,Calculations.random(100000,200000));
                    GrandExchange.collect();
                }
                if(GrandExchange.buyItem(config.ironKiteshield, 1, Calculations.random(1, 10) * 10000)){
                    sleepUntil(GrandExchange::isReadyToCollect,Calculations.random(100000,200000));
                    GrandExchange.collect();
                }
                if(GrandExchange.buyItem(config.mithKiteshield, 1, Calculations.random(1, 10) * 10000)){
                    sleepUntil(GrandExchange::isReadyToCollect,Calculations.random(100000,200000));
                    GrandExchange.collect();
                }
                if(GrandExchange.buyItem(config.runeKiteshield, 1, Calculations.random(4, 10) * 10000)){
                    sleepUntil(GrandExchange::isReadyToCollect,Calculations.random(100000,200000));
                    GrandExchange.collect();
                }
                if(GrandExchange.buyItem(config.ironScimitar, 1, Calculations.random(1, 10) * 10000)){
                    sleepUntil(GrandExchange::isReadyToCollect,Calculations.random(100000,200000));
                    GrandExchange.collect();
                }
                if(GrandExchange.buyItem(config.blackScimitar, 1, Calculations.random(1, 10) * 10000)){
                    sleepUntil(GrandExchange::isReadyToCollect,Calculations.random(100000,200000));
                    GrandExchange.collect();
                }
                if(GrandExchange.buyItem(config.mithScimitar, 1, Calculations.random(1, 10) * 10000)){
                    sleepUntil(GrandExchange::isReadyToCollect,Calculations.random(100000,200000));
                    GrandExchange.collect();
                }
                if(GrandExchange.buyItem(config.addyScimitar, 1, Calculations.random(1, 10) * 10000)){
                    sleepUntil(GrandExchange::isReadyToCollect,Calculations.random(100000,200000));
                    GrandExchange.collect();
                }
                if(GrandExchange.buyItem(config.runeScimitar, 1, Calculations.random(4, 10) * 10000)){
                    sleepUntil(GrandExchange::isReadyToCollect,Calculations.random(100000,200000));
                    GrandExchange.collect();
                }
                if(GrandExchange.buyItem(config.lobster, 100, Calculations.random(5, 10) * 100)){
                    sleepUntil(GrandExchange::isReadyToCollect,Calculations.random(100000,200000));
                    GrandExchange.collect();
                }
                if(GrandExchange.buyItem(config.strPot4, 300, Calculations.random(5, 10) * 100)){
                    sleepUntil(GrandExchange::isReadyToCollect,Calculations.random(100000,200000));
                    GrandExchange.collect();
                }
                if(GrandExchange.buyItem(config.leatherGloves, 1, Calculations.random(1, 10) * 10000)){
                    sleepUntil(GrandExchange::isReadyToCollect,Calculations.random(100000,200000));
                    GrandExchange.collect();
                }
            }

            if(Bank.open()){
                if (Bank.depositAllItems()) {
                    sleepUntil(this::invIsEmpty, Calculations.random(2000, 3000));
                }
            }
        }
        return 0;
    }

    private boolean bankIsOpen(){
        return Bank.isOpen();
    }

    private boolean bankIsClosed(){
        return !Bank.isOpen();
    }

    private boolean invIsEmpty(){
        return Inventory.isEmpty();
    }

    private boolean invIsNotEmpty(){
        return !Inventory.isEmpty();
    }

}
