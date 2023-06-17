package com.br.grupolaz.neocontra.stages;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;

public class HudStage extends Stage {

    private int lifeCount;
    private Label lifeLabel;

    public HudStage(SpriteBatch batch) {
        lifeCount = 3;
        lifeLabel = new Label(String.format("%02d", lifeCount), new LabelStyle(new BitmapFont(), Color.WHITE));

        setUpHud(batch);
    }

    private void setUpHud(SpriteBatch batch) {
        Table table = new Table();
        table.left().top();
        table.setFillParent(true);
        
        table.add(lifeLabel).padTop(10f).padLeft(10f);

        addActor(table);
    }

    @Override
    public Camera getCamera() {
        return super.getCamera();
    }

    @Override
    public void draw() {
        super.draw();
    }

}
