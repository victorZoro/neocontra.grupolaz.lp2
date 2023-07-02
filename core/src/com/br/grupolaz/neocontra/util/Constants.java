package com.br.grupolaz.neocontra.util;

import com.badlogic.gdx.math.Vector2;
/**
 * <h2>Constants</h2>
 * <p>a classe Constants armazena todas as constantes
 *  utilizadas no jogo, como configurações de 
 * tamanho de janela, nomes de arquivos de textura, configurações
 *  de mapa, configurações de corpos do Box2D e 
 * configurações do jogador.</p>
 * 
 * <h3>package<h3>
 * <p>ultil</p>
 */
//Inspired by Martian Run
public class Constants {

    //Global configuration
    public static final int PIXELS_PER_METER = 30; //later will be abandoned
    public static final int WINDOW_WIDTH = 160 / PIXELS_PER_METER;
    public static final int WINDOW_HEIGHT = 120 / PIXELS_PER_METER;


    //Texture configuration
    public static final String LEVEL1_MAP = "map/level1_map.tmx";
    public static final String PLAYER_ATLAS = "atlases/player/player.atlas";
    public static final String PLAYER_STILL_REGION = "Parado/still_000";
    public static final String PLAYER_RUNNING_REGION = "Andando/walking_000";
    public static final String PLAYER_JUMPING_REGION = "Pulando/jump_000";
    public static final String PLAYER_CROUCHING_REGION = "ParadoAgachado/still-low_000";
    public static final String PLAYER_RUNNING_AIMING_REGION = "AndandoMirando/walking-aiming_000";

    public static final String ENEMY_ATLAS = "atlases/player/enemy.atlas";
    public static final String ENEMY_STILL_REGION = "enemy_aiming000";
    public static final String ENEMY_RUNNING_REGION = "enemy_walking000";
    public static final String ENEMY_JUMPING_REGION = "enemy_walking006";
    
    //PLayer texture configuration - coming soon
    
    //World Map Configuration
    public static int BACKGROUND_LAYER = 0;
    public static int GROUND_BLOCK_LAYER = 1;
    public static int GROUND_LAYER = 2;
    public static int STAIRS_LAYER = 3;
    public static int PLATFORMS_LAYER = 4;
    public static int WALLS_LAYER = 5;
    public static int SEALEVEL_LAYER = 6;
    public static int CEILING_LAYER = 7;
    
    //Box2D Bodies
    public static Vector2 WORLD_GRAVITY = new Vector2(0, -10);
    public static int STATIC_BODY = 0;
    public static int DYNAMIC_BODY = 1;
    public static int KINEMATIC_BODY = 2;
    
    //Player config 
    public static final float PLAYER_X = -10f / PIXELS_PER_METER;
    public static final float PLAYER_Y = 100f / PIXELS_PER_METER;
    public static final float PLAYER_RADIUS = 6f / PIXELS_PER_METER;
    public static Vector2 PLAYER_JUMPING_LINEAR_IMPULSE = new Vector2(0, 5f);
    public static Vector2 PLAYER_RIGHT_LINEAR_IMPULSE = new Vector2(0.1f, 0);
    public static Vector2 PLAYER_LEFT_LINEAR_IMPULSE = new Vector2(-0.1f, 0);
    public static final float MAX_VELOCITY = 1.5f;
    public static final float PLAYER_BULLET_RADIUS = 1f / PIXELS_PER_METER;

    //Enemy config
    public static final float ENEMY_X = 4f;
    public static final float ENEMY_Y = 60f / PIXELS_PER_METER;

    //Projectiles config
    public static final float BULLET_SIZE = 3f / PIXELS_PER_METER;
}

