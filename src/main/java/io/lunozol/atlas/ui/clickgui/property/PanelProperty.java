package io.lunozol.atlas.ui.clickgui.property;

import io.lunozol.atlas.Constants;
import io.lunozol.atlas.utils.render.RenderUtils;

import java.awt.*;

public abstract class PanelProperty implements Constants {
    public final Color bg = new Color(10,10,10, 190);

    public void drawBase(String propertyName, int x, int y, int width, int height) {
        RenderUtils.rect(x, y, width, height, bg);
        mc.fontRendererObj.drawString(propertyName, x + padding, y + (height / 2) - (mc.fontRendererObj.FONT_HEIGHT / 2), Color.white.getRGB());
    }

    public abstract void draw(float mouseX, float mouseY, int x, int y, int width, int height);
}
