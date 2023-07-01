package com.br.grupolaz.neocontra.util;


import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

//Inspired by Martian Run and Brent Aureli Code
public class WorldUtils {

    private MapLoader mapLoader;
    private World world;

    public WorldUtils(MapLoader mapLoader) {
        this.mapLoader = mapLoader;
        this.world = new World(Constants.WORLD_GRAVITY, true);
        createWorldBodies();
    }

    public World getWorld() {
        return world;
    }

    public void createWorldBodies() {
        createObject(Constants.GROUND_LAYER);
        createObject(Constants.STAIRS_LAYER);
        createObject(Constants.PLATFORMS_LAYER);
        createObject(Constants.WALLS_LAYER);
        createObject(Constants.SEALEVEL_LAYER);
        createObject(Constants.CEILING_LAYER);
    }

    public void createObject(int layer) {
        for(MapObject object : mapLoader.getMap().getLayers().get(layer).getObjects().getByType(RectangleMapObject.class)){
            Rectangle r = ((RectangleMapObject)object).getRectangle();
            createStaticBody(world, r);
        }
    }

    public void createStaticBody(World world, Rectangle r) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set((r.getX() + r.getWidth() / 2) / Constants.PIXELS_PER_METER, (r.getY() + r.getHeight() / 2) / Constants.PIXELS_PER_METER);

        Body body;
        body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox((r.getWidth() / 2) / Constants.PIXELS_PER_METER, (r.getHeight() / 2) / Constants.PIXELS_PER_METER);
        
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        
        body.createFixture(fixtureDef);

        shape.dispose();
    }

    public Body createPerson(World world) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(Constants.PLAYER_X, Constants.PLAYER_Y);
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        
        FixtureDef fixtureDef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(Constants.PLAYER_RADIUS);

        fixtureDef.shape = shape;
        fixtureDef.friction = 0.5f;

        Body body = world.createBody(bodyDef);
        body.createFixture(fixtureDef);
        shape.dispose();

        return body;
    }

    public Body createProjectile(float x, float y, float radius, Vector2 velocity) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(x, y);
        bodyDef.type = BodyDef.BodyType.KinematicBody;

        FixtureDef fixtureDef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(radius);

        fixtureDef.shape = shape;
        
        Body body = world.createBody(bodyDef);
        body.createFixture(fixtureDef);
        shape.dispose();

        body.setLinearVelocity(velocity);

        return body;
    }

    public void dispose() {
        world.dispose();
    }
}
