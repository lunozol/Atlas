package io.lunozol.atlas.system.module.modules.visual;

import io.github.nevalackin.radbus.Listen;
import io.lunozol.atlas.Atlas;
import io.lunozol.atlas.system.event.events.render.Render2DEvent;
import io.lunozol.atlas.system.module.Module;
import io.lunozol.atlas.system.module.ModuleCategory;
import io.lunozol.atlas.system.module.property.properties.ModeProperty;
import io.lunozol.atlas.utils.render.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.EnumChatFormatting;

import java.awt.*;
import java.util.Comparator;
import java.util.stream.Collectors;

public class WatermarkModule extends Module {
    private ModeProperty mode = new ModeProperty("Mode", "Modes!", "Classic", "Classic", "Traditional");

    public WatermarkModule() {
        super("Watermark", "About the client!", ModuleCategory.VISUAL);
        registerSettings(mode);
        setEnabled(true);
    }

    @Listen
    public void onRender2D(Render2DEvent event) {
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
                float y = 36;

                for (Module module : Atlas.getInstance().getModuleManager().getEnabledModules().stream().sorted(Comparator.comparingInt(m -> -mc.fontRendererObj.getStringWidth(m.getName() + (m.getSuffix() == null ? "" : " " + EnumChatFormatting.GRAY + m.getSuffix())))).collect(Collectors.toList())) {
                    mc.fontRendererObj.drawString(module.getName() + (module.getSuffix() == null ? "" : " " + EnumChatFormatting.GRAY + module.getSuffix()), 3, y, Color.white.getRGB(), true);

                    y+= 9;
                }
                break;
            case "Traditional":
                int width = mc.fontRendererObj.getStringWidth("A");
                mc.fontRendererObj.drawString("A", 2, 2, Atlas.firstColor.getRGB(), true);
                mc.fontRendererObj.drawString("tlas", 2 + width, 2, Color.white.getRGB(), true);
                break;
        }

        setSuffix(String.valueOf(Minecraft.getDebugFPS()));
    }


}
