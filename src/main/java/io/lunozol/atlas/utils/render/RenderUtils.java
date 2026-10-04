package io.lunozol.atlas.utils.render;

import net.minecraft.client.gui.Gui;
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
