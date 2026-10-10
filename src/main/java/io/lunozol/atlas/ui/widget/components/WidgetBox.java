package io.lunozol.atlas.ui.widget.components;

import io.lunozol.atlas.Atlas;
import io.lunozol.atlas.Constants;
import io.lunozol.atlas.system.Manager;
import io.lunozol.atlas.system.widget.Widget;
import io.lunozol.atlas.ui.widget.components.subcomponents.WidgetPanel;
import io.lunozol.atlas.utils.render.RenderUtils;

import java.util.ArrayList;
import java.util.List;

public class WidgetBox implements Constants {
    private final List<WidgetPanel> widgetPanels = new ArrayList<>();

    public void draw(int x, int y, int mouseX, int mouseY) {
        if (widgetPanels.isEmpty()) {
            for (Widget widget : Atlas.getInstance().getManager().getWidgets()) {
                widgetPanels.add(new WidgetPanel(widget));
            }
        }

        int height = 150;
        int width = (WidgetPanel.getWidth() * 3) + (padding * 5);

        int xd = x - (width / 2);

        RenderUtils.rect(xd, y, width, height, bg);

        int xB = xd + padding;
        int yB = y + padding;
        int index = 1;
        for (WidgetPanel widgetPanel : widgetPanels) {
            widgetPanel.draw(xB, yB, mouseX, mouseY);

            xB += 50 + padding;
            index++;

            if (index == 4) {
                yB += height + padding;
                xB = xd + padding;
                index = 1;
            }

            if (widgetPanel.getOpenedWidget() != null) {
//                widgetPanel.getWidgetSetting().draw(mouseX, mouseY, x, y, width, height);
            }
        }
    }

    public static int getWidth() {
        return (WidgetPanel.getWidth() * 3) + (padding * 5);
    }

    public static int getHeight() {
        return 150;
    }
}
