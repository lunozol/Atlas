package io.lunozol.atlas.system.module.modules.player;

import io.github.nevalackin.radbus.Listen;
import io.lunozol.atlas.system.event.events.game.GameLoopEvent;
import io.lunozol.atlas.system.module.Module;
import io.lunozol.atlas.system.module.ModuleCategory;
import io.lunozol.atlas.system.module.property.properties.BooleanProperty;

public class DelayRemover extends Module {
    private BooleanProperty right = new BooleanProperty("Remove Right Click Delay", "Removes the delay between right clicking", true);
    private BooleanProperty left = new BooleanProperty("Remove Left Click Delay", "Removes the delay between left clicking", true);
    private BooleanProperty jump = new BooleanProperty("Remove Jump Delay", "Removes the delay between jumping", true);
    public DelayRemover() {
        super("DelayRemover", "Removes delay of certain things", ModuleCategory.PLAYER);
        registerSettings(right, left, jump);
    }

    @Listen
    public void onGameLoop(GameLoopEvent event) {
        if (mc.thePlayer == null) return;

        if (right.isEnabled()) mc.rightClickDelayTimer = 0;
        if (left.isEnabled()) mc.leftClickCounter = 0;
        if (jump.isEnabled()) mc.thePlayer.jumpTicks = 0;
    }
}
