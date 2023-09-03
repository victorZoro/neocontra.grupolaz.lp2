package com.br.grupolaz.neocontra.interactive;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import com.br.grupolaz.neocontra.actors.Enemy;
import com.br.grupolaz.neocontra.util.Constants;

public class Stairs extends InteractiveTileObject {

    public Stairs(World world, TiledMap map, Rectangle bounds) {
        super(world, map, bounds);
        createSensor();
    }

    private void createSensor() {
        EdgeShape sensor = new EdgeShape();

        sensor.set(new Vector2(-5f / Constants.PIXELS_PER_METER, 2f / Constants.PIXELS_PER_METER),
                new Vector2(-5f / Constants.PIXELS_PER_METER, -2f / Constants.PIXELS_PER_METER));
        fixtureDef.shape = sensor;
        fixtureDef.isSensor = true;

        //Não é o "this.fixture"
        Fixture fixture = body.createFixture(fixtureDef);

        body.getFixtureList().add(fixture);

        body.getFixtureList().get(2).setUserData("stairsSensor");
    }

    @Override
    public void onPlayerCollision(Body actor) {
        actor.applyLinearImpulse(0, 2f, actor.getWorldCenter().x, actor.getWorldCenter().y, true);
        if(actor.getUserData() instanceof Enemy){
                    actor.applyLinearImpulse(0, 5f, actor.getWorldCenter().x, actor.getWorldCenter().y, true);

        }
//        if(actor.getUserData() instanceof GameActor){
//            actor.applyLinearImpulse(0, 2f, actor.getWorldCenter().x, actor.getWorldCenter().y, true);
//        }
    }

}
