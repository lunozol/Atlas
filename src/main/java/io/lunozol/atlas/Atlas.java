package io.lunozol.atlas;

import io.github.nevalackin.radbus.PubSub;
import io.lunozol.atlas.system.event.Event;
import io.lunozol.atlas.system.module.ModuleManager;
import net.minecraft.client.Minecraft;
import org.lwjgl.opengl.Display;

import java.awt.Color;


public class Atlas implements GameAccessor {
    private static Atlas instance;
    private ModuleManager moduleManager = new ModuleManager();
    private final PubSub<Event> eventBus = PubSub.newInstance(System.err::println);

    public static final String name = "Atlas", version = "October 4th 2026";
    public static Color firstColor = new Color(66, 223, 253); // Ocean Blue
    public static Color secondColor = new Color(161, 251, 169); // some light green color

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

    public PubSub<Event> getEventBus() {
        return eventBus;
    }

    public ModuleManager getModuleManager() {
        return moduleManager;
    }
}
