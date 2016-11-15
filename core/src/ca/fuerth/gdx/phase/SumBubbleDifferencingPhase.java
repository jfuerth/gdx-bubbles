package ca.fuerth.gdx.phase;

import ca.fuerth.gdx.mesh.MeshBatch;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class SumBubbleDifferencingPhase implements Phase {

    @Override
    public boolean processInput(Input input) {
        return true;
    }

    @Override
    public void draw(MeshBatch meshBatch, SpriteBatch spriteBatch) {

    }

    @Override
    public void dispose() {

    }
}
