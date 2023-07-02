package com.br.grupolaz.neocontra.util;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
/**
 * <h2>TextureUtils</h2>
 * <p>A classe TextureUtils facilita o acesso 
 * e o uso de texturas e atlases de texturas 
 * no jogo, fornecendo métodos para obter as 
 * texturas desejadas.</p>
 * 
 * <h3>package</h3>
 * <p>util</p>
 * 
 * <h3>Variaveis</h3>
 * <p>-player_atlas: TextureAtlas</p>
 * <p>-lifeMedal: Texture</p>
 * <p>-gameOver: Texture</p>
 * <p>-playerBullet: Texture</p>
 * 
 * <h3>Métodos</h3>
 * <p>+static getPlayerAtlas(): TextureAtlas </p>
 * <p>+static getEnemyAtlas(): TextureAtlas</p>
 * <p>+static getLifeMedal(): Texture </p>
 * <p>+static getGameOver(): Texture</p>
 * <p>+static getPlayerBullet(): Texture</p>
 */
public class TextureUtils {
    private static TextureAtlas player_atlas = new TextureAtlas(Constants.PLAYER_ATLAS);
    private static TextureAtlas enemy_atlas = new TextureAtlas(Constants.ENEMY_ATLAS);
    private static final Texture lifeMedal = new Texture("life.png");
    private static final Texture gameOver = new Texture("gameover.png");
    private static final Texture playerBullet = new Texture("playerBullet.png");

    /**
     * <h2>getPlayerAtlas</h2>
     * <p> retorna o atlas de texturas do jogador</p>
     * @return player_atlas tipo TextureAtlas(get)
     */
    public static TextureAtlas getPlayerAtlas() {
        return player_atlas;
    }

    /**
     * <h2>getLifeMedal</h2>
     * <p>retona a textura da imagem de vida</p>
     * @return lifeMedal tipo Texture(get)
     */
    public static TextureAtlas getEnemyAtlas() {
        return enemy_atlas;
    }

    public static Texture getLifeMedal() {
        return lifeMedal;
    }
    /**
     * <h2>getGameOver</h2>
     * <P>retorna a imagem de game over </p>
     * @return gameOver tipo Texture(get)
     */
    public static Texture getGameOver() {
        return gameOver;
    }

    /**
     * <h2>getPlayerBullet<h2>
     * <p>retorna a imagem bala do jogador</p>
     * @return playerBullet tipo Texture(get)
     */
    public static Texture getPlayerBullet(){
        return playerBullet;
    }

}
