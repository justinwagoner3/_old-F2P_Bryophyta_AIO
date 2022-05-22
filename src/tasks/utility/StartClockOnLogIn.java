package tasks.utility;

import org.dreambot.api.Client;
import tasks.AbstractTask;

public class StartClockOnLogIn extends AbstractTask {
    @Override
    public boolean accept() {
        return Client.isLoggedIn() && config.timer.isPaused();
    }

    @Override
    public int execute() {
        log("Resuming timer");
        config.timer.resume();
        return 0;
    }

}
