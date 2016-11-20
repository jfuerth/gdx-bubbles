package ca.fuerth.gdx.phase;

import ca.fuerth.gdx.GameData;
import ca.fuerth.gdx.bubbles.Bubble;
import ca.fuerth.gdx.mesh.MeshBatch;
import ca.fuerth.gdx.motion.RisingBubbleMotion;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;

import static ca.fuerth.gdx.util.Preconditions.nonNull;
import static com.badlogic.gdx.math.MathUtils.random;
import static java.lang.Math.min;

public class BubblesRisingPhase extends AbstractGamePhase {

    private final Sound sound;
    private GameData gameData;
    private float bubbleProbability;
    private float redProbability;

    public BubblesRisingPhase(Graphics graphics, GameData gameData, float bubbleProbability, float redProbability) {
        super(graphics);
        this.gameData = nonNull(gameData);
        this.bubbleProbability = bubbleProbability;
        this.redProbability = redProbability;
        sound = Gdx.audio.newSound(Gdx.files.internal("sounds/popping_bubble.wav"));
    }

    @Override
    public boolean processInput(Input input) {
        if (input.isButtonPressed(Input.Buttons.LEFT)) {
            return false;
        }
        return true;
    }

    @Override
    public void draw(MeshBatch meshBatch, SpriteBatch spriteBatch) {
        ArrayList<Bubble> redBubbles = gameData.getRedBubbles();
        ArrayList<Bubble> blueBubbles = gameData.getBlueBubbles();

        if (random(1f) < bubbleProbability) {
            if (random(1f) < redProbability) {
                redBubbles.add(makeRedBubble());
            } else {
                blueBubbles.add(makeBlueBubble());
            }
        }

        process(meshBatch, blueBubbles);
        process(meshBatch, redBubbles);
    }

    private Bubble makeBlueBubble() {
        float desaturation = random(0.2f);
        float blueness = random(0.5f) + 0.5f;
        Color color = new Color(
                desaturation,
                desaturation,
                min(1f, blueness + desaturation),
                0.8f);
        float radius = random(14) + 1f;
        int x = random(width);
        sound.play(0.2f, 0.5f + radius/15f, (x * 2f / width) - 1f);
        return new Bubble(new RisingBubbleMotion(radius), radius, x, 0f, color);
    }

    private Bubble makeRedBubble() {
        float desaturation = random(0.2f);
        float redness = random(0.5f) + 0.5f;
        Color color = new Color(
                min(1f, redness + desaturation),
                desaturation,
                desaturation,
                0.8f);
        float radius = random(14) + 1f;
        int x = random(width);
        sound.play(0.2f, 0.5f + radius/15f, (x * 2f / width) - 1f);
        return new Bubble(new RisingBubbleMotion(radius), radius, x, 0f, color);
    }

    @Override
    public void dispose() {
        sound.dispose();
    }
}
