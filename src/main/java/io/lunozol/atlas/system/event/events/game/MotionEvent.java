package io.lunozol.atlas.system.event.events.game;

import io.lunozol.atlas.system.event.Event;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class MotionEvent extends Event {
    public double x, y, z;
    public float yaw, pitch;
    public boolean onGround;
}
