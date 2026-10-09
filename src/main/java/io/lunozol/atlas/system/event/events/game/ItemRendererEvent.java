package io.lunozol.atlas.system.event.events.game;

import io.lunozol.atlas.system.event.Event;
import lombok.AllArgsConstructor;
import lombok.Getter;
import net.minecraft.client.renderer.ItemRenderer;

@AllArgsConstructor
@Getter
public class ItemRendererEvent extends Event {
    private ItemRenderer itemRenderer;
    private float equipProgress;
    private float swingProgress;
}
