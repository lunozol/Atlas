package io.lunozol.atlas.utils.client;

import io.lunozol.atlas.Constants;
import lombok.Setter;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

import java.util.List;

public class BotCheckUtil implements Constants {
    public static boolean checkPlayer(Entity entity) {
        boolean uuidCheck = true;
        if (mc.getNetHandler() != null && mc.getNetHandler().getPlayerInfo(entity.getUniqueID()) == null) {
            uuidCheck = false;
        }

        return entity instanceof EntityPlayer && !entity.getName().startsWith("§") && uuidCheck;
    }
}
