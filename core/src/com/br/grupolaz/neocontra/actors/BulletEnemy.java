package com.br.grupolaz.neocontra.actors;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.br.grupolaz.neocontra.util.Constants;
import com.br.grupolaz.neocontra.util.TextureUtils;

public class BulletEnemy extends Projectile{
    public boolean tipebullet = true;

    public BulletEnemy(World world, float x, float y, Vector2 velocity) {
        super(world, x, y, velocity, Constants.PLAYER_BULLET_RADIUS);
        this.sprite = new Sprite(TextureUtils.getPlayerBullet());
        this.sprite.setSize(Constants.BULLET_SIZE, Constants.BULLET_SIZE);
    }

    public BulletEnemy(World world, Vector2 position, Vector2 velocity) {
        super(world, position, velocity, Constants.PLAYER_BULLET_RADIUS);
        this.sprite = new Sprite(TextureUtils.getPlayerBullet());
        this.sprite.setSize(Constants.BULLET_SIZE, Constants.BULLET_SIZE);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }

    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
    }
}
