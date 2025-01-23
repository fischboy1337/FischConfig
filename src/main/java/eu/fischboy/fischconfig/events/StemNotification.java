package eu.fischboy.fischconfig.events;

import cc.polyfrost.oneconfig.config.core.OneColor;
import eu.fischboy.fischconfig.config.ModConfig;
import net.minecraft.block.Block;
import net.minecraft.block.BlockStem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.opengl.GL11;

import java.util.HashSet;
import java.util.Set;

public class StemNotification {
    private final Minecraft mc = Minecraft.getMinecraft();
    private Set<BlockPos> destroyedStems = new HashSet<>();

    @SubscribeEvent
    public void onBlockBreak(BlockEvent.BreakEvent event) {
        if (event.getPlayer() != null && ModConfig.enableStem) {
            BlockPos pos = event.pos;
            if (pos != null && !destroyedStems.contains(pos)) {
                Block block = event.state.getBlock();
                if (block instanceof BlockStem) {
                    destroyedStems.add(pos);
                    sendActionBar("You destroyed a " + EnumChatFormatting.RED + "Stem" + EnumChatFormatting.RESET);
                    playStemDestroyedSound(pos);
                }
            }
        }
    }

    @SubscribeEvent
    public void onBlockPlaced(BlockEvent.PlaceEvent event) {
        BlockPos pos = event.pos;
        if (destroyedStems.contains(pos)) {
            destroyedStems.remove(pos);
        }
    }

    @SubscribeEvent
    public void onRenderWorldLast(RenderWorldLastEvent event) {
        if (ModConfig.enableStem) {
            for (BlockPos pos : destroyedStems) {
                renderBoxAroundBlock(pos);
            }
        }
    }

    private void sendActionBar(String message) {
        if (ModConfig.enableStemActionBar) {
            mc.ingameGUI.displayTitle(message, "", 1, (int) ModConfig.stemActionBarDelay, 1);
        }
        if (ModConfig.enableStemChat) {
            mc.thePlayer.addChatMessage(new ChatComponentText(message));
        }

    }

    private void playStemDestroyedSound(BlockPos pos) {
        if (ModConfig.enableStemSound) {
            mc.theWorld.playSound(pos.getX(), pos.getY(), pos.getZ(), "minecraft:block.note.pling", 1.0F, 0.4F, false);
        }
    }

    private void renderBoxAroundBlock(BlockPos pos) {
        double x = pos.getX() - mc.getRenderManager().viewerPosX;
        double y = pos.getY() - mc.getRenderManager().viewerPosY;
        double z = pos.getZ() - mc.getRenderManager().viewerPosZ;

        OneColor color = ModConfig.stemBoxColor;

        GlStateManager.pushMatrix();
        GlStateManager.disableTexture2D();
        GlStateManager.disableDepth();
        GlStateManager.disableLighting();
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, GL11.GL_ONE, GL11.GL_ZERO);
        GlStateManager.color(color.getRed() / 255.0F, color.getGreen() / 255.0F, color.getBlue() / 255.0F, 0.5F);

        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldRenderer = tessellator.getWorldRenderer();
        worldRenderer.begin(GL11.GL_LINE_LOOP, DefaultVertexFormats.POSITION);
        worldRenderer.pos(x, y, z).endVertex();
        worldRenderer.pos(x + 1, y, z).endVertex();
        worldRenderer.pos(x + 1, y + 1, z).endVertex();
        worldRenderer.pos(x, y + 1, z).endVertex();
        worldRenderer.pos(x, y, z).endVertex();
        tessellator.draw();

        worldRenderer.begin(GL11.GL_LINE_LOOP, DefaultVertexFormats.POSITION);
        worldRenderer.pos(x, y, z + 1).endVertex();
        worldRenderer.pos(x + 1, y, z + 1).endVertex();
        worldRenderer.pos(x + 1, y + 1, z + 1).endVertex();
        worldRenderer.pos(x, y + 1, z + 1).endVertex();
        worldRenderer.pos(x, y, z + 1).endVertex();
        tessellator.draw();

        worldRenderer.begin(GL11.GL_LINES, DefaultVertexFormats.POSITION);
        worldRenderer.pos(x, y, z).endVertex();
        worldRenderer.pos(x, y, z + 1).endVertex();
        worldRenderer.pos(x + 1, y, z).endVertex();
        worldRenderer.pos(x + 1, y, z + 1).endVertex();
        worldRenderer.pos(x + 1, y + 1, z).endVertex();
        worldRenderer.pos(x + 1, y + 1, z + 1).endVertex();
        worldRenderer.pos(x, y + 1, z).endVertex();
        worldRenderer.pos(x, y + 1, z + 1).endVertex();
        tessellator.draw();

        GlStateManager.enableLighting();
        GlStateManager.enableDepth();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
    }
}
