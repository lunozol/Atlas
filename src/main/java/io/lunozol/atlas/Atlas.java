package io.lunozol.atlas;

import io.github.nevalackin.radbus.PubSub;
import io.lunozol.atlas.system.event.Event;
import io.lunozol.atlas.system.finder.Finder;
import io.lunozol.atlas.system.Manager;
import io.lunozol.atlas.ui.clickgui.ClickGUIScreen;
import io.lunozol.atlas.utils.render.RenderUtils;
import lombok.Getter;
import org.lwjgl.opengl.Display;


public class Atlas implements Constants {
    private static Atlas instance;
    @Getter
    private Manager manager = new Manager();
    @Getter
    private final PubSub<Event> eventBus = PubSub.newInstance(System.err::println);
    @Getter
    private final ClickGUIScreen clickGUIScreen = new ClickGUIScreen();
    @Getter
    Finder finder = Finder.finder;

    public static int waveColor = RenderUtils.wave(firstColor.getRGB(), secondColor.getRGB(), System.currentTimeMillis(), 0);

    public void init() {
        Display.setTitle(name + " " + version);
        manager.init();
        finder.init();
        System.out.println("Initialised Atlas " + manager.getModules().size() + " modules loaded and " + manager.getWidgets().size() + " widgets loaded!");
    }

    public static Atlas getInstance() {
        if (instance == null) instance = new Atlas();
        return instance;
    }

    public static void updateColor() {
        waveColor = RenderUtils.wave(firstColor.getRGB(), secondColor.getRGB(), System.currentTimeMillis(), 0);
    }

}
