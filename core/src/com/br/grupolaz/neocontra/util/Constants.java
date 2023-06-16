package com.br.grupolaz.neocontra.util;

import com.badlogic.gdx.math.Vector2;

public class Constants {
    
    public static final int WINDOW_WIDTH = 160;
    public static final int WINDOW_HEIGHT = 120;

    public static Vector2 WORLD_GRAVITY = new Vector2(0, -10);

    public static int BACKGROUND_LAYER = 0;
    public static int GROUND_BLOCK_LAYER = 1;
    public static int GROUND_LAYER = 2;
    public static int STAIRS_LAYER = 3;
    public static int PLATFORMS_LAYER = 4;
    public static int WALLS_LAYER = 5;
    public static int SEALEVEL_LAYER = 6;
    public static int CEILING_LAYER = 7;

    public static int STATIC_BODY = 0;
    public static int DYNAMIC_BODY = 1;
    public static int KINEMATIC_BODY = 2;
    
    public static final float PLAYER_X = 30f;
    public static final float PLAYER_Y = 60f;
    public static final float PLAYER_WITDH = 10f;
    public static final float PLAYER_HEIGHT = 20f;
    public static final float PLAYER_GRAVITY_SCALE = 10f;
    public static final float PLAYER_DENSITY = 0.5f;
    public static final float PLAYER_CROUCH_X = PLAYER_Y;
    public static final float PLAYER_CROUCH_Y = PLAYER_Y - PLAYER_HEIGHT;
    public static Vector2 PLAYER_JUMPING_LINEAR_IMPULSE = new Vector2(0, 13f);
}

