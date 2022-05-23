package tasks.combat;

import methods.BankMethods;
import methods.WalkingMethods;
import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.methods.walking.impl.Walking;
import org.dreambot.api.methods.world.World;
import org.dreambot.api.methods.world.Worlds;
import org.dreambot.api.methods.worldhopper.WorldHopper;
import org.dreambot.api.wrappers.interactive.Player;
import tasks.AbstractTask;

import java.util.List;

public class EscapeBitchAssPker extends AbstractTask {

    private WalkingMethods wm = new WalkingMethods();
    private BankMethods bm = new BankMethods();
    private boolean teleBlocked = false;

    // if interacting with another player || skulled player within attack range
    @Override
    public boolean accept() {
        List<Player> allPlayers = Players.all(p -> p != null && p.isSkulled() && Math.abs(p.getLevel()-getLocalPlayer().getLevel()) <= wm.getWildernessLevel());
        if(!allPlayers.isEmpty()){
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public int execute() {
        log("[T] Escape Bitch Ass Pker");
        config.setStatus("Escape Bitch Ass Pker");
        // while not in ferox bank
        while(!config.edgevilleBank.contains(getLocalPlayer()) && !config.feroxEnclaveBank.contains(getLocalPlayer())  && !config.lumbridgeTeleArea.contains(getLocalPlayer())){

            // turn on quick prayers if not on and high enough level

            // check if need to eat
            if(getLocalPlayer().getHealthPercent() < 60){
                Inventory.interact("Lobster","Eat");
            }

            // check run is on
            if(!Walking.isRunEnabled()) {
                Walking.toggleRun();
            }

            // RUN
            if(config.isTeleBlocked()){
                if (Walking.shouldWalk(Calculations.random(10))) {
                    Walking.walk(config.edgevilleWildernessDitchSouthArea);
                }
            }
            else{
                if (Walking.shouldWalk(Calculations.random(10))) {
                    Walking.walk(config.feroxEnclaveBank);
                }
            }

            // TODO - implement teleport jewerly eventually

            // TODO - implement log out

        }

        // TODO - should be function
        // hop worlds
        World world = Worlds.getRandomWorld(w -> w.isF2P() && !w.isPVP() && w.getMinimumLevel() == 0);
        WorldHopper.hopWorld(world);
        // TODO - make a better sleep than this
        sleep(Calculations.random(9000,11000));

        if(!config.lumbridgeTeleArea.contains(getLocalPlayer())){
            log("Got away from that bitch");
            bm.OpenBank();
            bm.DepositAllInventory();
        }
        else{
            log("Damn that bitch got you");
        }


        return 0;
    }

}
