package config;

import org.dreambot.api.methods.combat.CombatStyle;
import org.dreambot.api.methods.map.Area;
import org.dreambot.api.methods.map.Tile;
import org.dreambot.api.methods.skills.Skill;
import org.dreambot.api.methods.skills.Skills;
import org.dreambot.api.utilities.Timer;

import java.util.ArrayList;

public class Config {
    // instantiate the class
    private static final Config config = new Config();

    // constructor
    private Config() {}

    // time
    public Timer timer = new Timer();

    // enum
    public enum State {
        RATS, FROGS, MOSSGIANTS, BRYOPHYTA
    }
    public enum FightingStyle {
        MELEE,RANGED,MAGIC
    }

    // states (or config)
    private boolean trainingMelee = false;
    private boolean trainingRanged = false;
    private boolean bryophytaMelee = false;
    private boolean bryophytaRanged = false;
    public boolean isMeleeSelected(){
        return isTrainingMelee() || isBryophytaMelee();
    }
    public boolean isRangedSelected(){
        return isTrainingRanged() || isBryophytaRanged();
    }

    private FightingStyle curFightingStyle = null;

    private String status = "Initializing";
    private State state = State.RATS;
    private int curAttackLevel = Skills.getRealLevel(Skill.ATTACK);
    private int curStrengthLevel = Skills.getRealLevel(Skill.STRENGTH);
    private int curDefenceLevel = Skills.getRealLevel(Skill.DEFENCE);
    private int curPrayerLevel;

    private boolean initBankOpened = false;

    private boolean isDistracted = false;
    public boolean justLoggedOut = false;
    private boolean teleBlocked = false;
    private boolean alreadyUnderAttack = false;

    private String curMonster = "Giant rat";

    private int nextEatAtPercentage = 50;
    private int nextRunEnergyPercentage = 20;
    private int nextDrinkPotionBoostedRealDiff = 0;

    private boolean majorLevelJustReached = false;

    // TODO - use this as a check when geared for certain levels, that way loop only has to check one variable instead of long condition
    private boolean gearedUp = false;
    private CombatStyle lastMajorLevelReached = CombatStyle.ATTACK;

    public ArrayList<String> gearList = new ArrayList<>();
    public String[] sharedGearArray = {this.amuletOfPower,this.blackCape,this.leatherBoots,this.lobster,this.swordfish};
    public String[] meleeGearList = {this.leatherGloves,this.ironPlatebody,this.ironPlatelegs,this.ironFullHelm,this.mithPlatebody,this.strPot4,
            this.mithPlatelegs,this.mithFullHelm,this.runeChainbody,this.runePlatelegs,this.runeFullHelm,this.ironKiteshield,this.mithKiteshield,
            this.runeKiteshield,this.ironScimitar,this.blackScimitar,this.mithScimitar,this.addyScimitar,this.runeScimitar,this.bronzeAxe};
    public String[] rangedGearList = {this.leatherCowl,this.coif,this.studdedBody,this.hardleatherBody,this.leatherBody,
            this.greendHideChaps,this.studdedChaps,this.leatherChaps,this.mapleShortbow,this.willowShortbow,this.oakShortbow,this.shortbow,
            this.addyArrow,this.mithArrow,this.ironArrow,this.greendHideVambraces,this.leatherVambraces};
    //TODO- prolly shouldn't be here
    public void ConfigureGearList(){
        for(String i : sharedGearArray){
            gearList.add(i);
        }
        if(this.isMeleeSelected()){
            for(String i : meleeGearList){
                gearList.add(i);
            }
        }
        if(this.isRangedSelected()){
            for(String i : rangedGearList){
                gearList.add(i);
            }
        }
    }


    // TODO - sloppy
    private final String[] lootItemsNoBones = {this.lawRune,this.cosmicRune,this.natureRune,this.deathRune,this.mossyKey,this.uncutRuby,
                                    this.blackSqShield,this.mithSword,this.steelKiteshield,this.steelArrow,this.chaosRune};
    private final int[] lootItemsPricesNoBones = {this.lawRunePrice,this.cosmicRunePrice,this.natureRunePrice,this.deathRunePrice,this.mossyKeyPrice,this.uncutRubyPrice,
            this.blackSqShieldPrice,this.mithSwordPrice,this.steelKiteshieldPrice,this.steelArrowPrice,this.chaosRunePrice};

