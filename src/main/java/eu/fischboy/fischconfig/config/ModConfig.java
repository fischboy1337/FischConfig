package eu.fischboy.fischconfig.config;

import cc.polyfrost.oneconfig.config.Config;
import cc.polyfrost.oneconfig.config.annotations.*;
import cc.polyfrost.oneconfig.config.core.OneColor;
import cc.polyfrost.oneconfig.config.data.InfoType;
import cc.polyfrost.oneconfig.config.data.Mod;
import cc.polyfrost.oneconfig.config.data.ModType;
import eu.fischboy.fischconfig.FischMod;

public class ModConfig extends Config {

    public ModConfig() {
        super(new Mod("FischConfig", ModType.THIRD_PARTY, "assets/icon.png"), FischMod.NAME + ".json");
        initialize();
        addDependency("enableStemActionBar", () -> enableStem);
        addDependency("enableStemChat", () -> enableStem);
        addDependency("enableStemSound", () -> enableStem);
        addDependency("stemBox", () -> enableStem);
        addDependency("stemBoxColor", () -> enableStemBox);
        addDependency("stemActionBarDelay", () -> enableStemActionBar);
    }

    // --- Skyblock ---
    // Stem
    @Switch(name = "Stem Notification", category = "Skyblock", subcategory = "Stem")
    public static boolean enableStem = true;

    @Switch(name = "Stem Sound", category = "Skyblock", subcategory = "Stem")
    public static boolean enableStemSound = true;

    @Switch(name = "Stem Action Bar", category = "Skyblock", subcategory = "Stem")
    public static boolean enableStemActionBar = true;

    @Slider(name = "Stem Action Bar Delay", category = "Skyblock", subcategory = "Stem", min = 1f, max = 10f)
    public static float stemActionBarDelay = 5f;

    @Switch(name = "Stem Chat", category = "Skyblock", subcategory = "Stem")
    public static boolean enableStemChat = true;

    @Switch(name = "Stem Box", category = "Skyblock", subcategory = "Stem")
    public static boolean enableStemBox = true;

    @Color(name = "Stem Box Color", category = "Skyblock", subcategory = "Stem")
    public static OneColor stemBoxColor = new OneColor(255, 0, 0);

    // --- Cosmetics ---
    // Cape
    @Switch(name = "Enable Cape", category = "Cosmetics", subcategory = "Cape")
    public static boolean enableCape = true;

    @Dropdown(name = "Cape Design", options = {"Classic", "Modern", "CustomColor"}, category = "Cosmetics", subcategory = "Cape")
    public static int capeDesign = 0;

    @Color(name = "Cape Color", category = "Cosmetics", subcategory = "Cape")
    public static OneColor capeColor = new OneColor(255, 0, 0);

    // Wings
    @Info(text = "Buggy on Hypixel", type = InfoType.ERROR, category = "Cosmetics", subcategory = "Wings")
    public static boolean ignored1;

    @Switch(name = "Enable Wings", category = "Cosmetics", subcategory = "Wings")
    public static boolean enableWings = false;

    @Color(name = "Wings Color", category = "Cosmetics", subcategory = "Wings")
    public static OneColor wingsColor = new OneColor(120, 120, 120);

    @Slider(name = "Wings Scale", min = 60f, max = 140f, category = "Cosmetics", subcategory = "Wings")
    public static float wingsScale = 100f;
}

