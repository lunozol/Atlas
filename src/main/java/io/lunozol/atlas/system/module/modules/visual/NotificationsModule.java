package io.lunozol.atlas.system.module.modules.visual;

import io.github.nevalackin.radbus.Listen;
import io.lunozol.atlas.system.event.events.render.Render2DEvent;
import io.lunozol.atlas.system.module.Module;
import io.lunozol.atlas.system.module.ModuleCategory;
import io.lunozol.atlas.system.module.modules.visual.entry.entries.NotificationEntry;

public class NotificationsModule extends Module {

    public NotificationsModule() {
        super("Notifications", "Notifies you!", ModuleCategory.VISUAL);
    }

    @Listen
    public void onRender2D(Render2DEvent event) {
        NotificationEntry.updateNotifications();

        int height = 35;
        int width = 120;
        int padding = 4;


        int y = event.getScaledResolution().getScaledHeight() - height - padding;
        for (NotificationEntry entry : NotificationEntry.getNotificationEntries()) {
            float factor = 1 - (float) entry.getAnim().getValue();
            float offsetX = (width + padding) * factor;
            float offsetY = (height + padding) * factor;

            int x = event.getScaledResolution().getScaledWidth() - width - padding;
            entry.draw(Math.round(x + offsetX), (int) Math.round(y + offsetY), width, height);
            y += Math.round((height + padding) + offsetY);
        }
    }
}