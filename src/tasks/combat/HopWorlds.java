package tasks.combat;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.methods.tabs.Tab;
import org.dreambot.api.methods.tabs.Tabs;
import org.dreambot.api.methods.world.World;
import org.dreambot.api.methods.world.Worlds;
import org.dreambot.api.methods.worldhopper.WorldHopper;
import org.dreambot.api.wrappers.interactive.Player;
import tasks.AbstractTask;

import java.util.ArrayList;
import java.util.List;

public class HopWorlds extends AbstractTask {

    private List<Player> allPlayersInteractingWithCurMonster = new ArrayList<>();
    @Override
    public boolean accept() {
        allPlayersInteractingWithCurMonster = Players.all(p -> p != null && p.getInteractingCharacter() != null && p.getInteractingCharacter().getName().equals(config.getCurMonster()));
        return allPlayersInteractingWithCurMonster != null && allPlayersInteractingWithCurMonster.size() >= 2 && getLocalPlayer().getInteractingCharacter() == null;
    }

    @Override
    public int execute() {
        log("[T] Hopping worlds");
        config.setStatus("Hopping Worlds");
        // F2P, no level requirement, no PVP world
        World world = Worlds.getRandomWorld(w -> w.isF2P() && !w.isPVP() && w.getMinimumLevel() == 0);
        //World world = Worlds.getWorld(w -> w.getWorld() != Worlds.getMyWorld().getWorld() && w.isF2P() && !w.isPVP() && w.getMinimumLevel() == 0);
        WorldHopper.hopWorld(world);
        return Calculations.random(5000,7000);
    }

}
