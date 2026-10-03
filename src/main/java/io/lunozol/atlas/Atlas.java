package io.lunozol.atlas;

import io.github.nevalackin.radbus.PubSub;
import io.lunozol.atlas.system.event.Event;
import io.lunozol.atlas.system.module.ModuleManager;
import net.minecraft.client.Minecraft;
import org.lwjgl.opengl.Display;

import java.awt.Color;


public class Atlas {
    private static Atlas instance;
    private ModuleManager moduleManager = new ModuleManager();
    private final PubSub<Event> eventBus = PubSub.newInstance(System.err::println);
    public final Minecraft mc = Minecraft.getMinecraft();

    public static final String name = "Atlas", version = "October 4th 2026";

    public static Color color = Color.green;

    public void init() {
        Display.setTitle(name + " " + version);

        moduleManager.init();
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
