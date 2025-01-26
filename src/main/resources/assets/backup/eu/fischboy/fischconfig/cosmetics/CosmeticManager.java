package eu.fischboy.fischconfig.cosmetics;

import eu.fischboy.fischconfig.newCosmetics.Cosmetic;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;
import java.util.List;

public class CosmeticManager {

    private final List<Cosmetic> cosmetics = new ArrayList<>();

    public void registerCosmetic(Cosmetic cosmetic) {
        cosmetics.add(cosmetic);
    }

    @SubscribeEvent
    public void onRenderPalyer(RenderPlayerEvent.Post event) {
        EntityPlayer player = event.entityPlayer;
        for (Cosmetic cosmetic : cosmetics) {
            cosmetic.render(player, event.partialRenderTick);
        }
    }
}
