package com.br.grupolaz.neocontra.actors;

import java.lang.annotation.Inherited;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.br.grupolaz.neocontra.util.Constants;
import com.br.grupolaz.neocontra.util.TextureUtils;
import com.br.grupolaz.neocontra.util.WorldUtils;

public class Enemy extends GameActor {

    Player player;
    
    public Enemy(WorldUtils world, Body body, TextureRegion region, Player player) {
        super(world, body, region);
        this.player = player;
        setUpAnimations();
    }

    public void facePlayer() {
        TextureRegion region = checkCurrentState();

        if(player.getBody().getPosition().x < body.getPosition().x && !(region.isFlipX())) {
            region.flip(true, false);
        } else if(player.getBody().getPosition().x > body.getPosition().x && region.isFlipX()) {
            region.flip(true, false);
        }
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        facePlayer();
    }


    @Override
    protected void setUpAnimations() {
        actorStanding = TextureUtils.getEnemyAtlas().findRegion(Constants.ENEMY_STILL_REGION);

        TextureRegion actorJumping = TextureUtils.getEnemyAtlas().findRegion(Constants.ENEMY_JUMPING_REGION);

        //Running
        TextureRegion runningRegion = TextureUtils.getEnemyAtlas().findRegion(Constants.ENEMY_RUNNING_REGION);
        for(int i = 0; i < 6; i++) {
            frames.add(new TextureRegion(runningRegion, i * 48, 0, 32, 48));
        }
        actorRunning = new Animation<TextureRegion>(0.15f, frames);
        frames.clear();
    }
    
}
