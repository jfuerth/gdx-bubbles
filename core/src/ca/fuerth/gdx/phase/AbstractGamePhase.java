package ca.fuerth.gdx.phase;

import ca.fuerth.gdx.bubbles.Bubble;
import ca.fuerth.gdx.mesh.MeshBatch;
import com.badlogic.gdx.Graphics;

import java.util.List;

abstract class AbstractGamePhase implements Phase {

    final int width;
    final int height;

    AbstractGamePhase(Graphics graphics) {
        this.width = graphics.getWidth();
        this.height = graphics.getHeight();
    }

    @Override
    public void begin() {
    }

    void process(MeshBatch batch, List<Bubble> bubbles) {
        for (int i = bubbles.size() - 1; i >= 0; i--) {
            if (!process(batch, bubbles.get(i))) {
                bubbles.remove(i).dispose();
            }
        }
    }

    /**
     * Draws the bubble or indicates it should be disposed. {@link #process(MeshBatch, List)} calls this once for each
     * bubble in its given list, removing it from the list and disposing it if this method returns false.
     *
     * @param batch the mesh batch to add drawing operations to
     * @param b the bubble to examine, updating it and drawing it if in bounds
     * @return true if the bubble was updated and drawn; false if it was not (in which case it should be removed)
     */
    boolean process(MeshBatch batch, Bubble b) {
        if (b.getY() > height || b.getY() < 0 || b.getX() < 0 || b.getX() > width) {
            return false;
        } else {
            b.update();
            b.draw(batch);
            return true;
        }
    }
}
