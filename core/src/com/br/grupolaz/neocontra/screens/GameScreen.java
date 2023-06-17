package com.br.grupolaz.neocontra.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.br.grupolaz.neocontra.NeoContra;
import com.br.grupolaz.neocontra.stages.GameStage;

public class GameScreen implements Screen {

    GameStage gameStage;

    private NeoContra game;

    public GameScreen(NeoContra game) {
        this.game = game;
        gameStage = new GameStage(game, this);
    }

    @Override
    public void show() {
    }



    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        gameStage.draw();
        gameStage.act(delta);
    }


    @Override
    public void resize(int width, int height) {
        game.getViewport().update(width, height);
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
        gameStage.dispose();
    }
    
}
