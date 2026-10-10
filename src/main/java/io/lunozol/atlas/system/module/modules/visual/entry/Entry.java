package io.lunozol.atlas.system.module.modules.visual.entry;

import io.lunozol.atlas.Atlas;
import io.lunozol.atlas.Constants;
import io.lunozol.atlas.system.module.Module;
import io.lunozol.atlas.system.module.modules.visual.entry.entries.ModuleEntry;
import io.lunozol.atlas.system.module.modules.visual.entry.entries.NotificationEntry;
import io.lunozol.atlas.utils.game.ChatUtil;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public abstract class Entry implements Constants {
    @Getter
    private static final List<NotificationEntry> notificationEntries = new ArrayList<>();
    @Getter
    private static final List<ModuleEntry> moduleEntries = new ArrayList<>();

    public static void registerNotification(String title, String description, boolean forceAdd) {
        NotificationEntry entry = new NotificationEntry(title, description);

        if (!forceAdd) {
            if (notificationEntries.stream().noneMatch(e -> e.getDescription().equals(entry.getDescription()))) {
                notificationEntries.add(entry);
                if (debug) {
                    System.out.println("registered notification " + entry.getDescription());
                }
                return;
            }
            if (debug) {
                ChatUtil.send("rejected notification " + entry.getDescription());
            }
        } else {
            notificationEntries.add(entry);
        }
    }

    public static void updateEntries() {
        for (Module module : Atlas.getInstance().getManager().getEnabledModules()) {
            if (moduleEntries.stream().noneMatch(e -> e.getModule().equals(module))) {
                moduleEntries.add(new ModuleEntry(module));
            }
        }

        for (ModuleEntry entry : moduleEntries) {
            entry.update();
        }

        moduleEntries.removeIf(ModuleEntry::isDone);
        notificationEntries.removeIf(NotificationEntry::isDone);
    }

}
