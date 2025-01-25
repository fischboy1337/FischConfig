package eu.fischboy.fischconfig.cosmetics;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;

public interface Cosmetic {

    Minecraft mc = Minecraft.getMinecraft();

    void render(EntityPlayer player, float partialTicks);
}
