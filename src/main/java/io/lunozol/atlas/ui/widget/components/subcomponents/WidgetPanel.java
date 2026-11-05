package io.lunozol.atlas.ui.widget.components.subcomponents;

import io.lunozol.atlas.Constants;
import io.lunozol.atlas.system.features.widget.Widget;
import io.lunozol.atlas.utils.game.ChatUtil;
import io.lunozol.atlas.utils.render.RenderUtils;
import lombok.Getter;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.input.Mouse;

import java.awt.*;

@Getter
public class WidgetPanel implements Constants {
    //    private final int height = 30;
//    private final int width = 50;
    private final Widget widget;
    private boolean hasClicked;
// TODO recode
    public WidgetPanel(Widget widget) {
        this.widget = widget;
    }

    public void draw(int x, int y, int mouseX, int mouseY, boolean canToggle) {
        if (!Mouse.isButtonDown(0)) hasClicked = false;

        int height = 27;
        int width = 75;

        RenderUtils.rect(x, y, width, height, bg.brighter().brighter());

        mc.fontRendererObj.drawStringWithShadow(widget.getName(), x + ((float) width / 2) - ((float) mc.fontRendererObj.getStringWidth(widget.getName()) / 2), y + padding, Color.white.getRGB());

        int tButtonY = y + height - 12 - (padding);
        if (!RenderUtils.hovered(mouseX, mouseY, x + padding, tButtonY, width - 4, 12)) {
            RenderUtils.gradientRect(x + padding, tButtonY, width - 4, 12, widget.isEnabled() ? Color.GREEN : Color.red, widget.isEnabled() ? Color.GREEN.darker() : Color.red.darker());
        } else {
            RenderUtils.gradientRect(x + padding, tButtonY, width - 4, 12, widget.isEnabled() ? Color.GREEN : Color.red, widget.isEnabled() ? Color.RED.darker() : Color.GREEN.darker());

            if (Mouse.isButtonDown(0) && !hasClicked && canToggle) {
                widget.toggle();
                hasClicked = true;
                ChatUtil.notify("toggled", "toggle");
            }
        }

        float bTW = mc.fontRendererObj.getStringWidth(widget.isEnabled() ? "Disable" : "Enable");
        mc.fontRendererObj.drawStringWithShadow(widget.isEnabled() ? "Disable" : "Enable", x + ((float) (width - 4) / 2) - (bTW / 2), tButtonY + ((float) 10 / 2) - ((float) mc.fontRendererObj.FONT_HEIGHT / 2) + 1, Color.white.getRGB());
    }

    public static int getWidth() {
        return 75;
    }

    public static int getHeight() {
        return 37;
    }

}
