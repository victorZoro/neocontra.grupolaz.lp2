package com.br.grupolaz.neocontra.util;

import com.br.grupolaz.neocontra.enums.GameState;

//Inspired by MartianRun
public class GameManager {
    private static GameManager instance = new GameManager();

    private GameState gameState;
    

    public static GameManager getInstance() {
        return instance;
    }

    private GameManager() {
        gameState = GameState.OVER;
    }

    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    //studying preferences class
}
