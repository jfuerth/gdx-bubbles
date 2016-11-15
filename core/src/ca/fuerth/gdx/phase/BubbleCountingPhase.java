package ca.fuerth.gdx.phase;

import ca.fuerth.gdx.GameData;
import ca.fuerth.gdx.bubbles.Bubble;
import ca.fuerth.gdx.mesh.MeshBatch;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

public class BubbleCountingPhase implements Phase {

    private GameData gameData;
    private int canvasWidth;
    private int canvasHeight;

    private float gatherSpeed = 0.5f;
    private float gatherAccel = 0.2f;

    public BubbleCountingPhase(Graphics graphics, GameData gameData) {
        this.gameData = gameData;
        canvasWidth = graphics.getWidth();
        canvasHeight = graphics.getHeight();
    }

    @Override
    public boolean processInput(Input input) {
        return !gameData.getBlueBubbles().isEmpty()
                || !gameData.getRedBubbles().isEmpty()
                || getRedSumBubble().getTempInflation() > 0f
                || getBlueSumBubble().getTempInflation() > 0f;
    }

    @Override
    public void draw(MeshBatch meshBatch, SpriteBatch spriteBatch) {
        process(meshBatch, gameData.getBlueBubbles(), getBlueSumBubble());
        process(meshBatch, gameData.getRedBubbles(), getRedSumBubble());

        getRedSumBubble().update();
        getRedSumBubble().draw(meshBatch);

        getBlueSumBubble().update();
        getBlueSumBubble().draw(meshBatch);
    }

    private void process(MeshBatch batch, ArrayList<Bubble> bubbles, Bubble target) {
        for (int i = bubbles.size() - 1; i >= 0; i--) {
            Bubble b = bubbles.get(i);
            if (moveTowardSum(target, b)) {
                Bubble removed = bubbles.remove(i);
                target.growAreaBy(removed);
                target.addTempInflation(2f);
                target.setColor(target.getColor().lerp(removed.getColor(), 0.8f));
                removed.dispose();
            } else {
                b.draw(batch);
            }
        }
        gatherSpeed += gatherAccel;
    }

    /**
     * Moves the contributor toward the target, enlarging the target by the contributor's area.
     * Returns true when the contributor reaches the target.
     */
    private boolean moveTowardSum(Bubble target, Bubble contributor) {
        /*
         6
         5
         4   t
         3
         2       c
         1
         0 1 2 3 4 5 6
         */

        float cx = contributor.getX();
        float cy = contributor.getY();
        float tx = target.getX();
        float ty = target.getY();

        Vector2 cTov = new Vector2(tx - cx, ty - cy);
        if (cTov.len() <= gatherSpeed + target.getDiameter() / 2f) {
            return true;
        }
        cTov.nor().scl(gatherSpeed);
        contributor.translate(cTov.x, cTov.y);
        return false;
    }

    @Override
    public void dispose() {
        getRedSumBubble().dispose();
        getBlueSumBubble().dispose();
    }

    private Bubble getBlueSumBubble() {
        return gameData.getBlueSumBubble();
    }

    private Bubble getRedSumBubble() {
        return gameData.getRedSumBubble();
    }
}
