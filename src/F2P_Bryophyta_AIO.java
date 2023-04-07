import config.Config;
import methods.BankingMethods;
import methods.CombatMethods;
import org.dreambot.api.Client;
import org.dreambot.api.data.GameState;
import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.container.impl.bank.Bank;
import org.dreambot.api.methods.input.Camera;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.methods.prayer.Prayer;
import org.dreambot.api.methods.settings.PlayerSettings;
import org.dreambot.api.methods.skills.Skill;
import org.dreambot.api.methods.skills.SkillTracker;
import org.dreambot.api.methods.skills.Skills;
import org.dreambot.api.methods.tabs.Tab;
import org.dreambot.api.methods.walking.impl.Walking;
import org.dreambot.api.methods.walking.pathfinding.impl.obstacle.impl.PassableObstacle;
import org.dreambot.api.script.Category;
import org.dreambot.api.script.ScriptManifest;
import org.dreambot.api.script.impl.TaskScript;
import org.dreambot.api.script.listener.ChatListener;
import org.dreambot.api.script.listener.GameStateListener;
import org.dreambot.api.script.listener.InventoryListener;
import org.dreambot.api.utilities.Timer;
import org.dreambot.api.wrappers.items.Item;
import org.dreambot.api.wrappers.widgets.message.Message;
import tasks.bryophyta.*;
import tasks.combat.*;
import tasks.states.*;
import tasks.frogs.EquipGearFrogs;
import tasks.frogs.TravelToFrogs;
import tasks.frogs.WithdrawInvFrogs;
import tasks.initialization.BuyGear;
import tasks.initialization.InitialBankOpen;
import tasks.looting.BuryBones;
import tasks.looting.Loot;
import tasks.looting.SellLoot;
import tasks.mossGiants.EquipGearMossGiants;
import tasks.mossGiants.TravelToFeroxBank;
import tasks.mossGiants.TravelToMossGiants;
import tasks.mossGiants.WithdrawInvMossGiants;
import tasks.rats.EquipGearRats;
import tasks.rats.WithdrawInvRats;
import tasks.shared_rats_frogs.TravelToLumbridgeBank;
import tasks.rats.TravelToRats;
import tasks.utility.EnableRun;
import tasks.utility.StartClockOnLogIn;
import tasks.utility.StopClockOnLogOut;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

@ScriptManifest(category = Category.COMBAT,
        name = "Jwagg's F2P Bryophyta AIO",
        description = "Choose melee and/or ranged. Buy gear. Train. Upgrade Gear. Fight Bryophyta.",
        author = "Jwagg",
        version = 1.1)


public class F2P_Bryophyta_AIO extends TaskScript implements InventoryListener, GameStateListener, ChatListener {
    Config config = Config.getConfig();
    private Image attImage;
    private Image strImage;
    private Image defImage;
    private Image rangedImage;
    private Image prayerImage;
    private Image mossyKeyImage;
    private Image bryophytaImage;
    private Image coinsImage;
    private int mossyKeyCount = 0;
    private long profit = 0;
    private boolean isRunning = false;
    private CombatMethods cm = new CombatMethods();
    private BankingMethods bm = new BankingMethods();

