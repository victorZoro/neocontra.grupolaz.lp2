package com.br.grupolaz.neocontra.actors;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Actor;

import static com.badlogic.gdx.physics.box2d.BodyDef.BodyType.KinematicBody;

public abstract class Projectile extends Actor {
    protected Body body;
    protected Sprite sprite;

    protected World world;

    protected boolean setToDestroy;

    public Projectile(World world, float x, float y, Vector2 velocity, float radius) {
        this.world = world;
        this.body = createBody(x, y, velocity, radius);
    }

    public Projectile(World world, Vector2 position, Vector2 velocity, float radius) {
        this.world = world;
        this.body = createBody(position, velocity, radius);
    }

    public Body createBody(float x, float y, Vector2 velocity, float radius) {
        BodyDef bodyDef = new BodyDef();

        bodyDef.position.set(x, y);
        bodyDef.type = KinematicBody;

        FixtureDef fixtureDef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(radius);

        fixtureDef.shape = shape;
        fixtureDef.isSensor = true;

        Body body = world.createBody(bodyDef);
        body.createFixture(fixtureDef);
        shape.dispose();

        body.setUserData(this);
        body.setLinearVelocity(velocity);

        return body;
    }

    public Body createBody(Vector2 position, Vector2 velocity, float radius) {
        BodyDef bodyDef = new BodyDef();

        bodyDef.position.set(position.x, position.y);
        bodyDef.type = KinematicBody;

        FixtureDef fixtureDef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(radius);

        fixtureDef.shape = shape;
        fixtureDef.isSensor = true;

        Body body = world.createBody(bodyDef);
        body.createFixture(fixtureDef);
        shape.dispose();

        body.setUserData(this);
        body.setLinearVelocity(velocity);

        return body;
    }
    public Body getBody() {
        return body;
    }

    public void setToDestroy() {
        setToDestroy = true;
    }

    public boolean isSetToDestroy() {
        return setToDestroy;
    }
    @Override
    public void act(float delta) {
        super.act(delta);
        float x = body.getPosition().x - sprite.getWidth() / 2;
        float y = body.getPosition().y - sprite.getHeight() / 2;
        sprite.setPosition(x, y);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        sprite.draw(batch);
    }

    
}
