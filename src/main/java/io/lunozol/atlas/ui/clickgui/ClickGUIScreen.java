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
import io.lunozol.atlas.utils.game.ChatUtil;
import io.lunozol.atlas.utils.render.RenderUtils;
import io.lunozol.atlas.utils.render.animation.Animation;
import io.lunozol.atlas.utils.render.animation.Easing;
import lombok.Setter;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ClickGUIScreen extends GuiScreen {
    private final Animation animation = new Animation(Easing.EASE_IN_OUT_CUBIC, 250);
    private final Map<Module, List<PanelProperty>> settings = new HashMap<>();
    private List<Module> opened = new ArrayList<>();
    private Module listeningModule;
    private int key = 0;
    private boolean hasLeftClicked;
    private boolean hasRightClicked;
    private boolean hasMiddleClicked;
    private final int INACTIVE = 914230131;
    @Setter
    private boolean canClose = false;

    @Override
    public void initGui() {
        canClose = false;
        super.initGui();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        animation.run(canClose ? 0 : 1);

        if (canClose && animation.getValue() == 0) {
            close();
        }

        int settingHeight = 12;

        if (settings.isEmpty()) {
            for (Module module : Atlas.getInstance().getManager().getModules()) {
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
        if (!Mouse.isButtonDown(2)) hasMiddleClicked = false;

        int panelWidth = 135;
        int cHeight = 20;
        int padding = 2;
        int moduleHeight = 14;

        int x = 10;
        int y = 50;
        GlStateManager.pushMatrix();
        GlStateManager.scale(animation.getValue(), animation.getValue(), 1);
        for (ModuleCategory category : ModuleCategory.values()) {
            if (!Atlas.getInstance().getManager().getModulesByCategory(category).isEmpty()) {
                RenderUtils.rect(x, y, panelWidth, cHeight, Atlas.firstColor.darker());

                GlStateManager.pushMatrix();
                GlStateManager.translate(x + ((float) panelWidth / 2), y + ((float) cHeight / 4), 1);
                GlStateManager.scale(1.5, 1.5, 1);
                mc.fontRendererObj.drawString(category.name(), -mc.fontRendererObj.getStringWidth(category.name()) / 2, 0, Color.white.getRGB());
                GlStateManager.popMatrix();

                int mY = y + cHeight;
                for (Module module : Atlas.getInstance().getManager().getModules().stream().filter(m -> m.getCategory().equals(category)).collect(Collectors.toList())) {
                    if (RenderUtils.hovered(mouseX, mouseY, x, mY, panelWidth, moduleHeight)) {
                        RenderUtils.rect(x, mY, panelWidth, moduleHeight, new Color(50, 50, 50, 190));
                        if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) && handleLeftClick()) {
                            listeningModule = module;
                        }
                    } else {
                        RenderUtils.rect(x, mY, panelWidth, moduleHeight, new Color(20, 20, 20, 190));
                    }
                    String name = module.getName();
                    if (listeningModule != null && listeningModule == module) {
                        name = "...";
                    }

                    handleAction(mouseX, mouseY, x, mY, panelWidth, moduleHeight, module);

                    GlStateManager.pushMatrix();
                    GlStateManager.translate(x + ((float) panelWidth / 2), mY + ((float) cHeight / 4), 1);
                    GlStateManager.scale(1.25, 1.25, 1);
                    mc.fontRendererObj.drawString(name, -mc.fontRendererObj.getStringWidth(name) / 2, -mc.fontRendererObj.FONT_HEIGHT / 2 + padding, module.isEnabled() ? Atlas.secondColor.getRGB() : Color.WHITE.getRGB());
                    GlStateManager.popMatrix();

                    int sY = mY + moduleHeight;
                    if (opened.contains(module)) {
                        List<PanelProperty> properties = settings.get(module);
                        for (PanelProperty property : properties) {
                            if (!property.isHidden()) {
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
                                    hasLeftClicked = ((ModePanel) property).hasClicked || hasLeftClicked;
                                }

                                sY += settingHeight;
                                settingHeight = 12;
                            }
                        }
                    }

                    mY += moduleHeight + (opened.contains(module) ? sY - (mY + moduleHeight) : 0);
                }

                x += panelWidth + padding;
            }
        }
        GlStateManager.popMatrix();
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

    private boolean handleMiddleClick() {
        if (Mouse.isButtonDown(2) && !hasMiddleClicked) {
            hasMiddleClicked = true;
            return true;
        }
        return false;
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        // written by gemini sorry it had a better way and i agreed
        if (listeningModule != null) {
            if (keyCode == Keyboard.KEY_ESCAPE || keyCode == Keyboard.KEY_DELETE) {
                listeningModule.setKeybind(0);
                ChatUtil.notify("Keybind change", "Cleared keybind of " + listeningModule.getName());
            } else {
                listeningModule.setKeybind(keyCode);
                ChatUtil.notify("Keybind change", "Set keybind of " + listeningModule.getName() + " to " + Keyboard.getKeyName(keyCode));
            }
            listeningModule = null;
            return;
        }

        if (keyCode == 1)
        {
            canClose = true;
        }
    }

    private void close() {
        this.mc.displayGuiScreen((GuiScreen)null);

        if (this.mc.currentScreen == null)
        {
            this.mc.setIngameFocus();
        }
    }

}
