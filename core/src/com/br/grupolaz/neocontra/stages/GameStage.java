package com.br.grupolaz.neocontra.stages;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.br.grupolaz.neocontra.NeoContra;
import com.br.grupolaz.neocontra.actors.Enemy;
import com.br.grupolaz.neocontra.actors.GameActor;
import com.br.grupolaz.neocontra.actors.Player;
import com.br.grupolaz.neocontra.enums.Layers;
import com.br.grupolaz.neocontra.interactive.Ceiling;
import com.br.grupolaz.neocontra.interactive.Ground;
import com.br.grupolaz.neocontra.interactive.SeaLevel;
import com.br.grupolaz.neocontra.interactive.Stairs;
import com.br.grupolaz.neocontra.screens.GameScreen;
import com.br.grupolaz.neocontra.util.*;

import java.util.ArrayList;

/**
 * <h2>GameStage</h2>
 * <p>
 * A classe GameStage é responsável por coordenar
 * a lógica do jogo, incluindo a atualização dos personagens,
 * a renderização do mapa, a interação física e a exibição
 * dos elementos na tela.
 * </p>
 *
 * <h3>package</h3>
 * <p>
 * stages
 * </p>
 *
 * <h3>Variaveis</h3>
 * <p>
 * +game: NeoContra
 * </p>
 * <p>
 * +gameScreen: Screen
 * </p>
 * <p>
 * -hud:HudStage
 * </p>
 * <p>
 * -mapLoader: MapLoader
 * </p>
 * <p>
 * -world: WorldUtils
 * </p>
 * <p>
 * -player: GameActor
 * </p>
 * <p>
 * -enemy: GameActor
 * </p>
 * <p>
 * -b2dRenderer: Box2DDebugRenderer
 * </p>
 *
 * <h3>Métodos</h3>
 * <p>
 * +GameStage(NeoContra, GameScreen)
 * </p>
 * <p>
 * +update(float): void
 * </p>
 * <p>
 * +act(float): void
 * </p>
 * <p>
 * -setUpCharacters(): void
 * </p>
 * <p>
 * -setUpPlayer(): void
 * </p>
 * <p>
 * -setUpEnemy(): void
 * </p>
 * <p>
 * +dispose(): void
 * </p>
 */
// Inspired by Martian Run and Brent Aureli Codes
public class GameStage extends Stage {

    NeoContra game;
    Screen gameScreen;
    private final HudStage hud;
    private final MapLoader mapLoader;
    private final WorldUtils world;
    private GameActor player;
    private GameActor player2;
    private GameActor enemy;
    private final String level;

    private final Box2DDebugRenderer b2dRenderer;

    private int numEnemies;
    ArrayList<Enemy> enemies = new ArrayList<>();

    private boolean singlePlayer;

    float areaMinX;
    float areaMaxX;
    float timeSpawnEnemy = 0;
    private float spawnTimer = 0;
    private float spawnEnemyInterval = 20.0f;

    /**
     * <h2>GameStage</h2>
     * <p>
     * Contrutor da classe GameStage é responsável
     * por criar a tela de jogo principal do jogo NeoContra.
     * </p>
     *
     * @param game       tipo NeoContra
     * @param gameScreen tipo GameScreen
     */
    public GameStage(NeoContra game, GameScreen gameScreen, String level, boolean singlePlayer, Stage oldStage) {
        this.game = game;
        this.gameScreen = gameScreen;
        this.level = level;
        this.singlePlayer = singlePlayer;
        oldStage.dispose();

        game.alignCameraToWorldCenter();

        b2dRenderer = new Box2DDebugRenderer();
        // b2dRenderer.setDrawBodies(false);
        this.mapLoader = new MapLoader(this.level);
        world = new WorldUtils();

        setUpCharacters();
        setUpMap();
        setUpMusic();

        hud = new HudStage((Player) player, (Player) player2);

        world.getWorld().setContactListener(new WorldContactListener());

    }

