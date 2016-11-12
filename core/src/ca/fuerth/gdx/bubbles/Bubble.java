package ca.fuerth.gdx.bubbles;

import ca.fuerth.gdx.mesh.MeshBatch;
import com.badlogic.gdx.graphics.Color;

import static com.badlogic.gdx.math.MathUtils.PI;
import static com.badlogic.gdx.math.MathUtils.cos;
import static com.badlogic.gdx.math.MathUtils.sin;
import static java.lang.Math.log;

public class Bubble {

    private float speed;
    private float xPhase;
    private float phaseRate;
    private float diameter;
    private float x;
    private float y;
    private Color color;

    public Bubble(float diameter, float x, float y, Color color) {
        this.diameter = diameter;
        this.x = x;
        this.y = y;
        this.color = color;

        speed = 1.5f * (float) log(1.0 + diameter);
        phaseRate = 3f / diameter;
    }

    public void update() {
        xPhase += phaseRate;
        translate(sin(xPhase) / 3f, speed);
    }

    public void draw(MeshBatch batch) {
        float r = diameter / 2f;

        final float step = 0.2f;
        float twopi = PI * 2f;
        for (float a = 0; a < twopi; a += step) {
            batch.add(
                    x, y,
                    x + diameter * cos(a), y + diameter * sin(a),
                    x + diameter * cos(a + step), y + diameter * sin(a + step),
                    color);
        }
    }

    public void dispose() {
    }

    public void growAreaBy(Bubble removed) {
        this.diameter += removed.diameter; // TODO grow by area
    }


    public void translate(float x, float y) {
        this.x += x;
        this.y += y;
    }

    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setColor(float r, float g, float b, float a) {
        setColor(new Color(r, g, b, a));
    }

    public float getDiameter() {
        return diameter;
    }
}
