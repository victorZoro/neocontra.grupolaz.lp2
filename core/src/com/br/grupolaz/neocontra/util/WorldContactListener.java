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

        if (fixtureA.getUserData() == "bullet" || fixtureB.getUserData() == "bullet01") {
            Fixture bullet = fixtureA.getUserData() == "bullet01" ? fixtureA : fixtureB;
            Fixture bullet01 = fixtureB.getUserData() == "bullet01" ? fixtureA : fixtureB;
            Fixture enemy = bullet == fixtureA ? fixtureB : fixtureA;
            Fixture player = bullet01 == fixtureB ? fixtureA : fixtureB;

            System.out.println(enemy.getUserData().getClass());
            System.out.println(player.getUserData());

            if (enemy.getUserData() != null && GameActor.class.isAssignableFrom(enemy.getUserData().getClass())) {
                ((GameActor) enemy.getUserData()).collision();
                ((GameActor) player.getUserData()).collision();
            }
        }
    }

    @Override
    public void endContact(Contact contact) {
        Gdx.app.log("End Contact", "test");
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
    }

}
