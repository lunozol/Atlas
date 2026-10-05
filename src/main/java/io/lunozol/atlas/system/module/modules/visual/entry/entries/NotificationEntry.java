package io.lunozol.atlas.system.module.modules.visual.entry.entries;

import io.lunozol.atlas.system.module.modules.visual.entry.Entry;
import io.lunozol.atlas.utils.render.RenderUtils;
import io.lunozol.atlas.utils.render.animation.Animation;
import io.lunozol.atlas.utils.render.animation.Easing;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.lwjgl.Sys;

import java.awt.*;

@Getter
@Setter
public class NotificationEntry extends Entry {
    public final Animation anim = new Animation(Easing.EASE_IN_OUT_CUBIC, 350);
    private String title;
    private String description;
    private long registerTime;
    private long duration = 5000;
    private boolean done = false;

    public NotificationEntry(String title, String description) {
        this.title = title;
        this.description = description;
        this.registerTime = System.currentTimeMillis();
    }

    public void draw(int x, int y, int width, int height) {
        if (registerTime + duration < System.currentTimeMillis() && anim.getValue() == 0) done = true;

        anim.run(registerTime + duration > System.currentTimeMillis() ? 1 : 0);

        RenderUtils.rect(x, y, width, height, bg);
        mc.fontRendererObj.drawStringWithShadow(title, x + padding, y + padding, Color.white.getRGB());
    }
}
