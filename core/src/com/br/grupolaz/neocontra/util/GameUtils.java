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
        if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            player.jump();
        }

        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            player.walk(true);
        } else if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            player.walk(false);
        } else {
            player.setWalkingFalse();
        }

        if(Gdx.input.isKeyJustPressed(Input.Keys.R)) {
            player.changeLinearVelocity(new Vector2(0, 0));
            player.getBody().setTransform(Constants.PLAYER_X, Constants.PLAYER_Y, player.getBody().getAngle());
        }

        if(Gdx.input.isKeyJustPressed(Input.Keys.E)) {
            player.hit();
        }
    }
}
