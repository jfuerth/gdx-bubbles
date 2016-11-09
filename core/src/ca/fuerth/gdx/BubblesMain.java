package ca.fuerth.gdx;

import ca.fuerth.gdx.phase.BubbleCountingPhase;
import ca.fuerth.gdx.phase.BubblesRisingPhase;
import ca.fuerth.gdx.phase.Phase;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;
import java.util.List;

public class BubblesMain extends ApplicationAdapter {
    private SpriteBatch batch;

    private GameData gameData;
    private List<Phase> phases;
    private int currentPhase;

    @Override
    public void resize(int width, int height) {
        System.err.println("Ignoring resize event");
    }

    @Override
    public void create() {
        gameData = new GameData();
        resetGamePhases();
        batch = new SpriteBatch();
    }

    private void resetGamePhases() {
        phases = new ArrayList<Phase>();
        phases.add(new BubblesRisingPhase(Gdx.graphics, gameData, 0.25f, 0.5f));
        phases.add(new BubbleCountingPhase(Gdx.graphics, gameData));
        currentPhase = 0;
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0.3f, 0.3f, 0.9f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        Phase phase = phases.get(currentPhase);

        batch.begin();
        boolean currentPhaseFinished = !phase.processInput(Gdx.input);
        phase.draw(batch);
        batch.end();

        if (currentPhaseFinished) {
            currentPhase++;
            if (currentPhase == phases.size()) {
                resetGamePhases();
            }
        }
    }

    @Override
    public void dispose() {
        batch.dispose();
        for (Phase phase : phases) {
            phase.dispose();
        }
    }
}