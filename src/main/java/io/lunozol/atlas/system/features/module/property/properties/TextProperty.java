package io.lunozol.atlas.system.features.module.property.properties;

import io.lunozol.atlas.system.features.module.property.Property;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TextProperty extends Property {
    private final String text;
    private boolean hidden = false;

    public TextProperty(String text) {
        this.text = text;
    }

    @Override
    public void require(boolean requirement) {
        hidden = !requirement;
    }
}
