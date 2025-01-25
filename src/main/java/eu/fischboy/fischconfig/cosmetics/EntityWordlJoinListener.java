package eu.fischboy.fischconfig.cosmetics;

import com.mojang.authlib.GameProfile;
import eu.fischboy.fischconfig.config.ModConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EnumPlayerModelParts;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EntityWordlJoinListener {

    @SubscribeEvent
    public void onEntityJoinWorld(EntityJoinWorldEvent event) {
        if (!(event.entity instanceof EntityPlayer)) return;
        GameProfile profile = Minecraft.getMinecraft().getSession().getProfile();
        if (event.entity.getPersistentID().equals(profile.getId())) {
            renderCape();
            renderWings((EntityPlayer) event.entity);
        }
    }

    private void renderCape() {
        Minecraft.getMinecraft().gameSettings.setModelPartEnabled(EnumPlayerModelParts.CAPE, true);
        for (RenderPlayer render : Minecraft.getMinecraft().getRenderManager().getSkinMap().values()) {
            render.addLayer(new RenderCapes(render));
        }

    }

    private void renderWings(EntityPlayer player) {
        if (ModConfig.enableWings && !player.isInvisible() && player == Minecraft.getMinecraft().thePlayer) {
            MinecraftForge.EVENT_BUS.register(new RenderWings());
        }else {
            MinecraftForge.EVENT_BUS.unregister(new RenderWings());
        }
    }
}

