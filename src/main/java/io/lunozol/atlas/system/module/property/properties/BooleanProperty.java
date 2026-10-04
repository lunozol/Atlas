package io.lunozol.atlas.system.module.property.properties;

import io.lunozol.atlas.system.module.property.Property;
import lombok.Setter;

public class BooleanProperty extends Property {
    private String name;
    private String description;
    @Setter
    private boolean value;

    public BooleanProperty(String name, String description, boolean defaultValue) {
        this.name = name;
        this.description = description;
        this.value = defaultValue;
    }

    public boolean getValue() {
        return value;
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
