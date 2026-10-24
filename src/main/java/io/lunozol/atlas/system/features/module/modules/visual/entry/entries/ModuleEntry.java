package io.lunozol.atlas.system.features.module.modules.visual.entry.entries;

import io.lunozol.atlas.system.features.module.Module;
import io.lunozol.atlas.system.features.module.modules.visual.entry.Entry;
import io.lunozol.atlas.utils.render.animation.Animation;
import io.lunozol.atlas.utils.render.animation.Easing;
import lombok.Getter;

@Getter
public class ModuleEntry extends Entry {
    private final Animation animation = new Animation(Easing.EASE_IN_OUT_CUBIC, 200);
    private final Animation outlineAnimation = new Animation(Easing.EASE_IN_OUT_CUBIC, 200);
    private final Module module;
    private boolean done = false;
    private int outLength;

    public ModuleEntry(Module module) {
        this.module = module;
    }

    private void setAnimationsEasing(Easing easing) {
        animation.setEasing(easing);
        outlineAnimation.setEasing(easing);
    }

    public void updateOutlineAnimation(int outLength) {
        this.outLength = outLength;
        outlineAnimation.run(outLength);
    }

    public void update() {
        if (module.isEnabled()) {
            setAnimationsEasing(Easing.EASE_OUT_CUBIC);
        } else {
            setAnimationsEasing(Easing.EASE_IN_CUBIC);
        }

        animation.run(module.isEnabled() ? 1 : 0);

        if (!module.isEnabled() && animation.getValue() <= 0) done = true;
    }
}
