package io.lunozol.atlas.system.features.module.modules.combat;

import io.github.nevalackin.radbus.Listen;
import io.lunozol.atlas.Atlas;
import io.lunozol.atlas.system.event.events.game.TickEvent;
import io.lunozol.atlas.system.features.module.Module;
import io.lunozol.atlas.system.features.module.ModuleCategory;
import io.lunozol.atlas.system.features.module.property.properties.BooleanProperty;
import net.minecraft.client.settings.KeyBinding;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import java.util.concurrent.ThreadLocalRandom;

// made from the first ever autoclicker i made its not very good and flags anticheats that check deviation (is that what its called?)
public class AutoClickerModule extends Module {
    private final BooleanProperty hitSelect = new BooleanProperty("Hit Select", "Hits only when necessary!", false);
    long lastClick;
    long cps = 50;
    long maxcps = 70;
    int cpsVal;

    public AutoClickerModule() {
        super("AutoClicker", "Clicks for you <3", ModuleCategory.COMBAT);
        setEnabled(false);
        setKeybind(Keyboard.KEY_R);
        registerSettings(hitSelect);
    }

    private boolean canHit() {
        if (!hitSelect.isEnabled()) return true;
        if (Atlas.getInstance().getFinder().getTargetFinder().getHoveredTargets().isEmpty()) return false;

        if (Atlas.getInstance().getFinder().getTargetFinder().getHoveredTargets().stream().findFirst().get().hurtTime < 2) {
            return true;
        }

        return false;
    }

    @Listen
    public void onTick(TickEvent event) {
        if (Mouse.isButtonDown(0) && mc.thePlayer != null && !mc.thePlayer.isBlocking() && calculateCPS(System.currentTimeMillis()) && mc.currentScreen == null && canHit()) {
            int key = mc.gameSettings.keyBindAttack.getKeyCode();

            KeyBinding.setKeyBindState(key, true);
            KeyBinding.onTick(key);
            KeyBinding.setKeyBindState(key, false);
        }
    }

    private boolean calculateCPS(long time) {
        if (cpsVal == 0) {
            cpsVal = ThreadLocalRandom.current().nextInt((int) cps, (int) maxcps);
        }
        if (time < lastClick) {
            lastClick = time;
        }
        if (time >= lastClick + cpsVal) {
            cpsVal = ThreadLocalRandom.current().nextInt((int) cps, (int) maxcps);
            lastClick = time;
            return true;
        }
        return false;
    }
}
