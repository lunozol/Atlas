package io.lunozol.atlas.system.features.module.modules.client;

import io.github.nevalackin.radbus.Listen;
import io.lunozol.atlas.system.event.events.render.Render2DEvent;
import io.lunozol.atlas.system.features.module.Module;
import io.lunozol.atlas.system.features.module.ModuleCategory;
import io.lunozol.atlas.utils.game.ChatUtil;
import io.lunozol.atlas.utils.render.RenderUtils;
import io.lunozol.atlas.utils.render.TestAnimation;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.util.ResourceLocation;

import java.awt.*;
import java.awt.image.BufferedImage;

public class TestModule extends Module {
    private final TestAnimation animation = new TestAnimation(5000, false);
    private float value = 1;
    private boolean flip = true;
    private BufferedImage image;
    private ResourceLocation resourceLocation;

    public TestModule() {
        super("Test", "Module to test stuff", ModuleCategory.CLIENT);
    }

    @Listen
    public void onRender2D(Render2DEvent event) {
        if (image == null || resourceLocation == null) {
            resourceLocation = new ResourceLocation("atlas/meimeowl.jpg");
            image = RenderUtils.getImage(resourceLocation);
        }

        animation.run(flip ? 360 : 0);

        if (animation.isFinished()) {
            flip = !flip;
            animation.reset();
        }

        int x = 250;
        int y = 500;

        GlStateManager.pushMatrix();
//        GlStateManager.translate(500, 500, 1);
//        GlStateManager.rotate(animation.getValue(), 0, 0, 1);
//        GlStateManager.translate((x / 2) - (150 / 2), (y / 2) - (150 / 2), 0);

        GlStateManager.translate(175, 575, 0);
        GlStateManager.rotate(animation.getValue(), 0, 0, 1);
        GlStateManager.translate(-175, -575, 0);

        RenderUtils.rect(100, 500, 150, 150, bg);

        GlStateManager.popMatrix();

        RenderUtils.drawImage(resourceLocation, image, x,  y, 0.5f, RenderUtils.withAlpha(Color.white, 190));

        ChatUtil.send(String.valueOf(animation.getProgress()));
    }
}
