package com.br.grupolaz.neocontra.interactive;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

public class Ground extends InteractiveTileObject {
    public Ground(World world, TiledMap map, Rectangle bounds) {
        super(world, map, bounds);
        fixture.setUserData(this);

    }

    @Override
    public void onPlayerCollision(Body player) {

    }

}
