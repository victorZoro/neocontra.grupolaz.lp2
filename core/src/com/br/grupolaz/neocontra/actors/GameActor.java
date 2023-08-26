package com.br.grupolaz.neocontra.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import com.br.grupolaz.neocontra.enums.ActorStates;
import com.br.grupolaz.neocontra.util.Constants;
import com.br.grupolaz.neocontra.util.WorldUtils;

/**
 * <h2>GameActor</h2>
 * <p>
 * A classe GameActor é uma classe abstrata que
 * fornece uma estrutura básica e funcionalidades
 * comuns para os atores do jogo, como controle de
 * animações, lógica de movimento e renderização.
 * Essa classe pode ser estendida por atores específicos
 * do jogo para adicionar comportamentos e características adicionais
 * </p>
 * 
 * <h3>package</h3>
 * <p>
 * actors
 * </p>
 * 
 * <h3>variaveis</h3>
 * <p>
 * #world: {@link WorldUtils}
 * </p>
 * <P>
 * #body: Body
 * </P>
 * <p>
 * #sprite: Sprite
 * </p>
 * <P>
 * #crouching: boolean
 * </p>
 * <p>
 * #alive: boolean
 * </p>
 * <p>
 * #currentState: ActorStates
 * </p>
 * <p>
 * #previousState: ActorStates
 * </p>
 * <p>
 * #actorRunning: Animation <TextureRegion>
 * </p>
 * <p>
 * #actorRunningAiming: Animation<TextureRegion>
 * </p>
 * <p>
 * #actorStanding: TextureRegion
 * </p>
 * <p>
 * #actorCrouching: TextureRegion
 * </p>
 * <p>
 * #runningRight: boolean
 * </p>
 * <p>
 * #stateTimer: float
 * </p>
 * <p>
 * #frames: Array <TextureRegion>
 * </P>
 * <p>
 * #projectiles: Array <Body>
 * </p>
 * 
 * <h3>Métodos</h3>
 * <p>
 * +GameActor(WorldUtils, Body, TextureRegion)
 * </p>
 * <p>
 * +setDrawRegion(int, int, int, int): void
 * </p>
 * <P>
 * #abstract setUpAnimations(): void
 * </p>
 * <p>
 * +getBody(): Body
 * </p>
 * <p>
 * +act(float): void
 * </p>
 * <p>
 * +getFrame(float): TextureRegion
 * </p>
 * <p>
 * #checkCurrentState(): TextureRegion
 * </p>
 * <p>
 * -flipSprite(TextureRegion): void
 * </p>
 * <p>
 * -checkStateTimer(float): float
 * </p>
 * <p>
 * +getState(): ActorStates
 * </p>
 * <p>
 * +changeLinearVelocity(Vector2): void
 * </p>
 * <p>
 * +jump(): void
 * </p>
 * <p>
 * +walk(boolean): void
 * </p>
 * <P>
 * +shoot(): void
 * </p>
 * <p>
 * +projectileOutOfBounds(OrthographicCamera): void
 * </p>
 * <p>
 * +isOutOfBounds(Body, OrthographicCamera): boolean
 * </p>
 * <p>
 * +resetSpriteSize(Sprite): void
 * </p>
 * <p>
 * +draw(Batch, float): void
 * </p>
 */
// Inspired by MartianRun
public abstract class GameActor extends Actor {
    protected World world;
    protected Body body;
    protected Sprite sprite;
    protected boolean setToDestroy;
    protected boolean destroyed;

    protected ActorStates currentState;
    protected ActorStates previousState;

    protected Animation<TextureRegion> actorRunning;
//    protected Animation<TextureRegion> actorRunningAiming;
    protected Animation<TextureRegion> actorDying;
    protected TextureRegion actorStanding;
    protected TextureRegion actorCrouching;

    protected boolean runningRight;
    protected float stateTimer;
    protected float stateTime;

    protected Array<TextureRegion> frames;

    protected Array<Projectile> projectiles;

    /**
     * <h2>GameActor</h2>
     * <p>O contrutor de GameActor é responsável por receber
     *  e atribuir os objetos necessários ao ator do jogo, definir
     * seu estado inicial, criar um objeto Sprite com base na região de
     * textura fornecida e inicializar outras variáveis de
     * instância relevantes
     * </p>
     * <p>Ele recebe um objeto WorldUtils, um 
     * objeto Body e uma região de textura region</p>
     * @param world  tipo WorldUtils
     * @param region tipo TextureRegion
     */
    public GameActor(World world, TextureRegion region, float x, float y) {
        this.world = world;
        this.body = createBody(x, y);
        this.sprite = new Sprite(region);
        this.sprite.setSize(16f / Constants.PIXELS_PER_METER, 20f / Constants.PIXELS_PER_METER);
        this.projectiles = new Array<>();

        currentState = previousState = ActorStates.STANDING;
        stateTimer = 0;
        runningRight = true;
        frames = new Array<>();
    }

