package eu.fischboy.fischconfig.newCosmetics.impl.wings;

import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class Wings {

    private final RenderWings renderWings;

    public Wings() {
        this.renderWings = new RenderWings();
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onRenderPlayer(RenderPlayerEvent.Post event) {
        renderWings.onRenderPlayer(event);
     }
}
