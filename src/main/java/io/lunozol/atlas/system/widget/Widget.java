package io.lunozol.atlas.system.widget;

import io.lunozol.atlas.Atlas;
import io.lunozol.atlas.system.module.ModuleCategory;
import io.lunozol.atlas.system.module.modules.visual.entry.entries.NotificationEntry;
import io.lunozol.atlas.system.module.property.Property;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
public class Widget {
    private final List<Property> settings = new ArrayList<>();
    private final String name;
    private final String description;
    private boolean enabled;
    @Setter
    private int keybind;

    public Widget(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public void setEnabled(boolean state) {
        if (this.enabled != state) {
            this.enabled = state;
            if (state) {
                onEnable();
                Atlas.getInstance().getEventBus().subscribe(this);
                NotificationEntry.registerNotification("Widget Enabled", name + " was enabled!", true);
            } else {
                Atlas.getInstance().getEventBus().unsubscribe(this);
                onDisable();
                NotificationEntry.registerNotification("Widget Disabled", name + " was disabled!", true);
            }
        }
    }

    public void registerSettings(Property... property) {
        settings.addAll(Arrays.asList(property));
    }

    public void toggle() {
        setEnabled(!enabled);
    }

    public void onEnable(){}

    public void onDisable(){}
}
