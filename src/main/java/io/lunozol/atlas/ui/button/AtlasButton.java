package io.lunozol.atlas.ui.button;

import io.lunozol.atlas.Constants;
import io.lunozol.atlas.utils.render.RenderUtils;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.StandardException;
import org.lwjgl.input.Mouse;

import java.awt.*;

@Getter
public class AtlasButton implements Constants {
    @Setter
    private String title;
    @Setter
    private int mouseX;
    @Setter
    private int mouseY;
    @Setter
    private int x;
    @Setter
    private int y;
    private final int width;
    private final int height;

    private boolean clicked = false;
    private boolean wasClicked;
    private boolean hovered = false;

    public AtlasButton(String title, int mouseY, int mouseX, int x, int y) {
        this.title = title;
        this.mouseY = mouseY;
        this.mouseX = mouseX;
        this.x = x;
        this.y = y;
        width = mc.fontRendererObj.getStringWidth(title) + (3 * 2) + padding;
        height = mc.fontRendererObj.FONT_HEIGHT + (padding * 3);
    }

    public AtlasButton(String title, int mouseX, int mouseY, int x, int y, int width, int height) {
        this.title = title;
        this.mouseX = mouseX;
        this.mouseY = mouseY;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void draw() {
        if (!Mouse.isButtonDown(0)) wasClicked = false;

        Color oColor = new Color(50, 50, 50, 255);
        int oSize = 1;
        int bW = this.width - (oSize);
        int bH = this.height - (oSize);

        // top
        RenderUtils.rect(x, y, this.width, oSize, oColor);
        // bottom
        RenderUtils.rect(x, y + this.height, this.width + 1, oSize, oColor);
        // left
        RenderUtils.rect(x, y, oSize, this.height, oColor);
        // right
        RenderUtils.rect(x + this.width, y, oSize, this.height, oColor);

        if (RenderUtils.hovered(mouseX, mouseY, x + oSize, y + oSize, bW, bH)) {
            RenderUtils.gradientRect(x + oSize, y + oSize, bW, bH, firstColor.darker(), secondColor.darker());
            hovered = true;
            if (Mouse.isButtonDown(0)) handleDown();
        } else {
            RenderUtils.gradientRect(x + oSize, y + oSize, bW, bH, bg, bg.darker());
            hovered = false;
        }

        mc.fontRendererObj.drawStringWithShadow(this.title, x + oSize + ((float) bW / 2) - ((float) mc.fontRendererObj.getStringWidth(title) / 2), y + oSize + ((float) bH / 2) - ((float) mc.fontRendererObj.FONT_HEIGHT / 2), Color.white.getRGB());
    }

    public void clearClick() {
        clicked = false;
    }

    private void handleDown() {
        if (!wasClicked) {
            clicked = true;
            wasClicked = true;
        }
    }
}
