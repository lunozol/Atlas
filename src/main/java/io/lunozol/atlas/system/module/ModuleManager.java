package io.lunozol.atlas.system.module;

import io.lunozol.atlas.system.module.modules.combat.AutoClickerModule;
import io.lunozol.atlas.system.module.modules.visual.WatermarkModule;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ModuleManager {
    private List<Module> modules = new ArrayList<>();

    public void init() {
        modules.add(new WatermarkModule());
        modules.add(new AutoClickerModule());
    }

    public List<Module> getModules() {
        return modules;
    }

    public List<Module> getEnabledModules() {
        return modules.stream().filter(Module::isEnabled).collect(Collectors.toList());
    }
}
