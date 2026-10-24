package io.lunozol.atlas.system.features.module.property.properties;

import io.lunozol.atlas.system.features.module.property.Property;
import lombok.Getter;
import lombok.Setter;

// not sure about this name ill make this some day
@Getter
@Setter
public class SliderProperty extends Property {
    private String name;
    private String description;
    private float value;
    private float min;
    private float max;
    private float step;
    private boolean hidden;

    public SliderProperty(String name, String description, float value, float min, float max, float step) {
        this.name = name;
        this.description = description;
        this.value = value;
        this.min = min;
        this.max = max;
        this.step = step;
    }

    @Override
    public void require(boolean requirement) {
        this.hidden = !requirement;
    }
}
