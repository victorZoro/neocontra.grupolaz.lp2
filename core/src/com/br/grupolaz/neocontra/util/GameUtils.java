package com.br.grupolaz.neocontra.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.physics.box2d.World;
import com.br.grupolaz.neocontra.actors.Player;

public class GameUtils {
    public static void fixTimeStep(World world, float delta) {
        world.step(1/60f, 6, 2);
    }

    public static void createInputHandler(Player player, float delta) {
        if(Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
            player.getBody().applyLinearImpulse(Constants.PLAYER_JUMPING_LINEAR_IMPULSE, player.getBody().getWorldCenter(), true);
        }

        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT) && player.getBody().getLinearVelocity().x <= 2) {
            player.getBody().applyLinearImpulse(Constants.PLAYER_RIGHT_LINEAR_IMPULSE, player.getBody().getWorldCenter(), true);
        } else if(Gdx.input.isKeyPressed(Input.Keys.LEFT) && player.getBody().getLinearVelocity().x >= -2) {
            player.getBody().applyLinearImpulse(Constants.PLAYER_LEFT_LINEAR_IMPULSE, player.getBody().getWorldCenter(), true);
        }

        if(Gdx.input.isKeyJustPressed(Input.Keys.R)) {
            player.getBody().setLinearVelocity(0, 0);
            player.getBody().setTransform(Constants.PLAYER_X, Constants.PLAYER_Y, player.getBody().getAngle());
        }
    }
}
