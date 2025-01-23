package eu.fischboy.fischconfig.cosmetics;

import cc.polyfrost.oneconfig.libs.universal.ChatColor;
import cc.polyfrost.oneconfig.utils.Notifications;
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
            renderWings();
        }
    }

    private void renderCape() {
        Minecraft.getMinecraft().gameSettings.setModelPartEnabled(EnumPlayerModelParts.CAPE, true);
        for (RenderPlayer render : Minecraft.getMinecraft().getRenderManager().getSkinMap().values()) {
            render.addLayer(new RenderCapes(render));
        }

    }

    private void renderWings() {
        MinecraftForge.EVENT_BUS.register(new RenderWings());
    }
}

