package io.lunozol.atlas.ui.screens.clickgui.property.properties;

import io.lunozol.atlas.system.features.module.property.properties.NumberProperty;
import io.lunozol.atlas.ui.screens.clickgui.property.PanelProperty;
import io.lunozol.atlas.utils.render.RenderUtils;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import net.minecraft.util.MathHelper;

@Getter
@Setter
public class NumberPanel extends PanelProperty {
    private final NumberProperty property;
    private float min;
    private float max;
    private float value;
    private float step;
    private boolean hovered;

    public NumberPanel(NumberProperty property) {
        this.property = property;
        this.min = property.getMin();
        this.max = property.getMax();
        this.value = property.getValue();
        this.step = property.getStep();
        this.hovered = false;
    }

    @Override
    public void draw(float mouseX, float mouseY, int x, int y, int width, int height) {
        if (RenderUtils.hovered(mouseX, mouseY, x, y, width, height)) {
            hovered = true;
        } else {
            hovered = false;
        }

        int xD = x + padding;
        int yD = y + padding;

        int barWidth = width - (padding * 2);
        int barHeight = 4;

        float sliderMouseX = MathHelper.clamp_float(mouseX / (xD + barWidth), xD, xD + barWidth);
    }

    @Override
    public boolean isHidden() {
        return property.isHidden();
    }
}
