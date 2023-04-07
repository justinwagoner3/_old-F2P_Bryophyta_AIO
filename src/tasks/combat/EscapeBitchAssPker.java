package tasks.combat;

import config.Config;
import methods.BankingMethods;
import methods.CombatMethods;
import methods.WalkingMethods;
import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.combat.Combat;
import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.interactive.GameObjects;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.methods.prayer.Prayer;
import org.dreambot.api.methods.prayer.Prayers;
import org.dreambot.api.methods.skills.Skill;
import org.dreambot.api.methods.skills.Skills;
import org.dreambot.api.methods.tabs.Tab;
import org.dreambot.api.methods.walking.impl.Walking;
import org.dreambot.api.methods.world.World;
import org.dreambot.api.methods.world.Worlds;
import org.dreambot.api.methods.worldhopper.WorldHopper;
import org.dreambot.api.wrappers.interactive.GameObject;
import org.dreambot.api.wrappers.interactive.Player;
import tasks.AbstractTask;

import java.util.List;

public class EscapeBitchAssPker extends AbstractTask {

    private WalkingMethods wm = new WalkingMethods();
    private BankingMethods bm = new BankingMethods();
    private CombatMethods cm = new CombatMethods();

    // TODO - should activate when north of wilderness and when not in the ferox enclave
    @Override
    public boolean accept() {
        if(getLocalPlayer().getY() > config.edgevilleWildernessDitchNorthArea.getY()) {
            List<Player> allPlayers = Players.all(p -> p != null && (p.getSkullIcon() != -1 && Math.abs(p.getLevel() - getLocalPlayer().getLevel()) <= 40) || (p.getInteractingCharacter() != null && p.getInteractingCharacter().equals(getLocalPlayer())));
            if (!allPlayers.isEmpty() && config.getState() == Config.State.MOSSGIANTS) { // TODO - change to north of wildy
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    @Override
    public int execute() {
        log("[T] Escape Bitch Ass Pker");
        config.setStatus("Escape Bitch Ass Pker");
        // while not in ferox bank or south of wildy
        while(!config.edgevilleBank.contains(getLocalPlayer()) && !config.feroxEnclaveBank.contains(getLocalPlayer())  && !config.lumbridgeTeleArea.contains(getLocalPlayer())){

            // check if need to eat
            if(getLocalPlayer().getHealthPercent() < 60){
                Inventory.interact("Lobster","Eat");
            }

            /*
            // TODO - implement something that checks if it would be possible to logout
            // try to log out
            Tabs.logout();
            if(!Client.isLoggedIn()){
                log("Succesfully switched worlds away from bitch as pker");
                // TODO - figure out how to change worlds from login menu instead of sleeping
                sleep(Calculations.random(30000,40000));
            }
            */

            // RUN, depending on if teleblocked or not
            if(config.isTeleBlocked()){
                if (Walking.shouldWalk(Calculations.random(12))) {
                    Walking.walk(config.edgevilleWildernessDitchSouthArea);
                    // TODO - turn to function and use with other wildy walking
                    GameObject wildyDitch = GameObjects.closest(object -> object.getName() != null && object.getID() == 23271);
                    if(wildyDitch != null){
                        log("wildy ditch not null");
                        if(wildyDitch.interact("Cross")){
                            sleepUntil(() -> config.edgevilleWildernessDitchSouthArea.contains(getLocalPlayer().getTile()), Calculations.random(2000,3000));
                        }
                    }
                }
            }
            else{
                if (Walking.shouldWalk(Calculations.random(12))) {
                    Walking.walk(config.feroxEnclaveBank);
                }
            }

            // TODO - turn to function
            // TODO - check range vs mage
            // TODO - disable range/mage once you only have x prayer left to make sure you keep item
            // turn on prayers if not on and high enough level and have prayer points
            if(Skills.getBoostedLevels(Skill.PRAYER) > 0) {
                if (Skills.getRealLevel(Skill.PRAYER) >= 25) {
                    if (!Prayers.isActive(Prayer.PROTECT_ITEM)) {
                        Prayers.toggle(true, Prayer.PROTECT_ITEM);
                    }
                }
                if (Skills.getRealLevel(Skill.PRAYER) >= 37) {
                    if (!Prayers.isActive(Prayer.PROTECT_FROM_MAGIC)) {
                        Prayers.toggle(true, Prayer.PROTECT_FROM_MAGIC);
                    }
                }
            }

            // turn auto retailiate off it necessary
            if(Combat.isAutoRetaliateOn()){
                Combat.toggleAutoRetaliate(false);
            }

            // check run is on
            if(!Walking.isRunEnabled() && Walking.getRunEnergy() > 2) {
                Walking.toggleRun();
            }

            // TODO - remove equipment if mager


            // TODO - implement teleport jewerly eventually

        }

        // turn prayers off
        cm.OpenTab(Tab.PRAYER);
        cm.TurnPrayerOff(Prayer.PROTECT_ITEM);
        cm.TurnPrayerOff(Prayer.PROTECT_FROM_MAGIC);
        sleep(Calculations.random(1000,2000));

        // reset tele blocked
        config.setTeleBlocked(false);


        // TODO - should be function
        // hop worlds
        World world = Worlds.getRandomWorld(w -> w.isF2P() && !w.isPVP() && w.getMinimumLevel() == 0);
        WorldHopper.hopWorld(world);
        // TODO - make a better sleep than this
        sleep(Calculations.random(9000,11000));

        if(!config.lumbridgeTeleArea.contains(getLocalPlayer())){
            log("Got away from that bitch");
            bm.OpenBank();
            bm.DepositAllInventory();
        }
        else{
            log("Damn that bitch got you");
        }

        return Calculations.random(600,1200);
    }

}
