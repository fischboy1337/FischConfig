package eu.fischboy.fischconfig.cosmetics.impl;

import eu.fischboy.fischconfig.cosmetics.Cosmetic;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

public class HatCosmetic implements Cosmetic {
    private final ModelRenderer hatModel;
    private final ResourceLocation HAT_TEXTURE = new ResourceLocation("cosmetics", "hat/hat.png");

    public HatCosmetic() {
        ModelBase modelBase = new ModelBase() {};
        hatModel = new ModelRenderer(modelBase, 0, 0);
        hatModel.addBox(-4.0f, -8.0f, -4.0f, 8, 8, 8);
    }

    @Override
    public void render(EntityPlayer player, float partialTicks) {
        mc.getTextureManager().bindTexture(HAT_TEXTURE);

        GlStateManager.pushMatrix();
        GlStateManager.translate(0, -player.height + 0.25, 0);
        hatModel.render(0.0625f);
        GlStateManager.popMatrix();
    }
}
