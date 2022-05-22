import config.Config;
import dataStructures.itemPrice;
import dataStructures.nameQuantity;
import org.dreambot.api.Client;
import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.container.impl.bank.Bank;
import org.dreambot.api.methods.grandexchange.GrandExchange;
import org.dreambot.api.methods.input.Camera;
import org.dreambot.api.methods.skills.Skill;
import org.dreambot.api.methods.skills.SkillTracker;
import org.dreambot.api.methods.skills.Skills;
import org.dreambot.api.script.Category;
import org.dreambot.api.script.ScriptManifest;
import org.dreambot.api.script.impl.TaskScript;
import org.dreambot.api.script.listener.InventoryListener;
import org.dreambot.api.wrappers.items.Item;
import tasks.allMonsters.MajorLevel;
import tasks.allMonsters.SwitchCombatStyle;
import tasks.combat.AttackMonster;
import tasks.combat.DrinkPotion;
import tasks.combat.EatFood;
import tasks.frogs.EquipGearInvFrogs;
import tasks.frogs.TravelToFrogs;
import tasks.frogs.WithdrawInvFrogs;
import tasks.initialization.BuyGear;
import tasks.initialization.InitialBankOpen;
import tasks.looting.Loot;
import tasks.mossGiants.EquipGearInvMossGiants;
import tasks.mossGiants.TravelToFeroxBank;
import tasks.mossGiants.TravelToMossGiants;
import tasks.mossGiants.WithdrawInvMossGiants;
import tasks.rats.EquipGearInvRats;
import tasks.rats.WithdrawInvRats;
import tasks.shared_rats_frogs.TravelToLumbridgeBank;
import tasks.rats.TravelToRats;
import tasks.allMonsters.UpgradeScimitar;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@ScriptManifest(category = Category.COMBAT,
        name = "Melee_F2P_AIO",
        description = "Buy gear. Train melee. Upgrade gear. Repeat.",
        author = "Jwagg",
        version = 1.1)


public class Melee_F2P_AIO extends TaskScript implements InventoryListener {
    Config config = Config.getConfig();
    private Anti time = new Anti(this);
    private Image attImage;
    private Image strImage;
    private Image defImage;
    private Image mossyKeyImage;
    private Image bryophytaImage;
    private Image coinsImage;
    private int mossyKeyCount = 0;
    private long profit = 0;
    private List<itemPrice> itemPrices = new ArrayList<>();


    @Override
    public void onStart() {

        sleepUntil(Client::isLoggedIn,120000);
        if(Client.isLoggedIn()) {
            log("CLient is logged");

            // load images
            LoadImages();

            // used for tracking time
            time = new Anti(this);

            // load the config
            Config config = Config.getConfig();

            // get the GE prices
            CreateItemPrices(config.getLootItems(), config.getLootItemsPrices());

            // Start DreamBot's skill tracker for the mining skill, so we can later see how much experience we've gained
            SkillTracker.start(Skill.ATTACK);
            SkillTracker.start(Skill.STRENGTH);
            SkillTracker.start(Skill.DEFENCE);
            SkillTracker.start(Skill.HITPOINTS);

            // set config values based on state
            if (Skills.getRealLevel(Skill.DEFENCE) < 20) {
                config.setCurMonster(config.giantRat);
            } else if (Skills.getRealLevel(Skill.DEFENCE) < 40) {
                config.setCurMonster(config.giantFrog);
            } else {
                config.setCurMonster(config.mossGiant);
            }


            // Now add our two tasks so the client knows what to do
            addNodes(new InitialBankOpen(),
                    new BuyGear(),
                    new SwitchCombatStyle(),
                    new Loot(),
                    new AttackMonster(),
                    new EatFood(),
                    new DrinkPotion(),
                    new MajorLevel(),
                    new UpgradeScimitar(),
                    new EquipGearInvRats(),
                    new TravelToRats(),
                    new TravelToLumbridgeBank(),
                    new WithdrawInvRats(),
                    new EquipGearInvFrogs(),
                    new TravelToFrogs(),
                    new WithdrawInvFrogs(),
                    new EquipGearInvMossGiants(),
                    new TravelToMossGiants(),
                    new TravelToFeroxBank(),
                    new WithdrawInvMossGiants()
            );
            log("Nodes Added");

            // zoom all the way out
            log("Setting zoom");
            while (Camera.getZoom() != 181) {
                Camera.setZoom(181);
            }
            log("Finished Setting zoom");
        }

    }

