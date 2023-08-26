package com.br.grupolaz.neocontra.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.br.grupolaz.neocontra.actors.Enemy;
import com.br.grupolaz.neocontra.actors.GameActor;
import com.br.grupolaz.neocontra.actors.Player;
import com.br.grupolaz.neocontra.actors.Projectile;
import com.br.grupolaz.neocontra.interactive.InteractiveTileObject;
import com.br.grupolaz.neocontra.interactive.Stairs;

public class WorldContactListener implements ContactListener {

    @Override
    public void beginContact(Contact contact) {
//        Fixture fixtureA = contact.getFixtureA();
//        Fixture fixtureB = contact.getFixtureB();
//
//
//        if((fixtureA.getUserData() == "bullet") || (fixtureB.getUserData() == "bullet")) {
//            Fixture bullet = fixtureA.getUserData() == "bullet" ? fixtureA : fixtureB;
//            Fixture enemy = bullet == fixtureA ? fixtureB : fixtureA;
//
//            System.out.println(enemy.getUserData());
//
//            if(enemy.getUserData() != null && GameActor.class.isAssignableFrom(enemy.getUserData().getClass())) {
//                ((GameActor) enemy.getUserData()).collision();
//            }
//        }

        Body bodyA = contact.getFixtureA().getBody();
        Body bodyB = contact.getFixtureB().getBody();

        System.out.println(bodyA.getUserData() + " :" + bodyB.getUserData());

        if((bodyA.getUserData() instanceof GameActor) || (bodyB.getUserData() instanceof GameActor)) {
            Body player = bodyA.getUserData() instanceof GameActor ? bodyA : bodyB;
            Body object = player == bodyA ? bodyB : bodyA;

            if(object.getFixtureList().size > 1) {
                if(object.getFixtureList().get(2).getUserData() == "stairsSensor") {
                    ((Stairs) object.getUserData()).onPlayerCollision(player);
                }
            }
        }


        if((bodyA.getUserData() instanceof Projectile) || (bodyB.getUserData() instanceof Projectile)) {
            Body projectile = bodyA.getUserData() instanceof Projectile ? bodyA : bodyB;
            Body object = projectile == bodyA ? bodyB : bodyA;

            System.out.println("Objeto instacia" +object.getUserData());

            if(object.getUserData() instanceof Enemy) {
                ((Enemy) object.getUserData()).collision();
                ((Projectile) projectile.getUserData()).setToDestroy();
            }

            if(bodyA.getUserData() != null){
                ((Player) object.getUserData()).isHit();
                ((Player) object.getUserData()).collision();;
                System.out.println("Player collision");
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
