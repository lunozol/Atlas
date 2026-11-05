package io.lunozol.atlas.system.features.widget.widgets;

import io.github.nevalackin.radbus.Listen;
import io.lunozol.atlas.system.event.events.render.Render2DEvent;
import io.lunozol.atlas.system.features.widget.Widget;

public class WatermarkWidget extends Widget {

    public WatermarkWidget() {
        super("Watermark", "Client's logo type thing");
    }

    @Listen
    public void onRender2D(Render2DEvent event) {

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
