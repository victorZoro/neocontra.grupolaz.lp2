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
        bodyDef.position.set(r.getX() + r.getWidth() / 2, r.getY() + r.getHeight() / 2);

        Body body;
        body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(r.getWidth() / 2, r.getHeight() / 2);
        
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        
        body.createFixture(fixtureDef);

        shape.dispose();
    }

    public Body createDynamicBody(World world, Vector2 position, int radius) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(position);
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        
        Body body;
        body = world.createBody(bodyDef);

        CircleShape shape = new CircleShape();
        shape.setRadius(radius);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;

        body.createFixture(fixtureDef);

        shape.dispose();

        return body;
    }
}
