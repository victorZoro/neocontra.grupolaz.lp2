package com.br.grupolaz.neocontra.util;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

//Inspired by Brent Aureli Code
public class MapLoader {
    
    private TmxMapLoader loader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    public MapLoader(String path_to_map_file) {
        loader = new TmxMapLoader();
        map = loader.load(path_to_map_file);
        renderer = new OrthogonalTiledMapRenderer(map, 1f / Constants.PIXELS_PER_METER);
    }

    /**
     * @return TmxMapLoader return the loader
     */
    public TmxMapLoader getLoader() {
        return loader;
    }

    /**
     * @return TiledMap return the map
     */
    public TiledMap getMap() {
        return map;
    }

    /**
     * @param map the map to set
     */
    public void setMap(TiledMap map) {
        this.map = map;
    }

    /**
     * @return OrthogonalTiledMapRenderer return the renderer
     */
    public OrthogonalTiledMapRenderer getRenderer() {
        return renderer;
    }

    public void dispose() {
        map.dispose();
        renderer.dispose();
    }

}
