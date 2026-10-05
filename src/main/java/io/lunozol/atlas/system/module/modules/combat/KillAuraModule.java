package io.lunozol.atlas.system.module.modules.combat;

import io.github.nevalackin.radbus.Listen;
import io.lunozol.atlas.system.event.events.game.GameLoopEvent;
import io.lunozol.atlas.system.event.events.render.Render2DEvent;
import io.lunozol.atlas.system.module.Module;
import io.lunozol.atlas.system.module.ModuleCategory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;

import java.util.Comparator;
import java.util.stream.Collectors;

public class KillAuraModule extends Module {

    public KillAuraModule() {
        super("KillAura", "Hits people for you!", ModuleCategory.COMBAT);
    }

    @Listen
    public void onGameLoop(Render2DEvent event) {
        if (mc.thePlayer == null) return;

        for (Entity entity : mc.theWorld.getLoadedEntityList().stream().filter(e -> mc.thePlayer.getDistanceToEntity(e) < 4 && e instanceof EntityPlayer && e != mc.thePlayer).sorted(Comparator.comparingDouble(e -> -mc.thePlayer.getDistanceToEntity(e))).collect(Collectors.toList())) {
            System.out.println(entity.getName() + mc.thePlayer.getDistanceToEntity(entity));
            Vec3 vec32 = new Vec3(mc.thePlayer.posX + mc.thePlayer.motionX, mc.thePlayer.posY + mc.thePlayer.motionY, mc.thePlayer.posZ + mc.thePlayer.motionZ);
            float f1 = entity.getCollisionBorderSize();
            AxisAlignedBB axisalignedbb = entity.getEntityBoundingBox().expand((double)f1, (double)f1, (double)f1);
            MovingObjectPosition movingobjectposition = axisalignedbb.calculateIntercept(entity.getPositionEyes(event.getPartialTicks()), vec32);
            mc.thePlayer.rotationYaw = movingobjectposition.entityHit.rotationYaw;
        }
    }
}
