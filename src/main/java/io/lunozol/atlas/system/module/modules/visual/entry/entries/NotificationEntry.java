package io.lunozol.atlas.system.module.modules.visual.entry.entries;

import io.lunozol.atlas.system.module.modules.visual.entry.Entry;
import io.lunozol.atlas.utils.render.RenderUtils;
import io.lunozol.atlas.utils.render.animation.Animation;
import io.lunozol.atlas.utils.render.animation.Easing;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.client.renderer.GlStateManager;

import java.awt.*;

@Getter
@Setter
public class NotificationEntry extends Entry {
    private final Animation anim = new Animation(Easing.EASE_IN_OUT_CUBIC, 350);
    private String title;
    private String description;
    private long registerTime;
    private long duration = 5000;
    private boolean done = false;
    private int height = 22;
    private int width = 125;

    public NotificationEntry(String title, String description) {
        this.title = title;
        this.description = description;
        this.registerTime = System.currentTimeMillis();
    }

    public void draw(int x, int y) {
        if (registerTime + duration < System.currentTimeMillis() && anim.getValue() == 0) done = true;

        double scale = 1.1;
        GlStateManager.pushMatrix();
        GlStateManager.scale(scale, scale, scale);
        int tWidth = mc.fontRendererObj.getStringWidth(title);
        GlStateManager.popMatrix();
        int dWidth = mc.fontRendererObj.getStringWidth(description);


        if (dWidth >= tWidth) {
            if (dWidth - padding > width) {
                width = dWidth + (padding * 2);
            }
        } else {
            if (tWidth - padding > width) {
                width = tWidth + (padding * 2);
            }
        }

        anim.run(registerTime + duration > System.currentTimeMillis() ? 1 : 0);

        RenderUtils.rect(x, y, width, height, bg);
        GlStateManager.pushMatrix();
        GlStateManager.translate(x + padding, y + padding, 1);
        GlStateManager.scale(scale, scale, scale);
        mc.fontRendererObj.drawStringWithShadow(title, 0, 0, Color.white.getRGB());
        GlStateManager.popMatrix();
        mc.fontRendererObj.drawStringWithShadow(description, x + padding, y + padding + 10, Color.white.getRGB());
    }
}
