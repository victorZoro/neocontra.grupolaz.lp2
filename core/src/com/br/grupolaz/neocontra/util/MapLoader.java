package com.br.grupolaz.neocontra.util;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
/**
 * <h2>MapLoader</h2>
 * <p>A classe MapLoader é responsável por carregar e
 *  renderizar mapas Tiled em um jogo. Ela fornece métodos 
 * para acessar o carregador de mapas, o mapa em si e o 
 * renderizador do mapa.</p> Além disso, a classe possui uma 
 * função para descartar os recursos do mapa quando não são
 *  mais necessários. Isso simplifica a utilização e o gerenciamento 
 * de mapas em um ambiente de jogo.</p>
 * 
 * *<h3>package</h3>
 * <p>Util</p>
 * 
 * <h3>Variaveis</h3>
 * <p>-loader: TmxMapLoader </p>
 * <p>-map: TiledMap </p>
 * <p>-renderer: OrthogonalTiledMapRenderer</p>
 * 
 * <h3>Métodos</h3>
 * <p>+MapLoader(String)</p>
 * <p>+getLoader(): TmxMapLoader</p>
 * <p>+getMap(): TiledMap </p>
 * <p>+setMap(TiledMap): void</p>
 * <p>+getRenderer(): OrthogonalTiledMapRenderer</p>
 * <p>+dispose(): void</p> 
 */
//Inspired by Brent Aureli Code
public class MapLoader {
    
    private TmxMapLoader loader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    /**
     * <h2>MapLoader</h2>
     * <p>Metodo reposavel por carregar o mapa 
     * Tiled com base no caminho do arquivo fornecido</p>
     * @param path_to_map_file tipo String
     */
    public  MapLoader(String path_to_map_file) {
        loader = new TmxMapLoader();
        map = loader.load(path_to_map_file);
        renderer = new OrthogonalTiledMapRenderer(map, 1f / Constants.PIXELS_PER_METER);
    }

    /**
     * <h2>getLoader</h2>
     * <p>retorna o aqrivo do mapa caregado</p>
     * @return loader tipo TmxMapLoader(get)
     */
    public TmxMapLoader getLoader() {
        return loader;
    }

    /**
     * <h2>getMap</h2>
     * <P>retorna o mapa</p>
     * @return map tipo TiledMap(get)
     */
    public TiledMap getMap() {
        return map;
    }

    /**
     * <h2>setMap</h2>
     * <p>Seta o valor de map</p>
     * @param map tipo TiledMap(set)
     */
    public void setMap(TiledMap map) {
        this.map = map;
    }

    /**
     * <h2>getRenderer</h2>
     * <p>Retorna o mapa renderizado</p>
     * @return renderer tipo OrthogonalTiledMapRenderer(get)
     */
    public OrthogonalTiledMapRenderer getRenderer() {
        return renderer;
    }
    /**
     * <h2>dispose</h2>
     * <p> responsável por descartar os recursos associados ao mapa e 
     * ao renderizador, liberando a memória utilizada por eles.</p>
     */
    public void dispose() {
        map.dispose();
        renderer.dispose();
    }

}
