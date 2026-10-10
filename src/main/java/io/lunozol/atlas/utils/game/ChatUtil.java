package io.lunozol.atlas.utils.game;

import io.lunozol.atlas.Atlas;
import io.lunozol.atlas.Constants;
import io.lunozol.atlas.system.module.modules.visual.entry.Entry;
import io.lunozol.atlas.system.widget.widgets.NotificationsWidget;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

public class ChatUtil implements Constants {
    public static void send(String message) {
        if (message == null) return;

        mc.ingameGUI.getChatGUI().printChatMessage(new ChatComponentText(chatName + EnumChatFormatting.WHITE + ": " + message));
    }

    public static void notify(String title, String message) {
        boolean enabled = Atlas.getInstance().getManager().getWidget(NotificationsWidget.class).isEnabled();
        if (enabled) {
            Entry.registerNotification(title, message, false);
        } else {
            send(message);
        }
    }
}
