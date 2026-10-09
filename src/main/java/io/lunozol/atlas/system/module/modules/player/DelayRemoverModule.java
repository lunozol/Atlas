package io.lunozol.atlas.system.module.modules.player;

import io.github.nevalackin.radbus.Listen;
import io.lunozol.atlas.system.event.events.game.GameLoopEvent;
import io.lunozol.atlas.system.event.events.game.TickEvent;
import io.lunozol.atlas.system.event.events.packet.SendPacketEvent;
import io.lunozol.atlas.system.module.Module;
import io.lunozol.atlas.system.module.ModuleCategory;
import io.lunozol.atlas.system.module.property.properties.BooleanProperty;
import net.minecraft.item.ItemBlock;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;

public class DelayRemoverModule extends Module {
    private final BooleanProperty right = new BooleanProperty("Remove Right Click Delay", "Removes the delay between right clicking", true);
    private final BooleanProperty onlyOnPlace = new BooleanProperty("Clear Only on Place", "Limits to clearing the right delay only on block place", true);
    private final BooleanProperty left = new BooleanProperty("Remove Left Click Delay", "Removes the delay between left clicking", true);
    private final BooleanProperty jump = new BooleanProperty("Remove Jump Delay", "Removes the delay between jumping", true);
    public DelayRemoverModule() {
        super("DelayRemover", "Removes delay of certain things", ModuleCategory.PLAYER);
        registerSettings(right, onlyOnPlace, left, jump);
    }

    @Listen
    public void onTick(TickEvent event) {
        onlyOnPlace.require(right.isEnabled());

        if (mc.thePlayer == null) return;

        if (right.isEnabled() && !onlyOnPlace.isEnabled()) mc.rightClickDelayTimer = 0;
        if (left.isEnabled()) mc.leftClickCounter = 0;
        if (jump.isEnabled()) mc.thePlayer.jumpTicks = 0;
    }

    @Listen
    public void onSendPacket(SendPacketEvent event) {
        if (right.isEnabled() && onlyOnPlace.isEnabled()) {
            if (event.packet instanceof C08PacketPlayerBlockPlacement && mc.thePlayer.getHeldItem().getItem() != null && mc.thePlayer.getHeldItem().getItem() instanceof ItemBlock) {
                mc.rightClickDelayTimer = 0;
                System.out.println("aaa");
            }
        }
    }
}
