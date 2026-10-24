package io.lunozol.atlas.system.features.module.modules.movement;

import io.github.nevalackin.radbus.Listen;
import io.lunozol.atlas.system.event.events.game.TickEvent;
import io.lunozol.atlas.system.features.module.Module;
import io.lunozol.atlas.system.features.module.ModuleCategory;

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
