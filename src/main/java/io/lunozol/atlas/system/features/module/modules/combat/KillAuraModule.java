package io.lunozol.atlas.system.features.module.modules.combat;

import io.github.nevalackin.radbus.Listen;
import io.lunozol.atlas.system.event.events.render.Render2DEvent;
import io.lunozol.atlas.system.features.module.Module;
import io.lunozol.atlas.system.features.module.ModuleCategory;

// TODO finish
public class KillAuraModule extends Module {

    public KillAuraModule() {
        super("KillAura", "Hits people for you!", ModuleCategory.COMBAT);
    }

    @Listen
    public void onGameLoop(Render2DEvent event) {
        if (mc.thePlayer == null) return;
    }
}
