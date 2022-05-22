package tasks.mossGiants;

import methods.WalkingMethods;
import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.methods.skills.Skill;
import org.dreambot.api.methods.skills.Skills;
import tasks.AbstractTask;

public class TravelToFeroxBank extends AbstractTask {
    private WalkingMethods wm = new WalkingMethods();

    @Override
    public boolean accept() {
        return (!Inventory.contains(config.lobster) || (!Inventory.contains(config.strPot4) && !Inventory.contains(config.strPot3) && !Inventory.contains(config.strPot2) && !Inventory.contains(config.strPot1))) // inv does not have potions or lobster
                && Skills.getRealLevel(Skill.DEFENCE) >= 40 // high enough stats for moss giants
                && !config.feroxEnclaveBank.contains(Players.localPlayer());
    }

    @Override
    public int execute() {
        log("[T] Traveling to Ferox bank");
        if(getLocalPlayer().getY() > config.edgevilleWildernessDitchNorthArea.getY()){
            wm.Walk(config.feroxEnclaveBank, "Ferox Bank already in wildy");
        }
        else {
            wm.WalkToWildy(config.feroxEnclaveBank, "Ferox Bank through ditch");
        }


        return 0;

    }

}
