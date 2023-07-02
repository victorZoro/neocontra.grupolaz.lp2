package com.br.grupolaz.neocontra.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.br.grupolaz.neocontra.enums.ActorStates;
import com.br.grupolaz.neocontra.util.Constants;
import com.br.grupolaz.neocontra.util.TextureUtils;
import com.br.grupolaz.neocontra.util.WorldUtils;

public class Player extends GameActor {

    private boolean hit;
    private int lifeCount;

    private Animation<TextureRegion> actorJumping;


    public Player(WorldUtils world, Body body, TextureRegion region) {
        super(world, body, region);
        spawn();
        lifeCount = 3;
        setUpAnimations();
    }

    public void spawn() {
        jump();
        body.setLinearVelocity(2f, 0);
    }
    
    public void stayInBounds() {
        if(body.getPosition().x < 0){
            body.setTransform(0, body.getPosition().y, body.getAngle());
        } else if(body.getPosition().x >= 25.5f) {
            body.setTransform(25.5f, body.getPosition().y, body.getAngle());
        }
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

    public int getLifeCount() {
        return lifeCount;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        stayInBounds();
    }
    

    @Override
    public ActorStates getState() {
        if(body.getLinearVelocity().y != 0) {
            return ActorStates.JUMPING;
        } else if(body.getLinearVelocity().x != 0 ) {
            return ActorStates.RUNNING;
        } else if(Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            return ActorStates.CROUCHING;
        } else {
            return ActorStates.STANDING;
        }
    }
    
    @Override
    protected TextureRegion checkCurrentState() {
        TextureRegion region;

        switch (currentState) {
            case JUMPING: {
                region = actorJumping.getKeyFrame(stateTimer, true);
                sprite.setPosition(sprite.getX(), sprite.getY() - (2f / Constants.PIXELS_PER_METER));
                break;
            }

            case RUNNING: {
                resetSpriteSize(sprite);
                region = actorRunning.getKeyFrame(stateTimer, true);
                break;
            }

            case CROUCHING: {
                region = actorCrouching;
                sprite.setSize(25f / Constants.PIXELS_PER_METER, 16f / Constants.PIXELS_PER_METER);
                sprite.setPosition(sprite.getX(), sprite.getY() - (2f / Constants.PIXELS_PER_METER));
                break;
            }
            
            // Next 3 cases are all the same,
            // so we jump to the next one until
            // we reach the default case.
            case FALLING: case STANDING: default: {
                region = actorStanding;
                resetSpriteSize(sprite);
                break;
            }
        }

        return region;
    }

    //Inspired by Brent Aureli Codes
    @Override
    protected void setUpAnimations() {
        //Standing
        actorStanding = TextureUtils.getPlayerAtlas().findRegion(Constants.PLAYER_STILL_REGION);

        //Crouching
        actorCrouching = TextureUtils.getPlayerAtlas().findRegion(Constants.PLAYER_CROUCHING_REGION);

        //Running
        TextureRegion runningRegion = TextureUtils.getPlayerAtlas().findRegion(Constants.PLAYER_RUNNING_REGION);
        for(int i = 0; i < 6; i++) {
            frames.add(new TextureRegion(runningRegion, i * 48, 0, 32, 48));
        }
        actorRunning = new Animation<TextureRegion>(0.15f, frames);
        frames.clear();


        //Jumping
        TextureRegion jumpingRegion = TextureUtils.getPlayerAtlas().findRegion(Constants.PLAYER_JUMPING_REGION);
        for(int i = 0; i < 4; i++) {
            frames.add(new TextureRegion(jumpingRegion, i * 48, 0, 48, 48));
        }
        actorJumping = new Animation<TextureRegion>(0.15f, frames);
        frames.clear();
    }



}
