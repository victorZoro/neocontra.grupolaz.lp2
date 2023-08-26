package com.br.grupolaz.neocontra.util;


import com.badlogic.gdx.physics.box2d.World;

/**
 * <h2>WorldUtils</h2>
 * <p>A classe WorldUtils fornecer utilitários para
 * criar e manipular corpos físicos no mundo do jogo,
 * com base nas informações do mapa. Isso permite a
 * simulação de física e interações realistas entre
 * os objetos no jogo.</p>
 *
 * <h3>package</h3>
 * <p>ultil<p>
 *
 * <h3>Variaveis</h3>
 * <p>-mapLoader: MapLoader</p>
 * <p>-world: World</p>
 *
 * <h3>Métodos</h3>
 * <p>+WorldUtils(MapLoader mapLoader)</p>
 * <p>+getWorld(): World</p>
 * <p>+createWorldBodies(): void</p>
 * <p>+createObject(int): void</p>
 * <p>+createStaticBody(World, Rectangle): void</p>
 * <p>+createPerson(World, float, float): Body</p>
 * <p>+dispose(): void</p>
 */
//Inspired by Martian Run and Brent Aureli Code
public class WorldUtils {

    private final World world;

    /**
     * <h2>WorldUtils</h2>
     * <p>Contrutor da classe WorldUtils, que tema função
     * de criar uma nova instância do mundo do jogo usando
     * a gravidade definida em Constants.WORLD_GRAVITY</p>
     *
     */
    public WorldUtils() {
        this.world = new World(Constants.WORLD_GRAVITY, true);
    }

    /**
     * <h2>getWorld</h2>
     * <p>retorna o objeto do mundo</p>
     *
     * @return world tipo World(get)
     */
    public World getWorld() {
        return world;
    }


    /**
     * <h2> dispose</h2>
     * <p> Descartar os
     * recursos do mundo do jogo quando não
     * são mais necessários.</p>
     */
    public void dispose() {
        world.dispose();
    }
}