    @Override
    public void onStart() {

        try {
            SwingUtilities.invokeAndWait(() ->  CreateGUI());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        }

        while(!isRunning){
            sleep(5000);
            log("Waiting for GUI input");
        }

        log("Training melee? " + config.isTrainingMelee());
        log("Training ranged? " + config.isTrainingRanged());
        log("Melee bryophyta? " + config.isBryophytaMelee());
        log("Ranged brophyta? " + config.isBryophytaRanged());

        config.ConfigureGearList();

        // wait til logged in
        sleepUntil(Client::isLoggedIn,120000);
        if(Client.isLoggedIn()) {
            log("Client is logged");

            // load images
            LoadImages();

            // used for tracking time
            config.timer = new Timer();

            // load the config
            Config config = Config.getConfig();

            // necessary for getting to bryophyta
            Walking.getAStarPathFinder().addObstacle(new PassableObstacle("Web", "Slash", null, null, null));
            Walking.getAStarPathFinder().addObstacle(new PassableObstacle("Door", "Open", null, null, null));
            Walking.getAStarPathFinder().addObstacle(new PassableObstacle("Wilderness Ditch", "Cross", null, null, null));
            Walking.getAStarPathFinder().addObstacle(new PassableObstacle("Barrier", "Pass-Through", null, null, null)); // TODO- double check this


            // Start DreamBot's skill tracker for the mining skill, so we can later see how much experience we've gained
            SkillTracker.start(Skill.ATTACK);
            SkillTracker.start(Skill.STRENGTH);
            SkillTracker.start(Skill.DEFENCE);
            SkillTracker.start(Skill.RANGED);
            SkillTracker.start(Skill.PRAYER);
            SkillTracker.start(Skill.HITPOINTS);

            // set the loot table, based on prayer level
            if(Skills.getRealLevel(Skill.PRAYER) >= 37){
                config.setCurLootItems(config.getLootItemsNoBones());
            }
            else{
                config.setCurLootItems(config.getLootItemsWithBones());
            }

            // Now add our two tasks so the client knows what to do
            addNodes(new EscapeBitchAssPker(),
                    new EatFood(),
                    new HopWorlds(),
                    new DrinkPotion(),
                    new InitialBankOpen(),
                    new StateChanger(),
                    new SellLoot(),
                    new BuyGear(),
                    new ChangeFightingStyle(),
                    new SwitchCombatStyle(),
                    new Loot(),
                    new BuryBones(),
                    new AttackMonster(),
                    new EnableRun(),
                    new MajorLevel(),
                    new UpgradeScimitar(),
                    new UpgradeShortbowAndCoif(),
                    new EquipGearRats(),
                    new TravelToRats(),
                    new TravelToLumbridgeBank(),
                    new WithdrawInvRats(),
                    new EquipGearFrogs(),
                    new TravelToFrogs(),
                    new WithdrawInvFrogs(),
                    new EquipGearMossGiants(),
                    new TravelToMossGiants(),
                    new TravelToFeroxBank(),
                    new WithdrawInvMossGiants(),
                    new EquipGearBryophyta(),
                    new TravelToBryophyta(),
                    new WithdrawInvBryophyta(),
                    new FightBryophyta(),
                    new TravelToVarrockEastBank(),
                    new StartClockOnLogIn(),
                    new StopClockOnLogOut()
            );
            log("Nodes Added");

            // close bank, if started with bank open
            if(Bank.isOpen()){
                log("Closing bank");
                config.setInitBankOpened(true);
                bm.CloseBank();
            }

            // zoom all the way out
            log("Setting zoom");
            if(Camera.getZoom() != 181) {
                Camera.setZoom(181);
            }
            if(Camera.getPitch() != 383) {
                Camera.keyboardRotateToPitch(383);
            }
            log("Finished Setting zoom");

            // turn on auto-retaliate
            log("Check auto-retaliate");
            cm.TurnAutoRetaliateOn();
            sleep(Calculations.random(500,1100));

            // turn prayers off
            log("Turn all prayers off");
            cm.TurnPrayerOff(Prayer.PROTECT_ITEM);
            cm.TurnPrayerOff(Prayer.PROTECT_FROM_MAGIC);
            sleep(Calculations.random(500,1100));

            // check equipment
            while(!cm.OpenTab(Tab.EQUIPMENT)){
                sleep(Calculations.random(500,1100));
            }
            sleep(Calculations.random(500,1100));

            // check inventory
            while(!cm.OpenTab(Tab.INVENTORY)){
                sleep(Calculations.random(500,1100));
            }
            sleep(Calculations.random(500,1100));

        }
    }

    public void SetPlayerSettings(){

    }

    @Override
    public void onItemChange(Item[] items) {
        // subtract
        if(Bank.isOpen()) {
            for (int i = 0; i < items.length; i++) {
                if (items[i].getName().equalsIgnoreCase(config.mossyKey)) {
                    mossyKeyCount++;
                }
                if(items[i].getName().equals(config.strPot4) || items[i].getName().equals(config.strPot3) || items[i].getName().equals(config.strPot2) || items[i].getName().equals(config.strPot1) || items[i].getName().equals(config.vial)){
                    profit -= (long) config.strPot4Price/8;
                }
                if(items[i].getName().equals(config.lobster)){
                    profit -= config.lobsterPrice;
                }

                else{
                    for(int j = 0; j < config.getCurLootItems().length; j++) {
                        if (items[i].getName().equalsIgnoreCase(config.getCurLootItems()[j])){
                            if(items[i].getName().equals(config.coins)){
                                profit += items[i].getAmount();
                            }
                            else{
                                //profit += config.getLootItemsPricesWithBones()[j];
                            }
                        }
                    }
                }
            }
        }
    }

