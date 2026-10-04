package io.lunozol.atlas.system.event.events.render;

import io.lunozol.atlas.system.event.Event;
import net.minecraft.client.gui.ScaledResolution;

public class Render2DEvent extends Event {
    private final ScaledResolution scaledResolution;
    private final float partialTicks;

    public Render2DEvent(ScaledResolution scaledResolution, float partialTicks) {
        this.scaledResolution = scaledResolution;
        this.partialTicks = partialTicks;
    }

    public ScaledResolution getScaledResolution() {
        return scaledResolution;
    }

    public float getPartialTicks() {
        return partialTicks;
    }
}
