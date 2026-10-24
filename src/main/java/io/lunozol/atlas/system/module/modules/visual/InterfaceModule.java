package io.lunozol.atlas.system.module.modules.visual;

import io.github.nevalackin.radbus.Listen;
import io.lunozol.atlas.Atlas;
import io.lunozol.atlas.system.event.events.render.Render2DEvent;
import io.lunozol.atlas.system.module.Module;
import io.lunozol.atlas.system.module.ModuleCategory;
import io.lunozol.atlas.system.module.modules.visual.entry.Entry;
import io.lunozol.atlas.system.module.modules.visual.entry.entries.ModuleEntry;
import io.lunozol.atlas.system.module.property.properties.BooleanProperty;
import io.lunozol.atlas.system.module.property.properties.ModeProperty;
import io.lunozol.atlas.utils.render.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.EnumChatFormatting;
import org.lwjgl.input.Keyboard;

import java.awt.*;
import java.util.Comparator;
import java.util.stream.Collectors;

public class InterfaceModule extends Module {
    private final ModeProperty mode = new ModeProperty("Mode", "Modes!", "Classic", "Classic", "Traditional");
    private final BooleanProperty whiteText = new BooleanProperty("White colored modules", "Uses white text instead of gradient colored text", false);
    private final BooleanProperty info = new BooleanProperty("Information", "Shows information in the corner", true);

    public InterfaceModule() {
        super("Interface", "About the client!", ModuleCategory.VISUAL);
        registerSettings(mode, whiteText);
        setEnabled(true);
    }

    @Listen
    public void onRender2D(Render2DEvent event) {
        Entry.updateEntries();
        whiteText.require(mode.getValue().equals("Classic"));
        int infoColor = 0;

        switch (mode.getValue()) {
            case "Classic":
                final int color = RenderUtils.wave(Atlas.firstColor.getRGB(), Atlas.secondColor.getRGB(), System.currentTimeMillis(), 0);
                GlStateManager.pushMatrix();
                GlStateManager.translate(0,0,1);
                GlStateManager.scale(2,2,1);
                mc.fontRendererObj.drawString(Atlas.name, 2, 2, color, true);

                GlStateManager.popMatrix();
                final int color2 = RenderUtils.wave(Atlas.firstColor.getRGB(), Atlas.secondColor.getRGB(), System.currentTimeMillis(), 1);
                GlStateManager.pushMatrix();
                GlStateManager.scale(1.5,1.5,1);
                mc.fontRendererObj.drawString(Atlas.version, 2, 14, color2, true);
                GlStateManager.popMatrix();

                int index = 2;
                float y = 36;
                for (ModuleEntry entry : Entry.getModuleEntries().stream().sorted(Comparator.comparingInt(e -> -mc.fontRendererObj.getStringWidth(e.getModule().getName() + (e.getModule().getSuffix() == null ? "" : " " + EnumChatFormatting.GRAY + e.getModule().getSuffix())))).collect(Collectors.toList())) {
                    int width = mc.fontRendererObj.getStringWidth(entry.getModule().getName() + (entry.getModule().getSuffix() == null ? "" : " " + EnumChatFormatting.GRAY + entry.getModule().getSuffix()));
                    float factor = (float) (1 - entry.getAnimation().getValue());
                    float offsetX = (width - padding + 3) * factor;
                    int color3 = RenderUtils.wave(Atlas.firstColor.getRGB(), Atlas.secondColor.getRGB(), System.currentTimeMillis(), index);
                    if (whiteText.isEnabled()) color3 = Color.white.getRGB();

                    mc.fontRendererObj.drawString(entry.getModule().getName() + (entry.getModule().getSuffix() == null ? "" : " " + EnumChatFormatting.GRAY + entry.getModule().getSuffix()), 3 - offsetX, y, color3, true);
                    y+= Math.round(9 * entry.getAnimation().getValue());
                    index++;
                }

                infoColor = RenderUtils.wave(Atlas.firstColor.getRGB(), Atlas.secondColor.getRGB(), System.currentTimeMillis(), index + 1);
                break;
            case "Traditional":
                int xPadding = 2;
                int yPadding = 1;
                GlStateManager.pushMatrix();
                GlStateManager.translate(4, 4, 1);
                GlStateManager.scale(1.5, 1.5, 1);
                int tWidth = mc.fontRendererObj.getStringWidth("A");
                mc.fontRendererObj.drawString("A", 0, 0, Atlas.waveColor, true);
                mc.fontRendererObj.drawString("tlas", tWidth, 0, Color.white.getRGB(), true);
                int fullWidth = (int) Math.round((tWidth * 1.5) + (mc.fontRendererObj.getStringWidth("tlas") * 1.5) + mc.fontRendererObj.getStringWidth(" "));
                GlStateManager.popMatrix();

                mc.fontRendererObj.drawStringWithShadow(version.substring(0, version.length() - 4), 4 + fullWidth, 8, Color.white.getRGB());

                int i = 2;
                int yDraw = 0;
                for (ModuleEntry entry : Entry.getModuleEntries().stream().sorted(Comparator.comparingInt(e -> -mc.fontRendererObj.getStringWidth(e.getModule().getName() + (e.getModule().getSuffix() == null ? "" : " " + EnumChatFormatting.GRAY + e.getModule().getSuffix())))).collect(Collectors.toList())) {
                    int width = mc.fontRendererObj.getStringWidth(entry.getModule().getName() + (entry.getModule().getSuffix() == null ? "" : " " + EnumChatFormatting.GRAY + entry.getModule().getSuffix()));
                    int sideWidth = 1;
                    int rectWidth = width + (xPadding * 2) + sideWidth;
                    int rectHeight = mc.fontRendererObj.FONT_HEIGHT + (yPadding * 2);
                    float factor = (float) (1 - entry.getAnimation().getValue());
                    float offsetX = (rectWidth + padding) * factor;
                    float offsetY = (rectHeight) * factor;
                    int x = event.getScaledResolution().getScaledWidth() - rectWidth + Math.round(offsetX);
                    int color3 = RenderUtils.wave(Atlas.firstColor.getRGB(), Atlas.secondColor.getRGB(), System.currentTimeMillis(), i);

                    GlStateManager.pushMatrix();
                    GlStateManager.translate(x, yDraw, 1);
                    RenderUtils.rect(0, 0, rectWidth, (int) Math.round(rectHeight * entry.getAnimation().getValue()), bg);
                    RenderUtils.rect(0 + rectWidth - sideWidth, 0, sideWidth, rectHeight, new Color(color3));
                    mc.fontRendererObj.drawString(entry.getModule().getName() + (entry.getModule().getSuffix() == null ? "" : " " + EnumChatFormatting.GRAY + entry.getModule().getSuffix()), xPadding, yPadding, color3, true);
                    GlStateManager.popMatrix();

                    yDraw = (int) Math.round((yDraw + rectHeight * entry.getAnimation().getValue()));
                    i++;
                }

                infoColor = RenderUtils.wave(Atlas.firstColor.getRGB(), Atlas.secondColor.getRGB(), System.currentTimeMillis(), i + 1);
                break;
        }

        if (info.isEnabled()) {
            mc.fontRendererObj.drawStringWithShadow("FPS: " + Minecraft.getDebugFPS(), 2, event.getScaledResolution().getScaledHeight() - 10, infoColor);
        }
    }


}
