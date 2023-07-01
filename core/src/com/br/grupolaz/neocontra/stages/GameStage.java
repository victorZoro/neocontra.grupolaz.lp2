package com.br.grupolaz.neocontra.stages;


import com.badlogic.gdx.Screen;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.br.grupolaz.neocontra.NeoContra;
import com.br.grupolaz.neocontra.actors.Enemy;
import com.br.grupolaz.neocontra.actors.GameActor;
import com.br.grupolaz.neocontra.actors.Player;
import com.br.grupolaz.neocontra.screens.GameScreen;
import com.br.grupolaz.neocontra.util.Constants;
import com.br.grupolaz.neocontra.util.GameUtils;
import com.br.grupolaz.neocontra.util.MapLoader;
import com.br.grupolaz.neocontra.util.SoundsUtils;
import com.br.grupolaz.neocontra.util.TextureUtils;
import com.br.grupolaz.neocontra.util.WorldUtils;

//Inspired by Martian Run and Brent Aureli Codes
public class GameStage extends Stage {

    NeoContra game;
    Screen gameScreen;
    private HudStage hud;
    private MapLoader mapLoader;
    private WorldUtils world;
    private GameActor player;
    private GameActor enemy;
    
    private Box2DDebugRenderer b2dRenderer;

    public GameStage(NeoContra game, GameScreen gameScreen) {
        this.game = game;
        this.gameScreen = gameScreen;

        game.alignCameraToWorldCenter();

        b2dRenderer = new Box2DDebugRenderer();
        this.mapLoader = new MapLoader(Constants.LEVEL1_MAP);
        world = new WorldUtils(mapLoader);

        setUpCharacters();
        setUpMusic();

        hud = new HudStage(game.getSpriteBatch(), (Player) player);

    }

    public void update(float delta) {
        GameUtils.createInputHandler((Player) player, delta);

        GameUtils.fixTimeStep(world.getWorld(), delta);

        game.getCamera().position.x = player.getBody().getPosition().x;

        game.getCamera().update();

        player.projectileOutOfBounds(game.getCamera());


        mapLoader.getRenderer().setView(game.getCamera());
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        update(delta);

        mapLoader.getRenderer().render();

        b2dRenderer.render(world.getWorld(), game.getCamera().combined);

        game.getSpriteBatch().setProjectionMatrix(game.getCamera().combined);
        player.act(delta);
        enemy.act(delta);
        game.getSpriteBatch().begin();
        player.draw(game.getSpriteBatch(), 0);
        enemy.draw(game.getSpriteBatch(), 0);
        game.getSpriteBatch().end();

        game.getSpriteBatch().setProjectionMatrix(hud.getCamera().combined);
        
        hud.act(delta);
        hud.draw();
    }

    private void setUpCharacters() {
        setUpPlayer();
        setUpEnemy();
    }

    private void setUpPlayer() {
        if(player != null) {
            player.remove();
        }

        player = new Player(world, world.createPerson(world.getWorld(), Constants.PLAYER_X, Constants.PLAYER_Y), TextureUtils.getPlayerAtlas().findRegion(Constants.PLAYER_STILL_REGION));
        addActor(player);
    }

    private void setUpEnemy() {
        enemy = new Enemy(world, world.createPerson(world.getWorld(), Constants.ENEMY_X, Constants.ENEMY_Y), TextureUtils.getEnemyAtlas().findRegion(Constants.ENEMY_STILL_REGION));
        addActor(enemy);
    }

    private void setUpMusic() {
        SoundsUtils.getThemeM().setLooping(true);
		SoundsUtils.getThemeM().play();
		SoundsUtils.getThemeM().setVolume(0.2f);
    }

    public void dispose() {
        mapLoader.dispose();
        world.dispose();
        b2dRenderer.dispose();
    }
}
