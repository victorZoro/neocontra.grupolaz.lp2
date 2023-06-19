package com.br.grupolaz.neocontra.actors;

import org.w3c.dom.Text;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import com.br.grupolaz.neocontra.enums.ActorStates;
import com.br.grupolaz.neocontra.util.Constants;


//Inspired by MartianRun & Brent Aureli
public abstract class GameActor extends Actor {
    protected Body body;
    protected TextureRegion region;
    protected Sprite sprite;

    //Sprite animation
    protected ActorStates currentState;
    protected ActorStates previousState;
    protected TextureRegion actorStanding;
    protected Animation<TextureRegion> actorRunning;
    protected Animation<TextureRegion> actorJumping;
    protected Animation<TextureRegion> actorCrounching;

    protected boolean runningRight;
    protected float stateTimer;

    public GameActor(Body body, TextureRegion region) {
        this.body = body;
        this.sprite = new Sprite(region);
        this.sprite.setSize(12.5f / Constants.PIXELS_PER_METER, 18f / Constants.PIXELS_PER_METER);
        this.region = new TextureRegion(region, 0, 0, 25, 36);

        Array<TextureRegion> frames = new Array<TextureRegion>();

        currentState = previousState = ActorStates.STANDING;
        stateTimer = 0;
        runningRight = true;
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
