package tasks.mossGiants;

import methods.WalkingMethods;
import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.methods.skills.Skill;
import org.dreambot.api.methods.skills.Skills;
import tasks.AbstractTask;

public class TravelToMossGiants extends AbstractTask {
    public WalkingMethods wm = new WalkingMethods();

    @Override
    public boolean accept() {
        return  !config.mossGiantWildernessArea.contains(Players.localPlayer()) // Not in the Giant Rat Area
                && Skills.getRealLevel(Skill.DEFENCE) >= 40 // Correct states to be fighting giant frogs
                && Inventory.contains("Lobster") && Inventory.contains("Strength potion(4)") // Inventory has supplies
                && (getLocalPlayer().getCharacterInteractingWithMe() == null || !getLocalPlayer().getCharacterInteractingWithMe().getName().equals(config.getCurMonster())); // Not already in combat with current monster
    }

    public int execute() {
        log("[T] Traveling to moss giants");
        if(getLocalPlayer().getY() > config.edgevilleWildernessDitchNorthArea.getY()){
            wm.Walk(config.mossGiantWildernessArea, "Moss Giants already in wildy");
        }
        else {
            wm.WalkToWildy(config.mossGiantWildernessArea, "Moss Giants through ditch");
        }
        return 0;
    }

}
