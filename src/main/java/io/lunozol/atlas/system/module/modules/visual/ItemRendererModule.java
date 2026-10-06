package io.lunozol.atlas.system.module.modules.visual;

import io.github.nevalackin.radbus.Listen;
import io.lunozol.atlas.system.event.events.game.ItemRendererEvent;
import io.lunozol.atlas.system.module.Module;
import io.lunozol.atlas.system.module.ModuleCategory;
import io.lunozol.atlas.system.module.property.properties.ModeProperty;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.ItemRenderer;

public class ItemRendererModule extends Module {
    private final ModeProperty blockAnim = new ModeProperty("Block Animation", "Lets you set a block animation", "Default", "1.7", "Default");

    public ItemRendererModule() {
        super("ItemRenderer", "Changes how your held item renders", ModuleCategory.VISUAL);
        registerSettings(blockAnim);
    }

    @Listen
    public void onItemRenderer(ItemRendererEvent event) {
        if (!blockAnim.getValue().equals("Default")) event.cancel();

        switch (blockAnim.getValue()) {
            case "1.7":
                event.getItemRenderer().transformFirstPersonItem(0.0f, event.getSwingProgress());
                GlStateManager.translate(-0.28,0.285,0);
                GlStateManager.scale(0.85,0.85,0.85);
                event.getItemRenderer().doBlockTransformations();
                break;
        }
    }
}
