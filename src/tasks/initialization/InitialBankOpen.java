package tasks.initialization;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.container.impl.bank.Bank;
import org.dreambot.api.methods.interactive.Players;
import tasks.AbstractTask;

public class InitialBankOpen extends AbstractTask {

    // TODO - change this to check if within certain distance of any bank
    @Override
    public boolean accept() {
        return !config.isInitBankOpened() && config.grandExchangeArea.contains(Players.localPlayer());
    }

    @Override
    public int execute() {
        log("[T] Initial Bank Open");
        config.setStatus("Initial Bank Open");
        if(Bank.open()){
            sleepUntil(Bank::isOpen,Calculations.random(2000,3000));
            Bank.close();
        }
        config.setInitBankOpened(true);
        return Calculations.random(300,500);
    }
}
