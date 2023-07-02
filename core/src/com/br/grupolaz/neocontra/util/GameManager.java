package com.br.grupolaz.neocontra.util;

import com.br.grupolaz.neocontra.enums.GameState;

/**
 * <h2>GameManager</h2>
 * <h3>Função</h3>
 * <p>GemeManager tem a função de gerenciar o estado do jogo</p>
 * <>A classe GemeManager armazena o estado atual do jogo, permite que outros componentes 
 * acessem e modifiquem esse estado. Ela também fornece uma única instância 
 * globalmente acessível para garantir um gerenciamento consistente do estado 
 * do jogo em várias partes do código.
 * <h3>package</h3>
 * <p>util</p>
 *  <h3>variaveis</h3>
 * <p>gameState: GameState</p>
 */
//Inspired by Martian Run
public class GameManager {
    private static GameManager instance = new GameManager();

    private GameState gameState;
    
    /**
     * <h2>getInstance</h2>
     * <p> permite a manipulação do estado do jogo</p>
     * @return tipo GameManager return instance
     */
    public static GameManager getInstance() {
        return instance;
    }
    /**
     * <h2>GameManager</h2>
     * Define o estado inicial do jogo
     */
    private GameManager() {
        gameState = GameState.OVER;
    }
    /**
     * <h2>getGameState</h2>
     * <p>etorna o estado atual do jogo armazenado na variável gameState</p>
     * @return gameState tipo GameState(get) 
     */
    public GameState getGameState() {
        return gameState;
    }

    /**
     * <h2>setGameState</h2>
     * <p>atualiza o estado do jogo com o valor fornecido como argumento.</p>
     * @param gameState tipo GameState(set)
     */
    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    //studying preferences class
}