    private void setUpMap() {
        for (RectangleMapObject object : mapLoader.getMap().getLayers().get(Layers.GROUND.getLayer()).getObjects()
                .getByType(RectangleMapObject.class)) {
            Rectangle r = object.getRectangle();
            new Ground(world.getWorld(), mapLoader.getMap(), r);
        }

        for (RectangleMapObject object : mapLoader.getMap().getLayers().get(Layers.STAIRS.getLayer()).getObjects()
                .getByType(RectangleMapObject.class)) {
            Rectangle r = object.getRectangle();
            new Stairs(world.getWorld(), mapLoader.getMap(), r);
        }

        for (RectangleMapObject object : mapLoader.getMap().getLayers().get(Layers.SEALEVEL.getLayer()).getObjects()
                .getByType(RectangleMapObject.class)) {
            Rectangle r = object.getRectangle();
            new SeaLevel(world.getWorld(), mapLoader.getMap(), r);
        }

        for (RectangleMapObject object : mapLoader.getMap().getLayers().get(Layers.CEILING.getLayer()).getObjects()
                .getByType(RectangleMapObject.class)) {
            Rectangle r = object.getRectangle();
            new Ceiling(world.getWorld(), mapLoader.getMap(), r);
        }
    }

    /**
     * <h2>update</h2>
     * <P>
     * O método update é responsável por atualizar o
     * estado do jogo em intervalos regulares.
     * </p>
     *
     * <h4>O que ele atualiza:</h4>
     * <p>
     * Atualiza a entrada de controle do jogador.
     * </p>
     * <p>
     * Atualiza o mundo físico chamando fixTimeStep de GameUtils.
     * </p>
     * <P>
     * Atualiza a posição da câmera para seguir o personagem principal.
     * </p>
     * <p>
     * Verifica se os projéteis do jogador estão fora da câmera e os remove, se
     * necessário.
     * </p>
     * <P>
     * Atualiza a visualização do mapa para a câmera atual.
     * </p>
     */
    public void update() {
        GameUtils.createInputHandler((Player) player, (Player) player2, level);

        GameUtils.fixTimeStep(world.getWorld());

        if (GameUtils.isKonamiCode()) {
            ((Player) player).setLifeCount(9999);
            if (!singlePlayer) {
                ((Player) player2).setLifeCount(9999);
            }
        }

        startFollowPlayer();
        stayInBounds((Player) player);
        stayInBounds((Player) player2);

        game.getCamera().update();

        player.projectileOutOfBounds(game.getCamera());

        for (Enemy enemy : enemies) {
            enemy.projectileOutOfBoundsEnemy(game.getCamera());
        }
        

        mapLoader.getRenderer().setView(game.getCamera());
    }

    /**
     * <h2>act</h2>
     * <P>
     * a função act é responsável
     * por atualizar a lógica e os estados dos atores,
     * renderizar o mapa, o mundo físico e os elementos
     * do jogo, bem como a interface do usuário (HUD).
     * É um componente importante no ciclo de atualização
     * e renderização do jogo.
     * </p>
     *
     * <h4>O que ele atualiza</h4>
     * <p>
     * Atualiza o estágio e seus atores.
     * </P>
     * <p>
     * Renderiza o mapa do jogo.
     * </p>
     * <p>
     * Renderiza os objetos físicos em modo de depuração.
     * </p>
     * <p>
     * Renderiza os atores (personagens) usando o SpriteBatch do jogo.
     * </p>
     * <p>
     * Atualiza e desenha o HudStage para exibir o cabeçalho do jogo.
     * </p>
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

        for (Enemy enemy : enemies) {
            enemy.act(delta);
        }

        game.getSpriteBatch().begin();
        player.draw(game.getSpriteBatch(), 0);

        for (Enemy enemy : enemies) {
            enemy.draw(game.getSpriteBatch(), 0);
        }
        game.getSpriteBatch().end();

        game.getSpriteBatch().setProjectionMatrix(hud.getCamera().combined);

        hud.act(delta);
        hud.draw();


    }

    /**
     * <h2>setUpCharacters</h2>
     * <p>
     * Metodo responsavel por chamar <b>setUpPlayer() e
     * setUpEnemy()</b> para configurar os personagens do jogo
     * </p>
     */
    private void setUpCharacters() {
        if (!singlePlayer) {
            setUpPlayer();
            setUpPlayer2();
        } else {
            setUpPlayer();
        }
        setUpEnemy();
    }

