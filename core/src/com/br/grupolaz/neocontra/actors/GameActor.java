package com.br.grupolaz.neocontra.actors;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.br.grupolaz.neocontra.util.TextureUtils;


//Inspired by MartianRun
public abstract class GameActor extends Actor {
    protected Body body;
    protected String region;
    protected Sprite sprite;
    protected TextureRegion textureRegion;

    public GameActor(Body body, String region) {
        this.body = body;
        sprite = new Sprite(TextureUtils.getAtlas().findRegion(region));
    }

}
