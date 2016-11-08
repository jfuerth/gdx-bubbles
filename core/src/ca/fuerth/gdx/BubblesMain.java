package ca.fuerth.gdx;

import ca.fuerth.gdx.bubbles.Bubble;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;

import static com.badlogic.gdx.math.MathUtils.random;
import static java.lang.Math.min;

public class BubblesMain extends ApplicationAdapter {
    private SpriteBatch batch;
    private ArrayList<Bubble> bubbles;

    private int width;
    private int height;

    private float bubbleProbability = 0.5f;

    @Override
    public void resize(int width, int height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public void create() {
        width = Gdx.graphics.getWidth();
        height = Gdx.graphics.getHeight();
        batch = new SpriteBatch();
        bubbles = new ArrayList<Bubble>();
    }

    @Override
    public void render() {
        if (random(1f) < bubbleProbability) {
            Bubble b = new Bubble(random(14) + 1f, random(width), 0f);
            float desaturation = random(0.2f);
            float blueness = random(0.5f) + 0.5f;
            b.setColor(
                    desaturation,
                    desaturation,
                    min(1f, blueness + desaturation),
                    0.8f
            );
            bubbles.add(b);
        }
        Gdx.gl.glClearColor(0.3f, 0.3f, 0.9f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();

        for (int i = bubbles.size() - 1; i >= 0; i--) {
            Bubble b = bubbles.get(i);
            if (b.getY() > height) {
                bubbles.remove(i);
            } else {
                b.update();
                b.draw(batch);
            }
        }

        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        bubbles.clear();
    }
}