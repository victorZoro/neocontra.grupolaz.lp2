package com.br.grupolaz.neocontra.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
/**
 * <h2>SoundsUtils</h2>
 * <p>A classe SoundsUtils encapsula o acesso 
 * aos arquivos de áudio do jogo, fornecendo
 *  métodos para obter o efeito sonoro do disparo 
 * e a música de tema.</p>
 * 
 * <h3>package</h3>
 * <p>util</p>
 * 
 * <h3>Variavies</h3>
 * <p>-THEMEMUSIC: Music</p>
 * <p>-SHOT_SOUND: Sound</p>
 * 
 * <h3>Métodos</h3>
 * <p>+static getShotSound(): Sound</p>
 * <p>+static getThemeM(): Music</p>
 */
public class SoundsUtils {
    private static final Music THEMEMUSIC = Gdx.audio.newMusic(Gdx.files.internal("sounds/ThemeMusic.wav"));
    private static final Sound SHOT_SOUND = Gdx.audio.newSound(Gdx.files.internal("sounds/shot.wav"));

    /**
     * <h2>getShotSound</h2>
     * <P>retorna o efeito sonoro do disparo/p>
     * @return SHOT_SOUND tipo Sound(get)
     */
    public static Sound getShotSound(){
        return SHOT_SOUND;
    }

    /**
     * <h2>getThemeM</h2>
     * <P>retorna a trilha sonora do jogo</p>
     * @return  THEMEMUSIC tipo Music(get)
     */
    public static Music getThemeM(){
        return THEMEMUSIC;
    }
}

