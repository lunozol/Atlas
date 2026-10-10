package io.lunozol.atlas.ui.widget.components.subcomponents;

import io.lunozol.atlas.Constants;
import io.lunozol.atlas.system.widget.Widget;
import io.lunozol.atlas.utils.render.RenderUtils;

public class WidgetSettings implements Constants {
    private final Widget widget;

    public WidgetSettings(Widget widget) {
        this.widget = widget;
    }

    public void draw(int mouseX, int mouseY, int x, int y, int width, int height) {
        int xD = x - (width / 2);

        RenderUtils.rect(xD, y, WidgetPanel.getWidth(), height, bg);
    }
}