    private void CreateItemPrices(String[] lootItems, int[] lootItemPrices) {
        for(int i = 0; i < lootItems.length; i++) {
            itemPrices.add(new itemPrice(lootItems[i], lootItemPrices[i]));
        }
    }


    @Override
    public void onItemChange(Item[] items) {
        if(!Bank.isOpen()) {
            for (int i = 0; i < items.length; i++) {
                if (items[i].getName().equalsIgnoreCase(config.mossyKey)) {
                    mossyKeyCount++;
                }
                else if(items[i].getName().equals(config.strPot4) || items[i].getName().equals(config.strPot3) || items[i].getName().equals(config.strPot2) || items[i].getName().equals(config.strPot1) || items[i].getName().equals(config.vial)){
                    profit -= (long) config.strPot4Price/8;
                }
                else if(items[i].getName().equals(config.lobster)){
                    profit -= config.lobsterPrice;
                }
                else{
                    for(int j = 0; j < config.getLootItems().length; j++) {
                        if (items[i].getName().equalsIgnoreCase(config.getLootItems()[j])){
                            profit += config.getLootItemsPrices()[j];
                        }
                    }
                }
            }
        }
    }

    private class Anti {

        public final long START_TIME;

        public Anti(Melee_F2P_AIO melee_f2P_aio) {
            START_TIME = System.currentTimeMillis();
        }

        public long getElapsedTime() {
            return System.currentTimeMillis() - START_TIME;
        }

    }


