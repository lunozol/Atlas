package io.lunozol.atlas;

import io.lunozol.atlas.utils.render.RenderUtils;
import net.minecraft.client.Minecraft;

import java.awt.*;

public interface Constants {
    Minecraft mc = Minecraft.getMinecraft();
    int padding = 2;

    public static final String name = "Atlas", version = "October 4th 2026";
    public static Color firstColor = new Color(66, 223, 253); // Ocean Blue
    public static Color secondColor = new Color(161, 251, 169); // some light green color
}