    /**
     * <h2>setUpPlayer</h2>
     * <P>
     * Esse método é responsavel por confugurar o ator player
     * </p>
     */
    private void setUpPlayer() {
        if (player != null) {
            player.remove();
        }

        switch (level) {
            case Constants.LEVEL1_MAP:
                player = new Player(world.getWorld(),
                        TextureUtils.getPlayerAtlas().findRegion(Constants.PLAYER_STILL_REGION),
                        (-10f / Constants.PIXELS_PER_METER), (100f / Constants.PIXELS_PER_METER));
                break;
            case Constants.LEVEL2_MAP:
                player = new Player(world.getWorld(),
                        TextureUtils.getPlayerAtlas().findRegion(Constants.PLAYER_STILL_REGION),
                        (-20f / Constants.PIXELS_PER_METER), (150f / Constants.PIXELS_PER_METER));
                break;
            case Constants.LEVEL3_MAP:
                player = new Player(world.getWorld(),
                        TextureUtils.getPlayerAtlas().findRegion(Constants.PLAYER_STILL_REGION),
                        (1f / Constants.PIXELS_PER_METER), (10f / Constants.PIXELS_PER_METER));
                break;
            case Constants.LEVEL4_MAP:
                player = new Player(world.getWorld(),
                        TextureUtils.getPlayerAtlas().findRegion(Constants.PLAYER_STILL_REGION),
                        (-10f / Constants.PIXELS_PER_METER), (80f / Constants.PIXELS_PER_METER));
                break;
        }

        addActor(player);
    }

    private void setUpPlayer2() {
        if (player2 != null) {
            player2.remove();
        }

        switch (level) {
            case Constants.LEVEL1_MAP:
                player2 = new Player(world.getWorld(),
                        TextureUtils.getPlayerAtlas().findRegion(Constants.PLAYER_STILL_REGION),
                        (-0.1f / Constants.PIXELS_PER_METER), (100f / Constants.PIXELS_PER_METER));
                break;
            case Constants.LEVEL2_MAP:
                player2 = new Player(world.getWorld(),
                        TextureUtils.getPlayerAtlas().findRegion(Constants.PLAYER_STILL_REGION),
                        (-22f / Constants.PIXELS_PER_METER), (150f / Constants.PIXELS_PER_METER));
                break;
            case Constants.LEVEL3_MAP:
                player2 = new Player(world.getWorld(),
                        TextureUtils.getPlayerAtlas().findRegion(Constants.PLAYER_STILL_REGION),
                        (-1f / Constants.PIXELS_PER_METER), (10f / Constants.PIXELS_PER_METER));
                break;
            case Constants.LEVEL4_MAP:
                player2 = new Player(world.getWorld(),
                        TextureUtils.getPlayerAtlas().findRegion(Constants.PLAYER_STILL_REGION),
                        (-0.1f / Constants.PIXELS_PER_METER), (80f / Constants.PIXELS_PER_METER));
                break;
        }

        addActor(player2);
    }

