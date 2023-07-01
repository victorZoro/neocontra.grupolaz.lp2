package com.br.grupolaz.neocontra.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import com.br.grupolaz.neocontra.enums.ActorStates;
import com.br.grupolaz.neocontra.util.Constants;
import com.br.grupolaz.neocontra.util.TextureUtils;
import com.br.grupolaz.neocontra.util.WorldUtils;


//Inspired by MartianRun and Brent Aureli Codes
public abstract class GameActor extends Actor {
    protected WorldUtils world;
    protected Body body;
    protected Sprite sprite;
    protected boolean crouching;

    protected ActorStates currentState;
    protected ActorStates previousState;

    protected Animation<TextureRegion> actorRunning;
    protected Animation<TextureRegion> actorRunningAiming;
    protected Animation<TextureRegion> actorJumping;
    protected TextureRegion actorStanding;
    protected TextureRegion actorCrouching;

    protected boolean runningRight;
    protected float stateTimer;
    protected Array<TextureRegion> frames;

    protected Array<Body> projectiles;

    public GameActor(WorldUtils world, Body body, TextureRegion region) {
        this.world = world;
        this.body = body;
        this.sprite = new Sprite(TextureUtils.getPlayerAtlas().findRegion(Constants.PLAYER_STILL_REGION));
        this.sprite.setSize(16f / Constants.PIXELS_PER_METER, 20f / Constants.PIXELS_PER_METER);
        this.projectiles = new Array<Body>();

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
        } else if(Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            return ActorStates.CROUCHING;
        } else {
            return ActorStates.STANDING;
        }
    }

    public void changeLinearVelocity(Vector2 velocity) {
        body.setLinearVelocity(velocity);
    }

    public void jump() {
        if(!(currentState == ActorStates.JUMPING)) {
            body.applyLinearImpulse(Constants.PLAYER_JUMPING_LINEAR_IMPULSE, body.getWorldCenter(), true);
        }
    }

    public void walk(boolean right) {
        if(right) {
            body.applyLinearImpulse(Constants.PLAYER_RIGHT_LINEAR_IMPULSE, body.getWorldCenter(), true);
            if(body.getLinearVelocity().x > Constants.MAX_VELOCITY) {
                body.setLinearVelocity(Constants.MAX_VELOCITY, body.getLinearVelocity().y);
            }
        } else {
            body.applyLinearImpulse(Constants.PLAYER_LEFT_LINEAR_IMPULSE, body.getWorldCenter(), true);
            if(body.getLinearVelocity().x < Constants.MAX_VELOCITY * -1) {
                body.setLinearVelocity(Constants.MAX_VELOCITY * -1, body.getLinearVelocity().y);
            }
        }
    }

    public void shoot() {
        if(!runningRight) {
            if(currentState == ActorStates.CROUCHING) {
                projectiles.add(world.createProjectile(body.getPosition().x - Constants.PLAYER_RADIUS - 1f / Constants.PIXELS_PER_METER, body.getPosition().y - 1.5f / Constants.PIXELS_PER_METER, Constants.PLAYER_BULLET_RADIUS, new Vector2(-3f, 0)));
            } else {
                projectiles.add(world.createProjectile(body.getPosition().x - Constants.PLAYER_RADIUS - 1f / Constants.PIXELS_PER_METER, body.getPosition().y + 2f / Constants.PIXELS_PER_METER, Constants.PLAYER_BULLET_RADIUS, new Vector2(-3f, 0)));
            }
        } else {
            if(currentState == ActorStates.CROUCHING) {
                projectiles.add(world.createProjectile(body.getPosition().x + Constants.PLAYER_RADIUS + 1f / Constants.PIXELS_PER_METER, body.getPosition().y - 1.5f / Constants.PIXELS_PER_METER, Constants.PLAYER_BULLET_RADIUS, new Vector2(3f, 0)));
            } else {
                projectiles.add(world.createProjectile(body.getPosition().x + Constants.PLAYER_RADIUS + 1f / Constants.PIXELS_PER_METER, body.getPosition().y + 2f / Constants.PIXELS_PER_METER, Constants.PLAYER_BULLET_RADIUS, new Vector2(3f, 0)));
            }
        }
    }

    public void projectileOutOfBounds(OrthographicCamera camera) {
        int i = 0;
        for(Body projectile : projectiles) {
            if(isOutOfBounds(projectile, camera)) {
                world.getWorld().destroyBody(projectile);
                projectiles.removeIndex(i);
            }
            i++;
        }
    }

    public boolean isOutOfBounds(Body body, OrthographicCamera camera) {
        if(body.getPosition().x <= camera.position.x - camera.viewportWidth / 2 ||
            body.getPosition().x >= camera.position.x + camera.viewportWidth / 2 ||
            body.getPosition().y <= camera.position.y - camera.viewportHeight / 2 ||
            body.getPosition().y >= camera.position.y + camera.viewportHeight / 2) {
                return true;
            }
        return false;
    }

    public void resetSpriteSize(Sprite sprite) {
        sprite.setSize(16f / Constants.PIXELS_PER_METER, 20f / Constants.PIXELS_PER_METER);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        sprite.draw(batch);
    }

}
