package ca.fuerth.gdx.phase;

import ca.fuerth.gdx.mesh.MeshBatch;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public interface Phase {

    /**
     * Reacts to user input and decides if this game phase should continue.
     *
     * @param input access to user input signals
     * @return true if this phase should keep going; false if it is over.
     */
    boolean processInput(Input input);

    /**
     * Draws the screen for the current game phase.
     */
    void draw(MeshBatch meshBatch, SpriteBatch spriteBatch);

    /**
     * Disposes OS resources that were allocated by this game phase.
     */
    void dispose();
}
