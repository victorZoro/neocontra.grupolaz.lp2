package com.br.grupolaz.neocontra.actors;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.br.grupolaz.neocontra.util.Constants;
import com.br.grupolaz.neocontra.util.TextureUtils;

public class Player extends GameActor {

    private boolean hit;
    private boolean jumping;
    private boolean walking_right, walking_left;
    private boolean idle;
    private boolean shooting;
    private boolean alive; //later
    private int lifeCount;

    public Player(Body body, TextureRegion region) {
        super(body, region);
        lifeCount = 3;
        idle = true;
        setUpAnimations();
    }

    public void changeLinearVelocity(Vector2 velocity) {
        body.setLinearVelocity(velocity);
    }

    public void jump() {
        if(!isJumping()) {
            body.applyLinearImpulse(Constants.PLAYER_JUMPING_LINEAR_IMPULSE, body.getWorldCenter(), true);
            jumping = true;
        }
    }

    public boolean isJumping() {
        if(body.getLinearVelocity().y != 0) {
            return jumping;
        }
        jumping = false;
        return jumping;
    }

    public void walk(boolean right) {
        if(right) {
            body.applyLinearImpulse(Constants.PLAYER_RIGHT_LINEAR_IMPULSE, body.getWorldCenter(), true);
            walking_right = true;
            if(body.getLinearVelocity().x > Constants.MAX_VELOCITY) {
                body.setLinearVelocity(Constants.MAX_VELOCITY, body.getLinearVelocity().y);
            }
        } else {
            body.applyLinearImpulse(Constants.PLAYER_LEFT_LINEAR_IMPULSE, body.getWorldCenter(), true);
            walking_left = true;
            if(body.getLinearVelocity().x < Constants.MAX_VELOCITY * -1) {
                body.setLinearVelocity(Constants.MAX_VELOCITY * -1, body.getLinearVelocity().y);
            }
        }
    }

    public void updateIdle() {
        if(!isWalking() && !isShooting() && !isJumping()) {
            idle = true;
        } else {
            idle = false;
        }
    }

    public void setWalkingFalse() {
        walking_right = walking_left = false;
    }

    public boolean isWalking() {
        if(walking_right || walking_left) {
            return true;
        }
        return false;
    }

    public void shoot() {
        // ... coming soon
        shooting = true;
    }

    public boolean isShooting() {
        return shooting;
    }

    public void hit() {
        hit = true;
        lifeCount--;
        System.out.println(lifeCount);
    }

    public void setHit(boolean hit) {
        this.hit = hit;
    }
    
    public boolean isHit() {
        return hit;
    }

    public Body getBody() {
        return body;
    }

    public int getLifeCount() {
        return lifeCount;
    }

    @Override
    protected void setUpAnimations() {
        //Standing
        actorStanding = TextureUtils.getPlayerAtlas().findRegion(Constants.PLAYER_STILL_REGION);

        //Walking
        TextureRegion runningRegion = TextureUtils.getPlayerAtlas().findRegion(Constants.PLAYER_RUNNING_REGION);
        for(int i = 0; i < 6; i++) {
            frames.add(new TextureRegion(runningRegion, i * Constants.SPRITE_PIXEL_SIZE, 0, Constants.SPRITE_PIXEL_SIZE, Constants.SPRITE_PIXEL_SIZE));
        }
        actorRunning = new Animation<TextureRegion>(0.1f, frames);
        frames.clear();

        //Jumping
        TextureRegion jumpingRegion = TextureUtils.getPlayerAtlas().findRegion(Constants.PLAYER_JUMPING_REGION);
        for(int i = 0; i < 4; i++) {
            frames.add(new TextureRegion(jumpingRegion, i * Constants.SPRITE_PIXEL_SIZE, 0, Constants.SPRITE_PIXEL_SIZE, Constants.SPRITE_PIXEL_SIZE));
        }
        actorJumping = new Animation<TextureRegion>(0.1f, frames);
        frames.clear();
    }

}
