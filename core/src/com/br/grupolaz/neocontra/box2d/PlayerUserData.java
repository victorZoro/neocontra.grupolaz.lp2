package com.br.grupolaz.neocontra.box2d;

import com.badlogic.gdx.math.Vector2;
import com.br.grupolaz.neocontra.enums.UserDataType;
import com.br.grupolaz.neocontra.util.Constants;

//Inspired by MartianRun
public class PlayerUserData extends UserData {
    private final Vector2 normalPosition = new Vector2(Constants.PLAYER_X, Constants.PLAYER_Y);
    private final Vector2 crouchPosition = new Vector2(Constants.PLAYER_CROUCH_X, Constants.PLAYER_CROUCH_Y);
    private Vector2 jumpingLinearImpulse;

    public PlayerUserData(float width, float height) {
        super(width,height);
        jumpingLinearImpulse = Constants.PLAYER_JUMPING_LINEAR_IMPULSE;
        userDataType = UserDataType.PLAYER;
    }

    public Vector2 getJumpingLinearImpulse() {
        return jumpingLinearImpulse;
    }

    public void setJumpingLinearImpulse(Vector2 jumpingLinearImpulse) {
        this.jumpingLinearImpulse = jumpingLinearImpulse;
    }

    public Vector2 getNormalPosition() {
        return normalPosition;
    }

    public Vector2 getCrouchPosition() {
        return crouchPosition;
    }

    public float getCrouchAngle() {
        return (float) (-90f * Math.PI / 180f);
    }
}
