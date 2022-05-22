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

    // TODO - might want to set it to return bool to maybe be used for double checking
    public void Walk(Area destination, String location){
        log("[m] Walking to " + location);

        // TODO- play around with 6 arg
        while(!destination.contains(getLocalPlayer().getTile())) {
            if (Walking.shouldWalk(Calculations.random(6))) {
                Walking.walk(destination);
            }
        }
    }

    public boolean WalkThroughFeroxEnclave(){
        log("[m] Walking through ferox enclave");
        // TODO- play around with 6 arg
        if (Walking.shouldWalk(Calculations.random(6))) {
            Walking.walk(config.feroxEnclaveInteriorNorth);
        }
        GameObject northBarrier = GameObjects.closest(object -> object.getName() != null && object.getName().equals("Barrier"));
        if(northBarrier != null) {
            if (northBarrier.interact("Pass-Through")) {
                sleepUntil(Dialogues::canEnterInput,Calculations.random(5000,6000));
                // TODO - forex enclove check should be function
                // TODO - Also first start of dialogue functions
                if (Dialogues.canContinue()) {
                    if (Dialogues.clickContinue()) {
                        sleep(Calculations.random(7000,10000));
                        if (Dialogues.areOptionsAvailable()) {
                            if (Dialogues.chooseOption(2)){
                                sleepUntil(() -> config.feroxEnclaveExteriorNorth.contains(getLocalPlayer()), Calculations.random(4000, 5000));
                            }
                        }
                    }
                }
            }
        }
        return config.feroxEnclaveExteriorNorth.contains(getLocalPlayer());

    }

    public void WalkToWildy(Area destination, String location){

        while(!config.edgevilleWildernessDitchNorthArea.contains(getLocalPlayer().getTile())) {
            Walk(config.edgevilleWildernessDitchSouthArea, "Edgevile Wildy Ditch");
            GameObject wildyDitch = GameObjects.closest(object -> object.getName() != null && object.getID() == 23271);
            if(wildyDitch != null && wildyDitch.isOnScreen()){
                log("wildy ditch not null");
                if(wildyDitch.interact("Cross")){
                    sleepUntil(() -> Widgets.getWidgetChild(475, 11, 1) != null || config.edgevilleWildernessDitchNorthArea.contains(getLocalPlayer().getTile()), Calculations.random(5000,8000));
                    CrossDitchInteraction();
                }
            }
        }
        Walk(destination,location);
    }

    public void WalkFromWildy(Area destination, String location) {
        while(!config.edgevilleWildernessDitchSouthArea.contains(getLocalPlayer().getTile())) {
            Walk(config.edgevilleWildernessDitchNorthArea, "Edgevile Wildy Ditch");
            GameObject wildyDitch = GameObjects.closest(object -> object.getName() != null && object.getID() == 23271);
            if(wildyDitch != null){
                log("wildy ditch not null");
                if(wildyDitch.interact("Cross")){
                    sleepUntil(() -> config.edgevilleWildernessDitchSouthArea.contains(getLocalPlayer().getTile()), Calculations.random(5000,8000));
                }
            }
        }
        Walk(destination,location);
    }

    public boolean CrossDitchInteraction(){
        WidgetChild widget = Widgets.getWidgetChild(475, 11, 1);
        if(widget == null){
            return true;
        }
        log("Interacting with ditch widget.");
        return widget.interact();
    }

    public boolean TeleHome(){
        log("[m] TeleHome");
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
}
