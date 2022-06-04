package tasks.initialization;

import methods.BankingMethods;
import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.interactive.Players;
import tasks.AbstractTask;

public class InitialBankOpen extends AbstractTask {

    private BankingMethods bm = new BankingMethods();

    // TODO - change this to check if within certain distance of any bank
    // TODO - should be in onStart probably
    // TODO - only necessary to do this when equipment slots are not full (except arrows and ring) to see if buying gear is necessary
    @Override
    public boolean accept() {
        return !config.isInitBankOpened()
                && (config.grandExchangeArea.contains(Players.localPlayer()) || config.feroxEnclave.contains(getLocalPlayer()) || config.varrockEastBank.contains(getLocalPlayer()) || config.lumbridgeBank.contains(getLocalPlayer()));
    }

    @Override
    public int execute() {
        log("[T] Initial Bank Open");
        config.setStatus("Initial Bank Open");
        bm.OpenBank();
        bm.CloseBank();
        config.setInitBankOpened(true);
        return Calculations.random(600,1200);
    }
}
