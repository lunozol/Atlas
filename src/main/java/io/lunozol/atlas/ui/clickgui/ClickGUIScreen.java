package io.lunozol.atlas.ui.clickgui;

import io.lunozol.atlas.Atlas;
import io.lunozol.atlas.system.module.Module;
import io.lunozol.atlas.system.module.ModuleCategory;
import io.lunozol.atlas.system.module.property.Property;
import io.lunozol.atlas.system.module.property.properties.BooleanProperty;
import io.lunozol.atlas.system.module.property.properties.ModeProperty;
import io.lunozol.atlas.ui.clickgui.property.PanelProperty;
import io.lunozol.atlas.ui.clickgui.property.properties.BooleanPanel;
import io.lunozol.atlas.ui.clickgui.property.properties.ModePanel;
import io.lunozol.atlas.utils.render.RenderUtils;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.input.Mouse;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ClickGUIScreen extends GuiScreen {
    private final Map<Module, List<PanelProperty>> settings = new HashMap<>();
    private List<Module> opened = new ArrayList<>();
    private boolean hasLeftClicked;
    private boolean hasRightClicked;
    private boolean debug = true;

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        int settingHeight = 12;

        if (settings.isEmpty()) {
            for (Module module : Atlas.getInstance().getModuleManager().getModules()) {
                List<PanelProperty> panelList = new ArrayList<>();

                for (Property property : module.getSettings()) {
                    if (property instanceof BooleanProperty) {
                        panelList.add(new BooleanPanel((BooleanProperty) property));
                    } else if (property instanceof ModeProperty) {
                        panelList.add(new ModePanel((ModeProperty) property, settingHeight));
                    }
                }

                settings.put(module, panelList);
            }
        }

        if (!Mouse.isButtonDown(0)) hasLeftClicked = false;
        if (!Mouse.isButtonDown(1)) hasRightClicked = false;

        int panelWidth = 150;
        int cHeight = 20;
        int padding = 2;
        int moduleHeight = 14;

        int x = 100;
        int y = 50;
        for (ModuleCategory category : ModuleCategory.values()) {
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

                int sY = mY + moduleHeight;
                if (opened.contains(module)) {
                    List<PanelProperty> properties = settings.get(module);
                    for (PanelProperty property : properties) {
                        property.draw(mouseX, mouseY, x, sY, panelWidth, settingHeight);
                        if (property instanceof BooleanPanel) {
                            if (((BooleanPanel) property).isHovered() && handleLeftClick()) {
                                ((BooleanPanel) property).getProperty().toggle();
                                hasLeftClicked = true;
                            }
                        }
                        if (property instanceof ModePanel) {
                            if (((ModePanel) property).isHovered() && handleRightClick()) {
                                ((ModePanel) property).setExpanded(!((ModePanel) property).isExpanded());
                            }

                            if (((ModePanel) property).isExpanded()) {
                                settingHeight = ((ModePanel) property).getHeight();
                            }
                        }

                        sY += settingHeight;
                    }
                }

                mY += moduleHeight + (opened.contains(module) ? sY - (mY + moduleHeight) : 0);
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
                if (opened.contains(module) || module.getSettings().isEmpty()) {
                    opened.remove(module);
                    System.out.println("Closed " + module.getName());
                } else {
                    opened.add(module);
                    System.out.println("Opened " + module.getName());
                }

                hasRightClicked = true;
            }
        }
    }

    private boolean handleLeftClick() {
        if (Mouse.isButtonDown(0) && !hasLeftClicked) {
            hasLeftClicked = true;
            return true;
        }
        return false;
    }

    private boolean handleRightClick() {
        if (Mouse.isButtonDown(1) && !hasRightClicked) {
            hasRightClicked = true;
            return true;
        }
        return false;
    }

}
