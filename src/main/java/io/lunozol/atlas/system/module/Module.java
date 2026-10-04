package io.lunozol.atlas.system.module;

import io.lunozol.atlas.Atlas;
import io.lunozol.atlas.GameAccessor;
import io.lunozol.atlas.system.module.property.Property;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.client.Minecraft;

import java.util.ArrayList;
import java.util.List;

@Getter
public abstract class Module implements GameAccessor {
    private List<Property> settings = new ArrayList<>();
    private String name;
    private String description;
    private ModuleCategory category;
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

    public void toggle() {
        setEnabled(!enabled);
    }

    public void onEnable(){}

    public void onDisable(){}

}