    // TODO - change the if to only around xp... i want to be able to see the timer start right when the script does
    public void onPaint(Graphics g) {
        if(SkillTracker.hasStarted(Skill.STRENGTH)) {
            //long ms = config.time.getElapsedTime();
            long ms = config.timer.elapsed();
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

            String rangedExpGainedText = String.format(
                    " %.1fk (%.1fk)",
                    (float) SkillTracker.getGainedExperience(Skill.RANGED) / 1000,
                    (float) SkillTracker.getGainedExperiencePerHour(Skill.RANGED) / 1000
            );
            String rangedLevelText = String.format(
                    "%d (%d) | ",
                    Skills.getRealLevel(Skill.RANGED),
                    SkillTracker.getGainedLevels(Skill.RANGED)
            );

            String prayerExpGainedText = String.format(
                    " %.1fk (%.1fk)",
                    (float) SkillTracker.getGainedExperience(Skill.PRAYER) / 1000,
                    (float) SkillTracker.getGainedExperiencePerHour(Skill.PRAYER) / 1000
            );
            String prayerLevelText = String.format(
                    "%d (%d) | ",
                    Skills.getRealLevel(Skill.PRAYER),
                    SkillTracker.getGainedLevels(Skill.PRAYER)
            );
            String title = "Jwagg's F2P Bryophyta AIO";

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
                    "(%.1f)", (float) (mossyKeyCount * 3600000) / config.timer.elapsed()
            );
            String profitText = String.format(
                    "%.1fk", (float) profit / 1000
            );
            String profitPHText = String.format(
                    "(%.1fk)", (float) (profit * 3600000) / config.timer.elapsed() / 1000
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
            g.fill3DRect(0, 340, 540, 30, true);

            g.setColor(Color.LIGHT_GRAY);
            g.fill3DRect(0, 470, 540, 30, true);

            g.setColor(Color.RED);
            g.fill3DRect(0, 340, 220, 32, true);

            g.setColor(Color.GREEN);
            g.fill3DRect(0, 372, 220, 32, true);

            g.setColor(Color.CYAN);
            g.fill3DRect(0, 404, 220, 32, true);

            g.setColor(Color.GREEN);
            g.fill3DRect(0, 436, 220, 32, true);

            g.setColor(Color.WHITE);
            g.fill3DRect(0, 468, 220, 32, true);

            g.setColor(Color.DARK_GRAY);
            g.fill3DRect(450, 340, 90, 30, true);


            g.setColor(Color.WHITE);
            g.setFont(new Font("Runescape UF", Font.PLAIN, 18));
            g.drawString(elapsedTime, 470, 360);
            g.setFont(new Font("Runescape UF", Font.PLAIN, 16));
            g.drawString(mossGiantKillsText, 290, 410);
            g.drawString(mossGiantKillsPHText, 290, 430);
            g.drawString(mossyKeysText, 380, 440);
            g.drawString(mossyKeysPHText, 380, 460);
            g.drawString(profitText, 460, 440);
            g.drawString(profitPHText, 460, 460);
            g.setColor(Color.BLACK);
            g.setFont(new Font("Runescape UF", Font.PLAIN, 16));
            g.drawString(attackLevelText, 35, 362);
            g.drawString(attExpGainedText, 95, 362);
            g.drawString(strengthLevelText, 35, 394);
            g.drawString(strExpGainedText, 95, 394);
            g.drawString(defenseLevelText, 35, 426);
            g.drawString(defExpGainedText, 95, 426);
            g.drawString(rangedLevelText, 35, 458);
            g.drawString(rangedExpGainedText, 95, 458);
            g.drawString(prayerLevelText, 35, 490);
            g.drawString(prayerExpGainedText, 95, 490);

            g.setColor(Color.BLACK);
            g.setFont(new Font("Runescape UF", Font.PLAIN, 17));
            g.drawString(title, 230, 360);
            g.drawString(config.getStatus(), 235, 490);

            // draw imaged
            if (attImage != null) {
                g.drawImage(attImage, 5, 343, null);
            }
            if (strImage != null) {
                g.drawImage(strImage, 5, 376, null);
            }
            if (defImage != null) {
                g.drawImage(defImage, 5, 409, null);
            }
            if (rangedImage != null) {
                g.drawImage(rangedImage, 5, 441, null);
            }
            if (prayerImage != null) {
                g.drawImage(prayerImage, 5, 473, null);
            }
            if (bryophytaImage != null) {
                g.drawImage(bryophytaImage, 225, 380, null);
            }
            if (mossyKeyImage != null) {
                g.drawImage(mossyKeyImage, 360, 385, null);
            }
            if (coinsImage != null) {
                g.drawImage(coinsImage, 460, 380, null);
            }

            // Now we'll draw the text on the canvas at (5, 35). (0, 0) is the top left of the canvas.
            if (config.isDistracted()) {
                g.drawString("DISTRACTED", 5, 0);
            }
        }
    }

