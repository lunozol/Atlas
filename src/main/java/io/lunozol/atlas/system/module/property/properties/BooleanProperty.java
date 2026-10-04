package io.lunozol.atlas.system.module.property.properties;

import io.lunozol.atlas.system.module.property.Property;

public class BooleanProperty extends Property {
    private String name;
    private String description;
    private boolean value;

    public BooleanProperty(String name, String description, boolean defaultValue) {
        this.name = name;
        this.description = description;
        this.value = defaultValue;
    }

    public boolean getValue() {
        return value;
    }

    public void setValue(boolean state) {
        this.value = state;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return description;
    }
}
