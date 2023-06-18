package com.br.grupolaz.neocontra.actors;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.br.grupolaz.neocontra.util.Constants;


//Inspired by MartianRun
public abstract class GameActor extends Actor {
    protected Body body;
    protected TextureRegion region;
    protected Sprite sprite;

    public GameActor(Body body, TextureRegion region) {
        this.body = body;
        this.sprite = new Sprite(region);
        this.sprite.setSize(12.5f / Constants.PIXELS_PER_METER, 18f / Constants.PIXELS_PER_METER);
        this.region = new TextureRegion(region, 0, 0, 25, 36);
    }

    public void setDrawRegion(int x, int y, int width, int height) {
        this.region.setRegion(x, y, width, height);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        float x = body.getPosition().x - sprite.getWidth() / 3;
        float y = body.getPosition().y - sprite.getHeight() / 2;
        sprite.setPosition(x, y);

        sprite.setRegion(this.region);
        sprite.draw(batch);
    }

}
