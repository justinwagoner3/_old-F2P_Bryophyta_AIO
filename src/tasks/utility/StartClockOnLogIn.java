package tasks.utility;

import org.dreambot.api.Client;
import org.dreambot.api.methods.skills.SkillTracker;
import tasks.AbstractTask;

public class StartClockOnLogIn extends AbstractTask {

    // TODO - could just use a listener for this and have it in the main class
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
