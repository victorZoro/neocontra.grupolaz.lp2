package com.br.grupolaz.neocontra.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.br.grupolaz.neocontra.actors.Player;

public class GameUtils {
    public static void fixTimeStep(World world, float delta) {
        world.step(1/60f, 6, 2);
    }

    public static void createInputHandler(Player player, float delta) {
        if(Gdx.input.isKeyJustPressed(Input.Keys.W)) {
            player.getBody().applyForceToCenter(new Vector2(0, 80f), true);
        }
        
        if(Gdx.input.isKeyJustPressed(Input.Keys.S)) {
            player.crouch();
        }
    }
}
