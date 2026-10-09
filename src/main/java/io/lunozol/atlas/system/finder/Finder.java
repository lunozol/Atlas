package io.lunozol.atlas.system.finder;

import io.lunozol.atlas.Atlas;
import io.lunozol.atlas.Constants;
import io.lunozol.atlas.system.finder.finders.TargetFinder;
import lombok.Getter;

@Getter
public class Finder implements Constants {
    public static Finder finder = new Finder();
    private final TargetFinder targetFinder = new TargetFinder();

    public void init() {
        Atlas.getInstance().getEventBus().subscribe(targetFinder);
    }
}
