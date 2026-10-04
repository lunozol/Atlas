package io.lunozol.atlas.system.module.property.properties;

import io.lunozol.atlas.system.module.property.Property;

public class ModeProperty extends Property {
    private String name;
    private String description;
    private String value;
    private String[] values;

    public ModeProperty(String name, String description, String defaultValue, String... values) {
        this.name = name;
        this.description = description;
        this.value = defaultValue;
        this.values = values;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    public String[] getValues() {
        return values;
    }


}