    private void LoadImages(){
        try{
            attImage = ImageIO.read(new File("/Users/justin.wagoner/DreamBot/Images/Attack_icon.png"));
        }
        catch(IOException e){
            log("Failed to load attack image");

        }
        try{
            strImage = ImageIO.read(new File("/Users/justin.wagoner/DreamBot/Images/Strength_icon.png"));
        }
        catch(IOException e){
            log("Failed to load str image");

        }
        try{
            defImage = ImageIO.read(new File("/Users/justin.wagoner/DreamBot/Images/Defence_icon.png"));
        }
        catch(IOException e){
            log("Failed to load defence image");
        }
        try{
            rangedImage = ImageIO.read(new File("/Users/justin.wagoner/DreamBot/Images/Ranged_icon.png"));
        }
        catch(IOException e){
            log("Failed to load defence image");
        }
        try{
            prayerImage = ImageIO.read(new File("/Users/justin.wagoner/DreamBot/Images/Prayer_icon.png"));
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

    // TODO - i think this will stop/start the timer but not for AFK breaks (maybe not the worst?)
    @Override
    public void onGameStateChange(GameState gameState) {
        if(gameState.equals(GameState.LOGIN_SCREEN)){
            config.timer.pause();
        }
        if(gameState.equals(GameState.LOGGING_IN)){
            config.timer.resume();
        }
    }

    @Override
    public void onGameMessage(Message message) {
        if (message.getMessage().contains(config.teleBlockedString)) {
            config.setTeleBlocked(true);
        }
    }

    private void CreateGUI(){
        JFrame frame = new JFrame();
        frame.setTitle("Jwagg's F2P Bryophyta AIO");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(Client.getInstance().getApplet());
        frame.setPreferredSize(new Dimension(400,200));
        frame.getContentPane().setLayout(new BorderLayout());


        // upper gui
        JPanel settingsPanel = new JPanel();
        settingsPanel.setLayout(new GridLayout(0,2));

        JLabel trainingText = new JLabel();
        trainingText.setText("Training Style:");
        settingsPanel.add(trainingText);

        JLabel bryophytaText = new JLabel();
        bryophytaText.setText("Bryophyta Style:");
        settingsPanel.add(bryophytaText);

        JCheckBox trainingMeleeCheckbox = new JCheckBox();
        trainingMeleeCheckbox.setText("Melee");
        trainingMeleeCheckbox.setSelected(true);
        settingsPanel.add(trainingMeleeCheckbox);

        JCheckBox bryophytaMeleeCheckbox = new JCheckBox();
        bryophytaMeleeCheckbox.setText("Melee");
        settingsPanel.add(bryophytaMeleeCheckbox);

        JCheckBox trainingRangedCheckbox = new JCheckBox();
        trainingRangedCheckbox.setText("Ranged");
        trainingRangedCheckbox.setSelected(true);
        settingsPanel.add(trainingRangedCheckbox);

        JCheckBox bryophytaRangedCheckbox = new JCheckBox();
        bryophytaRangedCheckbox.setText("Ranged");
        bryophytaRangedCheckbox.setSelected(true);
        settingsPanel.add(bryophytaRangedCheckbox);

        frame.getContentPane().add(settingsPanel, BorderLayout.CENTER);


        // lower gui
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());

        JButton startButton = new JButton();
        startButton.setText("Start");
        startButton.addActionListener(new ActionListener() {
              @Override
              public void actionPerformed(ActionEvent e) {
                  config.setTrainingMelee(trainingMeleeCheckbox.isSelected());
                  config.setTrainingRanged(trainingRangedCheckbox.isSelected());
                  config.setBryophytaMelee(bryophytaMeleeCheckbox.isSelected());
                  config.setBryophytaRanged(bryophytaRangedCheckbox.isSelected());
                  isRunning = true;

                  frame.dispose();
              }
        });
        buttonPanel.add(startButton);

        frame.getContentPane().add(buttonPanel,BorderLayout.SOUTH);


        frame.pack();
        frame.setVisible(true);
    }

}
