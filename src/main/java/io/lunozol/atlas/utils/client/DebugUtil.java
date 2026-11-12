package io.lunozol.atlas.utils.client;

import io.lunozol.atlas.Atlas;
import io.lunozol.atlas.Constants;
import io.lunozol.atlas.system.features.module.Module;
import io.lunozol.atlas.system.features.module.modules.client.DebugModule;

public class DebugUtil implements Constants {
    public static boolean canDebugNotifRegisters() {
        boolean bool = Atlas.getInstance().getManager().getModule(DebugModule.class).notifRegisters.isEnabled();

        return bool;
    }

    public static boolean canDebugNotifRejects() {
        boolean bool = Atlas.getInstance().getManager().getModule(DebugModule.class).notifRejects.isEnabled();

        return bool;
    }

    public static boolean canDebugClickGUI() {
        boolean bool = Atlas.getInstance().getManager().getModule(DebugModule.class).clickGui.isEnabled();

        return bool;
    }
}