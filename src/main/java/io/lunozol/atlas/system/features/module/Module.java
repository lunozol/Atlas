package io.lunozol.atlas.system.features.module;

import io.lunozol.atlas.Atlas;
import io.lunozol.atlas.Constants;
import io.lunozol.atlas.system.features.module.modules.visual.entry.entries.NotificationEntry;
import io.lunozol.atlas.system.features.module.property.Property;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
public abstract class Module implements Constants {
    private final List<Property> settings = new ArrayList<>();
    private final String name;
    private final String description;
    private final ModuleCategory category;
    private boolean enabled;
    @Setter
    private int keybind;
    @Setter
    private String suffix;

    public Module(String name, String description, ModuleCategory category) {
        this.name = name;
        this.description = description;
        this.category = category;
    }

    public void setEnabled(boolean state) {
        if (this.enabled != state) {
            this.enabled = state;
            if (state) {
                onEnable();
                if (this.isEnabled()) { // you might be confused why i check this twice, after onEnable() is ran the module might be disabled by the logic therefore we can't be sure it would still be enabled! Thanks clumsy <3!
                    Atlas.getInstance().getEventBus().subscribe(this);
                    NotificationEntry.registerNotification("Module Enabled", name + " was enabled!", true);
                }
            } else {
                onDisable();
                if (!this.isEnabled()) {
                    Atlas.getInstance().getEventBus().unsubscribe(this);
                    NotificationEntry.registerNotification("Module Disabled", name + " was disabled!", true);
                }
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
