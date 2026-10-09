package io.lunozol.atlas.system.module.modules.client;

import io.github.nevalackin.radbus.Listen;
import io.lunozol.atlas.system.event.events.game.GameLoopEvent;
import io.lunozol.atlas.system.module.Module;
import io.lunozol.atlas.system.module.ModuleCategory;
import io.lunozol.atlas.utils.game.ChatUtil;
import io.lunozol.atlas.utils.render.TestAnimation;

import java.util.concurrent.ThreadLocalRandom;

public class TestModule extends Module {
    private final TestAnimation animation = new TestAnimation(450, false);
    private float value = 1;

    public TestModule() {
        super("Test", "Module to test stuff", ModuleCategory.CLIENT);
    }

    @Listen
    public void onGameLoop(GameLoopEvent event) {
        animation.run(value);

        if (animation.isFinished() || animation.getValue() == value) {
            animation.reset();
        }

        ChatUtil.send(String.valueOf(animation.getProgress()));
    }
}
