package com.br.grupolaz.neocontra.stages;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.br.grupolaz.neocontra.NeoContra;
import com.br.grupolaz.neocontra.actors.Player;
import com.br.grupolaz.neocontra.screens.GameScreen;
import com.br.grupolaz.neocontra.util.Constants;
import com.br.grupolaz.neocontra.util.GameUtils;
import com.br.grupolaz.neocontra.util.MapLoader;
import com.br.grupolaz.neocontra.util.WorldUtils;

public class GameStage extends Stage {

    NeoContra game;
    Screen gameScreen;
    private HudStage hud;
    private MapLoader mapLoader;
    private WorldUtils world;
    private Player player;
    
    private Box2DDebugRenderer b2dRenderer;

    public GameStage(NeoContra game, GameScreen gameScreen) {
        this.game = game;
        this.gameScreen = gameScreen;

        this.mapLoader = new MapLoader(Constants.LEVEL1_MAP);
        hud = new HudStage(game.getSpriteBatch());

        game.alignCameraToWorldCenter();

        b2dRenderer = new Box2DDebugRenderer();
        world = new WorldUtils(mapLoader);
        setUpCharacters();
    }

    public void update(float delta) {
        GameUtils.createInputHandler(player, delta);

        GameUtils.fixTimeStep(world.getWorld(), delta);

        game.getCamera().position.x = player.getBody().getPosition().x;

        game.getCamera().update();

        mapLoader.getRenderer().setView(game.getCamera());
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        update(delta);

        mapLoader.getRenderer().render();

        b2dRenderer.render(world.getWorld(), game.getCamera().combined);

        game.getSpriteBatch().setProjectionMatrix(hud.getCamera().combined);
        
        hud.draw();

        game.getSpriteBatch().begin();
        player.draw(game.getSpriteBatch(), 1);
        game.getSpriteBatch().end();
    }

    private void setUpCharacters() {
        setUpPlayer();
    }

    private void setUpPlayer() {
        if(player != null) {
            player.remove();
        }

        player = new Player(world.createPerson(world.getWorld()), Constants.PLAYER_REGION);
        addActor(player);
    }

    public void dispose() {
        mapLoader.dispose();
        world.dispose();
        b2dRenderer.dispose();
    }

    
}
