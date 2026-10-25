package io.lunozol.atlas.system.features.module.modules.combat;

import io.github.nevalackin.radbus.Listen;
import io.lunozol.atlas.Atlas;
import io.lunozol.atlas.system.event.events.game.GameLoopEvent;
import io.lunozol.atlas.system.features.module.Module;
import io.lunozol.atlas.system.features.module.ModuleCategory;
import io.lunozol.atlas.system.finder.Finder;
import io.lunozol.atlas.system.finder.finders.TargetFinder;
import io.lunozol.atlas.utils.game.ChatUtil;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemSword;
import org.lwjgl.input.Mouse;

public class AutoBlockModule extends Module {
    private boolean haveDealtDamage;
    private boolean hasBlocked;

    public AutoBlockModule() {
        super("AutoBlock", "Blocks for you to give you an advantage", ModuleCategory.COMBAT);
    }

    private boolean canBlock() {
        if (mc.thePlayer == null || Atlas.getInstance().getFinder().getTargetFinder().getHoveredTargets().isEmpty())
            return false;

        if (mc.thePlayer.hurtTime > 2) return false;

        EntityPlayer entityPlayer = Atlas.getInstance().getFinder().getTargetFinder().getHoveredTargets().stream().findFirst().get();

        if (entityPlayer.hurtTime != 0 && mc.thePlayer.getCurrentEquippedItem() != null && mc.thePlayer.getCurrentEquippedItem().getItem() != null && mc.thePlayer.getCurrentEquippedItem().getItem() instanceof ItemSword) {
            haveDealtDamage = true;
        } else {
            haveDealtDamage = false;
        }

        if (haveDealtDamage) {
            if (mc.thePlayer.onGround && !mc.gameSettings.keyBindJump.pressed) {
                return true;
            } else {
                if (mc.gameSettings.keyBindJump.isPressed() && !mc.thePlayer.onGround) {
                    return true;
                }
            }
        }

        return false;
    }

    @Listen
    public void onGameLoop(GameLoopEvent event) {
        if (mc.thePlayer == null || !Mouse.isButtonDown(0)) return;

        if (mc.thePlayer.getCurrentEquippedItem() != null && mc.thePlayer.getCurrentEquippedItem().getItem() instanceof ItemSword) {
            if (canBlock()) {
                mc.gameSettings.keyBindUseItem.pressed = true;
                hasBlocked = true;
            } else if (hasBlocked) {
                mc.gameSettings.keyBindUseItem.pressed = false;
                hasBlocked = false;
            }
        }
    }
}
