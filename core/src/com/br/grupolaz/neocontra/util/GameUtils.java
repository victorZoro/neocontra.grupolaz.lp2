package com.br.grupolaz.neocontra.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.physics.box2d.World;

public class GameUtils {
    public static void fixTimeStep(World world, float delta) {
        world.step(1/60f, 6, 2);
    }

    public static void createInputHandler() {
        if(Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
            
        }
    }
}
