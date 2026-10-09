package io.lunozol.atlas.utils.client;

import io.lunozol.atlas.Constants;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

public class BotCheckUtil implements Constants {

    public static boolean checkPlayer(Entity entity) {
        return entity instanceof EntityPlayer && !entity.getName().startsWith("§") && !entity.getName().equals(mc.thePlayer.getName());
    }
}
