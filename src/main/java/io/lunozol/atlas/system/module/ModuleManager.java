package io.lunozol.atlas.system.module;

import io.lunozol.atlas.Constants;
import io.lunozol.atlas.system.module.modules.client.DebugModule;
import io.lunozol.atlas.system.module.modules.combat.AutoClickerModule;
import io.lunozol.atlas.system.module.modules.combat.KillAuraModule;
import io.lunozol.atlas.system.module.modules.movement.SprintModule;
import io.lunozol.atlas.system.module.modules.player.DelayRemoverModule;
import io.lunozol.atlas.system.module.modules.visual.ClickGUIModule;
import io.lunozol.atlas.system.module.modules.visual.InterfaceModule;
import io.lunozol.atlas.system.module.modules.visual.ItemRendererModule;
import io.lunozol.atlas.system.module.modules.visual.NotificationsModule;
import io.lunozol.atlas.system.module.modules.client.TestModule;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ModuleManager implements Constants {
    private List<Module> modules = new ArrayList<>();

    public void init() {
        modules.add(new InterfaceModule());
        modules.add(new AutoClickerModule());
        modules.add(new ClickGUIModule());
        modules.add(new DelayRemoverModule());
        modules.add(new SprintModule());
        modules.add(new KillAuraModule());
        modules.add(new NotificationsModule());
        modules.add(new ItemRendererModule());

        if (debug) {
            modules.add(new DebugModule());
            modules.add(new TestModule());
        }
    }

    public List<Module> getModules() {
        return modules;
    }

    public List<Module> getEnabledModules() {
        return modules.stream().filter(Module::isEnabled).collect(Collectors.toList());
    }

    public List<Module> getModulesByCategory(ModuleCategory category) {
        return modules.stream().filter(m -> m.getCategory().equals(category)).collect(Collectors.toList());
    }

    public Module getModule(Class module) {
        for (Module mod : modules) {
            if (mod.getClass().equals(module)) {
                return mod;
            }
        }
        System.out.println("failed");
        return null;
    }

    public void onKey(int key) {
        for (Module module : modules) {
            if (module.getKeybind() == key) {
                module.toggle();
            }
        }
    }
}
