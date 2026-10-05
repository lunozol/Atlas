package io.lunozol.atlas.ui.clickgui.property.properties;

import io.lunozol.atlas.system.module.property.properties.BooleanProperty;
import io.lunozol.atlas.ui.clickgui.property.PanelProperty;
import io.lunozol.atlas.utils.render.RenderUtils;
import io.lunozol.atlas.utils.render.animation.Animation;
import io.lunozol.atlas.utils.render.animation.Easing;
import lombok.Getter;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.MathHelper;

import java.awt.*;

@Getter
public class BooleanPanel extends PanelProperty {
    private final Animation anim = new Animation(Easing.LINEAR, 100);
    private final BooleanProperty property;
    private boolean hovered;

    public BooleanPanel(BooleanProperty property) {
        this.property = property;
    }

    public void draw(float mouseX, float mouseY, int x, int y, int width, int height) {
        anim.run(property.isEnabled() ? 1 : 0);

        drawBase(property.getName(), x, y, width, height);

        int toggleDimensionsBG = height - (padding * 2);
        int buttonBgX = x + width - toggleDimensionsBG - padding;
        RenderUtils.rect(buttonBgX, y + (height / 2) - (toggleDimensionsBG / 2), toggleDimensionsBG, toggleDimensionsBG, new Color(60, 60, 60));
        float toggleDimensions = (int) ((toggleDimensionsBG - padding) * anim.getValue());
        float buttonX = (buttonBgX) + ((toggleDimensionsBG - toggleDimensions) / 2);
        GlStateManager.pushMatrix();
        GlStateManager.translate(buttonX, y + (height / 2) - (toggleDimensions / 2), 1);
        GlStateManager.scale(MathHelper.clamp_double(toggleDimensions * anim.getValue(), 0, 1), MathHelper.clamp_double(toggleDimensions * anim.getValue(), 0, 1), 1);
        RenderUtils.rect(0, 0, (int) toggleDimensions, (int) toggleDimensions, firstColor);
        GlStateManager.popMatrix();

        hovered = RenderUtils.hovered(mouseX, mouseY, x + width - toggleDimensionsBG - padding - (padding / 2), y + (height / 2) - (toggleDimensionsBG / 2), toggleDimensionsBG, toggleDimensionsBG);
    }

    @Override
    public boolean isHidden() {
        return property.isHidden();
    }


}
