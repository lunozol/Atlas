package io.lunozol.atlas.utils.render;

import io.lunozol.atlas.Constants;
import io.lunozol.atlas.utils.game.ChatUtil;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.util.MathHelper;

// this is a test to see if i could make my own animation class, this would not have easings since I would have to figure out that
@Getter
public class TestAnimation implements Constants {
    private long startTime;
    private float progress;
    private float distanceProgress;
    @Setter
    private float dest;
    @Setter
    private long duration;
    private float value;
    private long elapsedTime;
    private boolean finished;
    private final boolean round;

    public TestAnimation(long duration, boolean round) {
        this.duration = duration;
        this.round = round;
        this.startTime = System.currentTimeMillis();
    }

    public void run(float destination) {
        dest = destination;

        elapsedTime = System.currentTimeMillis() - startTime;
        progress = MathHelper.clamp_float((float) elapsedTime / duration, 0, 1);
        System.out.println("progress " + progress);
        distanceProgress = 1 - progress;

        value = round ? Math.round(dest * progress) : dest * progress;

        if (value >= dest) {
            finished = true;
        } else {
            finished = false;
        }

        if (value > dest || progress > 1) {
            ChatUtil.send("panic");
        }
    }

    public void reset() {
        startTime = System.currentTimeMillis();
    }
}
