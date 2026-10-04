package io.lunozol.atlas;

import io.github.nevalackin.radbus.PubSub;
import io.lunozol.atlas.system.event.Event;
import io.lunozol.atlas.system.module.ModuleManager;
import io.lunozol.atlas.utils.render.RenderUtils;
import org.lwjgl.opengl.Display;

import java.awt.Color;


public class Atlas implements Constants {
    private static Atlas instance;
    private ModuleManager moduleManager = new ModuleManager();
    private final PubSub<Event> eventBus = PubSub.newInstance(System.err::println);

    public static int waveColor = RenderUtils.wave(firstColor.getRGB(), secondColor.getRGB(), System.currentTimeMillis(), 0);

    public void init() {
        Display.setTitle(name + " " + version);

        moduleManager.init();
//        eventBus.subscribe(moduleManager); dont need this anymore but ill keep it here
        System.out.println("Initialised Atlas " + moduleManager.getModules().size() + " modules loaded");
    }

    public static Atlas getInstance() {
        if (instance == null) instance = new Atlas();

        return instance;
    }

    public static void updateColor() {
        waveColor = RenderUtils.wave(firstColor.getRGB(), secondColor.getRGB(), System.currentTimeMillis(), 0);
    }

    public PubSub<Event> getEventBus() {
        return eventBus;
    }

    public ModuleManager getModuleManager() {
        return moduleManager;
    }
}
