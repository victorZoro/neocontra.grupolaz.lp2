package com.br.grupolaz.neocontra.util;

import com.badlogic.gdx.math.Vector2;

public class Constants {

    public static final int PIXELS_PER_METER = 30;
    
    public static final int WINDOW_WIDTH = 160 / PIXELS_PER_METER;
    public static final int WINDOW_HEIGHT = 120 / PIXELS_PER_METER;

    public static final String LEVEL1_MAP = "map/level1_map.tmx";
    public static final String CHARACTERS_ATLAS = "person/all-in-one.atlas";
    public static final String PLAYER_REGION = "player-atlas";

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
    
    public static final float PLAYER_X = 2f / PIXELS_PER_METER;
    public static final float PLAYER_Y = 60f / PIXELS_PER_METER;
    public static final float PLAYER_RADIUS = 5f / PIXELS_PER_METER;
    public static Vector2 PLAYER_JUMPING_LINEAR_IMPULSE = new Vector2(0, 5f);
    public static Vector2 PLAYER_RIGHT_LINEAR_IMPULSE = new Vector2(0.09f, 0);
    public static Vector2 PLAYER_LEFT_LINEAR_IMPULSE = new Vector2(-0.09f, 0);
}

