package com.br.grupolaz.neocontra.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.br.grupolaz.neocontra.NeoContra;
import com.br.grupolaz.neocontra.stages.HudStage;
import com.br.grupolaz.neocontra.util.MapLoader;
import com.br.grupolaz.neocontra.util.WorldUtils;

public class GameScreen implements Screen {

    private NeoContra game;
    private HudStage hud;
    private MapLoader mapLoader;
    private WorldUtils world;

    private Box2DDebugRenderer b2dRenderer;

    public GameScreen(NeoContra game) {
        this.game = game;
        this.mapLoader = new MapLoader("map/mapinha.tmx");
        hud = new HudStage(game.getSpriteBatch());
        
        game.alignCameraToWorldCenter();

        b2dRenderer = new Box2DDebugRenderer();
        world = new WorldUtils(mapLoader);
    }

    @Override
    public void show() {
    }

    public void update(float delta) {
        createInputHandler(delta);
        game.getCamera().update();

        mapLoader.getRenderer().setView(game.getCamera());
    }

    public void createInputHandler(float delta) {
        if(Gdx.input.isTouched()) { //Checking GameWorld
            game.getCamera().position.x += 100 * delta;
        }
    }

    @Override
    public void render(float delta) {
        update(delta);
        
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        mapLoader.getRenderer().render();

        b2dRenderer.render(world.getWorld(), game.getCamera().combined);

        game.getSpriteBatch().setProjectionMatrix(hud.getCamera().combined);
        
        hud.draw();
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
    }
    
}
