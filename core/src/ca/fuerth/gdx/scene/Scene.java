package ca.fuerth.gdx.scene;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public interface Scene {
    void draw(SpriteBatch batch);
    void dispose();
}
