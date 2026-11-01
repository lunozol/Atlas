package io.lunozol.atlas.ui.widgets.tabgui;

import io.lunozol.atlas.Atlas;
import io.lunozol.atlas.Constants;
import io.lunozol.atlas.system.Manager;
import io.lunozol.atlas.system.features.module.ModuleCategory;
import io.lunozol.atlas.ui.widgets.tabgui.components.TabCategory;
import io.lunozol.atlas.utils.render.RenderUtils;
import io.lunozol.atlas.utils.render.animation.Animation;
import io.lunozol.atlas.utils.render.animation.Easing;
import lombok.Getter;
import lombok.Setter;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import java.security.Key;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class TabGUIPanel implements Constants {
    private List<ModuleCategory> categories = new ArrayList<>();
    private Animation animation = new Animation(Easing.EASE_IN_OUT_CUBIC, 250);
    private int width = 100;
    private int height = 45;
    private int selectedIndex = 0;
    private boolean wasMoved;

    public void draw(int x, int y) {
        if (categories.isEmpty()) {
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
            TabCategory tabCategory = new TabCategory();
            tabCategory.draw(category, x, y, width, categoryHeight, selectedIndex == index);

            y += categoryHeight;
            index++;
        }
    }

    public void handleInput() {
        if (!Keyboard.isKeyDown(Keyboard.KEY_DOWN) && !Keyboard.isKeyDown(Keyboard.KEY_UP)) wasMoved = false;

        if (!wasMoved) {
            System.out.println("aa");
            if (Keyboard.isKeyDown(Keyboard.KEY_DOWN)) {
                selectedIndex++;
                wasMoved = true;
            } else if (Keyboard.isKeyDown(Keyboard.KEY_UP)) {
                selectedIndex--;
                wasMoved = true;
            }
        }

        if (selectedIndex < 0) {
            selectedIndex = categories.size() - 1;
        } else if (selectedIndex > categories.size() - 1) {
            selectedIndex = 0;
        }
    }
}
