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
        isEnabled = state;
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
}