    /**
     * <h2>setUpEnemy</h2>
     * <p>
     * Esse método é reposnsavel por configurar o ator inimigo
     * </p>
     */
    private void setUpEnemy() {
        switch (level) {
            case Constants.LEVEL1_MAP:
                numEnemies = 3;
                areaMinX = 7;
                areaMaxX = 11;
                for (int i = 0; i < numEnemies; i++) {
                    enemy = new Enemy(world.getWorld(),
                            TextureUtils.getEnemyAtlas().findRegion(Constants.ENEMY_STILL_REGION),
                            (Player) player, getRandomX(areaMinX, areaMaxX), Constants.ENEMY_Y, 1);
                    enemies.add((Enemy) enemy);
                    addActor(enemy);
                }
                break;
            case Constants.LEVEL2_MAP:
                numEnemies = 4;
                areaMinX = 3;
                areaMaxX = 22;
                for (int i = 0; i < numEnemies; i++) {
                    enemy = new Enemy(world.getWorld(),
                            TextureUtils.getEnemyAtlas().findRegion(Constants.ENEMY_STILL_REGION),
                            (Player) player, getRandomX(areaMinX, areaMaxX), Constants.ENEMY_Y * 5, 2);
                    enemies.add((Enemy) enemy);
                    addActor(enemy);
                }
                break;
            case Constants.LEVEL3_MAP:
                numEnemies = 6;
                areaMinX = 3;
                areaMaxX = 26;
                for (int i = 0; i < numEnemies; i++) {
                    enemy = new Enemy(world.getWorld(),
                            TextureUtils.getEnemyAtlas().findRegion(Constants.ENEMY_STILL_REGION),
                            (Player) player, getRandomX(areaMinX, areaMaxX), Constants.ENEMY_Y * 5, 3);
                    enemies.add((Enemy) enemy);
                    addActor(enemy);
                }
                break;
            case Constants.LEVEL4_MAP:
                numEnemies = 10;
                areaMinX = 1;
                areaMaxX = 32;
                for (int i = 0; i < numEnemies; i++) {
                    enemy = new Enemy(world.getWorld(),
                            TextureUtils.getEnemyAtlas().findRegion(Constants.ENEMY_STILL_REGION),
                            (Player) player, getRandomX(areaMinX, areaMaxX), Constants.ENEMY_Y, 4);
                    enemies.add((Enemy) enemy);
                    addActor(enemy);
                }
                break;
        }
    }

    private float getRandomX(float areaMinX, float areaMaxX) {
        return MathUtils.random(areaMinX, areaMaxX);
    }

    public void stayInBounds(Player player1) {
        if (player.getBody().getPosition().x < 0) {
            player.getBody().setTransform(0, player.getBody().getPosition().y, player.getBody().getAngle());
        } else if (player.getBody().getPosition().x >= game.getCamera().position.x + 2.5f) {
            player.getBody().setTransform(game.getCamera().position.x + 2.5f, player.getBody().getPosition().y,
                    player.getBody().getAngle());
        }
    }

    private void startFollowPlayer() {
        float cameraCenter;

        if (!singlePlayer) {
            if (player.isAlive() && player2.isAlive()) {
                cameraCenter = (player.getBody().getPosition().x + player2.getBody().getPosition().x) / 2;
            } else if (!player.isAlive()) {
                cameraCenter = player2.getBody().getPosition().x;
            } else if (!player2.isAlive()) {
                cameraCenter = player.getBody().getPosition().x;
            } else {
                cameraCenter = game.getCamera().position.x;
            }
        } else {
            cameraCenter = player.getBody().getPosition().x;
        }

        followPlayer(cameraCenter);
    }

    public void followPlayer(float cameraCenter) {
        if (cameraCenter <= 2.5f) {
            game.getCamera().position.x = 2.5f;
        } else {
            switch (level) {
                case Constants.LEVEL1_MAP:
                    game.getCamera().position.x = Math.min(cameraCenter, 23f);
                    break;
                case Constants.LEVEL2_MAP:
                    game.getCamera().position.x = Math.min(cameraCenter, 24f);
                    break;
                case Constants.LEVEL3_MAP:
                    game.getCamera().position.x = Math.min(cameraCenter, 37f);
                    break;
                case Constants.LEVEL4_MAP:
                    game.getCamera().position.x = Math.min(cameraCenter, 45f);
                    break;
            }
        }
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
        SoundsUtils.getThemeM().dispose();
    }
}
