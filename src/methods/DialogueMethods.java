package methods;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.dialogues.Dialogues;
import org.dreambot.api.methods.widget.Widgets;
import org.dreambot.api.wrappers.widgets.WidgetChild;

import static org.dreambot.api.Client.getLocalPlayer;
import static org.dreambot.api.methods.MethodProvider.log;
import static org.dreambot.api.methods.MethodProvider.sleepUntil;

public class DialogueMethods extends AbstractMethod {
    public void FeroxEnclaveMessage(){
        if (Dialogues.canContinue()) {
            log("handling ferox message for first time");
            if (Dialogues.clickContinue()) {
                sleepUntil(Dialogues::areOptionsAvailable,Calculations.random(1000,2000));
                if (Dialogues.areOptionsAvailable()) {
                    if (Dialogues.chooseOption(2)){
                        sleepUntil(() -> config.feroxEnclaveExteriorNorth.contains(getLocalPlayer()), Calculations.random(4000, 5000));
                    }
                }
            }
        }
    }

    // TODO - check the "Don't ask me again" button
    public void CrossWildyDitchMessage(){
        WidgetChild widget = Widgets.getWidgetChild(475, 11, 1);
        if(widget == null){
            return;
        }
        log("Interacting with ditch widget.");
        widget.interact();
        return;
    }

}
