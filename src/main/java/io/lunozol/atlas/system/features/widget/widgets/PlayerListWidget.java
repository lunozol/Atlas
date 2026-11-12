package io.lunozol.atlas.system.features.widget.widgets;

import io.github.nevalackin.radbus.Listen;
import io.lunozol.atlas.system.event.events.render.Render2DEvent;
import io.lunozol.atlas.system.event.events.render.RenderGuiChatEvent;
import io.lunozol.atlas.system.features.module.modules.visual.entry.Entry;
import io.lunozol.atlas.system.features.module.modules.visual.entry.entries.PlayerListEntry;
import io.lunozol.atlas.system.features.module.property.properties.NumberProperty;
import io.lunozol.atlas.system.features.widget.Widget;
import io.lunozol.atlas.utils.render.RenderUtils;
import org.lwjgl.input.Mouse;

import java.awt.*;

public class PlayerListWidget extends Widget {
    private int x = 4;
    private int y = 30;
    private int width = 180;
    private int height = 12;

    public PlayerListWidget() {
        super("PlayerList", "Lists the player in your world!");
        setEnabled(true);
    }

    private void handleDragging(int mouseX, int mouseY) {
        if (RenderUtils.hovered(mouseX, mouseY, x, y, width, height)) {
            if (Mouse.isButtonDown(0)) {
                x = mouseX - (width / 2);
                y = mouseY - (height / 2);
            }
        }
    }

    @Listen
    public void onRenderGuiChat(RenderGuiChatEvent event) {
        handleDragging(event.getMouseX(), event.getMouseY());
    }

    @Listen
    public void onRender2D(Render2DEvent event) {
        RenderUtils.rect(x, y, width, height, bg);

        int yD = y + height;
        for (PlayerListEntry entry : Entry.getPlayerListEntries()) {
            entry.setY(yD);
            entry.setX(x);
            entry.draw();

            yD += (int) Math.round((entry.getHeight() * 0.8f) * entry.getFadeAnim().getValue());
        }
    }

    @Override
    public void onEnable() {
        for (PlayerListEntry entry : Entry.getPlayerListEntries()) {
            entry.reset(y);
        }
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
