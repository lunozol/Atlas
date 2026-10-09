package io.lunozol.atlas.system.finder.finders;

import io.github.nevalackin.radbus.Listen;
import io.lunozol.atlas.Constants;
import io.lunozol.atlas.system.event.events.game.TickEvent;
import io.lunozol.atlas.system.finder.Finder;
import io.lunozol.atlas.utils.client.BotCheckUtil;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class TargetFinder implements Constants {
    private final List<EntityPlayer> playerTargets = new ArrayList<>();
    private final List<EntityPlayer> hoveredTargets = new ArrayList<>();

    @Setter
    public double distanceReq = 3.5;

    @Listen
    public void onTick(TickEvent event) {
        playerTargets.clear();
        hoveredTargets.clear();

        if (mc.thePlayer == null || mc.theWorld == null) return;

        for (Entity entity : mc.theWorld.loadedEntityList.stream().filter(e -> e instanceof EntityPlayer && mc.thePlayer.getDistanceToEntity(e) < distanceReq).collect(Collectors.toList())) {
            if (BotCheckUtil.checkPlayer(entity)) {
                if (mc.objectMouseOver.entityHit != null && mc.objectMouseOver.entityHit.equals(entity)) {
                    hoveredTargets.add((EntityPlayer) entity);
                }

                playerTargets.add((EntityPlayer) entity);
            }
        }
    }
}
