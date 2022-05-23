package tasks.frogs;

import methods.WalkingMethods;
import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.methods.skills.Skill;
import org.dreambot.api.methods.skills.Skills;
import tasks.AbstractTask;

public class TravelToFrogs extends AbstractTask {

    public WalkingMethods wm = new WalkingMethods();

    @Override
    public boolean accept() {
        return  !config.giantFrogArea.contains(Players.localPlayer()) // Not in the Giant Rat Area
                && (Skills.getRealLevel(Skill.DEFENCE) >= 20 && Skills.getRealLevel(Skill.DEFENCE) < 40) // Correct states to be fighting giant frogs
                && Inventory.contains("Lobster") && Inventory.contains("Strength potion(4)") // Inventory has supplies
                && !getLocalPlayer().isInCombat(); // Not already in combat
    }

    public int execute() {
        log("[T] Traveling to giant frogs");
        config.setStatus("Traveling to giant frogs");
        // teleport home, if far away from home
        // TODO - there's a todo about home tele in TravelToLumBank, add a check in the method about no need to teleport if already close enough
        if(!config.lumbridgeAndSwampArea.contains(Players.localPlayer()) && !config.lumbridgeBank.contains(Players.localPlayer()) && !config.lumbridgeSecondFloor.contains(Players.localPlayer())) {
            wm.TeleHome();
        }
        wm.Walk(config.giantFrogArea, "Giant Frogs");
        return 0;
    }

}
