package ca.fuerth.gdx;

import ca.fuerth.gdx.bubbles.Bubble;
import ca.fuerth.gdx.motion.MotionStrategy;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.Color;

import java.util.ArrayList;

public class GameData {
    private final ArrayList<Bubble> blueBubbles = new ArrayList<Bubble>();
    private final ArrayList<Bubble> redBubbles = new ArrayList<Bubble>();
    private Bubble blueSumBubble;
    private Bubble redSumBubble;

    public GameData(Graphics graphics) {
        Color emptyBubbleColor = new Color(0, 0, 0, 0);

        float canvasWidth = graphics.getWidth();
        float canvasHeight = graphics.getHeight();
        blueSumBubble = new Bubble(MotionStrategy.NO_MOTION, 0f, .25f * canvasWidth, .5f * canvasHeight, emptyBubbleColor);
        blueSumBubble.setColor(0f, 0f, 0f, 0f);

        redSumBubble = new Bubble(MotionStrategy.NO_MOTION, 0f, .75f * canvasWidth, .5f * canvasHeight, emptyBubbleColor);
        redSumBubble.setColor(0f, 0f, 0f, 0f);
    }

    public ArrayList<Bubble> getBlueBubbles() {
        return blueBubbles;
    }

    public ArrayList<Bubble> getRedBubbles() {
        return redBubbles;
    }

    public Bubble getBlueSumBubble() {
        return blueSumBubble;
    }

    public Bubble getRedSumBubble() {
        return redSumBubble;
    }
}
