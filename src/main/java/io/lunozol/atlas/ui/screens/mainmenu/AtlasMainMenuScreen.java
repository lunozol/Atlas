package io.lunozol.atlas.ui.screens.mainmenu;

import io.lunozol.atlas.Atlas;
import io.lunozol.atlas.Constants;
import io.lunozol.atlas.system.cache.caches.ImageCache;
import io.lunozol.atlas.ui.button.NewAtlasButton;
import io.lunozol.atlas.utils.render.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.*;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderRabbit;
import net.minecraft.util.ResourceLocation;

import java.awt.*;

public class AtlasMainMenuScreen extends GuiScreen implements Constants {
    private NewAtlasButton singlePlayer;
    private NewAtlasButton multiPlayer;
    private NewAtlasButton options;
    private NewAtlasButton quit;
    private NewAtlasButton confirm;
    private NewAtlasButton cancel;
    private int buttonWidth = 80;
    private int buttonHeight = 20;
    private int space = buttonWidth + padding;
    private boolean popUp;
    private ImageCache imgCache;

    @Override
    public void initGui() {
        singlePlayer = new NewAtlasButton("Singleplayer", 0, 0, 0, buttonWidth, buttonHeight);
        multiPlayer = new NewAtlasButton("Multiplayer", 1, space, 0, buttonWidth, buttonHeight);
        options = new NewAtlasButton("Options", 2, space * 2, 0, buttonWidth, buttonHeight);
        quit = new NewAtlasButton("Quit", 3, space * 3, 0, buttonWidth, buttonHeight);
        confirm = new NewAtlasButton("Confirm", 4, padding, 10, buttonWidth, buttonHeight);
        cancel = new NewAtlasButton("Cancel", 5, padding * 2 + buttonWidth, 10, buttonWidth, buttonHeight);
        ResourceLocation resourceLocation = new ResourceLocation("atlas/cat.jpg");
        imgCache = new ImageCache(resourceLocation, RenderUtils.getImage(resourceLocation));
        super.initGui();
    }
    
    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
//        RenderUtils.rect(0, 0, width, height, new Color(Atlas.waveColor).darker());
        RenderUtils.drawImage(imgCache.getResourceLocation(), 0, 0, width, height, 1, new Color(Atlas.waveColor));

        float topRowX = ((float) width / 2) - (float) ((buttonWidth * 4) - (padding * 3)) / 2;
        float topRowY = ((float) height / 2) - ((float) buttonHeight / 2);

        GlStateManager.pushMatrix();
        GlStateManager.translate(topRowX, topRowY, 1);

        singlePlayer.drawButton();
        multiPlayer.drawButton();
        options.drawButton();
        quit.drawButton();

        updateHover(mouseX, mouseY, topRowX, topRowY);

        GlStateManager.popMatrix();

        GlStateManager.pushMatrix();
        GlStateManager.translate((float) width / 2 - ((float) (Constants.mc.fontRendererObj.getStringWidth(name) * 5) / 2), 50, 0);
        GlStateManager.scale(5, 5, 1);
        Constants.mc.fontRendererObj.drawStringWithShadow(name, 0, 0, Atlas.waveColor);

        GlStateManager.popMatrix();

        if (popUp) {
            renderPopUp(mouseX, mouseY);
        }
    }

    private void updateHover(int mouseX, int mouseY, float topRowX, float topRowY) {
        if (!popUp) {
            confirm.setHovered(false);
            cancel.setHovered(false);

            singlePlayer.updateHover(mouseX, mouseY, Math.round(topRowX), Math.round(topRowY));
            multiPlayer.updateHover(mouseX, mouseY, Math.round(topRowX + space), Math.round(topRowY));
            options.updateHover(mouseX, mouseY, Math.round(topRowX + (space * 2)), Math.round(topRowY));
            quit.updateHover(mouseX, mouseY, Math.round(topRowX + (space * 3)), Math.round(topRowY));
        } else {
            singlePlayer.setHovered(false);
            multiPlayer.setHovered(false);
            options.setHovered(false);
            quit.setHovered(false);
        }
    }

    private void renderPopUp(int mouseX, int mouseY) {
        RenderUtils.rect(0, 0, width, height, RenderUtils.withAlpha(bg, 210));

        GlStateManager.pushMatrix();
        int bWidth = (padding * 3) + (buttonWidth * 2);
        int bHeight = 35;
        float x = (float) width / 2 - ((float) bWidth / 2);
        float y = (float) height / 2 - ((float) bHeight / 2);
        GlStateManager.translate(x, y, 1);
        RenderUtils.rect(0, 0, bWidth, bHeight, bg.brighter());
        Constants.mc.fontRendererObj.drawStringWithShadow("Are you sure you want to quit?", padding, padding, Color.white.getRGB());
        confirm.drawButton();
        cancel.drawButton();
        confirm.updateHover(mouseX, mouseY,  Math.round(x + padding), Math.round(y) + 10);
        cancel.updateHover(mouseX, mouseY,  Math.round(x + (padding * 2) + buttonWidth), Math.round(y) + 10);

        GlStateManager.popMatrix();
    }


    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (singlePlayer.isHovered()) {
            Minecraft.getMinecraft().displayGuiScreen(new GuiSelectWorld(this));
        } else if (multiPlayer.isHovered()) {
            Minecraft.getMinecraft().displayGuiScreen(new GuiMultiplayer(this));
        } else if (options.isHovered()) {
            Minecraft.getMinecraft().displayGuiScreen(new GuiOptions(this, Minecraft.getMinecraft().gameSettings));
        } else if (quit.isHovered()) {
            popUp = true;
        }

        if (popUp) {
            if (confirm.isHovered()) {
                Constants.mc.shutdown();
            } else if (cancel.isHovered()) {
                popUp = false;
            }
        }
    }
}
