package io.lunozol.atlas.system.module.modules.client;

import io.lunozol.atlas.system.module.Module;
import io.lunozol.atlas.system.module.ModuleCategory;
import io.lunozol.atlas.system.module.property.properties.BooleanProperty;
import lombok.Getter;

@Getter
public class DebugModule extends Module {
    public final BooleanProperty notifRejects = new BooleanProperty("Notification Rejects", "Controls if it should print notification rejects because of matching info already existing", false);
    public final BooleanProperty notifRegisters =  new BooleanProperty("Notification Registers", "Controls if it should print notification registers", false);
    public BooleanProperty clickGui = new BooleanProperty("ClickGUI Debug", "Controls if it should print debug from clickgui", false);

    public DebugModule() {
        super("Debug", "Debug", ModuleCategory.CLIENT);
        registerSettings(notifRejects, notifRegisters);
    }

    public boolean canDebugClickGUI() {
        return clickGui.isEnabled();
    }
}
