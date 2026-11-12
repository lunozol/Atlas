package io.lunozol.atlas.ui.screens.clickgui.property.properties;

import io.lunozol.atlas.Constants;
import io.lunozol.atlas.system.features.module.property.properties.NumberProperty;
import io.lunozol.atlas.ui.screens.clickgui.property.PanelProperty;
import io.lunozol.atlas.utils.game.ChatUtil;
import io.lunozol.atlas.utils.render.RenderUtils;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import net.minecraft.util.MathHelper;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import java.awt.*;

@Getter
@Setter
public class NumberPanel extends PanelProperty {
    private final NumberProperty property;
    private float min;
    private float max;
    private float value;
    private float step;
    private boolean hovered;
    boolean wasDown = false;

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
        if (RenderUtils.hovered(mouseX, mouseY, x, y, width, getHeight())) {
            hovered = true;
        } else {
            hovered = false;
        }

        drawBase(property.getName(), x, y, width, getHeight(), false);
        mc.fontRendererObj.drawStringWithShadow(property.getName(), x + padding, y + padding, Color.white.getRGB());
        mc.fontRendererObj.drawStringWithShadow("" + property.getValue(), x + width - mc.fontRendererObj.getStringWidth("" + property.getValue()) - padding, y + padding, Color.white.getRGB());

        int xD = x + padding;

        int barWidth = width - (padding * 2);
        int barHeight = 4;

        float bar = barWidth * (property.getValue() / property.getMax());

        float sliderMouseX = MathHelper.clamp_float(mouseX, xD, xD + barWidth);

        RenderUtils.rect(xD, y + getHeight() - padding - barHeight, barWidth, barHeight, bg.darker().darker());
        RenderUtils.gradientRect(xD, y + getHeight() - padding - barHeight, bar, barHeight, firstColor, firstColor.darker());

        if (!Keyboard.isKeyDown(Keyboard.KEY_LEFT) && !Keyboard.isKeyDown(Keyboard.KEY_RIGHT) && !Mouse.isButtonDown(0)) wasDown = false;

        if (hovered) {
            if (!wasDown) {
                if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) {
                    property.setValue(property.getValue() + step);
                    ChatUtil.send("+ " + property.getValue() + " " + step);
                    wasDown = true;
                } else if (Keyboard.isKeyDown(Keyboard.KEY_LEFT)) {
                    property.setValue(property.getValue() - step);
                    ChatUtil.send("- " + property.getValue() + " " + step);
                    wasDown = true;
                } else if (Mouse.isButtonDown(0)) {
                    property.setValue(sliderMouseX - (xD));
                }
            }
        }
    }

    @Override
    public boolean isHidden() {
        return property.isHidden();
    }

    @Override
    public int getHeight() {
        return 18;
    }
}
