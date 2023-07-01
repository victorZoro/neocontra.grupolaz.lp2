package com.br.grupolaz.neocontra.actors;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.br.grupolaz.neocontra.util.Constants;
import com.br.grupolaz.neocontra.util.TextureUtils;
import com.br.grupolaz.neocontra.util.WorldUtils;

public class Player extends GameActor {

    private boolean hit;
    private boolean alive; //later
    private int lifeCount;

    public Player(WorldUtils world, Body body, TextureRegion region) {
        super(world, body, region);
        lifeCount = 3;
        setUpAnimations();
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

    //Inspired by Brent Aureli Codes
    @Override
    protected void setUpAnimations() {
        //Standing
        actorStanding = TextureUtils.getPlayerAtlas().findRegion(Constants.PLAYER_STILL_REGION);

        //Crouching
        actorCrouching = TextureUtils.getPlayerAtlas().findRegion(Constants.PLAYER_CROUCHING_REGION);

        //Walking
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