    private final String[] lootItemsWithBones = {this.lawRune,this.cosmicRune,this.natureRune,this.deathRune,this.mossyKey,this.uncutRuby,
            this.blackSqShield,this.mithSword,this.steelKiteshield,this.steelArrow,this.chaosRune,this.bigBones,this.coins};

    private final int[] lootItemsPricesWithBones = {this.lawRunePrice,this.cosmicRunePrice,this.natureRunePrice,this.deathRunePrice,this.mossyKeyPrice,this.uncutRubyPrice,
            this.blackSqShieldPrice,this.mithSwordPrice,this.steelKiteshieldPrice,this.steelArrowPrice,this.chaosRunePrice,0,0};

    private String[] curLootItems;
    private int[] curLootItemPrices;


    // chat messages
    public final String teleBlockedString = "Tele Block";
    public final String alreadyUnderAttackString = "already under attack";


    // TODO - total prices, not price per
    // prices
    public final int blackSqShieldPrice = 454;
    public final int mossyKeyPrice = 75000;
    public final int mithSwordPrice = 309;
    public final int steelKiteshieldPrice = 258;
    public final int cosmicRunePrice = 336; //
    public final int natureRunePrice = 1176; //
    public final int deathRunePrice = 576; //
    public final int lawRunePrice = 396; //
    public final int steelArrowPrice = 330; //
    public final int uncutRubyPrice = 1220; //
    public final int strPot4Price = 328;
    public final int lobsterPrice = 165;
    public final int chaosRunePrice = 490;


    // locations
    public final Area deathsRoom = new Area(13002,5059,13012,5066);
    public final Tile bryophytaLairWeb = new Tile(3210, 9898);
    public final Area varrockSewerSouthLadderArea = new Area(3236,9857,3237,9860);
    public final Area bryophytaLairEntrance = new Area(3173,9896,3175,9902);
    public final Area bryophytaLair = new Area(9934,455,9948,469); // "Clamber" "Rock Pile" all caps
    public final Area varrockSewersManholeArea = new Area(3236,3455,3240,3460);
    public final Area varrockEastBank = new Area(3250,3418,3257,3426);
    public final Area feroxEnclaveInteriorNorth = new Area(3133,3633,3136,3639);
    public final Area feroxEnclaveExteriorNorth = new Area(3133,3640,3136,3645);
    public final Area feroxEnclaveBank = new Area(3130,3625,3139,3632);
    public final Area feroxEnclave = new Area(3127,3619,3144,3638);
    public final Area grandExchangeArea = new Area(3153, 3474, 3177, 3504);
    public final Area lumbridgeTeleArea = new Area(3218,3214,3226,3222);
    public final Area largeGiantRatArea = new Area(3140,3148,3234,3198);
    public final Area smallGiantRatArea = new Area(3193,3148,3214,3196);
    public final Area lumbridgeAndSwampArea = new Area(3162,3158,3300,3340);
    //public final Area lumbridgeBank = new Area(3200,3210,3220,3230,2);
    public final Area lumbridgeBank = new Area(3190,3200,3230,3240,2);
    public final Area lumbridgeGraveyard = new Area(3238,3192,3248,3203);
    public final Area lumbridgeSecondFloor = new Area(3200,3200,3300,3300,1);
    public final Area giantFrogArea = new Area(3189,3167,3212,3197);
    public final Area mossGiantWildernessArea = new Area(3130,3800,3153,3833);
    // TODO - could get the whole length (longer x plane)
    public final Area edgevilleWildernessDitchSouthArea = new Area(3086,3516,3140,3520);
    public final Area edgevilleWildernessDitchNorthArea = new Area(3086,3521,3140,3525);
    public final Area edgevilleBank = new Area(3090,3488,3099,3499);

