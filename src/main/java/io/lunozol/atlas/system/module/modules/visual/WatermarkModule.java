package io.lunozol.atlas.system.module.modules.visual;

import io.github.nevalackin.radbus.Listen;
import io.lunozol.atlas.Atlas;
import io.lunozol.atlas.system.event.events.render.Render2DEvent;
import io.lunozol.atlas.system.module.Module;
import io.lunozol.atlas.system.module.ModuleCategory;
import io.github.nevalackin.radbus.Listener;
import io.lunozol.atlas.system.module.ModuleManager;
import io.lunozol.atlas.system.module.property.properties.ModeProperty;

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
                mc.fontRendererObj.drawString(Atlas.name + " | " + Atlas.version, 3, 3, Atlas.firstColor.getRGB(), true);
                float y = 14;

                for (Module module : Atlas.getInstance().getModuleManager().getEnabledModules().stream().sorted(Comparator.comparingInt(m -> -mc.fontRendererObj.getStringWidth(m.getName()))).collect(Collectors.toList())) {
                    mc.fontRendererObj.drawString(module.getName(), 3, y, Color.white.getRGB(), true);

                    y+= 9;
                }
                break;
            case "Traditional":
                int width = mc.fontRendererObj.getStringWidth("A");
                mc.fontRendererObj.drawString("A", 2, 2, Atlas.firstColor.getRGB());
                mc.fontRendererObj.drawString("tlas", 2 + width, 2, Color.white.getRGB());
                break;
        }
    }

}
