package tasks.shared_rats_frogs;

import methods.WalkingMethods;
import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.methods.skills.Skill;
import org.dreambot.api.methods.skills.Skills;
import tasks.AbstractTask;

public class TravelToLumbridgeBank extends AbstractTask {

    private WalkingMethods wm = new WalkingMethods();

    @Override
    public boolean accept() {
        return (!Inventory.contains(config.lobster) || (!Inventory.contains(config.strPot4) && !Inventory.contains(config.strPot3) && !Inventory.contains(config.strPot2) && !Inventory.contains(config.strPot1))) // inv does not have potions or lobster
                && Skills.getRealLevel(Skill.DEFENCE) < 40 // Low enough stats to be fighting rats/frogs
                && !config.lumbridgeBank.contains(Players.localPlayer());
    }

    @Override
    public int execute() {
        log("[T] Traveling to Lumbridge bank");
        // teleport home, if far away from home
        // TODO - add in potential tele home instead of running
        wm.Walk(config.lumbridgeBank, "Lumbridge bank");


        return 0;

    }

}
