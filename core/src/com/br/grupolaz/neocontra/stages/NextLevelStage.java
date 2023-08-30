package com.br.grupolaz.neocontra.stages;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.br.grupolaz.neocontra.NeoContra;
import com.br.grupolaz.neocontra.util.Constants;
import com.br.grupolaz.neocontra.util.TextureUtils;

public class NextLevelStage extends Stage {
    private Label levelNumberLabel;
    private Label levelNameLabel;
    private Table table;
    public NextLevelStage(NeoContra game, Stage oldStage, String level) {
        oldStage.dispose();
        switch(level) {
            case Constants.LEVEL1_MAP:
                levelNumberLabel = new Label("Stage 1", new Label.LabelStyle(TextureUtils.getGameFont(), Color.WHITE));
                levelNameLabel = new Label("Entrance", new Label.LabelStyle(TextureUtils.getGameFont(), Color.WHITE));
//                game.setScreen(new GameScreen(game, this, Constants.LEVEL1_MAP));
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
    }


}
