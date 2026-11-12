package io.lunozol.atlas.system.features.module.property.properties;

import io.lunozol.atlas.system.features.module.property.Property;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.util.MathHelper;

@Getter
@Setter
public class NumberProperty extends Property {
    private String name;
    private String description;
    private float value;
    private float min;
    private float max;
    private float step;
    private boolean hidden;

    public NumberProperty(String name, String description, float value, float min, float max, float step) {
        this.name = name;
        this.description = description;
        this.value = value;
        this.min = min;
        this.max = max;
        this.step = step;
    }

    public void setValue(float value) {
        this.value = MathHelper.clamp_float(value, min, max);
        System.out.println(this.value);
    }

    @Override
    public void require(boolean requirement) {
        this.hidden = !requirement;
    }
}
