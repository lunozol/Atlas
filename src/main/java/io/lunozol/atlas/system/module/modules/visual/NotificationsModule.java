package io.lunozol.atlas.system.module.modules.visual;

import io.github.nevalackin.radbus.Listen;
import io.lunozol.atlas.system.event.events.render.Render2DEvent;
import io.lunozol.atlas.system.module.Module;
import io.lunozol.atlas.system.module.ModuleCategory;
import io.lunozol.atlas.system.module.modules.visual.entry.entries.NotificationEntry;
import io.lunozol.atlas.utils.client.DebugUtil;
import io.lunozol.atlas.utils.game.ChatUtil;
import net.minecraft.client.renderer.GlStateManager;

public class NotificationsModule extends Module {

    public NotificationsModule() {
        super("Notifications", "Notifies you!", ModuleCategory.VISUAL);
    }

    @Listen
    public void onRender2D(Render2DEvent event) {
        NotificationEntry.updateNotifications();

        int padding = 4;

        int y = event.getScaledResolution().getScaledHeight() - 22 - padding;
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
    }
}