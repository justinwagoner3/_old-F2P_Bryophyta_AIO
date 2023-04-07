package tasks.combat;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.methods.world.World;
import org.dreambot.api.methods.world.Worlds;
import org.dreambot.api.methods.worldhopper.WorldHopper;
import org.dreambot.api.wrappers.interactive.Player;
import tasks.AbstractTask;

import java.util.List;

public class HopWorlds extends AbstractTask {

    // TODO -figure out how to just hop to the world after yours (keep in mind f2p and p2p mix so can't just +1)

    @Override
    public boolean accept() {
        List<Player> allPlayersInteractingWithCurMonster = Players.all(p -> p != null && p.getInteractingCharacter() != null && p.getInteractingCharacter().getName() != null && p.getInteractingCharacter().getName().equals(config.getCurMonster()));
        return allPlayersInteractingWithCurMonster != null && allPlayersInteractingWithCurMonster.size() >= 2 && getLocalPlayer().getInteractingCharacter() == null;
    }

    @Override
    public int execute() {
        log("[T] Hopping worlds");
        config.setStatus("Hopping Worlds");
        // F2P, no level requirement, no PVP world
        World world = Worlds.getRandomWorld(w -> w.isF2P() && !w.isPVP() && w.getMinimumLevel() == 0);
        WorldHopper.hopWorld(world);
        return Calculations.random(5000,7000);
    }

}
