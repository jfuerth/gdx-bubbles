package ca.fuerth.gdx.phase;

import ca.fuerth.gdx.GameData;
import ca.fuerth.gdx.bubbles.Bubble;
import ca.fuerth.gdx.mesh.MeshBatch;
import ca.fuerth.gdx.motion.ProjectileMotion;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

import static com.badlogic.gdx.math.MathUtils.random;
import static java.lang.Math.max;

public class SumBubbleDifferencingPhase implements Phase {

    private final GameData gameData;

    private int height;

    private Vector2 redVelocity = new Vector2();
    private Vector2 blueVelocity = new Vector2();

    private float convergenceSpeedLimit = 0f;

    public SumBubbleDifferencingPhase(Graphics graphics, GameData gameData) {
        this.gameData = gameData;
        this.height = graphics.getHeight();
    }

    @Override
    public boolean processInput(Input input) {
        return gameData.getRedSumBubble().getRadius() > 0f
                && gameData.getBlueSumBubble().getRadius() > 0f;
    }

    @Override
    public void draw(MeshBatch meshBatch, SpriteBatch spriteBatch) {
        Bubble redSum = gameData.getRedSumBubble();
        Bubble blueSum = gameData.getBlueSumBubble();

        redVelocity.set(redSum.getX(), redSum.getY());
        blueVelocity.set(blueSum.getX(), blueSum.getY());

        adjustVectorToward(redVelocity, blueSum.getX(), blueSum.getY(), redSum.getRadius());
        adjustVectorToward(blueVelocity, redSum.getX(), redSum.getY(), convergenceSpeedLimit);

        float touchX = redSum.getX() + redVelocity.x;
        float touchY = redSum.getY() + redVelocity.y;

        redVelocity.y += 0.2;
        blueVelocity.y -= 0.2;

        redVelocity.clamp(0f, convergenceSpeedLimit);

        float distance = Vector2.len(
                redSum.getX() - blueSum.getX(),
                redSum.getY() - blueSum.getY());

        float touchingDistance = (redSum.getRadius() + blueSum.getRadius());
        float overlap = touchingDistance - distance;


        if (overlap > 0f) {
            // TODO this doesn't preserve relative bubble areas
            redSum.setRadius(redSum.getRadius() - overlap / 2f);
            blueSum.setRadius(blueSum.getRadius() - overlap / 2f);

            gameData.getRedBubbles().add(
                    new Bubble(
                            new ProjectileMotion(
                                    redVelocity.cpy().rotate(random(85, 95)).nor().scl(random(3.5f, 4f))),
                            1f, touchX, touchY, Color.RED));
            gameData.getBlueBubbles().add(
                    new Bubble(
                            new ProjectileMotion(
                                    redVelocity.cpy().rotate(-random(85, 95)).nor().scl(random(3.5f, 4f))),
                            1f, touchX, touchY, Color.BLUE));

            convergenceSpeedLimit = max(convergenceSpeedLimit - 1f, 0.2f);
        } else {
            convergenceSpeedLimit += 0.02f;
        }

        redSum.translate(redVelocity);
        blueSum.translate(blueVelocity);

        redSum.draw(meshBatch);
        blueSum.draw(meshBatch);

        process(meshBatch, gameData.getRedBubbles());
        process(meshBatch, gameData.getBlueBubbles());
    }

    private void adjustVectorToward(Vector2 from, float toX, float toY, float maxLen) {
        from.sub(toX, toY).scl(-1f).clamp(0f, maxLen);
    }

    private void process(MeshBatch batch, ArrayList<Bubble> bubbles) {
        for (int i = bubbles.size() - 1; i >= 0; i--) {
            Bubble b = bubbles.get(i);
            if (b.getY() > height || b.getY() < 0) {
                bubbles.remove(i).dispose();
            } else {
                b.update();
                b.draw(batch);
            }
        }
    }

    @Override
    public void dispose() {

    }
}
