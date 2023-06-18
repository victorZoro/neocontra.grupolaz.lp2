package com.br.grupolaz.neocontra.util;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class TextureUtils {
    private static TextureAtlas player_atlas = new TextureAtlas(Constants.PLAYER_ATLAS);
    private static final Texture lifeMedal = new Texture("life.png");
    private static final Texture gameOver = new Texture("gameover.png");

    public static TextureAtlas getPlayerAtlas() {
        return player_atlas;
    }

    public static Texture getLifeMedal() {
        return lifeMedal;
    }

    public static Texture getGameOver() {
        return gameOver;
    }
}
