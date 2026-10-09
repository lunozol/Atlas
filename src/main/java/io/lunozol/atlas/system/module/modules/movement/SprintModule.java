package io.lunozol.atlas.system.module.modules.movement;

import io.github.nevalackin.radbus.Listen;
import io.lunozol.atlas.Atlas;
import io.lunozol.atlas.system.event.events.game.GameLoopEvent;
import io.lunozol.atlas.system.event.events.game.TickEvent;
import io.lunozol.atlas.system.module.Module;
import io.lunozol.atlas.system.module.ModuleCategory;

public class SprintModule extends Module {

    public SprintModule() {
        super("Sprint", "Sprints for you, all the time...", ModuleCategory.MOVEMENT);
        setEnabled(true);
    }

    @Listen
    public void onTick(TickEvent event) {
        mc.gameSettings.keyBindSprint.pressed = true;
    }
}
