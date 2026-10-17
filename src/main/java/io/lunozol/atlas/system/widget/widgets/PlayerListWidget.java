package io.lunozol.atlas.system.widget.widgets;

import io.github.nevalackin.radbus.Listen;
import io.lunozol.atlas.system.event.events.render.Render2DEvent;
import io.lunozol.atlas.system.widget.Widget;
import io.lunozol.atlas.utils.render.RenderUtils;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

public class PlayerListWidget extends Widget {
    private int x = 4;
    private int y = 30;
    private int width = 180;
    private int height = 12;

    public PlayerListWidget() {
        super("PlayerList", "Lists the player in your world!");
    }

    private void handleDragging() {
        if (RenderUtils.hovered(Mouse.getX(), Mouse.getY(), x, y, width, height)) {

        }
    }

    @Listen
    public void onRender2D(Render2DEvent event) {
        int rectHeight = 12;


    }
}