    // objects
    public final String poolOfRefreshment = "Pool of Refreshment";
    public final String manhole = "Manhole";
    public final String ladder = "Ladder";


    // armor
    public final String amuletOfPower = "Amulet of power";
    public final String blackCape = "Black cape";
    public final String leatherBoots = "Leather boots";
    public final String leatherGloves = "Leather gloves";
    public final String ironPlatebody = "Iron platebody";
    public final String ironPlatelegs = "Iron platelegs";
    public final String ironFullHelm = "Iron full helm";
    public final String mithPlatebody = "Mithril platebody";
    public final String mithPlatelegs = "Mithril platelegs";
    public final String mithFullHelm = "Mithril full helm";
    public final String runeChainbody = "Rune chainbody";
    public final String runePlatelegs = "Rune platelegs";
    public final String runeFullHelm = "Rune full helm";
    public final String ironKiteshield = "Iron kiteshield";
    public final String mithKiteshield = "Mithril kiteshield";
    public final String runeKiteshield = "Rune kiteshield";
    public final String blackSqShield = "Black sq shield";
    public final String mithSword = "Mithril sword";
    public final String steelKiteshield = "Steel kiteshield";
    public final String coif = "Coif";
    public final String leatherCowl = "Leather cowl";
    public final String studdedBody = "Studded body";
    public final String hardleatherBody = "Hardleather body";
    public final String leatherBody = "Leather body";
    public final String greendHideChaps = "Green d'hide chaps";
    public final String studdedChaps = "Studded chaps";
    public final String leatherChaps = "Leather chaps";
    public final String greendHideVambraces = "Green d'hide vambraces";
    public final String leatherVambraces = "Leather vambraces";




    // weapons
    public final String ironScimitar = "Iron scimitar";
    public final String blackScimitar = "Black scimitar";
    public final String mithScimitar = "Mithril scimitar";
    public final String addyScimitar = "Adamant scimitar";
    public final String runeScimitar = "Rune scimitar";

    // bows
    public final String shortbow = "Shortbow";
    public final String oakShortbow = "Oak shortbow";
    public final String willowShortbow = "Willow shortbow";
    public final String mapleShortbow = "Maple shortbow";

    // arrows
    public final String addyArrow = "Adamant arrow";
    public final String mithArrow = "Mithril arrow";
    public final String steelArrow = "Steel arrow";
    public final String ironArrow = "Iron arrow";


    // food
    public final String lobster =  "Lobster";
    public final String swordfish = "Swordfish";

    // potions
    public final String strPot4 = "Strength potion(4)";
    public final String strPot3 = "Strength potion(3)";
    public final String strPot2 = "Strength potion(2)";
    public final String strPot1 = "Strength potion(1)";
    public final String vial = "Vial";


    // runes
    public final String lawRune = "Law rune"; //
    public final String airRune = "Air rune";
    public final String earthRune = "Earth rune";
    public final String chaosRune = "Chaos rune"; // temp not in the list
    public final String cosmicRune = "Cosmic rune"; //
    public final String natureRune = "Nature rune"; //
    public final String deathRune = "Death rune"; //

    // items
    public final String coins = "Coins";
    public final String mossyKey = "Mossy key"; //
    public final String uncutRuby = "Uncut ruby"; //
    public final String bigBones = "Big bones";
    public final String bones = "Bones";
    public final String bronzeAxe = "Bronze axe";


    // monsters
    public final String giantFrog = "Giant frog";
    public final String giantRat = "Giant rat";
    public final String mossGiant = "Moss giant";
    public final String bryophyta = "Bryophyta";
    public final String growthling = "Growthling";



