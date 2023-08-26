package com.br.grupolaz.neocontra.stages;


import com.badlogic.gdx.Screen;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.br.grupolaz.neocontra.NeoContra;
import com.br.grupolaz.neocontra.actors.Enemy;
import com.br.grupolaz.neocontra.actors.GameActor;
import com.br.grupolaz.neocontra.actors.Player;
import com.br.grupolaz.neocontra.enums.Layers;
import com.br.grupolaz.neocontra.interactive.*;
import com.br.grupolaz.neocontra.screens.GameScreen;
import com.br.grupolaz.neocontra.util.*;

/**
 * <h2>GameStage</h2>
 * <p>A classe GameStage é responsável por coordenar
 * a lógica do jogo, incluindo a atualização dos personagens,
 * a renderização do mapa, a interação física e a exibição
 * dos elementos na tela.</p>
 *
 * <h3>package</h3>
 * <p>stages</p>
 *
 * <h3>Variaveis</h3>
 * <p>+game: NeoContra</p>
 * <p>+gameScreen: Screen</p>
 * <p>-hud:HudStage</p>
 * <p>-mapLoader: MapLoader</p>
 * <p>-world: WorldUtils</p>
 * <p>-player: GameActor</p>
 * <p>-enemy: GameActor </p>
 * <p>-b2dRenderer: Box2DDebugRenderer</p>
 *
 * <h3>Métodos</h3>
 * <p>+GameStage(NeoContra, GameScreen)</p>
 * <p>+update(float): void</p>
 * <p>+act(float): void</p>
 * <p>-setUpCharacters(): void</p>
 * <p>-setUpPlayer(): void </p>
 * <p>-setUpEnemy(): void</p>
 * <p>+dispose(): void</p>
 */
//Inspired by Martian Run and Brent Aureli Codes
public class GameStage extends Stage {

    NeoContra game;
    Screen gameScreen;
    private final HudStage hud;
    private final MapLoader mapLoader;
    private final WorldUtils world;
    private GameActor player;
    private GameActor enemy;
    private final String level;

    private final Box2DDebugRenderer b2dRenderer;

    /**
     * <h2>GameStage</h2>
     * <p>Contrutor da classe GameStage é responsável
     * por criar a tela de jogo principal do jogo NeoContra.</p>
     *
     * @param game       tipo NeoContra
     * @param gameScreen tipo GameScreen
     */
    public GameStage(NeoContra game, GameScreen gameScreen, String level) {
        this.game = game;
        this.gameScreen = gameScreen;
        this.level = level;

        game.alignCameraToWorldCenter();

        b2dRenderer = new Box2DDebugRenderer();
//        b2dRenderer.setDrawBodies(false);
        this.mapLoader = new MapLoader(this.level);
        world = new WorldUtils();

        setUpCharacters();
        setUpMap();
        setUpMusic();

        hud = new HudStage((Player) player);

        world.getWorld().setContactListener(new WorldContactListener());

    }

    private void setUpMap() {
        for (RectangleMapObject object : mapLoader.getMap().getLayers().get(Layers.GROUND.getLayer()).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle r = object.getRectangle();
            new Ground(world.getWorld(), mapLoader.getMap(), r);
        }

        for (RectangleMapObject object : mapLoader.getMap().getLayers().get(Layers.STAIRS.getLayer()).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle r = object.getRectangle();
            new Stairs(world.getWorld(), mapLoader.getMap(), r);
        }


        for (RectangleMapObject object : mapLoader.getMap().getLayers().get(Layers.SEALEVEL.getLayer()).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle r = object.getRectangle();
            new SeaLevel(world.getWorld(), mapLoader.getMap(), r);
        }

        for (RectangleMapObject object : mapLoader.getMap().getLayers().get(Layers.CEILING.getLayer()).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle r = object.getRectangle();
            new Ceiling(world.getWorld(), mapLoader.getMap(), r);
        }
    }


