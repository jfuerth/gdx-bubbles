package ca.fuerth.gdx.bubbles;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

import static com.badlogic.gdx.math.MathUtils.ceil;
import static com.badlogic.gdx.math.MathUtils.sin;
import static java.lang.Math.log;

public class Bubble extends Sprite {

    private float speed;
    private float xPhase;
    private float phaseRate;
    private float diameter;

    public Bubble(float diameter, float x, float y) {
        super(BubbleTexture.ofSize(diameter));
        setCenter(diameter / 2, diameter / 2);
        speed = 1.5f * (float) log(1.0 + diameter);
        phaseRate = 3f/diameter;
        this.diameter = diameter;
        setX(x);
        setY(y);
    }

    public void update() {
        xPhase += phaseRate;
        translate(sin(xPhase) / 3f, speed);
    }

    public void dispose() {
        getTexture().dispose();
    }

    public void growAreaBy(Bubble removed) {
        this.diameter += removed.diameter; // TODO grow by area
        getTexture().dispose();
        Texture newTexture = BubbleTexture.ofSize(this.diameter);
        System.out.println("New texture size: " + newTexture.getWidth() + " " + newTexture.getHeight());
        setTexture(newTexture);
        setRegion(0, 0, newTexture.getWidth(), newTexture.getHeight());
        setColor(1, 1, 1, 1);
        setSize(newTexture.getWidth(), newTexture.getHeight());
        setOriginCenter();
    }
}
