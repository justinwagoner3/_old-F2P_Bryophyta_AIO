package tasks.bryophyta;

import config.Config;
import methods.WalkingMethods;
import org.dreambot.api.Client;
import org.dreambot.api.methods.MethodProvider;
import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.interactive.GameObjects;
import org.dreambot.api.methods.interactive.NPCs;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.methods.map.Map;
import org.dreambot.api.methods.map.Tile;
import org.dreambot.api.methods.walking.impl.Walking;
import org.dreambot.api.methods.walking.pathfinding.impl.web.WebFinder;
import org.dreambot.api.methods.walking.pathfinding.impl.web.WebPathNode;
import org.dreambot.api.methods.walking.web.node.AbstractWebNode;
import org.dreambot.api.methods.walking.web.node.impl.BasicWebNode;
import org.dreambot.api.methods.walking.web.node.impl.EntranceWebNode;
import org.dreambot.api.utilities.impl.Condition;
import org.dreambot.api.wrappers.interactive.Entity;
import tasks.AbstractTask;

public class TravelToVarrockEastBank extends AbstractTask {
    private WalkingMethods wm = new WalkingMethods();


    @Override
    public boolean accept() {
        return !config.varrockEastBank.contains(Players.localPlayer()) //
                && config.getState() == Config.State.BRYOPHYTA
                && (Inventory.count(config.swordfish) < 20 || !Inventory.contains(config.mossyKey)); //
    }

    @Override
    public int execute() {
        log("[T] Traveling to Varrock E Bank");
        config.setStatus("Traveling to Varrock E Bank");
        wm.Walk(config.varrockSewerSouthLadderArea,"Sewer south ladder");
        wm.GoUpLadder();
        wm.Walk(config.varrockEastBank,"Varrock E Bank");
            return 0;
    }

    class CustomWebNode extends EntranceWebNode {

        private Condition condition;

        public CustomWebNode(Tile tile, String entityName, String action) {
            super(tile.getX(), tile.getY(), tile.getZ());
            setEntityName(entityName);
            setAction(action);
        }

        @Override
        public boolean execute(AbstractWebNode nextNode) {
            if (getTile().distance() > 10) {
                Walking.walk(getTile());
                return false;
            }
            Entity entity = GameObjects.closest(getEntityName());
            if (entity == null) {
                entity = NPCs.closest(getEntityName());
            }
            if (entity != null) {
                if (Map.canReach(entity)) {
                    if (entity.interact(getAction())) {
                        MethodProvider.sleepUntil(() -> getTile().distance() > 20
                                || Client.getLocalPlayer().getZ() != getTile().getZ(), 10000);
                    }
                } else {
                    Walking.walk(entity);
                }
            }
            return false;
        }

        public CustomWebNode from(AbstractWebNode node) {
            node.addConnections(this);
            return this;
        }

        public CustomWebNode to(AbstractWebNode node) {
            this.addConnections(node);
            return this;
        }

        public CustomWebNode condition(Condition condition) {
            this.condition = condition;
            return this;
        }

        @Override
        public boolean hasRequirements() {
            return (condition == null || condition.verify()) && super.hasRequirements();
        }

        @Override
        public boolean isValid() {
            return true;
        }
    }

}
