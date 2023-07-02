package com.br.grupolaz.neocontra.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.br.grupolaz.neocontra.actors.GameActor;

public class WorldContactListener implements ContactListener {

    @Override
    public void beginContact(Contact contact) {
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();

        if(fixtureA.getUserData() == "enemy" || fixtureB.getUserData() == "enemy") {
            Fixture enemy = fixtureA.getUserData() == "enemy" ? fixtureA : fixtureB;
            Fixture object = enemy == fixtureA ? fixtureB : fixtureA;

            if(object.getUserData() != null && GameActor.class.isAssignableFrom(object.getUserData().getClass())) {
                ((GameActor)object.getUserData()).collision();
            }
        }
    }

    @Override
    public void endContact(Contact contact) {
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
    }
    
}
