package ca.fuerth.gdx.motion;

import static com.badlogic.gdx.math.MathUtils.sin;
import static java.lang.Math.log;

public class RisingBubbleMotion implements MotionStrategy {
    private float speed;
    private float xPhase;
    private float phaseRate;

    public RisingBubbleMotion(float bubbleDiameter) {
        speed = 1.5f * (float) log(1.0 + bubbleDiameter);
        phaseRate = 3f / bubbleDiameter;
    }

    @Override
    public void move(Translatable t) {
        xPhase += phaseRate;
        t.translate(sin(xPhase) / 3f, speed);
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public float getPhaseRate() {
        return phaseRate;
    }

    public void setPhaseRate(float phaseRate) {
        this.phaseRate = phaseRate;
    }
}
