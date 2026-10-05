package io.lunozol.atlas.system.module.modules.visual;

import io.github.nevalackin.radbus.Listen;
import io.lunozol.atlas.Atlas;
import io.lunozol.atlas.system.event.events.packet.ReceivePacketEvent;
import io.lunozol.atlas.system.module.Module;
import io.lunozol.atlas.system.module.ModuleCategory;
import io.lunozol.atlas.ui.clickgui.ClickGUIScreen;
import net.minecraft.command.server.CommandPublishLocalServer;
import net.minecraft.network.play.server.S2EPacketCloseWindow;
import org.lwjgl.input.Keyboard;

public class ClickGUIModule extends Module {

    public ClickGUIModule() {
        super("ClickGUI", "Sets the current Minecraft screen to the ClickGUI Screen", ModuleCategory.VISUAL);
        setKeybind(Keyboard.KEY_RSHIFT);
    }

    @Override
    public void onEnable() {
        mc.displayGuiScreen(Atlas.getInstance().getClickGUIScreen());
    }

    @Listen
    public void onReceivePacket(ReceivePacketEvent event) {
        if (event.packet instanceof S2EPacketCloseWindow) {
            event.cancel();
        }

        if (mc.currentScreen != Atlas.getInstance().getClickGUIScreen()) {
            super.toggle();
        }
    }

}
