package io.lunozol.atlas.system.features.module.modules.client;

import io.lunozol.atlas.system.features.module.Module;
import io.lunozol.atlas.system.features.module.ModuleCategory;
import io.lunozol.atlas.system.features.module.property.properties.BooleanProperty;
import lombok.Getter;

@Getter
public class DebugModule extends Module {
    public final BooleanProperty notifRejects = new BooleanProperty("Notification Rejects", "Controls if it should print notification rejects because of matching info already existing", false);
    public final BooleanProperty notifRegisters =  new BooleanProperty("Notification Registers", "Controls if it should print notification registers", false);
    public final BooleanProperty clickGui = new BooleanProperty("ClickGUI Debug", "Controls if it should print debug from ClickGUI", false);

    public boolean caa() {
        return clickGui.isEnabled();
    }

    public DebugModule() {
        super("Debug", "Debug", ModuleCategory.CLIENT);
        registerSettings(notifRejects, notifRegisters, clickGui);
    }
}
