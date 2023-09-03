package com.br.grupolaz.neocontra.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.br.grupolaz.neocontra.NeoContra;
import com.br.grupolaz.neocontra.stages.MainMenuStage;

public class MainMenu implements Screen {

    private MainMenuStage mainMenuStage;
    private final NeoContra game;

    public MainMenu(NeoContra game) {
        this.game = game;
        this.game.alignCameraToWorldCenter();
        mainMenuStage = new MainMenuStage(game, this);
    }

    public MainMenu(NeoContra game, Stage oldStage) {
        this.game = game;
        this.game.alignCameraToWorldCenter();
        mainMenuStage = new MainMenuStage(game, this, oldStage);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        mainMenuStage.act(delta);
        mainMenuStage.draw();
    }

    @Override
    public void resize(int width, int height) {
        game.getViewport().update(width, height);
    }

    public void setStage(MainMenuStage stage) {
        mainMenuStage = stage;
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
        mainMenuStage.dispose();
    }
}
