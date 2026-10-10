package io.lunozol.atlas.ui.widget.components.subcomponents;

import io.lunozol.atlas.Constants;
import io.lunozol.atlas.system.widget.Widget;
import io.lunozol.atlas.utils.game.ChatUtil;
import io.lunozol.atlas.utils.render.RenderUtils;
import lombok.Getter;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import org.lwjgl.input.Mouse;

import java.awt.*;

@Getter
public class WidgetPanel implements Constants {
    //    private final int height = 30;
//    private final int width = 50;
    private final Widget widget;
    private boolean hasClicked;
    private Widget openedWidget;

    public WidgetPanel(Widget widget) {
        this.widget = widget;
    }

    public void draw(int x, int y, int mouseX, int mouseY) {
        if (!Mouse.isButtonDown(0)) hasClicked = false;

        int height = 37;
        int width = 75;

        RenderUtils.rect(x, y, width, height, bg.brighter().brighter());

        mc.fontRendererObj.drawStringWithShadow(widget.getName(), x + ((float) width / 2) - ((float) mc.fontRendererObj.getStringWidth(widget.getName()) / 2), y + padding, Color.white.getRGB());

        int tButtonY = y + height - 12 - (padding) - 10;
        if (!RenderUtils.hovered(mouseX, mouseY, x + padding, tButtonY, width - 4, 10) || openedWidget != null) {
            RenderUtils.gradientRect(x + padding, tButtonY, width - 4, 10, widget.isEnabled() ? Color.GREEN : Color.red, widget.isEnabled() ? Color.GREEN.darker() : Color.red.darker());
        } else {
            RenderUtils.gradientRect(x + padding, tButtonY, width - 4, 10, widget.isEnabled() ? Color.GREEN : Color.red, widget.isEnabled() ? Color.RED.darker() : Color.GREEN.darker());

            if (Mouse.isButtonDown(0) && !hasClicked) {
                widget.toggle();
                hasClicked = true;
                ChatUtil.notify("toggled", "toggle");
            }
        }

        float bTW = mc.fontRendererObj.getStringWidth(widget.isEnabled() ? "Disable" : "Enable");
        mc.fontRendererObj.drawStringWithShadow(widget.isEnabled() ? "Disable" : "Enable", x + ((float) (width - 4) / 2) - (bTW / 2), tButtonY + ((float) 10 / 2) - ((float) mc.fontRendererObj.FONT_HEIGHT / 2) + 0.5f, Color.white.getRGB());

        if (!RenderUtils.hovered(mouseX, mouseY, x + padding, y + height - padding - 10, width - 4, 10) || openedWidget != null) {
            RenderUtils.gradientRect(x + padding, y + height - padding - 10, width - 4, 10, bg.brighter().brighter(), bg.brighter());
        } else {
            RenderUtils.gradientRect(x + padding, y + height - padding - 10, width - 4, 10, secondColor.darker(), secondColor.darker().darker());

            if (Mouse.isButtonDown(0) && !hasClicked) {
                openedWidget = widget;
                hasClicked = true;
            }
        }

        GlStateManager.pushMatrix();
        GlStateManager.translate(x + padding, y + height - padding - 10, 1);
        mc.fontRendererObj.drawStringWithShadow("Settings", ((float) (width - 4) / 2) - ((float) mc.fontRendererObj.getStringWidth("Settings") / 2), 5 - ((float) mc.fontRendererObj.FONT_HEIGHT / 2), Color.white.getRGB());
        GlStateManager.popMatrix();
    }

    public static int getWidth() {
        return 75;
    }

}
