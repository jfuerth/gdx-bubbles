package ca.fuerth.gdx;

import ca.fuerth.gdx.scene.BubblesRisingScene;
import ca.fuerth.gdx.scene.Scene;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class BubblesMain extends ApplicationAdapter {
    private SpriteBatch batch;

    private Scene scene;

    @Override
    public void resize(int width, int height) {
        System.err.println("Ignoring resize event");
    }

    @Override
    public void create() {
        scene = new BubblesRisingScene(Gdx.graphics, 0.25f, 0.5f);
        batch = new SpriteBatch();
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0.3f, 0.3f, 0.9f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();

        scene.draw(batch);

        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        scene.dispose();
    }
}