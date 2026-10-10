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

// TODO finish
public class KillAuraModule extends Module {

    public KillAuraModule() {
        super("KillAura", "Hits people for you!", ModuleCategory.COMBAT);
    }

    @Listen
    public void onGameLoop(Render2DEvent event) {
        if (mc.thePlayer == null) return;
    }
}
