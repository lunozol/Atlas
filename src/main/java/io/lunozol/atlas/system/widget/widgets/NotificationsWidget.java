package io.lunozol.atlas.system.widget.widgets;

import io.github.nevalackin.radbus.Listen;
import io.lunozol.atlas.system.event.events.render.Render2DEvent;
import io.lunozol.atlas.system.module.modules.visual.entry.Entry;
import io.lunozol.atlas.system.module.modules.visual.entry.entries.NotificationEntry;
import io.lunozol.atlas.system.widget.Widget;
import net.minecraft.client.renderer.GlStateManager;

import java.util.ConcurrentModificationException;

public class NotificationsWidget extends Widget {

    public NotificationsWidget() {
        super("Notifications", "Notifies you for some stuff");
    }

    @Listen
    public void onRender2D(Render2DEvent event) {
        Entry.updateEntries();

        int padding = 4;

        int y = event.getScaledResolution().getScaledHeight() - 27 - padding;
        try {
            for (NotificationEntry entry : NotificationEntry.getNotificationEntries()) {
                float factor = 1 - (float) entry.getAnim().getValue();
                float offsetX = (entry.getWidth() + padding) * factor;
                float offsetY = (entry.getHeight() + padding) * factor;

                int x = event.getScaledResolution().getScaledWidth() - entry.getWidth() - padding;
                GlStateManager.pushMatrix();
                GlStateManager.translate(x + offsetX, y + offsetY, 1);
                entry.draw(0, 0);
                GlStateManager.popMatrix();
                y -= Math.round((entry.getHeight() + padding) - offsetY);
            }
        } catch (ConcurrentModificationException e) {
            System.out.println("deleted while iterating?");
        }
    }

    @Override
    public void onEnable() {
        Entry.getNotificationEntries().clear();
    }
}
