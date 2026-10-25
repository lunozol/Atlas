package io.lunozol.atlas.system.features.module.modules.visual.entry.entries;

import io.lunozol.atlas.system.features.module.modules.visual.entry.Entry;
import io.lunozol.atlas.utils.client.BotCheckUtil;
import io.lunozol.atlas.utils.render.RenderUtils;
import io.lunozol.atlas.utils.render.animation.Animation;
import io.lunozol.atlas.utils.render.animation.Easing;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;

import java.awt.*;

// TODO finish
@Getter
public class PlayerListEntry extends Entry {
    private final Animation anim = new Animation(Easing.EASE_IN_OUT_CUBIC, 150);
    private final Animation fadeAnim = new Animation(Easing.EASE_IN_OUT_CUBIC, 150);
    private String name;
    private String displayName;
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
        this.displayName = entityPlayer.getDisplayName().toString();
        this.distToPlayer = mc.thePlayer.getDistanceToEntity(entityPlayer);
        this.entityPlayer = entityPlayer;
    }

    public void draw() {
        if (mc.theWorld.getLoadedEntityList().contains(entityPlayer) && BotCheckUtil.checkPlayer(entityPlayer)) {
            fadeAnim.run(1);
            standby = false;
        } else {
            fadeAnim.run(0);
            if (fadeAnim.getValue() == 0) standby = true;
        }

        anim.run(y);

        GlStateManager.pushMatrix();
        float centerX = x + ((float) width / 2);
        float centerY = y + ((float) height / 2);
        GlStateManager.translate(centerX, centerY, 0);
        GlStateManager.scale(0.8 * fadeAnim.getValue(), 0.8 * fadeAnim.getValue(), 1);
        GlStateManager.translate(-centerX, -centerY, 0);

        RenderUtils.rect(x, y, width, height, RenderUtils.withAlpha(bg, (int) Math.round(bg.getAlpha() * fadeAnim.getValue())));

        mc.fontRendererObj.drawStringWithShadow(name, x + padding, y + ((float) height / 2) - ((float) mc.fontRendererObj.FONT_HEIGHT / 2), entityPlayer.equals(mc.thePlayer) ? RenderUtils.withAlpha(firstColor, (int) Math.round(255 * fadeAnim.getValue())).getRGB() : RenderUtils.withAlpha(Color.white, MathHelper.clamp_int((int) Math.round(255 * fadeAnim.getValue()), 10, 255)).getRGB());

        GlStateManager.popMatrix();
    }

    public void reset(int y) {
        anim.setValue(y);
        anim.reset();
    }
}
