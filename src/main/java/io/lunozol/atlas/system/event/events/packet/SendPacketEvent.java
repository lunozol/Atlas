package io.lunozol.atlas.system.event.events.packet;

import io.lunozol.atlas.system.event.Event;
import lombok.AllArgsConstructor;
import net.minecraft.network.EnumPacketDirection;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;

@AllArgsConstructor
public class SendPacketEvent extends Event {
    public final Packet<?> packet;
    public final INetHandler iNetHandler;
    public final EnumPacketDirection direction;
}
