package io.lunozol.atlas.ui.button;

import io.lunozol.atlas.Atlas;
import io.lunozol.atlas.Constants;
import io.lunozol.atlas.utils.render.RenderUtils;
import io.lunozol.atlas.utils.render.animation.Animation;
import io.lunozol.atlas.utils.render.animation.Easing;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;

import java.awt.*;

@Getter
@Setter
@RequiredArgsConstructor
public class NewAtlasButton implements Constants {
    private Animation animation = new Animation(Easing.EASE_OUT_CUBIC, 150, 2);
    private final String title;
    private final int id;
    private final int x;
    private final int y;
    private final int width;
    private final int height;
    private boolean hovered;


    public void drawButton() {
        animation.run(hovered ? 1 : 2);

        float factor = (float) (1 - animation.getValue());
        float div = (float) (animation.getValue() / 2);
        int additionAlpha = Math.round(55 * factor);
        int alpha = 200 + additionAlpha;

        GlStateManager.pushMatrix();
        GlStateManager.translate(x, y, 1);
        float textY = ((float) height / 2) - ((float) mc.fontRendererObj.FONT_HEIGHT / 2);
        float barWidth = (float) (width / animation.getValue());
        float spacing = padding * div;
        float barY = (float) (height - 1 - spacing);

        RenderUtils.rect(0, 0, width, height, RenderUtils.withAlpha(bg, alpha));
        RenderUtils.gradientRect(((float) width / 2) - (barWidth / 2), barY, barWidth, 2, firstColor, firstColor.darker());

        mc.fontRendererObj.drawStringWithShadow(this.title, ((float) width / 2) - ((float) mc.fontRendererObj.getStringWidth(this.title) / 2), textY, Color.white.getRGB());

        GlStateManager.popMatrix();
    }

    public void updateHover(int mouseX, int mouseY, int hoverX, int hoverY) {
        hovered = RenderUtils.hovered(mouseX, mouseY, hoverX, hoverY, width, height);
    }
}
