package io.lunozol.atlas.system.features.module.modules.movement;

import io.github.nevalackin.radbus.Listen;
import io.lunozol.atlas.system.event.events.game.TickEvent;
import io.lunozol.atlas.system.features.module.Module;
import io.lunozol.atlas.system.features.module.ModuleCategory;

public class LowHopModule extends Module {

    public LowHopModule() {
        super("Lowhop", "Modifies motion to make you do a low hop", ModuleCategory.MOVEMENT);
    }

    @Listen
    public void onTick(TickEvent event) {
        if (mc.gameSettings.keyBindJump.pressed) return;

        if (mc.thePlayer.onGround) mc.thePlayer.jump();

        switch (mc.thePlayer.offGroundTicks) {
            case 6:
                mc.thePlayer.motionY -= 0.1f;
        }
    }
}
