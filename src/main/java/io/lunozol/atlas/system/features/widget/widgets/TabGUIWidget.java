package io.lunozol.atlas.system.features.widget.widgets;

import io.github.nevalackin.radbus.Listen;
import io.lunozol.atlas.Constants;
import io.lunozol.atlas.system.event.events.render.Render2DEvent;
import io.lunozol.atlas.system.features.module.property.properties.NumberProperty;
import io.lunozol.atlas.system.features.widget.Widget;
import io.lunozol.atlas.ui.widgets.tabgui.TabGUIPanel;
import io.lunozol.atlas.utils.render.RenderUtils;
import io.lunozol.atlas.utils.render.animation.Animation;
import io.lunozol.atlas.utils.render.animation.Easing;
import net.minecraft.client.gui.Gui;

public class TabGUIWidget extends Widget {
    private final Animation animation = new Animation(Easing.EASE_IN_OUT_CUBIC, 1000);
    boolean flip = false;
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

        animation.run(flip ? 0 : 100);

        if (animation.getValue() == 100f && animation.isFinished() || animation.getValue() == 0 && animation.isFinished()) {
            flip = !flip;
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
