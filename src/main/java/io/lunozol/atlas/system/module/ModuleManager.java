package io.lunozol.atlas.system.module;

import io.github.nevalackin.radbus.Listen;
import io.lunozol.atlas.system.event.events.game.KeyEvent;
import io.lunozol.atlas.system.module.modules.combat.AutoClickerModule;
import io.lunozol.atlas.system.module.modules.movement.SprintModule;
import io.lunozol.atlas.system.module.modules.player.DelayRemover;
import io.lunozol.atlas.system.module.modules.visual.ClickGUIModule;
import io.lunozol.atlas.system.module.modules.visual.WatermarkModule;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ModuleManager {
    private List<Module> modules = new ArrayList<>();

    public void init() {
        modules.add(new WatermarkModule());
        modules.add(new AutoClickerModule());
        modules.add(new ClickGUIModule());
        modules.add(new DelayRemover());
        modules.add(new SprintModule());
    }

    public List<Module> getModules() {
        return modules;
    }

    public List<Module> getEnabledModules() {
        return modules.stream().filter(Module::isEnabled).collect(Collectors.toList());
    }

    public void onKey(int key) {
        for (Module module : modules) {
            if (module.getKeybind() == key) {
                module.toggle();
                System.out.println("toggled " + module.getName() + " to" + module.isEnabled());
            }

        }
    }
}
