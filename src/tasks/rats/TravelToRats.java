package tasks.rats;

import methods.WalkingMethods;
import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.methods.skills.Skill;
import org.dreambot.api.methods.skills.Skills;
import tasks.AbstractTask;

public class TravelToRats extends AbstractTask {

    private WalkingMethods wm = new WalkingMethods();

    @Override
    public boolean accept() {
        return  !config.smallGiantRatArea.contains(Players.localPlayer()) // Not in the Giant Rat Area
                && Skills.getRealLevel(Skill.DEFENCE) < 20 // Low enough stats to be fighting rats
                && Inventory.contains("Lobster") && Inventory.contains("Strength potion(4)") // Inventory has supplies
                && !getLocalPlayer().isInCombat(); // Not already in combat
    }

    @Override
    public int execute() {
        log("[T] Traveling to rats");
        config.setStatus("Traveling to rats");
        // teleport home, if far away from home
        // TODO - there's a todo about home tele in TravelToLumBank, add a check in the method about no need to teleport if already close enough
        if(!config.lumbridgeAndSwampArea.contains(Players.localPlayer()) && !config.lumbridgeBank.contains(Players.localPlayer()) && !config.lumbridgeSecondFloor.contains(Players.localPlayer())) {
            wm.TeleHome();
        }
        wm.Walk(config.smallGiantRatArea, "Rats");
        return 0;
    }
}
