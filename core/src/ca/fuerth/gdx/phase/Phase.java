package ca.fuerth.gdx.phase;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public interface Phase {
    boolean processInput(Input input);
    void draw(SpriteBatch batch);
    void dispose();
}
