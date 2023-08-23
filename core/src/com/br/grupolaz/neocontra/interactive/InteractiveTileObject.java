package com.br.grupolaz.neocontra.interactive;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;
import com.br.grupolaz.neocontra.util.Constants;

// Inspired by Brent Aureli Code
public abstract class InteractiveTileObject {
    protected World world;
    protected TiledMap map;
    protected Rectangle bounds;

    protected Body body;

    protected Fixture fixture;

    public InteractiveTileObject(World world, TiledMap map, Rectangle bounds) {
        this.world = world;
        this.map = map;
        this.bounds = bounds;

        BodyDef bodyDef = new BodyDef();
        FixtureDef fixtureDef = new FixtureDef();
        PolygonShape shape = new PolygonShape();

        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set((bounds.getX() + bounds.getWidth() / 2) / Constants.PIXELS_PER_METER, (bounds.getY() + bounds.getHeight() / 2) / Constants.PIXELS_PER_METER);

        body = world.createBody(bodyDef);

        shape.setAsBox((bounds.getWidth() / 2) / Constants.PIXELS_PER_METER, (bounds.getHeight() / 2) / Constants.PIXELS_PER_METER);
        fixtureDef.shape = shape;
        fixture = body.createFixture(fixtureDef);
    }

}
