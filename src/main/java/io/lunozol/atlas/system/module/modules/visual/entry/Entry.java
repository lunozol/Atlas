package io.lunozol.atlas.system.module.modules.visual.entry;

import io.lunozol.atlas.Constants;
import io.lunozol.atlas.system.module.modules.visual.entry.entries.NotificationEntry;
import io.lunozol.atlas.utils.game.ChatUtil;
import net.minecraft.client.gui.GuiChat;

import java.util.ArrayList;
import java.util.List;

public abstract class Entry implements Constants {
    private static List<NotificationEntry> notifEntries = new ArrayList<>();

    public static void registerNotification(String title, String description) {
        NotificationEntry entry = new NotificationEntry(title, description);

        if (notifEntries.stream().noneMatch(e -> e.getDescription().equals(entry.getDescription()))) {
            notifEntries.add(entry);
            if (debug) {
                System.out.println("registered notification " + entry.getDescription());
            }
            return;
        }
        if (debug) {
            ChatUtil.send("rejected notification " + entry.getDescription());
        }
    }

    public static List<NotificationEntry> getNotificationEntries() {
        return notifEntries;
    }

    public static void updateNotifications() {
        notifEntries.removeIf(e -> e.isDone());
    }

}
