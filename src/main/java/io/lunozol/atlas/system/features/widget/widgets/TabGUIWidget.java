package io.lunozol.atlas.system.features.widget.widgets;

import io.github.nevalackin.radbus.Listen;
import io.lunozol.atlas.system.event.events.render.Render2DEvent;
import io.lunozol.atlas.system.features.module.property.properties.NumberProperty;
import io.lunozol.atlas.system.features.widget.Widget;
import io.lunozol.atlas.ui.widgets.tabgui.TabGUIPanel;

public class TabGUIWidget extends Widget {
    private TabGUIPanel panel;

    public TabGUIWidget() {
        super("TabGUI", "Description!!!");
        setEnabled(true);
    }

    @Listen
    public void onRender2D(Render2DEvent event) {
        if (panel == null) {
            panel = new TabGUIPanel();
        }

        panel.draw(50, 50);
    }

    @Override
    public float getX() {
        return 0;
    }

    @Override
    public float getY() {
        return 0;
    }

    @Override
    public float setX(float x) {
        return 0;
    }

    @Override
    public float setY(float y) {
        return 0;
    }

    @Override
    public float getWidth() {
        return 0;
    }

    @Override
    public float getHeight() {
        return 0;
    }
}
