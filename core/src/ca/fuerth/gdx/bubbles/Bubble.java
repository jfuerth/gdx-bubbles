package ca.fuerth.gdx.bubbles;

import ca.fuerth.gdx.mesh.MeshBatch;
import ca.fuerth.gdx.motion.MotionStrategy;
import ca.fuerth.gdx.motion.Translatable;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

import static com.badlogic.gdx.math.MathUtils.*;
import static java.lang.Math.*;

public class Bubble implements Translatable {

    private static final float TWOPI = MathUtils.PI * 2f;

    private MotionStrategy motionStrategy;
    private float diameter;
    private float x;
    private float y;
    private Color color;
    private float tempInflation;
    private float deflatePercentage = 0.95f;

    public Bubble(MotionStrategy motionStrategy, float diameter, float x, float y, Color color) {
        this.motionStrategy = motionStrategy;
        this.diameter = diameter;
        this.x = x;
        this.y = y;
        this.color = color;
    }

    public void update() {
        motionStrategy.move(this);
        tempInflation = max(0f, (tempInflation - 0.1f) * deflatePercentage);
    }

    public void draw(MeshBatch batch) {
        int steps = max(12, floor(diameter / 1.5f));
        final float step = TWOPI / steps;
        for (int s = 0; s < steps; s++) {
            float a = s * step;
            float nextA = (s == steps - 1) ? 0f : (s + 1) * step;
            float d = diameter + tempInflation;
            batch.add(
                    x, y,
                    x + d * cos(a), y + d * sin(a),
                    x + d * cos(nextA), y + d * sin(nextA),
                    color);
        }
    }

    public void dispose() {
    }

    public void growAreaBy(Bubble removed) {
        float mr = this.diameter / 2f;
        float tr = removed.diameter / 2f;
        // ma = pi * mr * mr
        // ta = pi * tr * tr
        // na = ma + ta
        //    = (pi * mr * mr) + (pi * tr * tr)
        //    = pi (mr^2 + tr^2)
        // pi * nr^2 = pi (mr^2 + tr^2)
        // nr^2 = mr^2 + tr^2
        // nr = sqrt(mr^2 + tr^2)
        this.diameter = (float) (2f * sqrt(mr * mr + tr * tr));
    }


    public void translate(float x, float y) {
        this.x += x;
        this.y += y;
    }

    public void translate(Vector2 v) {
        this.x += v.x;
        this.y += v.y;
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

    public void setDiameter(float diameter) {
        this.diameter = diameter;
    }

    public void addTempInflation(float v) {
        this.tempInflation += v;
    }

    public float getTempInflation() {
        return tempInflation;
    }

    public void setTempInflation(float tempInflation) {
        this.tempInflation = tempInflation;
    }
}
