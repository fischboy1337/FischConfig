package eu.fischboy.fischconfig.cosmetics;

import cc.polyfrost.oneconfig.config.core.OneColor;
import eu.fischboy.fischconfig.config.ModConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.opengl.GL11;

public class RenderWings extends ModelBase {

    private final Minecraft mc;
    private final ModelRenderer wing;
    private final ModelRenderer wingTip;
    private final boolean playerUsesFullHeight;

    private final ResourceLocation WINGS_TEXTURE;

    public RenderWings() {
        this.mc = Minecraft.getMinecraft();
        this.playerUsesFullHeight = Loader.isModLoaded("animations");
        WINGS_TEXTURE = new ResourceLocation("cosmetics", "Wings/wings.png");

        setTextureOffset("wing.bone", 0, 0);
        setTextureOffset("wing.skin", -10, 8);
        setTextureOffset("wingtip.bone", 0, 5);
        setTextureOffset("wingtip.skin", -10, 18);

        wing = new ModelRenderer(this, "wing");
        wing.setTextureSize(30, 30);
        wing.setRotationPoint(-2F, 0, 0);
        wing.addBox("bone", -10.0F, -1.0F, -1.0F, 10, 2, 2);
        wing.addBox("skin", -10.0F, 0.0F, 0.5F, 10, 0, 10);

        wingTip = new ModelRenderer(this, "wingtip");
        wingTip.setTextureSize(30, 30);
        wingTip.setRotationPoint(-10.0F, 0.0F, 0.0F);
        wingTip.addBox("bone", -10.0F, -0.5F, -0.5F, 10, 1, 1);
        wingTip.addBox("skin", -10.0F, 0.0F, 0.5F, 10, 0, 10);
        wing.addChild(wingTip);
    }

    @SubscribeEvent
    public void onRenderPlayer(RenderPlayerEvent.Post event)
    {
        EntityPlayer player = event.entityPlayer;

        if (!player.isInvisible() && ModConfig.enableWings) {
            renderWings(player, event.partialRenderTick);
        }
    }

    private void renderWings(EntityPlayer player, float partialTicks) {
        double scale = (double) (ModConfig.wingsScale) / 100D;
        double rotate = interpolate(player.prevRenderYawOffset, player.renderYawOffset, partialTicks);

        GlStateManager.pushMatrix();
        GlStateManager.scale(-scale, -scale, scale);
        GlStateManager.rotate((float) (180 + rotate), 0, 1, 0);
        GlStateManager.translate(0, -(playerUsesFullHeight ? 1.45 : 1.25) / scale, 0);
        GlStateManager.translate(0, 0, 0.2 / scale);

        if (player.isSneaking()) {
            GlStateManager.translate(0D, 0.125D / scale, 0D);
        }

        OneColor color = ModConfig.wingsColor;
        GlStateManager.color(color.getRed() / 255.0F, color.getGreen() / 255.0F, color.getBlue() / 255.0F);
        mc.getTextureManager().bindTexture(WINGS_TEXTURE);

        for (int j = 0; j < 2; ++j) {
            GlStateManager.enableCull();
            float f11 = (System.currentTimeMillis() % 1000) / 1000F * (float) Math.PI * 2.0F;
            this.wing.rotateAngleX = (float) Math.toRadians(-80F) - (float) Math.cos(f11) * 0.2F;
            this.wing.rotateAngleY = (float) Math.toRadians(20F) + (float) Math.sin(f11) * 0.4F;
            this.wing.rotateAngleZ = (float) Math.toRadians(20F);
            this.wingTip.rotateAngleZ = -((float) (Math.sin((f11 + 2.0F)) + 0.5D)) * 0.75F;
            this.wing.render(0.0625F);
            GlStateManager.scale(-1.0F, 1.0F, 1.0F);

            if (j == 0) {
                GlStateManager.cullFace(GL11.GL_FRONT);
            }
        }

        GlStateManager.cullFace(GL11.GL_BACK);
        GlStateManager.disableCull();
        GlStateManager.color(1.0F, 1.0F, 1.0F);
        GlStateManager.popMatrix();
    }

    private float interpolate(float yaw1, float yaw2, float percent)
    {
        float f = (yaw1 + (yaw2 - yaw1) * percent) % 360;

        if (f < 0)
        {
            f += 360;
        }

        return f;
    }
}
