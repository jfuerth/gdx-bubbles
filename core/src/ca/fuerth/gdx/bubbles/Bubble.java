package ca.fuerth.gdx.bubbles;

import com.badlogic.gdx.graphics.g2d.Sprite;

import static com.badlogic.gdx.math.MathUtils.sin;
import static java.lang.Math.log;

public class Bubble extends Sprite {

    private float speed;
    private float xPhase;
    private float phaseRate;

    public Bubble(float diameter, float x, float y) {
        super(BubbleTexture.ofSize(diameter));
//        setCenter(diameter / 2, texture.getHeight() - diameter / 2);
        speed = 1.5f * (float) log(1.0 + diameter);
        phaseRate = 3f/diameter;
        setX(x);
        setY(y);
    }

    public void update() {
        xPhase += phaseRate;
        translate(sin(xPhase) / 3f, speed);
    }
}
