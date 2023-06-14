package com.br.grupolaz.neocontra.actors;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.br.grupolaz.neocontra.box2d.UserData;


//Inspired by MartianRun
public abstract class GameActor extends Actor {
    protected Body body;
    protected UserData userData;
    
    public GameActor(Body body) {
        this.body = body;
        this.userData = (UserData) body.getUserData();
    }

    @Override
    public void act(float delta) {
        super.act(delta);

    }

    public abstract UserData getUserData();
}