    public GameActor(World world, TextureRegion region, Vector2 position) {
        this.world = world;
        this.body = createBody(position);
        this.sprite = new Sprite(region);
        this.sprite.setSize(16f / Constants.PIXELS_PER_METER, 20f / Constants.PIXELS_PER_METER);
        this.projectiles = new Array<>();

        currentState = previousState = ActorStates.STANDING;
        stateTimer = 0;
        runningRight = true;
        frames = new Array<>();
    }

    public TextureRegion getFrame(float delta) {
        currentState = getState();

        TextureRegion region = checkCurrentState();

        flipSprite(region);
        checkStateTimer(delta);

        previousState = currentState;
        return region;
    }

    protected abstract TextureRegion checkCurrentState();

    private void flipSprite(TextureRegion region) {
        if ((body.getLinearVelocity().x < 0 || !runningRight) && !region.isFlipX()) {
            region.flip(true, false);
            runningRight = false;
        } else if ((body.getLinearVelocity().x > 0 || runningRight) && region.isFlipX()) {
            region.flip(true, false);
            runningRight = true;
        }
    }

    private void checkStateTimer(float delta) {
        if (currentState == previousState) {
            stateTimer += delta;
        } else {
            stateTimer = 0;
        }

    }

    public ActorStates getState() {
        if (body.getLinearVelocity().y != 0) {
            return ActorStates.JUMPING;
        } else if (body.getLinearVelocity().y < 0) {
            return ActorStates.FALLING;
        } else if (body.getLinearVelocity().x != 0) {
            return ActorStates.RUNNING;
        } else if(setToDestroy) {
            return ActorStates.DEAD;
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
        if (!(currentState == ActorStates.JUMPING)) {
            body.applyLinearImpulse(Constants.PLAYER_JUMPING_LINEAR_IMPULSE, body.getWorldCenter(), true);
        }
    }

    public void walk(boolean right) {
        if (right) {
            body.applyLinearImpulse(Constants.PLAYER_RIGHT_LINEAR_IMPULSE, body.getWorldCenter(), true);
            if (body.getLinearVelocity().x > Constants.MAX_VELOCITY) {
                body.setLinearVelocity(Constants.MAX_VELOCITY, body.getLinearVelocity().y);
            }
        } else {
            body.applyLinearImpulse(Constants.PLAYER_LEFT_LINEAR_IMPULSE, body.getWorldCenter(), true);
            if (body.getLinearVelocity().x < Constants.MAX_VELOCITY * -1) {
                body.setLinearVelocity(Constants.MAX_VELOCITY * -1, body.getLinearVelocity().y);
            }
        }
    }

    public void projectileOutOfBounds(OrthographicCamera camera) {
        int i = 0;
        for (Projectile projectile : projectiles) {
            if (isOutOfBounds(projectile.getBody(), camera) || projectile.isSetToDestroy()) {
                world.destroyBody(projectile.getBody());
                projectiles.removeIndex(i);
            }
            i++;
        }
    }

    protected abstract void setUpAnimations();
    public abstract void shoot();
    public abstract void collision();

    public Body getBody() {
        return body;
    }

    public void resetSpriteSize(Sprite sprite) {
        sprite.setSize(16f / Constants.PIXELS_PER_METER, 20f / Constants.PIXELS_PER_METER);
    }

    public boolean isOutOfBounds(Body body, OrthographicCamera camera) {
        return body.getPosition().x <= camera.position.x - camera.viewportWidth / 2
                || body.getPosition().x >= camera.position.x + camera.viewportWidth / 2
                || body.getPosition().y <= camera.position.y - camera.viewportHeight / 2
                || body.getPosition().y >= camera.position.y + camera.viewportHeight / 2;
    }

    public void setCategoryFilter(short filterBit) {
        Filter filter = new Filter();
        filter.categoryBits = filterBit;
        body.getFixtureList().get(0).setFilterData(filter);
    }

    public Body createBody(float x, float y) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(x, y);
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        Body body = world.createBody(bodyDef);

        FixtureDef fixtureDef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(Constants.PLAYER_RADIUS);

        fixtureDef.shape = shape;
        fixtureDef.friction = 0.5f;

        body.createFixture(fixtureDef);

        body.setUserData(this);

        return body;
    }

    public Body createBody(Vector2 position) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(position.x, position.y);
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        Body body = world.createBody(bodyDef);

        FixtureDef fixtureDef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(Constants.PLAYER_RADIUS);

        fixtureDef.shape = shape;
        fixtureDef.friction = 0.5f;

        body.createFixture(fixtureDef);

        return body;
    }
    @Override
    public void act(float delta) {
        super.act(delta);
        float x = body.getPosition().x - sprite.getWidth() / 2;
        float y = body.getPosition().y - sprite.getHeight() / 2;
        sprite.setPosition(x, y);

        sprite.setRegion(getFrame(delta));

        for (Projectile projectile : projectiles) {
            projectile.act(delta);
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        sprite.draw(batch);

        for (Projectile projectile : projectiles) {
            projectile.draw(batch, parentAlpha);
        }
    }

}
