package io.lunozol.atlas.system.module.modules.combat;

import io.github.nevalackin.radbus.Listen;
import io.lunozol.atlas.system.event.events.game.GameLoopEvent;
import io.lunozol.atlas.system.module.Module;
import io.lunozol.atlas.system.module.ModuleCategory;
import net.minecraft.client.settings.KeyBinding;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import java.util.concurrent.ThreadLocalRandom;

public class AutoClickerModule extends Module {
    long lastClick;
    long cps = 50;
    long maxcps = 70;
    int cpsVal;

    public AutoClickerModule() {
        super("AutoClicker", "Clicks for you <3", ModuleCategory.COMBAT);
        setEnabled(true);
        setKeybind(Keyboard.KEY_R);
    }

    @Listen
    public void onGameLoop(GameLoopEvent event) {
        if (Mouse.isButtonDown(0) && mc.thePlayer != null && !mc.thePlayer.isBlocking() && calculateCPS(System.currentTimeMillis()) && mc.currentScreen == null) {
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
