package ca.fuerth.gdx.phase;

import ca.fuerth.gdx.mesh.MeshBatch;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public interface Phase {
    boolean processInput(Input input);
    void draw(MeshBatch meshBatch, SpriteBatch spriteBatch);
    void dispose();
}
