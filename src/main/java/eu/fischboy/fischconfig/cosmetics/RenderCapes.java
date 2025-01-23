package eu.fischboy.fischconfig.cosmetics;

import cc.polyfrost.oneconfig.config.core.OneColor;
import eu.fischboy.fischconfig.config.ModConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.player.EnumPlayerModelParts;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

public class RenderCapes implements LayerRenderer<AbstractClientPlayer> {
    private static ResourceLocation CAPE_TEXTURE;
    private final RenderPlayer playerRenderer;

    public RenderCapes(RenderPlayer playerRendererIn) {
        this.playerRenderer = playerRendererIn;
    }

    public void handleTexture() {
        switch (ModConfig.capeDesign) {
            case 0:
                CAPE_TEXTURE = new ResourceLocation("cosmetics", "Capes/Classic.png");
                break;
            case 1:
                CAPE_TEXTURE = new ResourceLocation("cosmetics", "Capes/Modern.png");
                break;
            case 2:
                CAPE_TEXTURE = new ResourceLocation("cosmetics", "Capes/CustomColor.png");
                break;
        }
    }

    @Override
    public void doRenderLayer(AbstractClientPlayer player, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        if (player.hasPlayerInfo() && !player.isInvisible() && player.isWearing(EnumPlayerModelParts.CAPE)) {
            if (player.getName().equals(Minecraft.getMinecraft().getSession().getUsername()) && ModConfig.enableCape) {
                handleTexture();
                this.playerRenderer.bindTexture(CAPE_TEXTURE);

                GlStateManager.pushMatrix();
                GlStateManager.translate(0.0F, 0.0F, 0.125F);

                if (ModConfig.capeDesign == 2) {
                    OneColor color = ModConfig.capeColor;
                    GlStateManager.color(color.getRed(), color.getGreen(), color.getBlue(), 1.0F);
                }else {
                    GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
                }

                float waveTime = ageInTicks + partialTicks;
                float waveAmplitude = 4.0F;
                float waveFrequency = 0.15F;
                float waveOffset = MathHelper.sin(waveTime * waveFrequency) * waveAmplitude;

                double d0 = player.prevChasingPosX + (player.chasingPosX - player.prevChasingPosX) * (double) partialTicks
                        - (player.prevPosX + (player.posX - player.prevPosX) * (double) partialTicks);
                double d1 = player.prevChasingPosY + (player.chasingPosY - player.prevChasingPosY) * (double) partialTicks
                        - (player.prevPosY + (player.posY - player.prevPosY) * (double) partialTicks);
                double d2 = player.prevChasingPosZ + (player.chasingPosZ - player.prevChasingPosZ) * (double) partialTicks
                        - (player.prevPosZ + (player.posZ - player.prevPosZ) * (double) partialTicks);

                float f = player.prevRenderYawOffset + (player.renderYawOffset - player.prevRenderYawOffset) * partialTicks;
                double d3 = MathHelper.sin(f * (float) Math.PI / 180.0F);
                double d4 = -MathHelper.cos(f * (float) Math.PI / 180.0F);
                float f1 = (float) d1 * 10.0F;
                f1 = MathHelper.clamp_float(f1, -6.0F, 32.0F);
                float f2 = (float) (d0 * d3 + d2 * d4) * 100.0F;
                float f3 = (float) (d0 * d4 - d2 * d3) * 100.0F;

                if (f2 < 0.0F) f2 = 0.0F;
                if (f2 > 165.0F) f2 = 165.0F;
                if (f1 < -5.0F) f1 = -5.0F;

                float f4 = player.prevCameraYaw + (player.cameraYaw - player.prevCameraYaw) * partialTicks;
                f1 = f1 + MathHelper.sin((player.prevDistanceWalkedModified + (player.distanceWalkedModified - player.prevDistanceWalkedModified) * partialTicks) * 6.0F) * 32.0F * f4;

                if (player.isSneaking()) {
                    f1 += 25.0F;
                    GlStateManager.translate(0.0F, 0.142F, -0.0178F);
                }

                GlStateManager.rotate(6.0F + f2 / 2.0F + f1 + waveOffset, 1.0F, 0.0F, 0.0F);
                GlStateManager.rotate(f3 / 2.0F, 0.0F, 0.0F, 1.0F);
                GlStateManager.rotate(-f3 / 2.0F + waveOffset / 2.0F, 0.0F, 1.0F, 0.0F);
                GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);

                this.playerRenderer.getMainModel().renderCape(0.0625F);
                GlStateManager.popMatrix();
            }
        }
    }

    @Override
    public boolean shouldCombineTextures() {
        return false;
    }
}
