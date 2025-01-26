package eu.fischboy.fischconfig.events;

import eu.fischboy.fischconfig.FischMod;
import eu.fischboy.fischconfig.config.ModConfig;
import eu.fischboy.fischconfig.newCosmetics.impl.cape.RenderCapes;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class CosmeticsInitializer {

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        int capeDesign = ModConfig.capeDesign;
        FischMod.INSTANCE.cosmeticManager.updateCapeType(capeDesign);

        if (event.phase == TickEvent.Phase.START) {
            for (RenderPlayer renderPlayer : Minecraft.getMinecraft().getRenderManager().getSkinMap().values()) {
                renderPlayer.addLayer(new RenderCapes(renderPlayer));
            }
        }
    }
}

