package io.lunozol.atlas.system.event.events.game;

public class KeyEvent {
    private int key;

    public KeyEvent(int key) {
        this.key = key;
    }

    public int getKey() {
        return key;
    }
}
