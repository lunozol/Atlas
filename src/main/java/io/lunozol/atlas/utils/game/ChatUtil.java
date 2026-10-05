package io.lunozol.atlas.utils.game;

import io.lunozol.atlas.Constants;
import net.minecraft.util.ChatComponentText;

import java.util.regex.Matcher;

public class ChatUtil implements Constants {
    public static void send(String message) {
        if (message == null) return;

        mc.ingameGUI.getChatGUI().printChatMessage(new ChatComponentText(message));
    }
}
