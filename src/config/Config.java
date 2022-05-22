package config;

import org.dreambot.api.methods.combat.CombatStyle;
import org.dreambot.api.methods.map.Area;
import org.dreambot.api.methods.skills.Skill;
import org.dreambot.api.methods.skills.Skills;
import org.dreambot.api.utilities.Timer;

public class Config {
    // instantiate the class
    private static final Config config = new Config();

    // constructor
    private Config() {}

    // time
    public Timer timer = new Timer();


    // states (or config)
    private int startAttLvl = Skills.getRealLevel(Skill.ATTACK);
    private int startStrLvl = Skills.getRealLevel(Skill.STRENGTH);
    private int startDefLvl = Skills.getRealLevel(Skill.DEFENCE);
    private int curAttLvl = Skills.getRealLevel(Skill.ATTACK);
    private int curStrLvl = Skills.getRealLevel(Skill.STRENGTH);
    private int curDefLvl = Skills.getRealLevel(Skill.DEFENCE);

    private boolean initBankOpened = false;

    private boolean isDistracted = false;
    public boolean justLoggedOut = false;

    private String curMonster = "Giant rat";

    private int nextEatAtPercentage = 30;
    private int nextRunEnergyPercentage = 20;
    private int nextDrinkPotionBoostedRealDiff = 0;

    private boolean majorLevelJustReached = false;
    private CombatStyle lastMajorLevelReached = CombatStyle.ATTACK;

    private String[] gearList = {this.amuletOfPower,this.blackCape,this.leatherBoots,this.leatherGloves,this.ironPlatebody,this.ironPlatelegs,this.ironFullHelm,this.mithPlatebody,
            this.mithPlatelegs,this.mithFullHelm,this.runeChainbody,this.runePlatelegs,this.runeFullHelm,this.ironKiteshield,this.mithKiteshield,
            this.runeKiteshield,this.blackSqShield,this.mithSword,this.steelKiteshield,this.ironScimitar,this.blackScimitar,this.mithScimitar,this.addyScimitar,this.runeScimitar};


    // TODO - sloppy
    private String[] lootItems = {this.lawRune,this.cosmicRune,this.natureRune,this.deathRune,this.mossyKey,this.uncutRuby,
                                    this.blackSqShield,this.mithSword,this.steelKiteshield,this.steelArrow,this.chaosRune};
    private int[] lootItemsPrices = {this.lawRunePrice,this.cosmicRunePrice,this.natureRunePrice,this.deathRunePrice,this.mossyKeyPrice,this.uncutRubyPrice,
            this.blackSqShieldPrice,this.mithSwordPrice,this.steelKiteshieldPrice,this.steelArrowPrice,this.chaosRunePrice};



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
    public final Area feroxEnclaveInteriorNorth = new Area(3133,3633,3135,3639);
    public final Area feroxEnclaveExteriorNorth = new Area(3133,3640,3135,3645);
    public final Area feroxEnclaveBank = new Area(3132,3627,3137,3630);
    public final Area grandExchangeArea = new Area(3153, 3474, 3177, 3504);
    public final Area lumbridgeTeleArea = new Area(3218,3214,3226,3222);
    public final Area largeGiantRatArea = new Area(3162,3158,3229,3220);
    public final Area smallGiantRatArea = new Area(3193,3159,3214,3196);
    public final Area lumbridgeAndSwampArea = new Area(3162,3158,3300,3340);
    public final Area lumbridgeBank = new Area(3200,3210,3220,3230,2);
    public  final Area lumbridgeSecondFloor = new Area(3200,3200,3300,3300,1);
    public final Area giantFrogArea = new Area(3189,3167,3212,3191);
    public final Area mossGiantWildernessArea = new Area(3134,3802,3151,3833);
    // TODO - could get the whole length (longer x plane)
    public final Area edgevilleWildernessDitchSouthArea = new Area(3086,3516,3140,3520);
    public final Area edgevilleWildernessDitchNorthArea = new Area(3086,3521,3140,3525);
    public final Area edgevilleBank = new Area(3090,3488,3099,3499);

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

    // weapons
    public final String ironScimitar = "Iron scimitar";
    public final String blackScimitar = "Black scimitar";
    public final String mithScimitar = "Mithril scimitar";
    public final String addyScimitar = "Adamant scimitar";
    public final String runeScimitar = "Rune scimitar";

    // arrows
    public final String addyArrow = "Adamant arrow";
    public final String mithArrow = "Mithril arrow";
    public final String steelArrow = "Steel arrow";


    // food
    public final String lobster =  "Lobster";

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





    // monsters
    public final String giantFrog = "Giant frog";
    public final String giantRat = "Giant rat";
    public final String mossGiant = "Moss giant";


    // setter and getters
    public static Config getConfig() {
        return config;
    }

    public int getStartAttLvl() {
        return startAttLvl;
    }

    public void setStartAttLvl(int startAttLvl) {
        this.startAttLvl = startAttLvl;
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
    public int getCurAttLvl() {
        return curAttLvl;
    }

    public void setCurAttLvl(int curAttLvl) {
        this.curAttLvl = curAttLvl;
    }

    public int getCurStrLvl() {
        return curStrLvl;
    }

    public void setCurStrLvl(int curStrLvl) {
        this.curStrLvl = curStrLvl;
    }

    public int getCurDefLvl() {
        return curDefLvl;
    }

    public void setCurDefLvl(int curDefLvl) {
        this.curDefLvl = curDefLvl;
    }

    public String[] getLootItems() {
        return lootItems;
    }

    public void setLootItems(String[] lootItems) {
        this.lootItems = lootItems;
    }

    public int[] getLootItemsPrices() {
        return lootItemsPrices;
    }

    public void setLootItemsPrices(int[] lootItemsPrices) {
        this.lootItemsPrices = lootItemsPrices;
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

    public String[] getGearList() {
        return gearList;
    }

    public void setGearList(String[] gearList) {
        this.gearList = gearList;
    }
}
