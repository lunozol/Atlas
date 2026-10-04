package io.lunozol.atlas.utils.render;

import net.minecraft.client.gui.Gui;

import java.awt.*;

public class RenderUtils {

    public static void rect(int x, int y, int width, int height, Color color) {
        Gui.drawRect(x, y, x + width, y + height, color.getRGB());
    }

    public static boolean hovered(float mouseX, float mouseY, int x, int y, int width, int height) {
        if (mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height) return true;
        return false;
    }
}
