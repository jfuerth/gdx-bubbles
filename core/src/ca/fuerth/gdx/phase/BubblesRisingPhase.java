package ca.fuerth.gdx.phase;

import ca.fuerth.gdx.GameData;
import ca.fuerth.gdx.bubbles.Bubble;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;

import static com.badlogic.gdx.math.MathUtils.random;
import static java.lang.Math.min;

public class BubblesRisingPhase implements Phase {

    private GameData gameData;
    private float bubbleProbability;
    private float redProbability;
    private int width;
    private int height;
    private BitmapFont font;

    public BubblesRisingPhase(Graphics graphics, GameData gameData, float bubbleProbability, float redProbability) {
        this.width = graphics.getWidth();
        this.height = graphics.getHeight();
        this.gameData = gameData;
        this.bubbleProbability = bubbleProbability;
        this.redProbability = redProbability;
        this.font = new BitmapFont(Gdx.files.internal("fonts/arial-15.fnt"), false);
    }

    @Override
    public boolean processInput(Input input) {
        if (input.isButtonPressed(Input.Buttons.LEFT)) {
            return false;
        }
        return true;
    }

    @Override
    public void draw(SpriteBatch batch) {
        ArrayList<Bubble> redBubbles = gameData.getRedBubbles();
        ArrayList<Bubble> blueBubbles = gameData.getBlueBubbles();

        if (random(1f) < bubbleProbability) {
            if (random(1f) < redProbability) {
                redBubbles.add(makeRedBubble());
            } else {
                blueBubbles.add(makeBlueBubble());
            }
        }

        process(batch, blueBubbles);
        process(batch, redBubbles);

        font.draw(batch, "" + blueBubbles.size() + " blueBubbles", 15, 15);
        font.draw(batch, "" + redBubbles.size() + " redBubbles", 15, 30);
    }

    private void process(SpriteBatch batch, ArrayList<Bubble> bubbles) {
        for (int i = bubbles.size() - 1; i >= 0; i--) {
            Bubble b = bubbles.get(i);
            if (b.getY() > height) {
                bubbles.remove(i).dispose();
            } else {
                b.update();
                b.draw(batch);
            }
        }
    }

    private Bubble makeBlueBubble() {
        Bubble b = new Bubble(random(14) + 1f, random(width), 0f);
        float desaturation = random(0.2f);
        float blueness = random(0.5f) + 0.5f;
        b.setColor(
                desaturation,
                desaturation,
                min(1f, blueness + desaturation),
                0.8f
        );
        return b;
    }

    private Bubble makeRedBubble() {
        Bubble b = new Bubble(random(14) + 1f, random(width), 0f);
        float desaturation = random(0.2f);
        float redness = random(0.5f) + 0.5f;
        b.setColor(
                min(1f, redness + desaturation),
                desaturation,
                desaturation,
                0.8f
        );
        return b;
    }

    @Override
    public void dispose() {
        font.dispose();
    }
}
