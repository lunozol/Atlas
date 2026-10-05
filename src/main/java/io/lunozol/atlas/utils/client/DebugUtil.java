package io.lunozol.atlas.utils.client;

import io.lunozol.atlas.Atlas;
import io.lunozol.atlas.system.module.Module;
import io.lunozol.atlas.system.module.modules.client.DebugModule;
import io.lunozol.atlas.utils.game.ChatUtil;

public class DebugUtil {
    static Module debugModule = Atlas.getInstance().getModuleManager().getModule(DebugModule.class);
    public static void a() {
        ChatUtil.send(debugModule.getDescription());
    }
}
