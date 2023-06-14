package com.br.grupolaz.neocontra.box2d;

import com.badlogic.gdx.math.Vector2;
import com.br.grupolaz.neocontra.util.Constants;

//Inspired by MartianRun
public class PlayerUserData extends UserData {
    private Vector2 jumpingLinearImpulse;

    public PlayerUserData(float width, float height) {
        super(width,height);
        jumpingLinearImpulse = Constants.PLAYER_JUMPING_LINEAR_IMPULSE;
    }

    public Vector2 getJumpingLinearImpulse() {
        return jumpingLinearImpulse;
    }

    public void setJumpingLinearImpulse(Vector2 jumpingLinearImpulse) {
        this.jumpingLinearImpulse = jumpingLinearImpulse;
    }
}
