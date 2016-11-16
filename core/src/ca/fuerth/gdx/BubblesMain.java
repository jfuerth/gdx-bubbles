package ca.fuerth.gdx;

import ca.fuerth.gdx.mesh.MeshBatch;
import ca.fuerth.gdx.phase.*;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.List;

public class BubblesMain extends ApplicationAdapter {

    private enum ResetType {FULL_RESET, PRESERVE_SCORE}

    private SpriteBatch spriteBatch;
    private MeshBatch meshBatch;
    private BitmapFont font;

    private GameData gameData;
    private List<Phase> phases;
    private int currentPhase;

    private Vector2 scorePosition;

    @Override
    public void resize(int width, int height) {
        System.err.println("Ignoring resize event");
    }

    @Override
    public void create() {
        spriteBatch = new SpriteBatch();
        meshBatch = new MeshBatch(10240);
        this.font = new BitmapFont(Gdx.files.internal("fonts/arial-15.fnt"), false);
        scorePosition = new Vector2(
                Gdx.graphics.getWidth() - 50,
                Gdx.graphics.getHeight() - font.getLineHeight());
        resetGame(ResetType.FULL_RESET);
    }

    private void resetGame(ResetType resetType) {
        GameData oldGameData = gameData;
        gameData = new GameData(Gdx.graphics);
        if (resetType == ResetType.PRESERVE_SCORE) {
            gameData.setScore(oldGameData.getScore());
        }
        phases = new ArrayList<Phase>();
        phases.add(new BubblesRisingPhase(Gdx.graphics, gameData, 0.25f, 0.5f));
        phases.add(new BubbleCountingPhase(Gdx.graphics, gameData));
        phases.add(new SumBubbleDifferencingPhase(Gdx.graphics, gameData));
        phases.add(new ScoreAddingPhase(Gdx.graphics, gameData, scorePosition));
        currentPhase = 0;
        phases.get(currentPhase).begin();
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0.3f, 0.3f, 0.9f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        Phase phase = phases.get(currentPhase);

        meshBatch.begin(Gdx.graphics);
        spriteBatch.begin();
        boolean currentPhaseFinished = !phase.processInput(Gdx.input);
        phase.draw(meshBatch, spriteBatch);

        font.draw(spriteBatch, formatScore(gameData),
                scorePosition.x - 100, scorePosition.y + font.getLineHeight());

        spriteBatch.end();
        meshBatch.end();


        if (currentPhaseFinished) {
            phase.dispose();
            currentPhase++;
            if (currentPhase == phases.size()) {
                resetGame(ResetType.PRESERVE_SCORE);
            } else {
                phases.get(currentPhase).begin();
            }
        }
    }

    private String formatScore(GameData gameData) {
        StringBuffer sb = new StringBuffer(20);
        String score = String.valueOf(gameData.getScore());
        sb.append("Score:");
        for (int i = sb.length(); i < sb.capacity() - score.length(); i++) {
            sb.append(" ");
        }
        sb.append(score);
        return sb.toString();
    }

    @Override
    public void dispose() {
        spriteBatch.dispose();
        font.dispose();
        meshBatch.dispose();
        for (Phase phase : phases) {
            phase.dispose();
        }
    }
}