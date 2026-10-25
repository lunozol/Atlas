package io.lunozol.atlas.ui.button;

import io.lunozol.atlas.Constants;
import io.lunozol.atlas.utils.render.RenderUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.minecraft.client.renderer.GlStateManager;

@Getter
@RequiredArgsConstructor
public class NewAtlasButton implements Constants {
    private final String title;
    private final int id;
    private final int x;
    private final int y;
    private final int width;
    private final int height;
    private boolean hovered;


    public void drawButton(int mouseX, int mouseY) {
        GlStateManager.pushMatrix();
        GlStateManager.translate(x, y, 1);

        RenderUtils.rect(0, 0, width, height, bg);

        GlStateManager.popMatrix();
    }
}
