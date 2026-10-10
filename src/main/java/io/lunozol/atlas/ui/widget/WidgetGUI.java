package io.lunozol.atlas.ui.widget;

import io.lunozol.atlas.Atlas;
import io.lunozol.atlas.Constants;
import io.lunozol.atlas.system.widget.Widget;
import io.lunozol.atlas.ui.button.AtlasButton;
import io.lunozol.atlas.ui.widget.components.WidgetBox;
import io.lunozol.atlas.ui.widget.components.subcomponents.WidgetSettings;
import io.lunozol.atlas.utils.render.animation.Animation;
import io.lunozol.atlas.utils.render.animation.Easing;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.input.Mouse;

import java.util.List;

public class WidgetGUI implements Constants {
    private static final Animation animation = new Animation(Easing.EASE_IN_OUT_CUBIC, 250);
    private static final AtlasButton button = new AtlasButton("Widgets", Mouse.getX(), Mouse.getY(), 0, 0);
    private static final WidgetBox widgetBox = new WidgetBox();
    private static boolean canDraw = false;
    private WidgetSettings openedWidget;

    public static void draw(int x, int y, int mouseX, int mouseY) {
        animation.run(canDraw ? 1 : -5);

        button.setX(x - (button.getWidth() / 2));
        button.setY(y - (button.getHeight()) / 2);
        button.setMouseX(mouseX);
        button.setMouseY(mouseY);

        if (animation.getValue() > -5) {
            GlStateManager.pushMatrix();
            widgetBox.draw(x, (int) Math.round((y + button.getHeight() + padding) * animation.getValue()), mouseX, mouseY);
            GlStateManager.popMatrix();
        }

        button.draw();

        if (button.isClicked()) {
            canDraw = !canDraw;
            button.clearClick();
        }

    }

}
