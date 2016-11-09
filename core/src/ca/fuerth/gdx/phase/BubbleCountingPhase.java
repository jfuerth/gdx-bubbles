package ca.fuerth.gdx.phase;

import ca.fuerth.gdx.GameData;
import ca.fuerth.gdx.bubbles.Bubble;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

public class BubbleCountingPhase implements Phase {

    private GameData gameData;
    int canvasWidth;
    int canvasHeight;

    private Bubble blueSumBubble;
    private Bubble redSumBubble;

    private float gatherSpeed = .5f;
    private float gatherAccel = 0.005f;

    public BubbleCountingPhase(Graphics graphics, GameData gameData) {
        this.gameData = gameData;
        canvasWidth = graphics.getWidth();
        canvasHeight = graphics.getHeight();

        blueSumBubble = new Bubble(0f, .25f * canvasWidth, .5f * canvasHeight);
        blueSumBubble.setColor(0f, 0f, 0f, 0f);

        redSumBubble = new Bubble(0f, .75f * canvasWidth, .5f * canvasHeight);
        redSumBubble.setColor(0f, 0f, 0f, 0f);
    }

    @Override
    public boolean processInput(Input input) {
        return !gameData.getBlueBubbles().isEmpty() || !gameData.getRedBubbles().isEmpty();
    }

    @Override
    public void draw(SpriteBatch batch) {
        process(batch, gameData.getBlueBubbles(), blueSumBubble);
        process(batch, gameData.getRedBubbles(), redSumBubble);
        blueSumBubble.draw(batch);
        redSumBubble.draw(batch);
    }

    private void process(SpriteBatch batch, ArrayList<Bubble> bubbles, Bubble target) {
        for (int i = bubbles.size() - 1; i >= 0; i--) {
            Bubble b = bubbles.get(i);
            if (moveTowardSum(target, b)) {
                Bubble removed = bubbles.remove(i);
                target.growAreaBy(removed);
                target.setColor(target.getColor().lerp(removed.getColor(), 0.8f));
                if (removed.getX() > canvasWidth / 2) {
                    target.setPosition(
                            .75f * canvasWidth - target.getWidth() / 2,
                            .5f * canvasHeight - target.getHeight() / 2);
                } else {
                    target.setPosition(
                            .25f * canvasWidth - target.getWidth() / 2,
                            .5f * canvasHeight - target.getHeight() / 2);
                }
                removed.dispose();
            } else {
                b.draw(batch);
            }
        }
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
        gatherSpeed += gatherAccel;
        if (cTov.len() <= gatherSpeed) {
            return true;
        }
        cTov.nor().scl(gatherSpeed);
        contributor.translate(cTov.x, cTov.y);
        return false;
    }

    @Override
    public void dispose() {

    }
}
