package io.lunozol.atlas.system.module.modules.visual.entry.entries;

import io.lunozol.atlas.system.module.modules.visual.entry.Entry;
import io.lunozol.atlas.utils.render.RenderUtils;
import io.lunozol.atlas.utils.render.animation.Animation;
import io.lunozol.atlas.utils.render.animation.Easing;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.player.EntityPlayer;

import java.awt.*;

// TODO finish
@Getter
public class PlayerListEntry extends Entry {
    private final Animation anim = new Animation(Easing.EASE_IN_OUT_CUBIC, 150);
    private final Animation fadeAnim = new Animation(Easing.EASE_IN_OUT_CUBIC, 150);
    private String name;
    private double distToPlayer;
    private EntityPlayer entityPlayer;
    private boolean standby;

    private int width = 160;
    private int height = 12;

    @Setter
    private int x;
    @Setter
    private int y;

    public PlayerListEntry(EntityPlayer entityPlayer) {
        this.name = entityPlayer.getName();
        this.distToPlayer = mc.thePlayer.getDistanceToEntity(entityPlayer);
        this.entityPlayer = entityPlayer;
    }

    public void draw() {
        if (mc.theWorld.getLoadedEntityList().contains(entityPlayer)) {
            fadeAnim.run(1);
            standby = false;
        } else {
            fadeAnim.run(0);
            if (fadeAnim.getValue() == 0) standby = true;
        }

        anim.run(y);

        GlStateManager.pushMatrix();

        RenderUtils.rect(x, (int) Math.round(anim.getValue()), width, height, RenderUtils.withAlpha(bg, (int) Math.round(bg.getAlpha() * fadeAnim.getValue())));
        GlStateManager.translate(x, (int) Math.round(anim.getValue()) + (height / 2) - (mc.fontRendererObj.FONT_HEIGHT / 2), 1);
        mc.fontRendererObj.drawStringWithShadow(name, padding, 0, entityPlayer.equals(mc.thePlayer) ? RenderUtils.withAlpha(firstColor, (int) Math.round(255 * fadeAnim.getValue())).getRGB() : RenderUtils.withAlpha(Color.white, (int) Math.round(255 * fadeAnim.getValue())).getRGB());
        GlStateManager.popMatrix();

    }

    public void reset(int y) {
        anim.setValue(y);
        anim.reset();
    }
}
