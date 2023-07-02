package com.br.grupolaz.neocontra;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.br.grupolaz.neocontra.screens.GameScreen;
import com.br.grupolaz.neocontra.util.Constants;

/**
 * @author <p>Anna Luisa, Lucas Vinicios, Victor gabriel</p>
 * <h2>NeoContra</h2>>
 * <P>NeoContra é a classe principal do
 *  jogo e gerencia a inicialização e configuração de
 * várias componentes essenciais, como o SpriteBatch,
 * a OrthographicCamera e o FitViewport. Ela também define a tela inicial do jogo como
 * GameScreen e lida com a reprodução de sons.</p>
 *
 * <h3>package</h3>
 * <p>neocontra</p>
 *
 * <h3>Variaveis</h3>
 * <p>-batch: SpriteBatch </p>
 * <p>-camera: OrthographicCamera </p>
 * <p>-viewport: Viewport</p>
 *
 * <h3>Metoodos</h3>
 * <p>+create(): void</p>
 * <p>+getSpriteBatch(): SpriteBatch</p>
 * <p>+getCamera(): OrthographicCamera</p>
 * <p>+getViewport(): Viewport</p>
 * <p>+alignCameraToWorldCenter():void</p>
 * <p>+render(): void</p>
 * <p>+dispose(): void</p>
 */
public class NeoContra extends Game {

	private SpriteBatch batch;
	private OrthographicCamera camera;
	private Viewport viewport;

	/**
	 * <h2>create</h2>
	 * <p>O create é responsável por criar e configurar objetos
	 *  importantes do jogo, como o <b>SpriteBatch, a OrthographicCamera e o FitViewport.</b></p>
	 */
	public void create () {
		batch = new SpriteBatch();
		camera = new OrthographicCamera();
		viewport = new FitViewport(Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT, camera);
		setScreen(new GameScreen(this));
	}
	/**
	 * <h2>getSpriteBatch</h2>
	 * <p>Retona batch que desenha os elementos graficos do jogo</p>
	 * @return batch tipo SpriteBatch (get)
	 */
	public SpriteBatch getSpriteBatch () {
		return batch;
	}
	/**
	 * <h2>getCamera</h2>
	 * <p>Retona camera que é utilizada para visualizara acena do jogo</p>
	 * @return camera tipo OrthographicCamera (get)
	 */
	public OrthographicCamera getCamera() {
		return camera;
	}

	/**
	 * <h2> getViewport</h2>
	 * <p>Rtorna viewport que controla o redimencionamento da tela</p>
	 * @return viewport
	 */
	public Viewport getViewport() {
		return viewport;
	}
	/**
	 * <h2>alignCameraToWorldCenter</h2>
	 * <p>alinha a câmera no centro do mundo do jogo.</p>
	 */
	public void alignCameraToWorldCenter() {
		//posição central
		camera.position.set(viewport.getWorldWidth() / 2, viewport.getWorldHeight() / 2, 0);
	}

	/**
	 * <h2>render</h2>
	 * <p> O metodo render é responsável
	 * por renderizar os elementos do jogo.</p>
	 */
	public void render () {
		super.render();
	}

	/**
	 * <h2>dispose</h2>
	 * <p>dispose é esponsável por liberar recursos
	 * utilizados pelo jogo.</p>
	 */
	public void dispose () {
	}
}
