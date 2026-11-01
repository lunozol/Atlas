package io.lunozol.atlas.ui.widgets.tabgui.components;

import io.lunozol.atlas.Constants;
import io.lunozol.atlas.system.features.module.ModuleCategory;
import io.lunozol.atlas.ui.widgets.tabgui.TabGUIPanel;
import io.lunozol.atlas.utils.render.RenderUtils;

import java.awt.*;

public class TabCategory extends TabGUIPanel {

    public void draw(ModuleCategory moduleCategory, int x, int y, int width, int height, boolean opened) {
        mc.fontRendererObj.drawStringWithShadow(RenderUtils.normalizeString(moduleCategory.name()), x + padding, y + ((float) height / 2) - ((float) mc.fontRendererObj.FONT_HEIGHT / 2), Color.white.getRGB());
    }
}
