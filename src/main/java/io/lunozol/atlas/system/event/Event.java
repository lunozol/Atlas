package io.lunozol.atlas.system.event;

public abstract class Event {
    private boolean cancelled;

    public boolean isCancelled() {
        return cancelled;
    }

    public void setCancelled(boolean state) {
        cancelled = state;
    }

    public void cancel() {
        cancelled = true;
    }
}
