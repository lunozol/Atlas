package io.lunozol.atlas.utils.client;

import io.lunozol.atlas.Atlas;
import io.lunozol.atlas.Constants;
import io.lunozol.atlas.system.module.Module;
import io.lunozol.atlas.system.module.modules.client.DebugModule;

public class DebugUtil implements Constants {
    static Module module = Atlas.getInstance().getModuleManager().getModule(DebugModule.class);
}
