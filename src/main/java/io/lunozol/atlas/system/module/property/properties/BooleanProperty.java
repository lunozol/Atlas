package io.lunozol.atlas.system.module.property.properties;

import io.lunozol.atlas.system.module.property.Property;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BooleanProperty extends Property {
    private final String name;
    private final String description;
    private boolean enabled;
    private boolean hidden;

    public BooleanProperty(String name, String description, boolean defaultValue) {
        this.name = name;
        this.description = description;
        this.enabled = defaultValue;
    }

    @Override
    public void require(boolean requirement) {
        hidden = !requirement;
    }

    public void toggle() {
        setEnabled(!enabled);
    }
}
