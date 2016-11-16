package ca.fuerth.gdx.phase;

import ca.fuerth.gdx.GameData;
import ca.fuerth.gdx.bubbles.Bubble;
import ca.fuerth.gdx.mesh.MeshBatch;
import ca.fuerth.gdx.motion.ProjectileMotion;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

import static ca.fuerth.gdx.util.Preconditions.nonNull;

public class ScoreAddingPhase extends AbstractGamePhase {

    private Bubble scoreBubble;
    private boolean isNegative;
    private GameData gameData;
    private Vector2 scorePosition;

    public ScoreAddingPhase(Graphics graphics, GameData gameData, Vector2 scorePosition) {
        super(graphics);
        this.gameData = nonNull(gameData);
        this.scorePosition = nonNull(scorePosition);
    }

    @Override
    public void begin() {
        if (gameData.getRedSumBubble().getRadius() > 0f) {
            scoreBubble = gameData.getRedSumBubble();
            isNegative = false;
        } else {
            scoreBubble = gameData.getBlueSumBubble();
            isNegative = true;
        }
    }

    @Override
    public boolean processInput(Input input) {
        return scoreBubble.getRadius() > 0f
                || gameData.getRedBubbles().size() > 0
                || gameData.getBlueBubbles().size() > 0;
    }

    @Override
    public void draw(MeshBatch meshBatch, SpriteBatch spriteBatch) {
        if (scoreBubble.getRadius() > 0f) {
            ArrayList<Bubble> bubbles = isNegative
                    ? gameData.getBlueBubbles()
                    : gameData.getRedBubbles();
            bubbles.add(new Bubble(
                    ProjectileMotion.toward(new Vector2(scoreBubble.getX(), scoreBubble.getY()),
                            scorePosition,
                            MathUtils.random(8f, 12f)),
                    1,
                    scoreBubble.getX(), scoreBubble.getY(),
                    scoreBubble.getColor()));
            scoreBubble.shrinkAreaBy(MathUtils.PI);
        }

        process(meshBatch, gameData.getRedBubbles());
        process(meshBatch, gameData.getBlueBubbles());
        process(meshBatch, scoreBubble);
    }

    @Override
    boolean process(MeshBatch batch, Bubble b) {
        boolean shouldRemain = super.process(batch, b);
        shouldRemain &= (b.getY() < scorePosition.y && b.getX() > scorePosition.x);
        if (!shouldRemain) {
            int diff = isNegative ? -1 : 1;
            gameData.setScore(gameData.getScore() + diff);
        }
        return shouldRemain;
    }

    @Override
    public void dispose() {
    }
}
