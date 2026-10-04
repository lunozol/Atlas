package io.lunozol.atlas.system.event.events.render;

import io.lunozol.atlas.system.event.Event;
import lombok.AllArgsConstructor;
import lombok.Getter;
import net.minecraft.client.gui.ScaledResolution;

@Getter
@AllArgsConstructor
public class Render2DEvent extends Event {
    private final ScaledResolution scaledResolution;
    private final float partialTicks;
}