    public void onPaint(Graphics g) {
        long ms = time.getElapsedTime();
        long hours = ms / 3600000;
        ms = ms % 3600000;
        long minutes = ms / 60000;
        ms = ms % 60000;
        long seconds = ms / 1000;

        String elapsedTime = String.format(
                "%d:%d:%d",
                hours,
                minutes,
                seconds
        );

        String attExpGainedText = String.format(
                " %.1fk (%.1fk)",
                (float) SkillTracker.getGainedExperience(Skill.ATTACK) / 1000,
                (float) SkillTracker.getGainedExperiencePerHour(Skill.ATTACK) / 1000
        );
        String attackLevelText = String.format(
                "%d (%d) | ",
                Skills.getRealLevel(Skill.ATTACK),
                SkillTracker.getGainedLevels(Skill.ATTACK)
        );

        String strExpGainedText = String.format(
                " %.1fk (%.1fk)",
                (float) SkillTracker.getGainedExperience(Skill.STRENGTH) / 1000,
                (float) SkillTracker.getGainedExperiencePerHour(Skill.STRENGTH) / 1000
        );
        String strengthLevelText = String.format(
                "%d (%d) | ",
                Skills.getRealLevel(Skill.STRENGTH),
                SkillTracker.getGainedLevels(Skill.STRENGTH)
        );
        String defExpGainedText = String.format(
                " %.1fk (%.1fk)",
                (float) SkillTracker.getGainedExperience(Skill.DEFENCE) / 1000,
                (float) SkillTracker.getGainedExperiencePerHour(Skill.DEFENCE) / 1000
        );
        String defenseLevelText = String.format(
                "%d (%d) | ",
                Skills.getRealLevel(Skill.DEFENCE),
                SkillTracker.getGainedLevels(Skill.DEFENCE)
        );
        String title = "Jwagg's Melee F2P AIO";

        long mossGiantKills = SkillTracker.getGainedExperience(Skill.HITPOINTS) / 80;
        long mossGiantKillsPerHour = SkillTracker.getGainedExperiencePerHour(Skill.HITPOINTS) / 80;
        String mossGiantKillsText = String.format(
                "%d", mossGiantKills
        );
        String mossGiantKillsPHText = String.format(
                "(%d)", mossGiantKillsPerHour
        );
        String mossyKeysText = String.format(
                "%d", mossyKeyCount
        );
        String mossyKeysPHText = String.format(
                "(%.1f)", (float) (mossyKeyCount * 3600000) / time.getElapsedTime()
        );
        String profitText = String.format(
                "%.1fk", (float) profit / 1000
        );
        String profitPHText = String.format(
                "(%.1fk)", (float) (profit * 3600000) / time.getElapsedTime() / 1000
        );


        /*
        long ms2 = SkillTracker.getTimeToLevel(Skill.ATTACK);
        long hours2 = ms2 / 3600000;
        ms2 = ms2 % 3600000;
        long minutes2 = ms2 / 60000;
        ms2 = ms2 % 60000;
        long seconds2 = ms2 / 1000;

        String timeToLevel = String.format(
                "Time To Next Level: %d:%d:%d",
                hours2,
                minutes2,
                seconds2
        );
        */

        // background - y:340 - 500
        g.setColor(Color.BLACK);
        g.fill3DRect(0, 350, 540, 160, true);

        g.setColor(Color.LIGHT_GRAY);
        g.fill3DRect(0, 335, 540, 45, true);

        g.setColor(Color.RED);
        g.fill3DRect(0, 350, 220, 50, true);

        g.setColor(Color.GREEN);
        g.fill3DRect(0, 400, 220, 50, true);

        g.setColor(Color.CYAN);
        g.fill3DRect(0, 450, 220, 50, true);

        g.setColor(Color.DARK_GRAY);
        g.fill3DRect(450, 350, 90, 30, true);


        g.setColor(Color.WHITE);
        g.setFont(new Font("Runescape UF", Font.PLAIN, 18));
        g.drawString(elapsedTime, 470, 370);
        g.drawString(mossGiantKillsText, 290, 430);
        g.drawString(mossGiantKillsPHText, 290, 450);
        g.drawString(mossyKeysText, 380, 460);
        g.drawString(mossyKeysPHText, 380, 480);
        g.drawString(profitText, 460, 460);
        g.drawString(profitPHText, 460, 480);
        g.setColor(Color.BLACK);
        g.setFont(new Font("Runescape UF", Font.PLAIN, 16));
        g.drawString(attackLevelText, 35, 380);
        g.drawString(attExpGainedText, 95, 380);
        g.drawString(strengthLevelText, 35, 430);
        g.drawString(strExpGainedText, 95, 430);
        g.drawString(defenseLevelText, 35, 480);
        g.drawString(defExpGainedText, 95, 480);

        g.setColor(Color.BLACK);
        g.setFont(new Font("Runescape UF", Font.PLAIN, 18));
        g.drawString(title, 235, 370);

        // draw imaged
        if (attImage != null) {
            g.drawImage(attImage, 12, 355, null);
        }
        if (strImage != null) {
            g.drawImage(strImage, 12, 405, null);
        }
        if (defImage != null) {
            g.drawImage(defImage, 12, 455, null);
        }
        if (bryophytaImage != null) {
            g.drawImage(bryophytaImage, 225, 395, null);
        }
        if (mossyKeyImage != null) {
            g.drawImage(mossyKeyImage, 360, 390, null);
        }
        if (coinsImage != null) {
            g.drawImage(coinsImage, 460, 390, null);
        }

        // Now we'll draw the text on the canvas at (5, 35). (0, 0) is the top left of the canvas.
        if (config.isDistracted()) {
            g.drawString("DISTRACTED", 5, 0);
        }
    }

    private void LoadImages(){
        try{
            attImage = ImageIO.read(new File("/Users/justin.wagoner/DreamBot/Images/Attack_cape.png"));
        }
        catch(IOException e){
            log("Failed to load attack image");

        }
        try{
            strImage = ImageIO.read(new File("/Users/justin.wagoner/DreamBot/Images/Strength_cape.png"));
        }
        catch(IOException e){
            log("Failed to load str image");

        }
        try{
            defImage = ImageIO.read(new File("/Users/justin.wagoner/DreamBot/Images/Defence_cape.png"));
        }
        catch(IOException e){
            log("Failed to load defence image");
        }
        try{
            coinsImage = ImageIO.read(new File("/Users/justin.wagoner/DreamBot/Images/Coins.png"));
        }
        catch(IOException e){
            log("Failed to load coins image");
        }
        try{
            bryophytaImage = ImageIO.read(new File("/Users/justin.wagoner/DreamBot/Images/Bryophyta.png"));
        }
        catch(IOException e){
            log("Failed to load Bryophyta image");
        }
        try{
            mossyKeyImage = ImageIO.read(new File("/Users/justin.wagoner/DreamBot/Images/Mossy_key.png"));
        }
        catch(IOException e){
            log("Failed to load mossy key image");
        }
    }

}
