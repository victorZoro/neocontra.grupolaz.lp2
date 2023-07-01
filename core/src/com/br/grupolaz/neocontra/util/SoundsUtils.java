package com.br.grupolaz.neocontra.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

public class SoundsUtils {
    private static final Music THEMEMUSIC = Gdx.audio.newMusic(Gdx.files.internal("sounds/ThemeMusic.wav"));
    private static final Sound SHOT_SOUND = Gdx.audio.newSound(Gdx.files.internal("sounds/shot.wav"));

    public static Sound getShotSound(){
        return SHOT_SOUND;
    }

    public static Music getThemeM(){
        return THEMEMUSIC;
    }
}

