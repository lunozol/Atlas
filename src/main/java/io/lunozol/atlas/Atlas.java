package io.lunozol.atlas;

import io.lunozol.atlas.system.module.ModuleManager;
import net.minecraft.client.Minecraft;
import org.lwjgl.opengl.Display;

import java.awt.*;

public class Atlas {
    public static Atlas instance;
    public ModuleManager moduleManager = new ModuleManager();
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
}
