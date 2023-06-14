package com.br.grupolaz.neocontra.actors;

import com.badlogic.gdx.physics.box2d.Body;
import com.br.grupolaz.neocontra.box2d.PlayerUserData;

public class Player extends GameActor {

    private boolean hit;
    private boolean jumping;
    private boolean crouching;

    public Player(Body body) {
        super(body);
        hit = false;
    }

    @Override
    public PlayerUserData getUserData() {
        return (PlayerUserData) userData;
    }

    public void jump() {
        if(!(jumping || crouching)) {
            body.applyLinearImpulse(getUserData().getJumpingLinearImpulse(), body.getWorldCenter(), true);
            jumping = true;
        }
    }

    public void land() {
        jumping = false;
    }

    public void crouch() {
        if(!jumping) {
            body.setTransform(getUserData().getCrouchPosition(), 0);
            crouching = true;
        }
    }

    public void riseUp() {
        crouching = false;
    }

    public boolean isCrouching() {
        return crouching;
    }

    public void hit() {
        hit = true;
    }

    public void setHit(boolean hit) {
        this.hit = hit;
    }
    
    public boolean isHit() {
        return hit;
    }

    public void setGravityScale(float gravityScale) {
        body.setGravityScale(gravityScale);
        body.resetMassData();
    }
}
