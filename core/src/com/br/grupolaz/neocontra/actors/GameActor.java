package com.br.grupolaz.neocontra.actors;

import com.badlogic.gdx.graphics.g2d.Animation;
import org.w3c.dom.Text;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import com.br.grupolaz.neocontra.enums.ActorStates;
import com.br.grupolaz.neocontra.util.Constants;


//Inspired by MartianRun
public abstract class GameActor extends Actor {
    protected Body body;
    protected TextureRegion region;
    protected Sprite sprite;

    public GameActor(Body body, TextureRegion region) {
        this.body = body;
        this.sprite = new Sprite(region);
        this.sprite.setSize(12.5f / Constants.PIXELS_PER_METER, 18f / Constants.PIXELS_PER_METER);
        this.region = new TextureRegion(region, 0, 0, 25, 36);
    }

    public void setDrawRegion(int x, int y, int width, int height) {
        this.region.setRegion(x, y, width, height);
    }

    protected abstract void setUpAnimations();

    @Override
    public void act(float delta) {
        super.act(delta);
        float x = body.getPosition().x - sprite.getWidth() / 3;
        float y = body.getPosition().y - sprite.getHeight() / 2;
        sprite.setPosition(x, y);

        sprite.setRegion(getFrame(delta));
    }

    public TextureRegion getFrame(float delta) {
        currentState = getState();

        TextureRegion region = checkCurrentState();

        flipSprite(region);
        checkStateTimer(delta);

        previousState = currentState;
        return region;
    }

    private TextureRegion checkCurrentState() {
        TextureRegion region;

        switch (currentState) {
            case JUMPING: {
                region = actorJumping.getKeyFrame(stateTimer);
                break;
            }

            case RUNNING: {
                region = actorRunning.getKeyFrame(stateTimer, true);
                break;
            }

            // Next 3 cases are all the same,
            // so we jump to the next one until
            // we reach the default case.
            case FALLING: case STANDING: default: {
                region = actorStanding;
                break;
            }
        }

        return region;
    }

    private void flipSprite(TextureRegion region) {
        if((body.getLinearVelocity().x  < 0 || !runningRight) && !region.isFlipX()) {
            region.flip(true, false);
            runningRight = false;
        } else if((body.getLinearVelocity().x > 0 || runningRight) && region.isFlipX()) {
            region.flip(true, false);
            runningRight = true;
        }
    }

    private float checkStateTimer(float delta) {
        if(currentState == previousState) {
            stateTimer += delta; 
        } else {
            stateTimer = 0;
        }

        return stateTimer;
    }

    public ActorStates getState() {
        if(body.getLinearVelocity().y > 0 || (body.getLinearVelocity().y < 0 && previousState == ActorStates.JUMPING)) {
            return ActorStates.JUMPING;
        } else if(body.getLinearVelocity().y < 0) {
            return ActorStates.FALLING;
        } else if(body.getLinearVelocity().x != 0 ) {
            return ActorStates.RUNNING;
        } else {
            return ActorStates.STANDING;
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        sprite.setRegion(this.region);
        sprite.draw(batch);
    }

}
