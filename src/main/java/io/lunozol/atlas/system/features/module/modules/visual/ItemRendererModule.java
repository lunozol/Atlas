package io.lunozol.atlas.system.features.module.modules.visual;

import io.github.nevalackin.radbus.Listen;
import io.lunozol.atlas.system.event.events.game.ItemRendererEvent;
import io.lunozol.atlas.system.features.module.Module;
import io.lunozol.atlas.system.features.module.ModuleCategory;
import io.lunozol.atlas.system.features.module.property.properties.ModeProperty;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.MathHelper;
import org.lwjgl.opengl.GL11;

public class ItemRendererModule extends Module {
    private final ModeProperty blockAnim = new ModeProperty("Block Animation", "Lets you set a block animation", "Default", "1.7", "ZeroDay", "Default");

    public ItemRendererModule() {
        super("ItemRenderer", "Changes how your held item renders", ModuleCategory.VISUAL);
        registerSettings(blockAnim);
    }

    @Listen
    public void onItemRenderer(ItemRendererEvent event) {
        if (!blockAnim.getValue().equals("Default")) event.cancel();
        float f1 = mc.thePlayer.getSwingProgress(event.partialTicks);
        float var9 = MathHelper.sin(MathHelper.sqrt_float(f1) * MathHelper.PI);

        switch (blockAnim.getValue()) {
            case "1.7":
                event.renderer.transformFirstPersonItem(0.0f, f1);
                GlStateManager.translate(-0.28,0.285,0);
                GlStateManager.scale(0.85,0.85,0.85);
                event.renderer.doBlockTransformations();
                break;

            case "ZeroDay":
                GL11.glTranslated(-0.04D, 0.16D, 0.0D);
                event.renderer.transformFirstPersonItem(event.equippedProgress / 2.5F, 0.0f);
                GlStateManager.rotate(-var9 * 40.0F / 2.0F, var9 / 2.0F, 1.0F, 4.0F * 0.2f);
                GlStateManager.rotate(-var9 * 30.0F, 1.0F, var9 / 3.0F, -0.0F);
                event.renderer.doBlockTransformations();
                break;
        }
    }
}
