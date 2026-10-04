package io.lunozol.atlas.system.module.modules.visual;

import io.lunozol.atlas.system.module.Module;
import io.lunozol.atlas.system.module.ModuleCategory;
import io.lunozol.atlas.ui.clickgui.ClickGUIScreen;
import org.lwjgl.input.Keyboard;

public class ClickGUIModule extends Module {

    public ClickGUIModule() {
        super("ClickGUI", "Sets the current Minecraft screen to the ClickGUI Screen", ModuleCategory.VISUAL);
        setKeybind(Keyboard.KEY_RSHIFT);
    }

    @Override
    public void onEnable() {
        mc.displayGuiScreen(new ClickGUIScreen());
        super.toggle();
    }
}
