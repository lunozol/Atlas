package io.lunozol.atlas.system.event.events.game;

import io.lunozol.atlas.system.event.Event;
import lombok.AllArgsConstructor;
import lombok.Getter;
import net.minecraft.client.renderer.ItemRenderer;

@AllArgsConstructor
public class ItemRendererEvent extends Event {
    public final ItemRenderer renderer;
    public final float partialTicks;
    public float equippedProgress, swingProgress, prevEquippedProgress;
}
