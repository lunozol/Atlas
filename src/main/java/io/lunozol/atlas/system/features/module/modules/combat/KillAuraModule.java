package io.lunozol.atlas.system.features.module.modules.combat;

import io.github.nevalackin.radbus.Listen;
import io.lunozol.atlas.Atlas;
import io.lunozol.atlas.system.event.events.game.MotionEvent;
import io.lunozol.atlas.system.event.events.render.Render2DEvent;
import io.lunozol.atlas.system.features.module.Module;
import io.lunozol.atlas.system.features.module.ModuleCategory;
import io.lunozol.atlas.utils.game.ChatUtil;
import lombok.Getter;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemSword;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

// Intent Tutorial :money_mouth:
public class KillAuraModule extends Module {
    private List<EntityPlayer> targets = new ArrayList<>();
    @Getter
    private EntityPlayer target;
    long lastClick;
    long cps = 50;
    long maxcps = 70;
    int cpsVal;

    public KillAuraModule() {
        super("KillAura", "Hits people for you!", ModuleCategory.COMBAT);
    }

    private void updateTarget() {
        targets =  Atlas.getInstance().getFinder().getTargetFinder().getPlayerTargets();
    }

    @Listen
    public void onMotion(MotionEvent event) {
        updateTarget();
        if (mc.thePlayer == null || targets.isEmpty() || (mc.thePlayer.getHeldItem() != null) && !(mc.thePlayer.getHeldItem().getItem() instanceof ItemSword)) return;
        mc.thePlayer.setItemInUse(mc.thePlayer.getHeldItem(), 1);

        target = targets.stream().findFirst().get();

//        faceEntity(target, 180);

        event.setYaw(getRotations(target)[0]);
        event.setPitch(getRotations(target)[1]);

       if (calculateCPS(System.currentTimeMillis())) {
           ChatUtil.send(target.getName());
//           mc.getNetHandler().addToSendQueue(
//                   new C07PacketPlayerDigging(
//                           C07PacketPlayerDigging.Action.RELEASE_USE_ITEM,
//                           BlockPos.ORIGIN,
//                           EnumFacing.DOWN
//                   )
//           );

           mc.getNetHandler().addToSendQueue(
                   new C08PacketPlayerBlockPlacement(
                           mc.thePlayer.getHeldItem()
                   )
           );
           mc.thePlayer.swingItem();
           mc.getNetHandler().addToSendQueue(
                   new C02PacketUseEntity(target, C02PacketUseEntity.Action.ATTACK)
           );
       }
    }

    public float[] getRotations(Entity entity) {
        double deltaX = entity.posX + (entity.posX - entity.lastTickPosX) - mc.thePlayer.posX, // difference between entity position x so we can interpolate between it
                deltaY = entity.posY - 3.5 + entity.getEyeHeight() - mc.thePlayer.posY + mc.thePlayer.getEyeHeight(),
                deltaZ = entity.posZ + (entity.posZ - entity.lastTickPosZ) - mc.thePlayer.posZ,
                distance = Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaZ, 2));

        float yaw = (float) Math.toDegrees(-Math.atan(deltaX / deltaZ)),
                pitch = (float) -Math.toDegrees(Math.atan(deltaY / distance));

        if (deltaX < 0 && deltaZ < 0) {
            yaw = (float) (90 + Math.toDegrees(Math.atan(deltaZ / deltaX)));
        } else if (deltaX < 0 && deltaZ > 0) {
            yaw = (float) (-90 + Math.toDegrees(Math.atan(deltaZ / deltaX)));
        }

        return new float[] {yaw, pitch};
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
