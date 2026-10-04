package io.lunozol.atlas.system.event.events.game;

import io.lunozol.atlas.system.event.Event;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class MovementInputEvent extends Event {
    private float forward, strafe;
    private boolean jump, sneak;
}
