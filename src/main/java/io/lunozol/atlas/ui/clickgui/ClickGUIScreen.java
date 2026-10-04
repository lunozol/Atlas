package io.lunozol.atlas.ui.clickgui;

import io.lunozol.atlas.utils.render.RenderUtils;
import net.minecraft.client.gui.GuiScreen;
import org.lwjgl.input.Keyboard;

import java.awt.*;
import java.io.IOException;

public class ClickGUIScreen extends GuiScreen {

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        RenderUtils.rect(50, 50, 300, 300, new Color(20, 20, 20, 195));
    }

}
