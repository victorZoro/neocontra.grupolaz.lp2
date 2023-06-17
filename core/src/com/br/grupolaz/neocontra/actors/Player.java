package com.br.grupolaz.neocontra.actors;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.br.grupolaz.neocontra.util.Constants;

public class Player extends GameActor {

    private boolean hit;

    public Player(Body body, String region) {
        super(body, region);
        textureRegion = new TextureRegion(sprite.getTexture(), 0, 0, 256, 256);
        setBounds(0, 0, 8 / Constants.PIXELS_PER_METER, 8 / Constants.PIXELS_PER_METER);
        sprite.setRegion(textureRegion);
        hit = false;
    }


    public void hit() {
        hit = true;
    }

    public void setHit(boolean hit) {
        this.hit = hit;
    }
    
    public boolean isHit() {
        return hit;
    }

    public Body getBody() {
        return body;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        sprite.draw(batch, parentAlpha);
    }
}
