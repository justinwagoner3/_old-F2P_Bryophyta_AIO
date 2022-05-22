package tasks.utility;

import org.dreambot.api.Client;
import tasks.AbstractTask;

public class StopClockOnLogOut extends AbstractTask {

    long logTime;

    @Override
    public boolean accept() {
        return !Client.isLoggedIn();
    }

    @Override
    public int execute() {
        log("Pasuing timer");
        config.timer.pause();
        return 0;
    }

}
