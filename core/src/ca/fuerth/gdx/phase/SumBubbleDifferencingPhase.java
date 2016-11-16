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

import static com.badlogic.gdx.math.MathUtils.random;
import static java.lang.Math.max;

public class SumBubbleDifferencingPhase extends AbstractGamePhase {

    private final GameData gameData;

    private Vector2 redVelocity = new Vector2();
    private Vector2 blueVelocity = new Vector2();

    private float convergenceSpeedLimit = 0f;

    public SumBubbleDifferencingPhase(Graphics graphics, GameData gameData) {
        super(graphics);
        this.gameData = gameData;
    }

    @Override
    public boolean processInput(Input input) {
        boolean stillGoing = gameData.getRedSumBubble().getRadius() > 0f
                && gameData.getBlueSumBubble().getRadius() > 0f;
//                || gameData.getRedBubbles().size() > 0
//                || gameData.getBlueBubbles().size() > 0;
        if (!stillGoing) {
            // add back negative loser area
            if (gameData.getRedSumBubble().getRadius() > 0f) {
                gameData.getRedSumBubble().growAreaBy(gameData.getBlueSumBubble());
            } else {
                gameData.getBlueSumBubble().growAreaBy(gameData.getRedSumBubble());
            }
        }
        return stillGoing;
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


        if (redSum.getRadius() <= 0f) {
            redSum.setColor(0f, 0f, 0f, 0f);
        } else if (blueSum.getRadius() <= 0f) {
            blueSum.setColor(0f, 0f, 0f, 0f);
        } else if (overlap > 0f && blueSum.getRadius() > 0f) {
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

    @Override
    public void dispose() {

    }
}
