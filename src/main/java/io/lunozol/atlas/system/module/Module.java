package io.lunozol.atlas.system.module;

import io.lunozol.atlas.Atlas;
import net.minecraft.client.Minecraft;

public abstract class Module {
    public static final Minecraft mc = Atlas.getInstance().mc;
    private String name;
    private String description;
    private ModuleCategory category;
    private boolean isEnabled;

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


}
