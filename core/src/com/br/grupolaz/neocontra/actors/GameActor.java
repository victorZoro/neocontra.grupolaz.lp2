package com.br.grupolaz.neocontra.actors;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import com.br.grupolaz.neocontra.enums.ActorStates;
import com.br.grupolaz.neocontra.util.Constants;
import com.br.grupolaz.neocontra.util.TextureUtils;


//Inspired by MartianRun and Brent Aureli Codes
public abstract class GameActor extends Actor {
    protected Body body;
    protected Sprite sprite;

    protected ActorStates currentState;
    protected ActorStates previousState;

    protected Animation<TextureRegion> actorRunning;
    protected Animation<TextureRegion> actorJumping;
    protected TextureRegion actorStanding;

    protected boolean runningRight;
    protected float stateTimer;
    protected Array<TextureRegion> frames;

    public GameActor(Body body, TextureRegion region) {
        this.body = body;
        this.sprite = new Sprite(TextureUtils.getPlayerAtlas().findRegion(Constants.PLAYER_STILL_REGION));
        this.sprite.setSize(18f / Constants.PIXELS_PER_METER, 20f / Constants.PIXELS_PER_METER);

        currentState = previousState = ActorStates.STANDING;
        stateTimer = 0;
        runningRight = true;
        frames = new Array<TextureRegion>();
    }

    public void setDrawRegion(int x, int y, int width, int height) {
        sprite.setRegion(x, y, width, height);
    }

    protected abstract void setUpAnimations();

    @Override
    public void act(float delta) {
        super.act(delta);
        float x = body.getPosition().x - sprite.getWidth() / 2;
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
                region = actorJumping.getKeyFrame(stateTimer, true);
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

        sprite.draw(batch);
    }

}
