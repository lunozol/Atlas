package io.lunozol.atlas.system.widget.widgets;

import io.github.nevalackin.radbus.Listen;
import io.lunozol.atlas.system.event.events.render.Render2DEvent;
import io.lunozol.atlas.system.widget.Widget;

// TODO extend widget
public class PlayerListWidget extends Widget {

    public PlayerListWidget() {
        super("PlayerList", "Lists the player in your world!");
    }

    @Listen
    public void onRender2D(Render2DEvent event) {

    }
}
