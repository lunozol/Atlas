package io.lunozol.atlas.utils.client;

import io.lunozol.atlas.Constants;
import lombok.Setter;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

import java.util.List;

public class BotCheckUtil implements Constants {
    public static boolean checkPlayer(Entity entity) {
        if (!(entity instanceof EntityPlayer)) return false;
        if (entity.getName().startsWith("§")) return false;

        if (mc.getNetHandler() != null) {
            return mc.getNetHandler().getPlayerInfo(entity.getUniqueID()) != null;
        }

        return false;
    }
}
