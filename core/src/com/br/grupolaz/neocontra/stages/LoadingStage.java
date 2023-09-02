package com.br.grupolaz.neocontra.stages;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.TimeUtils;
import com.br.grupolaz.neocontra.NeoContra;
import com.br.grupolaz.neocontra.screens.GameScreen;
import com.br.grupolaz.neocontra.util.Constants;
import com.br.grupolaz.neocontra.util.TextureUtils;

public class LoadingStage extends Stage {
    private Label levelNumberLabel;
    private Label levelNameLabel;

    private final NeoContra game;

    private final String level;
    private final boolean singlePlayer;

    private final long startTime = TimeUtils.millis();
    public LoadingStage(NeoContra game, String level, boolean singlePlayer, Stage oldStage) {
        this.game = game;
        this.level = level;
        this.singlePlayer = singlePlayer;
        oldStage.dispose();

        switch(level) {
            case Constants.LEVEL1_MAP:
                levelNumberLabel = new Label("Stage 1", new Label.LabelStyle(TextureUtils.getGameFont(), Color.WHITE));
                levelNameLabel = new Label("Entrance", new Label.LabelStyle(TextureUtils.getGameFont(), Color.WHITE));
                break;
            case Constants.LEVEL2_MAP:
                levelNumberLabel = new Label("Stage 2", new Label.LabelStyle(TextureUtils.getGameFont(), Color.WHITE));
                levelNameLabel = new Label("River", new Label.LabelStyle(TextureUtils.getGameFont(), Color.WHITE));
                break;
            case Constants.LEVEL3_MAP:
                levelNumberLabel = new Label("Stage 3", new Label.LabelStyle(TextureUtils.getGameFont(), Color.WHITE));
                levelNameLabel = new Label("East Walls", new Label.LabelStyle(TextureUtils.getGameFont(), Color.WHITE));
            case Constants.LEVEL4_MAP:
                levelNumberLabel = new Label("Stage 4", new Label.LabelStyle(TextureUtils.getGameFont(), Color.WHITE));
                levelNameLabel = new Label("Sewer", new Label.LabelStyle(TextureUtils.getGameFont(), Color.WHITE));
        }

        setUpTable();
    }

    public void setUpTable() {
        Table root = new Table();
        root.setFillParent(true);
        root.center();
        root.pad(10);
        root.add(levelNumberLabel).padBottom(10);
        root.row();
        root.add(levelNameLabel).padBottom(10);
        addActor(root);
    }

    @Override
    public void draw() {
        super.draw();

        if(TimeUtils.timeSinceMillis(startTime) > 2500) {
            game.setScreen(new GameScreen(game, level, singlePlayer, this));
        }

    }
}
