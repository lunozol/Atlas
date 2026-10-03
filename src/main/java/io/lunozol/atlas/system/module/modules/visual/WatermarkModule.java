package io.lunozol.atlas.system.module.modules.visual;

import io.github.nevalackin.radbus.Listen;
import io.lunozol.atlas.Atlas;
import io.lunozol.atlas.system.module.Module;
import io.lunozol.atlas.system.module.ModuleCategory;
import io.github.nevalackin.radbus.Listener;

public class WatermarkModule extends Module {

    public WatermarkModule() {
        super("Watermark", "About the client!", ModuleCategory.VISUAL);
        setEnabled(true);
    }

    @Listen
    public void onA() {
    }
}
