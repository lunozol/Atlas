package io.lunozol.atlas.ui.clickgui.property.properties;

import io.lunozol.atlas.system.features.module.property.properties.BooleanProperty;
import io.lunozol.atlas.system.features.module.property.properties.TextProperty;
import io.lunozol.atlas.ui.clickgui.property.PanelProperty;
import io.lunozol.atlas.utils.render.RenderUtils;
import io.lunozol.atlas.utils.render.animation.Animation;
import io.lunozol.atlas.utils.render.animation.Easing;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.MathHelper;

import java.awt.*;

public class TextPanel extends PanelProperty {
    private final TextProperty property;

    public TextPanel(TextProperty property) {
        this.property = property;
    }

    public void draw(float mouseX, float mouseY, int x, int y, int width, int height) {
        RenderUtils.rect(x, y, width, height, bg);
        GlStateManager.pushMatrix();
        GlStateManager.translate(x + (width / 2) - (mc.fontRendererObj.getStringWidth(property.getText()) / 2), y + (height / 2) - (mc.fontRendererObj.FONT_HEIGHT / 2), 0);
        mc.fontRendererObj.drawStringWithShadow(property.getText(), 0, 0, Color.white.getRGB());
        GlStateManager.popMatrix();
    }

    @Override
    public boolean isHidden() {
        return property.isHidden();
    }
}
