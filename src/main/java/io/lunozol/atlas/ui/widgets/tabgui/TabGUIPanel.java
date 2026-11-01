package io.lunozol.atlas.ui.widgets.tabgui;

import io.lunozol.atlas.Atlas;
import io.lunozol.atlas.Constants;
import io.lunozol.atlas.system.Manager;
import io.lunozol.atlas.system.features.module.Module;
import io.lunozol.atlas.system.features.module.ModuleCategory;
import io.lunozol.atlas.ui.widgets.tabgui.components.TabCategory;
import io.lunozol.atlas.utils.render.RenderUtils;
import io.lunozol.atlas.utils.render.animation.Animation;
import io.lunozol.atlas.utils.render.animation.Easing;
import lombok.Getter;
import lombok.Setter;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import java.awt.*;
import java.security.Key;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class TabGUIPanel implements Constants {
    private List<ModuleCategory> categories = new ArrayList<>();
    private ModuleCategory selectedCategory;
    private Animation animation = new Animation(Easing.LINEAR, 100);
    private Animation modAnimation = new Animation(Easing.LINEAR, 100);
    private int width = 100;
    private int height = 45;
    private int selectedIndex = 0;
    private int selectedModIndex = 0;
    private boolean expanded;
    private boolean wasMoved;

    public void draw(int x, int y) {
        if (categories.isEmpty()) {
            selectedCategory = ModuleCategory.COMBAT;
            categories = Arrays.stream(ModuleCategory.values()).filter(c -> !Atlas.getInstance().getManager().getModulesByCategory(c).isEmpty()).collect(Collectors.toList());
            height = 14 * categories.size();
        }

        handleInput();

        height = 12 * categories.size();

        RenderUtils.rect(x, y, width, height, bg);

        int categoryHeight = height / categories.size();
        animation.run((categoryHeight * selectedIndex));
        RenderUtils.rect(x, (int) Math.round(y + animation.getValue()), width, categoryHeight, firstColor);

        int index = 0;
        for (ModuleCategory category : categories) {

            mc.fontRendererObj.drawStringWithShadow(RenderUtils.normalizeString(category.name()), x + padding, y + ((float) categoryHeight / 2 ) - ((float) mc.fontRendererObj.FONT_HEIGHT / 2), Color.white.getRGB());

            if (selectedIndex == index) {
                selectedCategory = category;
                if (expanded) {
                    int xD = x + width + padding;
                    int height = categoryHeight * Atlas.getInstance().getManager().getModulesByCategory(category).size();

                    RenderUtils.rect(xD, y, width, height, bg);

                    modAnimation.run(categoryHeight * selectedModIndex);

                    RenderUtils.rect(xD, (int) Math.round(y + modAnimation.getValue()), width, categoryHeight, firstColor);

                    int yD = y;
                    int modIndex = 0;
                    for (Module module : Atlas.getInstance().getManager().getModulesByCategory(category)) {
                        mc.fontRendererObj.drawStringWithShadow(module.getName(), xD + padding, yD + ((float) categoryHeight / 2) - ((float) mc.fontRendererObj.FONT_HEIGHT / 2), module.isEnabled() ? secondColor.getRGB() : Color.white.getRGB());

                        if (!wasMoved && selectedModIndex == modIndex && Keyboard.isKeyDown(Keyboard.KEY_RETURN)) {
                            module.toggle();
                            wasMoved = true;
                        }

                        yD += categoryHeight;
                        modIndex++;
                    }
                }
            }

            y += categoryHeight;
            index++;
        }
    }

    public void handleInput() {
        if (!Keyboard.isKeyDown(Keyboard.KEY_DOWN) && !Keyboard.isKeyDown(Keyboard.KEY_UP) && !Keyboard.isKeyDown(Keyboard.KEY_RIGHT) && !Keyboard.isKeyDown(Keyboard.KEY_LEFT) && !Keyboard.isKeyDown(Keyboard.KEY_RETURN)) wasMoved = false;

        if (!wasMoved) {
            if (Keyboard.isKeyDown(Keyboard.KEY_DOWN)) {
                if (!expanded) {
                    selectedIndex++;
                } else {
                    selectedModIndex++;
                }
                wasMoved = true;
            } else if (Keyboard.isKeyDown(Keyboard.KEY_UP)) {
                if (!expanded) {
                    selectedIndex--;
                } else {
                    selectedModIndex--;
                }
                wasMoved = true;
            } else if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) {
                if (!expanded) {
                    selectedModIndex = 0;
                }
                expanded = true;
                wasMoved = true;
            } else if (Keyboard.isKeyDown(Keyboard.KEY_LEFT)) {
                expanded = false;
                wasMoved = true;
            }
        }

        if (selectedIndex < 0) {
            selectedIndex = categories.size() - 1;
        } else if (selectedIndex > categories.size() - 1) {
            selectedIndex = 0;
        }

        if (selectedModIndex < 0) {
            selectedModIndex = Atlas.getInstance().getManager().getModulesByCategory(Arrays.stream(ModuleCategory.values()).collect(Collectors.toList()).get(selectedIndex)).size() - 1;
        } else if (selectedIndex >= Atlas.getInstance().getManager().getModulesByCategory(selectedCategory).size()) {
            selectedModIndex = 0;
        }
    }
}
