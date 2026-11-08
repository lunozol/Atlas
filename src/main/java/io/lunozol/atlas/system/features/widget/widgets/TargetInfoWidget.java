package io.lunozol.atlas.system.features.widget.widgets;

import io.github.nevalackin.radbus.Listen;
import io.lunozol.atlas.Atlas;
import io.lunozol.atlas.system.cache.Cache;
import io.lunozol.atlas.system.event.events.render.Render2DEvent;
import io.lunozol.atlas.system.features.module.modules.combat.KillAuraModule;
import io.lunozol.atlas.system.features.module.property.properties.NumberProperty;
import io.lunozol.atlas.system.features.widget.Widget;
import io.lunozol.atlas.utils.render.RenderUtils;
import io.lunozol.atlas.utils.render.animation.Animation;
import io.lunozol.atlas.utils.render.animation.Easing;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.awt.*;

public class TargetInfoWidget extends Widget {
    Cache cache = new Cache();
    private final Animation animation = new Animation(Easing.EASE_IN_OUT_CUBIC, 250);
    private int x = 300, y = 500;
    private long lastStateUpdate;
    private boolean wasDrawn = false;
    private final long delay = 1000;

    public TargetInfoWidget() {
        super("TargetInfo", "Displays target information");
    }

    public boolean hasTarget() {
        if (cache.getMap().get(Keys.ENTITY) == null || Atlas.getInstance().getManager().getModule(KillAuraModule.class).getTarget() == null || !Atlas.getInstance().getFinder().getTargetFinder().getHoveredTargets().contains((EntityPlayer) cache.getMap().get(Keys.ENTITY)) && !(mc.currentScreen instanceof GuiChat))
            return false;

        return true;
    }

    public void updateTarget() {
        EntityPlayer target = null;

        if (Atlas.getInstance().getManager().getModule(KillAuraModule.class).getTarget() != null) {
            target = Atlas.getInstance().getManager().getModule(KillAuraModule.class).getTarget();
        } else if (!Atlas.getInstance().getFinder().getTargetFinder().getHoveredTargets().isEmpty()) {
            target = Atlas.getInstance().getFinder().getTargetFinder().getHoveredTargets().stream().findFirst().get();
        } else if (mc.currentScreen instanceof GuiChat) {
            target = mc.thePlayer;
        }

        if (target != null) {
            cache.getMap().put(Keys.ENTITY, target);
            cache.getMap().put(Keys.NAME, target.getName());
            cache.getMap().put(Keys.DISPLAYNAME, target.getDisplayName().getFormattedText());
            cache.getMap().put(Keys.DISTFROMPLAYER, mc.thePlayer.getDistanceToEntity(target));
            cache.getMap().put(Keys.HEALTH, target.getHealth());
            cache.getMap().put(Keys.MAXHEALTH, target.getMaxHealth());
            cache.getMap().put(Keys.HURTTIME, target.hurtTime);
            cache.getMap().put(Keys.MAXHURTTIME, target.maxHurtTime);
            cache.getMap().put(Keys.LOCATIONSKIN, ((AbstractClientPlayer) target).getLocationSkin());
        }
    }

    @Listen
    public void onRender2D(Render2DEvent event) {
        updateTarget();
        if (cache.getMap().get(Keys.ENTITY) == null) return;

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
            int width = 110;
            int height = 30;

            if (height + mc.fontRendererObj.getStringWidth((String) cache.getMap().get(Keys.DISPLAYNAME)) > width) {
                width = height + mc.fontRendererObj.getStringWidth((String) cache.getMap().get(Keys.DISPLAYNAME)) + padding;
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
            float barProgress = (float) cache.getMap().get(Keys.HEALTH) / (float) cache.getMap().get(Keys.MAXHEALTH);

            RenderUtils.drawHead((ResourceLocation) cache.getMap().get(Keys.LOCATIONSKIN), padding, padding, headSize, Color.white);

            mc.fontRendererObj.drawStringWithShadow((String) cache.getMap().get(Keys.DISPLAYNAME), height, padding, Color.white.getRGB());
            mc.fontRendererObj.drawStringWithShadow(String.format("%.1f", cache.getMap().get(Keys.HEALTH)), height, padding + 10, Color.white.getRGB());

            RenderUtils.rect(height, padding + 20, barWidth, 6, bg.darker());
            RenderUtils.gradientRect(height, padding + 20, (int) Math.round(barWidth * barProgress), 6, Atlas.firstColor, Atlas.firstColor.darker());

            GlStateManager.popMatrix();
        }
    }

    @Override
    public float getX() {
        return 0;
    }

    @Override
    public float getY() {
        return 0;
    }

    @Override
    public float setX(float x) {
        return 0;
    }

    @Override
    public float setY(float y) {
        return 0;
    }

    @Override
    public float getWidth() {
        return 0;
    }

    @Override
    public float getHeight() {
        return 0;
    }

    enum Keys {
        ENTITY,
        NAME,
        DISPLAYNAME,
        DISTFROMPLAYER,
        HEALTH,
        MAXHEALTH,
        HURTTIME,
        MAXHURTTIME,
        LOCATIONSKIN
    }
}