    /**
     * <h2>update</h2>
     * <P>O método update é responsável por atualizar o
     * estado do jogo em intervalos regulares.</p>
     *
     * <h4>O que ele atualiza:</h4>
     * <p>Atualiza a entrada de controle do jogador.</p>
     * <p>Atualiza o mundo físico chamando fixTimeStep de GameUtils.</p>
     * <P>Atualiza a posição da câmera para seguir o personagem principal.</p>
     * <p>Verifica se os projéteis do jogador estão fora da câmera e os remove, se necessário.</p>
     * <P>Atualiza a visualização do mapa para a câmera atual.</p>
     *
     */
    public void update() {
        GameUtils.createInputHandler((Player) player);

        GameUtils.fixTimeStep(world.getWorld());

        followPlayer();

        game.getCamera().update();

        player.projectileOutOfBounds(game.getCamera());

        mapLoader.getRenderer().setView(game.getCamera());
    }

    /**
     * <h2>act</h2>
     * <P>a função act é responsável
     * por atualizar a lógica e os estados dos atores,
     * renderizar o mapa, o mundo físico e os elementos
     * do jogo, bem como a interface do usuário (HUD).
     * É um componente importante no ciclo de atualização
     * e renderização do jogo.</p>
     *
     * <h4>O que ele atualiza</h4>
     * <p>Atualiza o estágio e seus atores.</P>
     * <p>Renderiza o mapa do jogo.</p>
     * <p>Renderiza os objetos físicos em modo de depuração.</p>
     * <p>Renderiza os atores (personagens) usando o SpriteBatch do jogo.</p>
     * <p>Atualiza e desenha o HudStage para exibir o cabeçalho do jogo.</p>
     *
     * @param delta tipo float
     */
    public void act(float delta) {
        super.act(delta);
        update();

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

    /**
     * <h2>setUpCharacters</h2>
     * <p>Metodo responsavel por chamar <b>setUpPlayer() e
     * setUpEnemy()</b> para configurar os personagens do jogo</p>
     */
    private void setUpCharacters() {
        setUpPlayer();
        setUpEnemy();
    }

    /**
     * <h2>setUpPlayer</h2>
     * <P> Esse método é responsavel por confugurar o ator player</p>
     */
    private void setUpPlayer() {
        if (player != null) {
            player.remove();
        }

//        if(this.level.equals(Constants.LEVEL1_MAP)) {
            player = new Player(world.getWorld(), TextureUtils.getPlayerAtlas().findRegion(Constants.PLAYER_STILL_REGION), Constants.PLAYER_X, Constants.PLAYER_Y);
//        }

        addActor(player);
    }

    /**
     * <h2>setUpEnemy</h2>
     * <p>Esse método é reposnsavel por configurar o ator inimigo</p>
     */
    private void setUpEnemy() {
        enemy = new Enemy(world.getWorld(), TextureUtils.getEnemyAtlas().findRegion(Constants.ENEMY_STILL_REGION), (Player) player, Constants.ENEMY_X, Constants.ENEMY_Y);
        addActor(enemy);
    }

    private void followPlayer() {
        if (player.getBody().getPosition().x <= 2.5f) {
            game.getCamera().position.x = 2.5f;
        } else {
            game.getCamera().position.x = Math.min(player.getBody().getPosition().x, 23f);
        }

        if(player.getBody().getPosition().y <= 3.5f) {
            game.getCamera().position.y = 2f;
        } else {
            game.getCamera().position.y = 3f;
        }

//        game.getCamera().position.y = player.getBody().getPosition().y;
        System.out.println(game.getCamera().position.y);
    }

    private void setUpMusic() {
        SoundsUtils.getThemeM().setLooping(true);
        SoundsUtils.getThemeM().play();
        SoundsUtils.getThemeM().setVolume(0.2f);
        System.out.println("Musica Inicializada");
    }

    public void dispose() {
        mapLoader.dispose();
        world.dispose();
        b2dRenderer.dispose();
    }
}
