package io.lunozol.atlas.system.module;

import io.lunozol.atlas.Atlas;
import io.lunozol.atlas.system.module.property.Property;
import net.minecraft.client.Minecraft;

import java.util.ArrayList;
import java.util.List;

public abstract class Module {
    private List<Property> settings = new ArrayList<>();
    public static final Minecraft mc = Atlas.getInstance().mc;
    private String name;
    private String description;
    private ModuleCategory category;
    private boolean isEnabled;
    private int key;
    private String suffix;

    public Module(String name, String description, ModuleCategory category) {
        this.name = name;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean state) {
        if (this.isEnabled != state) {
            this.isEnabled = state;
            if (state) {
                onEnable();
                Atlas.getInstance().getEventBus().subscribe(this);
            } else {
                Atlas.getInstance().getEventBus().unsubscribe(this);
                onDisable();
            }
        }
    }

    public void registerSettings(Property property) {
        settings.add(property);
    }

    public List<Property> getSettings() {
        return settings;
    }

    public void toggle() {
        setEnabled(!isEnabled);
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public ModuleCategory getCategory() {
        return category;
    }

    public void onEnable(){}

    public void onDisable(){}

    public void setKeybind(int key) {
        this.key = key;
    }

    public int getKeybind() {
        return key;
    }

    public String setSuffix(String suffix) {
        return this.suffix = suffix;
    }

    public String getSuffix() {
        return this.suffix;
    }
}
