package io.lunozol.atlas.system.module.property.properties;

import io.lunozol.atlas.system.module.property.Property;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ModeProperty extends Property {
    private final String name;
    private final String description;
    private String value;
    private String[] values;
    private boolean hidden;

    public ModeProperty(String name, String description, String defaultValue, String... values) {
        this.name = name;
        this.description = description;
        this.value = defaultValue;
        this.values = values;
    }

    @Override
    public void require(boolean requirement) {
        hidden = !requirement;
    }
}
