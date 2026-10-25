package io.lunozol.atlas.system.features.module.modules.combat;

import io.github.nevalackin.radbus.Listen;
import io.lunozol.atlas.Atlas;
import io.lunozol.atlas.system.event.events.render.Render2DEvent;
import io.lunozol.atlas.system.features.module.Module;
import io.lunozol.atlas.system.features.module.ModuleCategory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

// Mostly from @Aznos tutorial
public class KillAuraModule extends Module {
    private List<EntityPlayer> targets = new ArrayList<>();
    private EntityPlayer target;
    long lastClick;
    long cps = 50;
    long maxcps = 70;
    int cpsVal;

    public KillAuraModule() {
        super("KillAura", "Hits people for you!", ModuleCategory.COMBAT);
    }

    private void updateTarget() {
        targets.clear();
        if (Atlas.getInstance().getFinder().getTargetFinder().getPlayerTargets().isEmpty()) {
            target = null;
            return;
        }

        targets = Atlas.getInstance().getFinder().getTargetFinder().getPlayerTargets();

        target = targets.stream().sorted(Comparator.comparingDouble(t -> -mc.thePlayer.getDistanceToEntity(t))).collect(Collectors.toList()).stream().findFirst().get();
    }

    @Listen
    public void onGameLoop(Render2DEvent event) {
        updateTarget();
        if (mc.thePlayer == null || target == null) return;

        faceEntity(target, 180);

    }

    private boolean faceEntity(Entity entity, double speed) {
        double diffX = entity.posX - mc.thePlayer.posX;
        double diffY = (entity.posY + entity.getEyeHeight()) - (mc.thePlayer.posZ + mc.thePlayer.getEyeHeight());
        double diffZ = entity.posZ - mc.thePlayer.posZ;

        double distXZ = Math.sqrt(diffX * diffX + diffZ * diffZ);

        float targetYaw = (float) (Math.toDegrees(Math.atan2(diffZ, diffX)) - 90.0f);
        float targetPitch = (float) (-Math.toDegrees(Math.atan2(diffY, diffZ)));

        float currYaw = mc.thePlayer.rotationYaw;
        float currPitch = mc.thePlayer.rotationPitch;

        float yawDiff = MathHelper.wrapAngleTo180_float(targetYaw - currYaw);
        float pitchDiff = MathHelper.wrapAngleTo180_float(targetPitch - currPitch);

        float rotationStep = (float) speed;
        boolean yawComplete = false;
        boolean pitchComplete = false;

        if (Math.abs(yawDiff) > rotationStep) {
            yawDiff = (yawDiff > 0) ? rotationStep : -rotationStep;
        } else {
            yawComplete = true;
        }

        if (Math.abs(pitchDiff) > rotationStep) {
            pitchDiff = (pitchDiff > 0) ? rotationStep : -rotationStep;
        } else {
            pitchComplete = true;
        }

        mc.thePlayer.rotationYaw += yawDiff;
        mc.thePlayer.rotationPitch += pitchDiff;

        return yawComplete && pitchComplete;
    }

    private boolean calculateCPS(long time) {
        if (cpsVal == 0) {
            cpsVal = ThreadLocalRandom.current().nextInt((int) cps, (int) maxcps);
        }
        if (time < lastClick) {
            lastClick = time;
        }
        if (time >= lastClick + cpsVal) {
            cpsVal = ThreadLocalRandom.current().nextInt((int) cps, (int) maxcps);
            lastClick = time;
            return true;
        }
        return false;
    }
}
