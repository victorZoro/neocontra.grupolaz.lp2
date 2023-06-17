package com.br.grupolaz.neocontra.util;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class TextureUtils {
    private static TextureAtlas atlas = new TextureAtlas(Constants.CHARACTERS_ATLAS);

    public static TextureAtlas getAtlas() {
        return atlas;
    }
}
