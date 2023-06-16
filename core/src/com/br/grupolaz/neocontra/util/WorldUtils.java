package com.br.grupolaz.neocontra.util;


import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.br.grupolaz.neocontra.box2d.PlayerUserData;

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

    public Body createPlayer(World world) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(new Vector2(Constants.PLAYER_X, Constants.PLAYER_Y));
        bodyDef.type = BodyDef.BodyType.DynamicBody;

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(Constants.PLAYER_WITDH / 2, Constants.PLAYER_HEIGHT / 2);

        Body body;
        body = world.createBody(bodyDef);
        body.setGravityScale(Constants.PLAYER_GRAVITY_SCALE);
        body.createFixture(shape, Constants.PLAYER_DENSITY);
        body.resetMassData();
        body.setUserData(new PlayerUserData(Constants.PLAYER_WITDH, Constants.PLAYER_HEIGHT));

        shape.dispose();

        return body;
    }
}
