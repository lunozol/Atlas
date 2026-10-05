package io.lunozol.atlas.ui.clickgui.property.properties;

import io.lunozol.atlas.system.module.property.properties.ModeProperty;
import io.lunozol.atlas.ui.clickgui.property.PanelProperty;
import io.lunozol.atlas.utils.render.RenderUtils;
import lombok.Getter;
import lombok.Setter;
import org.lwjgl.input.Mouse;

import java.awt.*;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

public class ModePanel extends PanelProperty {
    @Getter
    private final ModeProperty property;
    @Setter
    @Getter
    private boolean expanded;
    @Getter
    private boolean hovered;
    private final int height;
    private int expandedHeight;
    public boolean hasClicked;

    public ModePanel(ModeProperty property, int height) {
        this.property = property;
        this.height = height;
    }

    public void draw(float mouseX, float mouseY, int x, int y, int width, int height) {
        if (!Mouse.isButtonDown(0)) hasClicked = false;
        if (Arrays.stream(property.getValues()).count() < 2) expanded = false;

        drawBase(property.getName(), x, y, width, expanded ? expandedHeight : height);

        expandedHeight = (int) (height * (Arrays.stream(property.getValues()).count()));

        int tWidth = mc.fontRendererObj.getStringWidth(property.getValue() + "+");
        int tY = y + (height / 2) - ((mc.fontRendererObj.FONT_HEIGHT) / 2);
        int tX = x + width - tWidth - padding;
        hovered = RenderUtils.hovered(mouseX, mouseY, tX, tY, tWidth, mc.fontRendererObj.FONT_HEIGHT);
        mc.fontRendererObj.drawString(property.getValue() + (expanded ? "" : "+"), tX, tY, hovered ? secondColor.getRGB() : firstColor.getRGB());


        if (expanded) {
            tY += mc.fontRendererObj.FONT_HEIGHT + padding;

            for (String mode : Arrays.stream(property.getValues()).filter(m -> !Objects.equals(m, property.getValue())).collect(Collectors.toList())) {
                tWidth = mc.fontRendererObj.getStringWidth(mode + "+");
                tX = x + width - tWidth - padding;

                hovered = RenderUtils.hovered(mouseX, mouseY, tX, tY, tWidth, mc.fontRendererObj.FONT_HEIGHT);

                mc.fontRendererObj.drawString(mode, tX, tY, hovered ? secondColor.getRGB() : Color.white.getRGB());

                if (Mouse.isButtonDown(0) && !hasClicked && hovered) {
                    property.setValue(mode);
                    expanded = false;
                    hasClicked = true;
                }

                tY += mc.fontRendererObj.FONT_HEIGHT + padding;
            }

        }
    }

    public int getHeight() {
        return expanded ? expandedHeight : height;
    }
}
