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
    private HudStage hud;
    private MapLoader mapLoader;
    private WorldUtils world;
    private GameActor player;
    private GameActor enemy;

    private Box2DDebugRenderer b2dRenderer;
    /**
     * <h2>GameStage</h2>
     * <p>Contrutor da classe GameStage é responsável
     * por criar a tela de jogo principal do jogo NeoContra.</p>
     * @param game tipo NeoContra
     * @param gameScreen tipo GameScreen
     */
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
     * @param delta tipo float
     */
    public void update(float delta) {
        GameUtils.createInputHandler((Player) player, delta);

        GameUtils.fixTimeStep(world.getWorld(), delta);

        followPlayer();

        game.getCamera().update();

        player.projectileOutOfBounds(game.getCamera());


        mapLoader.getRenderer().setView(game.getCamera());
    }

    /**
     * <h2>act</h2>
     * <P>a função act é responsável
     * por atualizar a lógica e os estados dos atores,
     *  renderizar o mapa, o mundo físico e os elementos
     *  do jogo, bem como a interface do usuário (HUD).
     *  É um componente importante no ciclo de atualização
     *  e renderização do jogo.</p>
     *
     * <h4>O que ele atualiza</h4>
     * <p>Atualiza o estágio e seus atores.</P>
     * <p>Renderiza o mapa do jogo.</p>
     * <p>Renderiza os objetos físicos em modo de depuração.</p>
     * <p>Renderiza os atores (personagens) usando o SpriteBatch do jogo.</p>
     * <p>Atualiza e desenha o HudStage para exibir o cabeçalho do jogo.</p>
     * @param delta tipo float
     */
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

    /**
     * <h2>setUpCharacters</h2>
     * <p>Metodo responsavel por chamar <b>setUpPlayer() e
        setUpEnemy()</b> para configurar os personagens do jogo</p>
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
        if(player != null) {
            player.remove();
        }

        player = new Player(world, world.createPerson(world.getWorld(), Constants.PLAYER_X, Constants.PLAYER_Y), TextureUtils.getPlayerAtlas().findRegion(Constants.PLAYER_STILL_REGION));
        addActor(player);
    }

    /**
     * <h2>setUpEnemy</h2>
     * <p>Esse método é reposnsavel por configurar o ator inimigo</p>
     */
    private void setUpEnemy() {
        enemy = new Enemy(world, world.createPerson(world.getWorld(), 23f, Constants.ENEMY_Y), TextureUtils.getEnemyAtlas().findRegion(Constants.ENEMY_STILL_REGION), (Player) player);
        addActor(enemy);
    }

    private void followPlayer() {
        if(player.getBody().getPosition().x <= 2.5f) {
            game.getCamera().position.x = 2.5f;
        } else if(player.getBody().getPosition().x >= 23f) {
            game.getCamera().position.x = 23f;
        } else {
            game.getCamera().position.x = player.getBody().getPosition().x;
        }

    }

    private void setUpMusic() {
        SoundsUtils.getThemeM().setLooping(true);
		SoundsUtils.getThemeM().play();
		SoundsUtils.getThemeM().setVolume(0.2f);
    }

    /**
     * <h2> dispose</h2>
     * <p> Libera recursos utilizados pelo
     * MapLoader, WorldUtils e Box2DDebugRenderer.</p>
     */
    public void dispose() {
        mapLoader.dispose();
        world.dispose();
        b2dRenderer.dispose();
    }
}
