package io.lunozol.atlas.system.features.module.modules.visual;

import io.github.nevalackin.radbus.Listen;
import io.lunozol.atlas.Atlas;
import io.lunozol.atlas.system.event.events.packet.ReceivePacketEvent;
import io.lunozol.atlas.system.features.module.Module;
import io.lunozol.atlas.system.features.module.ModuleCategory;
import io.lunozol.atlas.system.features.module.property.properties.BooleanProperty;
import net.minecraft.network.play.server.S2EPacketCloseWindow;
import org.lwjgl.input.Keyboard;

public class ClickGUIModule extends Module {
    private final BooleanProperty cancelPackets = new BooleanProperty("Cancel Screen Packets", "Makes so it doesnt send a screen open/close packet when the clickgui is opened/closed", false);
    public final BooleanProperty background = new BooleanProperty("Background", "Enable/Disable ClickGuiScreen background", false);

    public ClickGUIModule() {
        super("ClickGUI", "Sets the current Minecraft screen to the ClickGUI Screen", ModuleCategory.VISUAL);
        setKeybind(Keyboard.KEY_RSHIFT);
        registerSettings(cancelPackets, background);
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

//    @Listen
//    public void onSendPacket(SendPacketEvent event) {
//        if (cancelPackets.isEnabled()) {
//            if (event.packet instanceof C)
//        }
//    }

}
