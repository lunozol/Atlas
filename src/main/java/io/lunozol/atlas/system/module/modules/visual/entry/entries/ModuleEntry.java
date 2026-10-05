package io.lunozol.atlas.system.module.modules.visual.entry.entries;

import io.lunozol.atlas.system.module.Module;
import io.lunozol.atlas.system.module.modules.visual.entry.Entry;
import io.lunozol.atlas.utils.render.animation.Animation;
import io.lunozol.atlas.utils.render.animation.Easing;
import lombok.Getter;

@Getter
public class ModuleEntry extends Entry {
    private final Animation animation = new Animation(Easing.EASE_IN_OUT_CUBIC, 250);
    private final Module module;
    private boolean done = false;

    public ModuleEntry(Module module) {
        this.module = module;
    }

    public void update() {
        animation.run(module.isEnabled() ? 1 : 0);

        if (!module.isEnabled() && animation.getValue() <= 0) done = true;
    }
}
