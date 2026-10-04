package io.lunozol.atlas.system.module.property.properties;

import io.lunozol.atlas.system.module.property.Property;
import lombok.Getter;
import lombok.Setter;

@Getter
public class ModeProperty extends Property {
    private String name;
    private String description;
    @Setter
    private String value;
    @Setter
    private String[] values;

    public ModeProperty(String name, String description, String defaultValue, String... values) {
        this.name = name;
        this.description = description;
        this.value = defaultValue;
        this.values = values;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getDescription() {
        return this.description;
    }


}
