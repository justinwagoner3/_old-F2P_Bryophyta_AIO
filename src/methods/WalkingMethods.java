package methods;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.dialogues.Dialogues;
import org.dreambot.api.methods.interactive.GameObjects;
import org.dreambot.api.methods.magic.Magic;
import org.dreambot.api.methods.magic.Normal;
import org.dreambot.api.methods.map.Area;
import org.dreambot.api.methods.walking.impl.Walking;
import org.dreambot.api.methods.widget.Widgets;
import org.dreambot.api.wrappers.interactive.GameObject;
import org.dreambot.api.wrappers.widgets.WidgetChild;

import static org.dreambot.api.Client.getLocalPlayer;
import static org.dreambot.api.methods.MethodProvider.*;

public class WalkingMethods extends AbstractMethod {

    private DialogueMethods dm = new DialogueMethods();

    // TODO - might want to set it to return bool to maybe be used for double checking
    public void Walk(Area destination, String location){
        log("[m] Walking to " + location);

        while(!destination.contains(getLocalPlayer())) {
            // check marked mini map tile if in destination location, then don't spam walk
            if(destination.contains(Walking.getDestination())){
                sleepUntil(() -> destination.contains(getLocalPlayer()),7000);
            }
            if (Walking.shouldWalk(Calculations.random(0,6))) {
                Walking.walk(destination);
            }
        }
    }

    // TODO - probably a better way to handle this
    // Handles hopping wilderness ditch and leaving ferox enclave
    public void WalkToWildy(Area destination, String location){
        log("[m] Walk To Wildy " + location);
        // check if in ferox enclave
        if(config.feroxEnclave.contains((getLocalPlayer()))){
            while(!config.feroxEnclaveExteriorNorth.contains(getLocalPlayer().getTile())) {
                Walk(config.feroxEnclaveInteriorNorth,"Ferox enclave north");
                GameObject northBarrier = GameObjects.closest(object -> object.getName() != null && object.getName().equals("Barrier"));
                if(northBarrier != null) {
                    if (northBarrier.interact("Pass-Through")) {
                        sleepUntil(() -> Dialogues.canContinue() || config.feroxEnclaveExteriorNorth.contains(getLocalPlayer()), Calculations.random(3000,4000));
                        dm.FeroxEnclaveMessage();
                    }
                }
            }
            Walk(destination,location);
        }
        // if already in Wildy, no need to jump the barrier
        else if(getLocalPlayer().getY() > config.edgevilleWildernessDitchNorthArea.getY()){
            Walk(destination,location);
        }
        else{
            while(!config.edgevilleWildernessDitchNorthArea.contains(getLocalPlayer())) {
                Walk(config.edgevilleWildernessDitchSouthArea, "Edgevile Wildy Ditch");
                GameObject wildyDitch = GameObjects.closest(object -> object.getName() != null && object.getID() == 23271);
                if(wildyDitch != null && wildyDitch.isOnScreen()){
                    log("wildy ditch not null");
                    if(wildyDitch.interact("Cross")){
                        sleepUntil(() -> Widgets.getWidgetChild(475, 11, 1) != null || config.edgevilleWildernessDitchNorthArea.contains(getLocalPlayer().getTile()), Calculations.random(5000,8000));
                        dm.CrossWildyDitchMessage();
                        sleepUntil(() -> config.edgevilleWildernessDitchNorthArea.contains(getLocalPlayer()),Calculations.random(3000,4000));
                    }
                }
            }
            sleep(Calculations.random(1000,2000)); // TODO -an extra sleep to make sure don't double cross ditch, but not best implementation
            Walk(destination,location);
        }
    }

    public void GoDownManhole(){
        while(!config.varrockSewerSouthLadderArea.contains(getLocalPlayer())) {
            GameObject manhole = GameObjects.closest(o -> o != null && o.getName().equals(config.manhole));
            if (manhole != null) {
                manhole.interact();
                sleep(Calculations.random(800, 2000));

            }
        }
    }
    public boolean TeleHome(){
        log("[m] TeleHome");
        if(config.lumbridgeTeleArea.getRandomTile().distance(getLocalPlayer()) < 200){
            return true;
        }
        // cast spell, wait to see if you are in lumbridge
        Magic.castSpell(Normal.HOME_TELEPORT);
        // TODO - sleep until || message listener says cooldown not reached
        sleepUntil(() -> config.lumbridgeTeleArea.contains(getLocalPlayer()), Calculations.random(20000, 30000));
        // double check - if failed, try again
        // TODO - check why failed - message listener about not having cooldown, if so don't try to tele again
        if(!config.lumbridgeTeleArea.contains(getLocalPlayer())){
            Magic.castSpell(Normal.HOME_TELEPORT);
            sleepUntil(() -> config.lumbridgeTeleArea.contains(getLocalPlayer()), Calculations.random(20000, 30000));
        }
        // return true or false if in lumbridge
        if(config.lumbridgeTeleArea.contains(getLocalPlayer())){
            return true;
        }
        else{
            return false;
        }
    }

    // TODO - fix this, would be useful to know when to stop running from a pker
    public int getWildernessLevel() {
        WidgetChild wildernessLevelWidget = Widgets.getWidgetChild(90, 46);
        if (wildernessLevelWidget != null) {
            return Integer.valueOf(wildernessLevelWidget.getText().replaceAll("\\D+",""));
        }
        return 0;
    }

    public void GoUpLadder() {
        while(!config.varrockSewersManholeArea.contains(getLocalPlayer())) {
            GameObject ladder = GameObjects.closest(o -> o != null && o.getName().equals(config.ladder));
            if (ladder != null) {
                ladder.interact();
                sleepUntil(() -> config.varrockSewersManholeArea.contains(getLocalPlayer()), Calculations.random(2000,3000));
            }
        }
    }
}
