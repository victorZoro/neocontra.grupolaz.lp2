package com.br.grupolaz.neocontra.actors;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.br.grupolaz.neocontra.util.Constants;
import com.br.grupolaz.neocontra.util.TextureUtils;

public class Bullet extends Projectile {

    public Bullet(Body body) {
        super(body);
        this.sprite = new Sprite(TextureUtils.getPlayerBullet());
        this.sprite.setSize(3f / Constants.PIXELS_PER_METER, 3f / Constants.PIXELS_PER_METER);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }

    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
    }
    
}
