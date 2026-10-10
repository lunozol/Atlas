package io.lunozol.atlas.system;

import io.lunozol.atlas.Constants;
import io.lunozol.atlas.system.module.Module;
import io.lunozol.atlas.system.module.ModuleCategory;
import io.lunozol.atlas.system.module.modules.client.DebugModule;
import io.lunozol.atlas.system.module.modules.combat.AutoClickerModule;
import io.lunozol.atlas.system.module.modules.combat.KillAuraModule;
import io.lunozol.atlas.system.module.modules.movement.SprintModule;
import io.lunozol.atlas.system.module.modules.player.DelayRemoverModule;
import io.lunozol.atlas.system.module.modules.visual.ClickGUIModule;
import io.lunozol.atlas.system.module.modules.visual.InterfaceModule;
import io.lunozol.atlas.system.module.modules.visual.ItemRendererModule;
import io.lunozol.atlas.system.module.modules.client.TestModule;
import io.lunozol.atlas.system.widget.Widget;
import io.lunozol.atlas.system.widget.widgets.NotificationsWidget;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class Manager implements Constants {
    private final List<Module> modules = new ArrayList<>();
    private final List<Widget> widgets = new ArrayList<>();

    public void init() {
        // modules
        modules.add(new InterfaceModule());
        modules.add(new AutoClickerModule());
        modules.add(new ClickGUIModule());
        modules.add(new DelayRemoverModule());
        modules.add(new SprintModule());
        modules.add(new KillAuraModule());
        modules.add(new ItemRendererModule());

        if (debug) {
            modules.add(new DebugModule());
            modules.add(new TestModule());
        }

        // widgets
        widgets.add(new NotificationsWidget());
    }

    public List<Module> getEnabledModules() {
        return modules.stream().filter(Module::isEnabled).collect(Collectors.toList());
    }
    public List<Widget> getEnabledWidgets() {
        return widgets.stream().filter(Widget::isEnabled).collect(Collectors.toList());
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
        System.out.println("Failed to get module from Manager");
        return null;
    }

    public Widget getWidget(Class widget) {
        for (Widget widgt : widgets) {
            if (widgt.getClass().equals(widget)) {
                return widgt;
            }
        }
        System.out.println("Failed to get widget from Manager");
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
