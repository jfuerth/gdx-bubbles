package ca.fuerth.gdx.motion;

import com.badlogic.gdx.math.Vector2;

public class ProjectileMotion implements MotionStrategy {
    private Vector2 velocity;

    public ProjectileMotion(Vector2 velocity) {
        this.velocity = velocity;
    }

    @Override
    public void move(Translatable t) {
        t.translate(velocity.x, velocity.y);
    }
}
