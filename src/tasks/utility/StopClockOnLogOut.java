package tasks.utility;

import org.dreambot.api.Client;
import tasks.AbstractTask;

// TODO - could just use a listener for this and have it in the main class
public class StopClockOnLogOut extends AbstractTask {

    long logTime;

    @Override
    public boolean accept() {
        return !Client.isLoggedIn();
    }

    @Override
    public int execute() {
        log("Pausing timer");
        config.timer.pause();
        return 0;
    }

}
