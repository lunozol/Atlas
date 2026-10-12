package io.lunozol.atlas.system.module.modules.client;

import io.github.nevalackin.radbus.Listen;
import io.lunozol.atlas.system.event.events.game.GameLoopEvent;
import io.lunozol.atlas.system.event.events.render.Render2DEvent;
import io.lunozol.atlas.system.module.Module;
import io.lunozol.atlas.system.module.ModuleCategory;
import io.lunozol.atlas.utils.game.ChatUtil;
import io.lunozol.atlas.utils.render.RenderUtils;
import io.lunozol.atlas.utils.render.TestAnimation;
import net.minecraft.client.renderer.GlStateManager;

import java.util.concurrent.ThreadLocalRandom;

public class TestModule extends Module {
    private final TestAnimation animation = new TestAnimation(5000, false);
    private float value = 1;
    private boolean flip = true;

    public TestModule() {
        super("Test", "Module to test stuff", ModuleCategory.CLIENT);
    }

    @Listen
    public void onRender2D(Render2DEvent event) {
        animation.run(flip ? 360 : 0);

        if (animation.isFinished()) {
            flip = !flip;
            animation.reset();
        }

        GlStateManager.pushMatrix();
        GlStateManager.translate(500, 500, 1);
        GlStateManager.rotate(animation.getValue(), 0, 0, 1);
        RenderUtils.rect(0, 0, 150, 150, bg);

        GlStateManager.popMatrix();

        ChatUtil.send(String.valueOf(animation.getProgress()));
    }
}
