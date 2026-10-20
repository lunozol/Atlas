package io.lunozol.atlas.utils.render;

import io.lunozol.atlas.Constants;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.MathHelper;
import org.lwjgl.input.Mouse;

import java.awt.*;
import java.util.Locale;

public class RenderUtils implements Constants {
    public static final ScaledResolution scaledResolution = new ScaledResolution(mc);

    public static void rect(int x, int y, int width, int height, Color color) {
        Gui.drawRect(x, y, x + width, y + height, color.getRGB());
    }

    public static boolean hovered(float mouseX, float mouseY, int x, int y, int width, int height) {
        if (mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height) return true;
        return false;
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

    public static int lerp(int a, int b, float f) {
        return a + (int)(f * (float)(b - a));
    }

    public static String normalizeString(String input) {
        String result = "";

        result += input.toUpperCase().charAt(0);
        result += input.toLowerCase().substring(1, input.length());
        return result;
    }

    public static Color withAlpha(Color color, int alpha) {
        return new Color(color.getRed(), color.getBlue(), color.getGreen(), alpha);
    }

    public static int getMouseX() {
        return Mouse.getX() / scaledResolution.getScaleFactor() - 3;
    }

    public static int getMouseY() {
        return Mouse.getY() / scaledResolution.getScaleFactor() - 27;
    }
}
