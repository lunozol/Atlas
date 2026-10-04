package io.lunozol.atlas.ui.clickgui;

import io.lunozol.atlas.Atlas;
import io.lunozol.atlas.system.module.Module;
import io.lunozol.atlas.system.module.ModuleCategory;
import io.lunozol.atlas.utils.render.RenderUtils;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.input.Mouse;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ClickGUIScreen extends GuiScreen {
    private List<Module> opened = new ArrayList<>();
    private boolean hasLeftClicked;
    private boolean hasRightClicked;

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        if (!Mouse.isButtonDown(0)) hasLeftClicked = false;
        if (!Mouse.isButtonDown(1)) hasRightClicked = false;

        int panelWidth = 100;
        int cHeight = 20;
        int padding = 2;
        int moduleHeight = 14;

        int x = 50;
        int y = 50;
        for (ModuleCategory category : ModuleCategory.values()) {
            y = 50;
            RenderUtils.rect(x, y, panelWidth, cHeight, Atlas.firstColor.darker());
            GlStateManager.pushMatrix();
            GlStateManager.translate(x + ((float) panelWidth / 2),y + ((float) cHeight / 4),1);
            GlStateManager.scale(1.5,1.5,1);
            mc.fontRendererObj.drawString(category.name(), -mc.fontRendererObj.getStringWidth(category.name()) / 2, 0,  Color.white.getRGB());
            GlStateManager.popMatrix();
            int mY = y + cHeight;
            for (Module module : Atlas.getInstance().getModuleManager().getModules().stream().filter(m -> m.getCategory().equals(category)).collect(Collectors.toList())) {
                if (RenderUtils.hovered(mouseX, mouseY, x, mY, panelWidth, moduleHeight)) {
                    RenderUtils.rect(x,mY, panelWidth, moduleHeight, new Color(50, 50, 50, 190));
                } else {
                    RenderUtils.rect(x,mY, panelWidth, moduleHeight, new Color(20, 20, 20, 190));
                }

                handleAction(mouseX, mouseY, x, mY, panelWidth, moduleHeight, module);

                GlStateManager.pushMatrix();
                GlStateManager.translate(x + ((float) panelWidth / 2),mY + ((float) cHeight / 4),1);
                GlStateManager.scale(1.25,1.25,1);
                mc.fontRendererObj.drawString(module.getName(), -mc.fontRendererObj.getStringWidth(module.getName()) / 2, -mc.fontRendererObj.FONT_HEIGHT / 2 + padding,  module.isEnabled() ? Atlas.secondColor.getRGB() : Color.WHITE.getRGB());
                GlStateManager.popMatrix();

                mY += moduleHeight;
            }


            x += panelWidth + padding;
        }
    }

    private void handleAction(float mouseX, float mouseY, int x, int y, int width, int height, Module module) {
        if (RenderUtils.hovered(mouseX, mouseY, x, y, width, height)) {
            if (Mouse.isButtonDown(0) && !hasLeftClicked) {
                module.toggle();
                hasLeftClicked = true;
            }

            if (Mouse.isButtonDown(1) && !hasRightClicked) {
                opened.add(module);
                hasRightClicked = true;
            }
        }
    }

}
