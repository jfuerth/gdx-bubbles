package ca.fuerth.gdx;

import ca.fuerth.gdx.bubbles.Bubble;

import java.util.ArrayList;

public class GameData {
    private final ArrayList<Bubble> blueBubbles = new ArrayList<Bubble>();
    private final ArrayList<Bubble> redBubbles = new ArrayList<Bubble>();

    public ArrayList<Bubble> getBlueBubbles() {
        return blueBubbles;
    }

    public ArrayList<Bubble> getRedBubbles() {
        return redBubbles;
    }
}
