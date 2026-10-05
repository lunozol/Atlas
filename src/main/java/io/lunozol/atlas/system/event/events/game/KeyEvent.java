package io.lunozol.atlas.system.event.events.game;

import io.lunozol.atlas.system.event.Event;

public class KeyEvent extends Event {
    private int key;

    public KeyEvent(int key) {
        this.key = key;
    }

    public int getKey() {
        return key;
    }
}
