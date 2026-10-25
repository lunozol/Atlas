package io.lunozol.atlas.system.features.module.modules.movement;

import io.github.nevalackin.radbus.Listen;
import io.lunozol.atlas.system.event.events.game.TickEvent;
import io.lunozol.atlas.system.features.module.Module;
import io.lunozol.atlas.system.features.module.ModuleCategory;
import io.lunozol.atlas.system.features.module.property.properties.BooleanProperty;

public class SprintModule extends Module {
    private final BooleanProperty omni = new BooleanProperty("Omni", "Sprint in all directions", false);

    public SprintModule() {
        super("Sprint", "Sprints for you, all the time...", ModuleCategory.MOVEMENT);
        setEnabled(true);
        registerSettings(omni);
    }

    @Listen
    public void onTick(TickEvent event) {
        if (omni.isEnabled()) {
            // i know this doesnt work but ig ill just keep it until i find out how
            mc.thePlayer.setSprinting(true);
        } else {
            mc.gameSettings.keyBindSprint.pressed = true;
        }
    }
}