    // setter and getters
    public static Config getConfig() {
        return config;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public void setCurPrayerLevel(int curPrayerLevel) {
        this.curPrayerLevel = curPrayerLevel;
    }

    public int getCurPrayerLevel() {
        return curPrayerLevel;
    }

    public boolean isDistracted() {
        return isDistracted;
    }

    public void setDistracted(boolean distracted) {
        isDistracted = distracted;
    }

    public boolean isInitBankOpened() {
        return initBankOpened;
    }

    public void setInitBankOpened(boolean initBankOpened) {
        this.initBankOpened = initBankOpened;
    }

    public String getCurMonster() {
        return curMonster;
    }

    public void setCurMonster(String curMonster) {
        this.curMonster = curMonster;
    }

    public int getNextEatAtPercentage() {
        return nextEatAtPercentage;
    }

    public void setNextEatAtPercentage(int nextEatAtPercentage) {
        this.nextEatAtPercentage = nextEatAtPercentage;
    }

    public boolean isMajorLevelJustReached() {
        return majorLevelJustReached;
    }

    public void setMajorLevelJustReached(boolean majorLevelJustReached) {
        this.majorLevelJustReached = majorLevelJustReached;
    }
    public int getCurAttackLevel() {
        return curAttackLevel;
    }

    public void setCurAttackLevel(int curAttackLevel) {
        this.curAttackLevel = curAttackLevel;
    }

    public int getCurStrengthLevel() {
        return curStrengthLevel;
    }

    public void setCurStrengthLevel(int curStrengthLevel) {
        this.curStrengthLevel = curStrengthLevel;
    }

    public int getCurDefenceLevel() {
        return curDefenceLevel;
    }

    public void setCurDefenceLevel(int curDefenceLevel) {
        this.curDefenceLevel = curDefenceLevel;
    }

    public String[] getLootItemsNoBones() {
        return lootItemsNoBones;
    }

    public int getNextRunEnergyPercentage() {
        return nextRunEnergyPercentage;
    }

    public void setNextRunEnergyPercentage(int nextRunEnergyPercentage) {
        this.nextRunEnergyPercentage = nextRunEnergyPercentage;
    }

    public int getNextDrinkPotionBoostedRealDiff() {
        return nextDrinkPotionBoostedRealDiff;
    }

    public void setNextDrinkPotionBoostedRealDiff(int nextDrinkPotionBoostedRealDiff) {
        this.nextDrinkPotionBoostedRealDiff = nextDrinkPotionBoostedRealDiff;
    }

    public boolean isTeleBlocked() {
        return teleBlocked;
    }

    public void setTeleBlocked(boolean teleBlocked) {
        this.teleBlocked = teleBlocked;
    }

    public String[] getLootItemsWithBones() {
        return lootItemsWithBones;
    }


    public int[] getLootItemsPricesWithBones() {
        return lootItemsPricesWithBones;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String[] getCurLootItems() {
        return curLootItems;
    }

    public void setCurLootItems(String[] curLootItems) {
        this.curLootItems = curLootItems;
    }

    public int[] getCurLootItemPrices() {
        return curLootItemPrices;
    }

    public void setCurLootItemPrices(int[] curLootItemPrices) {
        this.curLootItemPrices = curLootItemPrices;
    }

    public int[] getLootItemsPricesNoBones() {
        return lootItemsPricesNoBones;
    }

    public boolean isAlreadyUnderAttack() {
        return alreadyUnderAttack;
    }

    public void setAlreadyUnderAttack(boolean alreadyUnderAttack) {
        this.alreadyUnderAttack = alreadyUnderAttack;
    }

    public boolean isTrainingMelee() {
        return trainingMelee;
    }

    public void setTrainingMelee(boolean trainingMelee) {
        this.trainingMelee = trainingMelee;
    }

    public boolean isTrainingRanged() {
        return trainingRanged;
    }

    public void setTrainingRanged(boolean trainingRanged) {
        this.trainingRanged = trainingRanged;
    }

    public boolean isBryophytaMelee() {
        return bryophytaMelee;
    }

    public void setBryophytaMelee(boolean bryophytaMelee) {
        this.bryophytaMelee = bryophytaMelee;
    }

    public boolean isBryophytaRanged() {
        return bryophytaRanged;
    }

    public void setBryophytaRanged(boolean bryophytaRanged) {
        this.bryophytaRanged = bryophytaRanged;
    }

    public FightingStyle getCurFightingStyle() {
        return curFightingStyle;
    }

    public void setCurFightingStyle(FightingStyle curFightingStyle) {
        this.curFightingStyle = curFightingStyle;
    }

}
