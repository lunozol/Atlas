package io.lunozol.atlas.system.event.events.game;

import io.lunozol.atlas.system.event.Event;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class KeyEvent extends Event {
    private int key;
}
