package com.br.grupolaz.neocontra.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.br.grupolaz.neocontra.NeoContra;
import com.br.grupolaz.neocontra.stages.GameStage;

//Inspired by Martian Run
/**
 * <h2>GameStage</h2>
 * <p>GameScreen , que representa uma tela do jogo. 
 * Essa classe é responsável por renderizar a tela, atualizar as informações visuais e lidar 
 * com eventos relacionados à interação do jogador.</p>
 * 
 * <h3>package</h3> 
 * <p>Screen</p>
 * 
 * <h3>variaveis</h3>
 * <p>+gameStage: GameStage</p>
 * <p>-game: NeoContra</p>
 * 
 * <h3>métodos</h3>
 * <p>+GameScreen(NeoContra)</p>
 * <p>+show(): void</p>
 * <p>+render(float): void</p>
 * <p>+resize(int, int): void </p> 
 * <p>+pause(): void</p>
 * <p>+resume(): void</p>
 * <p>+hide(): void</p>
 * <p>+dispose(): void</p>
 */
public class GameScreen implements Screen {

    GameStage gameStage;

    private NeoContra game;
    /**
     * <h2> Costrutor GameScreen</h2>
     * <p>O Costrutor do GameScreen é responsável por criar e inicializar um 
     * objeto GameStage dentro da classe GameScreen</p>
     * @param game tipo NeoContra
     */
    public GameScreen(NeoContra game) {
        this.game = game;
        gameStage = new GameStage(game, this);
    }
    /**
     * <h2>show</h2>
     */
    public void show() {
    }



    
    /**
     * <h2>render</h2>
     * <p>É responsável por renderizar a tela do jogo. Ele limpa o buffer de cor 
     * e chama os métodos draw e act do gameStage para atualizar e desenhar 
     * os elementos do estágio do jogo.</p>
     * @param delta tipo float
     */
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        gameStage.draw();
        gameStage.act(delta);
    }


    /**
     * <h2>resize</h2>
     * <p>O metodo resize é responsavel por lidar com o redimensionamento da tela do jogo.</p>
     * @param width tipo int
     * @param height tipo int
     */
    public void resize(int width, int height) {
        game.getViewport().update(width, height);
    }

    /**
     * <h2>pause</h2>
     */
    public void pause() {
    }

   /**
    * <h2>resume</h2>
    */
    public void resume() {
    }

    /**
     * <h2>hide</h2>
     */
    public void hide() {
    }

    /**
     * <h2>dispose</h2>
     * <p>Esse metodo tem a função de realizar a limpeza 
     * e liberação de recursos utilizados pela tela do jogo.</p>
     */
    public void dispose() {
        gameStage.dispose();
    }
    
}
