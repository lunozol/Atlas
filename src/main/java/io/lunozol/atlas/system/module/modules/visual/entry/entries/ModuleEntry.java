package io.lunozol.atlas.system.module.modules.visual.entry.entries;

import io.lunozol.atlas.system.module.Module;
import io.lunozol.atlas.system.module.modules.visual.entry.Entry;
import io.lunozol.atlas.utils.render.animation.Animation;
import io.lunozol.atlas.utils.render.animation.Easing;
import lombok.Getter;

@Getter
public class ModuleEntry extends Entry {
    private final Animation animation = new Animation(Easing.EASE_IN_OUT_CUBIC, 200);
    private final Module module;
    private boolean done = false;

    public ModuleEntry(Module module) {
        this.module = module;
    }

    public void update() {
        if (module.isEnabled()) {
            animation.setEasing(Easing.EASE_OUT_CUBIC);
        } else {
            animation.setEasing(Easing.EASE_IN_CUBIC);
        }

        animation.run(module.isEnabled() ? 1 : 0);

        if (!module.isEnabled() && animation.getValue() <= 0) done = true;
    }
}
