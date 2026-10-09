package io.lunozol.atlas.utils.render.animation;

import lombok.Getter;
import lombok.Setter;
import net.minecraft.util.MathHelper;

// pasted from weedhack
@Getter
public class Animation {
    @Setter
    private Easing easing;
    @Setter
    private long duration;
    private long startTime;

    private double startValue;
    private double destinationValue;
    @Setter
    private double value;
    private boolean finished;

    public Animation(final Easing easing, final long duration) {
        this.easing = easing;
        this.startTime = System.currentTimeMillis();
        this.duration = duration;
    }

    public void run(final double destinationValue) {
        long millis = System.currentTimeMillis();
        if (this.destinationValue != destinationValue) {
            this.destinationValue = destinationValue;
            this.reset();
        } else {
            this.finished = millis - this.duration > this.startTime;
            if (this.finished) {
                this.value = destinationValue;
                return;
            }
        }

        final double result = this.easing.getFunction().apply(this.getProgress());
        if (this.value > destinationValue) {
            this.value = this.startValue - (this.startValue - destinationValue) * result;
        } else {
            this.value = this.startValue + (destinationValue - this.startValue) * result;
        }
    }

    public double getProgress() {
        return MathHelper.clamp_double((double) (System.currentTimeMillis() - this.startTime) / this.duration, 0, 1);
    }

    public double getDistanceProgress() {
        return 1 - getProgress();
    }

    public void reset() {
        this.startTime = System.currentTimeMillis();
        this.startValue = value;
        this.finished = false;
    }
}
