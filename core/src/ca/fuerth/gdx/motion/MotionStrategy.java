package ca.fuerth.gdx.motion;

public interface MotionStrategy {

    MotionStrategy NO_MOTION = new MotionStrategy() {
        @Override
        public void move(Translatable t) {
        }
    };

    void move(Translatable t);
}
