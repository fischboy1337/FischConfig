package eu.fischboy.fischconfig;

import eu.fischboy.fischconfig.config.ModConfig;
import eu.fischboy.fischconfig.events.CosmeticsInitializer;
import eu.fischboy.fischconfig.events.StemNotification;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

@Mod(modid = FischMod.MODID, name = FischMod.NAME, version = FischMod.VERSION)
public class FischMod {

    public static final String MODID = "@ID@";
    public static final String NAME = "@NAME@";
    public static final String VERSION = "@VER@";

    @Mod.Instance(MODID)
    public static FischMod INSTANCE;

    public static ModConfig config;
    @Mod.EventHandler
    public void onInit(FMLInitializationEvent event) {
        config = new ModConfig();

        MinecraftForge.EVENT_BUS.register(new StemNotification());
        MinecraftForge.EVENT_BUS.register(new CosmeticsInitializer());
    }
}
