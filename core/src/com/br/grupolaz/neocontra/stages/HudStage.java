package com.br.grupolaz.neocontra.stages;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.br.grupolaz.neocontra.actors.Player;
import com.br.grupolaz.neocontra.util.TextureUtils;

public class HudStage extends Stage {

    private Sprite sprite;
    private Player player;
    private Table table;

    public HudStage(SpriteBatch batch, Player player) {
        this.player = player;
        table = new Table();
        table.left().top();
        table.setFillParent(true);
        addActor(table);

        sprite = new Sprite(TextureUtils.getLifeMedal());
        sprite.setSize(64f, 64f);
    }

    private void updateTable(int lifeCount) {
        table.clear();

        for (int i = 0; i < lifeCount; i++) {
            Image spriteImage = new Image(sprite);
            table.add(spriteImage).size(sprite.getWidth(), sprite.getHeight());
        }
    }

    public boolean gameOver() {
            table.bottom();
            sprite.setTexture(TextureUtils.getGameOver());
            sprite.setSize(256f, 128f);
            
            Image spriteImage = new Image(sprite);
            table.add(spriteImage).size(sprite.getWidth(), sprite.getHeight());
            return true;
    }

    @Override
    public Camera getCamera() {
        return super.getCamera();
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if(table.getCells().size != player.getLifeCount()) {
            updateTable(player.getLifeCount());
        }

        if(table.getCells().size == 0) {
            gameOver();
        }
    }

    @Override
    public void draw() {
        super.draw();
    }
}
