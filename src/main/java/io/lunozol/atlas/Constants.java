package io.lunozol.atlas;

import io.lunozol.atlas.utils.client.DebugUtil;
import io.lunozol.atlas.utils.render.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.util.EnumChatFormatting;

import java.awt.*;

public interface Constants {
    Boolean debug = new Boolean(true); // this is for when u want to check
    Minecraft mc = Minecraft.getMinecraft();
    int padding = 2;

    String name = "Atlas", version = "October 9th 2026";
    String chatName = EnumChatFormatting.AQUA + "Atlas" + EnumChatFormatting.WHITE;
    Color firstColor = new Color(66, 223, 253); // Ocean Blue
    Color secondColor = new Color(161, 251, 169); // some light green color

    Color bg = new Color(20, 20, 20, 190);
}
