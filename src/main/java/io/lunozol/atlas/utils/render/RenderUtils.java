package io.lunozol.atlas.utils.render;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.MathHelper;

import java.awt.*;

public class RenderUtils {

    public static void rect(int x, int y, int width, int height, Color color) {
        Gui.drawRect(x, y, x + width, y + height, color.getRGB());
    }

    public static boolean hovered(float mouseX, float mouseY, int x, int y, int width, int height) {
        if (mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height) return true;
        return false;
    }

    public static void drgradientRect(int left, int top, int right, int bottom, int startColor, int endColor)
    {
        float f = (float)(startColor >> 24 & 255) / 255.0F;
        float f1 = (float)(startColor >> 16 & 255) / 255.0F;
        float f2 = (float)(startColor >> 8 & 255) / 255.0F;
        float f3 = (float)(startColor & 255) / 255.0F;
        float f4 = (float)(endColor >> 24 & 255) / 255.0F;
        float f5 = (float)(endColor >> 16 & 255) / 255.0F;
        float f6 = (float)(endColor >> 8 & 255) / 255.0F;
        float f7 = (float)(endColor & 255) / 255.0F;
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.shadeModel(7425);
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        worldrenderer.begin(7, DefaultVertexFormats.POSITION_COLOR);
        worldrenderer.pos(right, (double)top, (double)Gui.zLevel).color(f1, f2, f3, f).endVertex();
        worldrenderer.pos(left, (double)top, (double)Gui.zLevel).color(f1, f2, f3, f).endVertex();
        worldrenderer.pos(left, (double)bottom, (double)Gui.zLevel).color(f5, f6, f7, f4).endVertex();
        worldrenderer.pos(right, (double)bottom, (double)Gui.zLevel).color(f5, f6, f7, f4).endVertex();
        tessellator.draw();
        GlStateManager.shadeModel(7424);
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.enableTexture2D();
    }

    public static void gradientRect(int x, int y, int width, int height, Color startColor, Color endColor) {
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.shadeModel(7425);
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        worldrenderer.begin(7, DefaultVertexFormats.POSITION_COLOR);
        worldrenderer.pos(x + width, y, Gui.zLevel).color(startColor.getRed(), startColor.getGreen(), startColor.getBlue(), startColor.getAlpha()).endVertex();
        worldrenderer.pos(x, y, Gui.zLevel).color(startColor.getRed(), startColor.getGreen(), startColor.getBlue(), startColor.getAlpha()).endVertex();
        worldrenderer.pos(x, y + height, Gui.zLevel).color(endColor.getRed(), endColor.getGreen(), endColor.getBlue(), endColor.getAlpha()).endVertex();
        worldrenderer.pos(x + width, y + height, Gui.zLevel).color(endColor.getRed(), endColor.getGreen(), endColor.getBlue(), endColor.getAlpha()).endVertex();
        tessellator.draw();
        GlStateManager.shadeModel(7424);
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.enableTexture2D();
    }

    // skidded from weedhack aswell since I WILL NOT do it myself

    public static int wave(int color, int color2, long timeMS, int count) {
        float factor = Math.abs((((timeMS * 2L) - count * 500L) % 8001) / 4000.0F - 1.0F);
        return interpolate(new Color(color), new Color(color2), factor).getRGB();
    }

    public static Color interpolate(Color current, Color target, float factor) {
        return new Color(
                lerp(current.getRed(), target.getRed(), factor),
                lerp(current.getGreen(), target.getGreen(), factor),
                lerp(current.getBlue(), target.getBlue(), factor),
                lerp(current.getAlpha(), target.getAlpha(), factor)
        );
    }

    public static Color interpolate(Color current, Color target, int speed, float delta) {
        return new Color(
                incrementTo(current.getRed(), target.getRed(), (int) (speed * delta)),
                incrementTo(current.getGreen(), target.getGreen(), (int) (speed * delta)),
                incrementTo(current.getBlue(), target.getBlue(), (int) (speed * delta)),
                incrementTo(current.getAlpha(), target.getAlpha(), (int) (speed * delta))
        );
    }

    public static int lerp(int a, int b, float f) {
        return a + (int)(f * (float)(b - a));
    }

    public static int incrementTo(int current, int target, int speed) {
        if (current == target) {
            return current;
        }
        return current < target ? Math.min(current + speed, target) : Math.max(current - speed, target);
    }


}
