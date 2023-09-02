package com.br.grupolaz.neocontra.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.*;
import com.br.grupolaz.neocontra.actors.*;
import com.br.grupolaz.neocontra.interactive.Stairs;

public class WorldContactListener implements ContactListener {

    @Override
    public void beginContact(Contact contact) {
        // Fixture fixtureA = contact.getFixtureA();
        // Fixture fixtureB = contact.getFixtureB();
        //
        //
        // if((fixtureA.getUserData() == "bullet") || (fixtureB.getUserData() ==
        // "bullet")) {
        // Fixture bullet = fixtureA.getUserData() == "bullet" ? fixtureA : fixtureB;
        // Fixture enemy = bullet == fixtureA ? fixtureB : fixtureA;
        //
        // System.out.println(enemy.getUserData());
        //
        // if(enemy.getUserData() != null &&
        // GameActor.class.isAssignableFrom(enemy.getUserData().getClass())) {
        // ((GameActor) enemy.getUserData()).collision();
        // }
        // }

        Body bodyA = contact.getFixtureA().getBody();
        Body bodyB = contact.getFixtureB().getBody();

        System.out.println(bodyA.getUserData() + " :" + bodyB.getUserData());

        if ((bodyA.getUserData() instanceof GameActor) || (bodyB.getUserData() instanceof GameActor)) {
            Body actor = bodyA.getUserData() instanceof GameActor ? bodyA : bodyB;
            Body object = actor == bodyA ? bodyB : bodyA;

            if (object.getFixtureList().size > 1) {
                System.out.println("you can bet it is bigger than 1");
                if (object.getFixtureList().get(2).getUserData() == "stairsSensor") {
                    System.out.println("hit a staircase. better jump");
                    ((Stairs) object.getUserData()).onPlayerCollision(actor);
                }
            }
        }

        if ((bodyA.getUserData() instanceof Projectile) || (bodyB.getUserData() instanceof Projectile)) {
            Body projectile = bodyA.getUserData() instanceof Projectile ? bodyA : bodyB;
            Body object = projectile == bodyA ? bodyB : bodyA;

            System.out.println("Objeto instacia " + object.getUserData());

            
            if (object.getUserData() instanceof Enemy) {
                if (bodyB.getUserData() instanceof Bullet) {
                    ((Enemy) object.getUserData()).collision();
                    ((Projectile) projectile.getUserData()).setToDestroy();
                    System.out.println("instancia do objeto: " + object.getUserData());
                }

            } else if (object.getUserData() instanceof Player) {
                if (bodyB.getUserData() instanceof BulletEnemy) {
                    if (projectile.getUserData() instanceof BulletEnemy) {
                        ((Player) object.getUserData()).hit();
                        ((Player) object.getUserData()).collision();
                        ((Projectile) projectile.getUserData()).setToDestroy();
                        System.out.println("Colisão com o player instancia do objeto: " + projectile.getUserData());
                    }
                }
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
