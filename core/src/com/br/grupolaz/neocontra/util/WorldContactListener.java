package com.br.grupolaz.neocontra.util;

import com.badlogic.gdx.physics.box2d.*;
import com.br.grupolaz.neocontra.actors.GameActor;
import com.br.grupolaz.neocontra.interactive.Stairs;


public class WorldContactListener implements ContactListener {

    @Override
    public void beginContact(Contact contact) {
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();

        if((fixtureA.getUserData() == "bullet") || (fixtureB.getUserData() == "bullet")) {
            Fixture bullet = fixtureA.getUserData() == "bullet" ? fixtureA : fixtureB;
            Fixture enemy = bullet == fixtureA ? fixtureB : fixtureA;

            System.out.println(enemy.getUserData());

            if(enemy.getUserData() != null && GameActor.class.isAssignableFrom(enemy.getUserData().getClass())) {
                ((GameActor) enemy.getUserData()).collision();
            }
        }

        if((fixtureA.getUserData()  == "head") || (fixtureB.getUserData() == "head")) {
            Fixture head = fixtureA.getUserData() == "head" ? fixtureA : fixtureB;
            Fixture object = head == fixtureA ? fixtureB : fixtureA;

            System.out.println(object.getUserData().getClass());


            if(object.getUserData() != null && Stairs.class.isAssignableFrom(object.getUserData().getClass())) {
                ((Stairs) object.getUserData()).onHeadHit();
            }
        }

    }

    @Override
    public void endContact(Contact contact) {
//        Gdx.app.log("End Contact", "test");
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
    }
    
}
