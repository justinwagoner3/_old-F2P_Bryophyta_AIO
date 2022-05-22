package tasks.combat;

import methods.WalkingMethods;
import tasks.AbstractTask;

public class EscapeBitchAssPker extends AbstractTask {

    WalkingMethods wm = new WalkingMethods();

    // if interacting with another player || skulled player within attack range
    @Override
    public boolean accept() {
        return false;
    }

    @Override
    public int execute() {
        log("[T] Escape Bitch Ass Pker");
        // while not in ferox bank
        while(config.feroxEnclaveBank.contains(getLocalPlayer())){

            // check run is on

            // RUN to enclave bank

            // if tele block'd switch to edgville

            //WalkFromWildy

            //

        }
        wm.WalkFromWildy(config.edgevilleBank,"EdvilleBank");

        return 0;
    }

}
