package io.lunozol.atlas.system.event.events.render;

import io.lunozol.atlas.system.event.Event;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class RenderGuiChatEvent extends Event {
    private int mouseX;
    private int mouseY;
    private float partialTicks;
}
