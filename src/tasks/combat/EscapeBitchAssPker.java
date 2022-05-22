package tasks.combat;

import methods.WalkingMethods;
import org.dreambot.api.methods.walking.impl.Walking;
import org.dreambot.api.script.listener.ChatListener;
import org.dreambot.api.wrappers.widgets.message.Message;
import tasks.AbstractTask;

public class EscapeBitchAssPker extends AbstractTask {

    private WalkingMethods wm = new WalkingMethods();
    private boolean teleBlocked = false;

    // if interacting with another player || skulled player within attack range
    @Override
    public boolean accept() {
        return false;
    }

    @Override
    public int execute() {
        log("[T] Escape Bitch Ass Pker");
        // while not in ferox bank
        while(config.feroxEnclaveBank.contains(getLocalPlayer()) && !teleBlocked){

            // check run is on
            if(!Walking.isRunEnabled()) {

            }
            // RUN to enclave bank

            // if tele block'd switch to edgville

            //WalkFromWildy

            //

        }
        wm.WalkFromWildy(config.edgevilleBank,"EdvilleBank");

        return 0;
    }

}
