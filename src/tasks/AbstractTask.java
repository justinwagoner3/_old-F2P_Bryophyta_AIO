package tasks;

import config.Config;
import org.dreambot.api.script.TaskNode;

public class AbstractTask extends TaskNode {

    protected Config config = Config.getConfig();

    @Override
    public boolean accept() {
        return false;
    }

    @Override
    public int execute() {
        return 0;
    }
}
