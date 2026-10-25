package io.lunozol.atlas.system.features.widget.widgets;

import io.github.nevalackin.radbus.Listen;
import io.lunozol.atlas.Atlas;
import io.lunozol.atlas.Constants;
import io.lunozol.atlas.system.cache.caches.EntityCache;
import io.lunozol.atlas.system.event.events.render.Render2DEvent;
import io.lunozol.atlas.system.features.widget.Widget;
import io.lunozol.atlas.utils.client.BotCheckUtil;
import io.lunozol.atlas.utils.render.RenderUtils;
import io.lunozol.atlas.utils.render.animation.Animation;
import io.lunozol.atlas.utils.render.animation.Easing;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;

import java.awt.*;

// TODO extend widget

public class TargetInfoWidget extends Widget {
    private EntityCache entityCache;
    private final Animation animation = new Animation(Easing.EASE_IN_OUT_CUBIC, 250);
    private int x = 300, y = 500;
    private long lastStateUpdate;
    private boolean wasDrawn = false;
    private final long delay = 2000;

    public TargetInfoWidget() {
        super("TargetInfo", "Displays target information");
    }

    public boolean hasTarget() {
        if (entityCache == null || entityCache.getEntity() == null || !Atlas.getInstance().getFinder().getTargetFinder().getHoveredTargets().contains(entityCache.getEntity()) && !(mc.currentScreen instanceof GuiChat))
            return false;

        return true;
    }

    public void updateTarget() {
        if (Atlas.getInstance().getFinder().getTargetFinder().getHoveredTargets().isEmpty() && !(mc.currentScreen instanceof GuiChat)) return;

        EntityPlayer target = mc.currentScreen instanceof GuiChat ? mc.thePlayer : Atlas.getInstance().getFinder().getTargetFinder().getHoveredTargets().stream().findFirst().get();

        if (target != null) {
            if (entityCache == null) {
                entityCache = new EntityCache(target, target.getName(), target.getDisplayName().getFormattedText(), mc.thePlayer.getDistanceToEntity(target), target.getHealth(), target.getMaxHealth(), target.hurtTime, target.maxHurtTime, ((AbstractClientPlayer) target).getLocationSkin());
                return;
            }

            entityCache.setEntity(target);
            entityCache.setName(target.getName());
            entityCache.setDisplayName(target.getDisplayName().getFormattedText());
            entityCache.setDistFromPlayer(mc.thePlayer.getDistanceToEntity(target));
            entityCache.setHealth(target.getHealth());
            entityCache.setMaxHealth(target.getMaxHealth());
            entityCache.setHurtTime(target.hurtTime);
            entityCache.setMaxHurtTime(target.maxHurtTime);
            entityCache.setLocationSkin(((AbstractClientPlayer) target).getLocationSkin());
        }
    }

    @Listen
    public void onRender2D(Render2DEvent event) {
        updateTarget();
        if (mc.thePlayer == null && entityCache == null) return;

        if (hasTarget()) {
            animation.setEasing(Easing.EASE_OUT_CUBIC);
            animation.run(1);

            if (animation.getValue() >= 1) {
                wasDrawn = true;
            } else {
                wasDrawn = false;
            }
        } else {
            if (wasDrawn) {
                lastStateUpdate = System.currentTimeMillis() + delay;
                wasDrawn = false;
            }

            animation.setEasing(Easing.EASE_IN_CUBIC);

            if (lastStateUpdate < System.currentTimeMillis()) {
                animation.run(0);
            }
        }

        if (animation.getValue() > 0) {
            int width = 135;
            int height = 30;

            if (height + mc.fontRendererObj.getStringWidth(entityCache.getDisplayName()) > width) {
                width = height + mc.fontRendererObj.getStringWidth(entityCache.getDisplayName()) + padding;
            }

            GlStateManager.pushMatrix();
            GlStateManager.translate(x, y, 1);
            GlStateManager.translate(width / 2, height / 2, 1);
            GlStateManager.scale(animation.getValue(), animation.getValue(), 1);
            GlStateManager.translate(-(width / 2), -(height / 2), 1);

            RenderUtils.rect(0, 0, width, height, bg);
            // since the head is drawn at 8 pixels by default we have to do (height - (padding * 2)) / 8
            float headSize = (float) (height - (padding * 2)) / 8;
            int barWidth = width - height - padding;
            double barProgress = entityCache.getHealth() / entityCache.getMaxHealth();

            RenderUtils.drawHead(entityCache.getLocationSkin(), padding, padding, headSize, Color.white);

            mc.fontRendererObj.drawStringWithShadow(entityCache.getDisplayName(), height, padding, Color.white.getRGB());
            mc.fontRendererObj.drawStringWithShadow(String.format("%.1f", entityCache.getHealth()), height, padding + 10, Color.white.getRGB());

            RenderUtils.rect(height, padding + 20, barWidth, 6, bg.darker());
            RenderUtils.gradientRect(height, padding + 20, (int) Math.round(barWidth * barProgress), 6, Atlas.firstColor, Atlas.firstColor.darker());

            GlStateManager.popMatrix();
        }
    }
}
