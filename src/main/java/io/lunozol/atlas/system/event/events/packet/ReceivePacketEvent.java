package io.lunozol.atlas.system.event.events.packet;

import io.lunozol.atlas.system.event.Event;
import net.minecraft.network.EnumPacketDirection;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;

// from sulphide since I really dont know how this works
public class ReceivePacketEvent extends Event {
    public final Packet<?> packet;
    public final INetHandler iNetHandler;
    public final EnumPacketDirection direction;

    public ReceivePacketEvent(Packet<?> packet, INetHandler iNetHandler, EnumPacketDirection direction) {
        this.packet = packet;
        this.iNetHandler = iNetHandler;
        this.direction = direction;
    }
}
