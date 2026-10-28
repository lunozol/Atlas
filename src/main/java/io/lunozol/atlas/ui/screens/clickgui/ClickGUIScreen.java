package io.lunozol.atlas.ui.screens.clickgui;

import io.lunozol.atlas.Atlas;
import io.lunozol.atlas.system.cache.caches.ImageCache;
import io.lunozol.atlas.system.features.module.Module;
import io.lunozol.atlas.system.features.module.ModuleCategory;
import io.lunozol.atlas.system.features.module.modules.visual.ClickGUIModule;
import io.lunozol.atlas.system.features.module.property.Property;
import io.lunozol.atlas.system.features.module.property.properties.BooleanProperty;
import io.lunozol.atlas.system.features.module.property.properties.ModeProperty;
import io.lunozol.atlas.system.features.module.property.properties.TextProperty;
import io.lunozol.atlas.ui.screens.clickgui.property.PanelProperty;
import io.lunozol.atlas.ui.screens.clickgui.property.properties.BooleanPanel;
import io.lunozol.atlas.ui.screens.clickgui.property.properties.ModePanel;
import io.lunozol.atlas.ui.screens.clickgui.property.properties.TextPanel;
import io.lunozol.atlas.utils.game.ChatUtil;
import io.lunozol.atlas.utils.render.RenderUtils;
import io.lunozol.atlas.utils.render.animation.Animation;
import io.lunozol.atlas.utils.render.animation.Easing;
import lombok.Setter;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import java.awt.*;
import java.io.IOException;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class ClickGUIScreen extends GuiScreen {
    private ClickGUIModule clickGUIModule;
    private ImageCache imageCache;
    private final Animation animation = new Animation(Easing.EASE_OUT_CUBIC, 250);
    private final Map<Module, List<PanelProperty>> settings = new HashMap<>();
    private List<Module> opened = new ArrayList<>();
    private Module listeningModule;
    private int key = 0;
    private boolean hasLeftClicked;
    private boolean hasRightClicked;
    private boolean hasMiddleClicked;
    @Setter
    private boolean canClose = false;
    private int totalWidth = 0;

    @Override
    public void initGui() {
        clickGUIModule = (ClickGUIModule) Atlas.getInstance().getManager().getModule(ClickGUIModule.class);

        if (imageCache == null) {
            imageCache = new ImageCache(new ResourceLocation("atlas/cousin.png"), RenderUtils.getImage(new ResourceLocation("atlas/cousin.png")));
        }

        canClose = false;
        super.initGui();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        if (canClose) {
            animation.setEasing(Easing.EASE_IN_CUBIC);
        } else {
            animation.setEasing(Easing.EASE_OUT_CUBIC);
        }

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
                    } else if (property instanceof TextProperty) {
                        panelList.add(new TextPanel(((TextProperty) property)));
                    }
                }

                settings.put(module, panelList);
            }
        }

        if (!Mouse.isButtonDown(0)) hasLeftClicked = false;
        if (!Mouse.isButtonDown(1)) hasRightClicked = false;
        if (!Mouse.isButtonDown(2)) hasMiddleClicked = false;

        float factor = (float) (1 - animation.getValue());
        float imageScale = 0.2f;
        GlStateManager.pushMatrix();
        GlStateManager.translate(Math.round(width - (imageCache.getBufferedImage().getWidth() * imageScale) - 8), Math.round(height - (imageCache.getBufferedImage().getHeight() * imageScale) - 8) + ((height) * factor), 1);
        RenderUtils.drawImage(imageCache.getResourceLocation(), imageCache.getBufferedImage(), 0, 0, imageScale, Color.white);
        GlStateManager.popMatrix();

        if (clickGUIModule.background.isEnabled())
            RenderUtils.rect(0, 0, width * 2, height * 2, RenderUtils.withAlpha(Atlas.bg, (int) Math.round(100 * animation.getValue())));

        int panelWidth = 135;
        int cHeight = 12;
        int padding = 2;
        int moduleHeight = 14;

        if (totalWidth == 0) {
            for (ModuleCategory category : ModuleCategory.values()) {
                if (!Atlas.getInstance().getManager().getModulesByCategory(category).isEmpty()) {
                    totalWidth += panelWidth + padding;
                }
            }

            totalWidth -= padding;
        }

        int x = Math.round((width / 2) - (totalWidth / 2));
        int y = 50;

        GlStateManager.pushMatrix();
        GlStateManager.translate(x + (totalWidth / 2), -((height / 2) * factor), 0);
        GlStateManager.scale(animation.getValue(), animation.getValue(), 1);
        GlStateManager.translate(-(x + (totalWidth / 2)), -(height * factor), 0);
        for (ModuleCategory category : ModuleCategory.values()) {
            if (!Atlas.getInstance().getManager().getModulesByCategory(category).isEmpty()) {
                RenderUtils.gradientRect(x, y, panelWidth, cHeight, Atlas.bg, Atlas.bg.darker().darker().darker());

                GlStateManager.pushMatrix();
                GlStateManager.translate(x + ((float) panelWidth / 2), y + ((float) cHeight / 4) - 1, 1);
                mc.fontRendererObj.drawStringWithShadow(RenderUtils.normalizeString(category.name()), (float) -mc.fontRendererObj.getStringWidth(RenderUtils.normalizeString(category.name())) / 2, 0, Color.white.getRGB());
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
                    GlStateManager.translate(x + ((float) panelWidth / 2), mY + ((float) cHeight / 4) + padding, 1);
                    GlStateManager.scale(1.25, 1.25, 1);
                    mc.fontRendererObj.drawStringWithShadow(name, (float) -mc.fontRendererObj.getStringWidth(name) / 2, (float) -mc.fontRendererObj.FONT_HEIGHT / 2 + padding, module.isEnabled() ? Atlas.secondColor.getRGB() : Color.WHITE.getRGB());
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

                                if (property instanceof TextPanel) {
                                    property.draw(mouseX, mouseY, x, sY, panelWidth, settingHeight);
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

        if (keyCode == 1) {
            canClose = true;
        }
    }

    private void close() {
        this.mc.displayGuiScreen((GuiScreen) null);

        if (this.mc.currentScreen == null) {
            this.mc.setIngameFocus();
        }
    }
}
