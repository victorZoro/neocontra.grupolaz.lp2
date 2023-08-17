package com.br.grupolaz.neocontra.util;


import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.br.grupolaz.neocontra.enums.Bits;

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

    private final MapLoader mapLoader;
    private final World world;

    /**
     * <h2>WorldUtils</h2>
     * <p>Contrutor da classe WorldUtils, que tema função
     * de criar uma nova instância do mundo do jogo usando
     * a gravidade definida em Constants.WORLD_GRAVITY</p>
     *
     * @param mapLoader tipo MapLoader
     */
    public WorldUtils(MapLoader mapLoader) {
        this.mapLoader = mapLoader;
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
     * <h2>createPerson</h2>
     * <p>Cria um corpo dinâmico para representar
     * o personagem do jogador. <p>Ele configura a
     * definição do corpo, cria a forma do corpo
     * como um círculo e adiciona a forma ao corpo
     * usando uma definição de fixação. O corpo é
     * então adicionado ao mundo do jogo e retornado.</p>
     *
     * @param world tipo World
     * @param x     tipo float
     * @param y     tipo float
     * @return body
     */

    public Body createPerson(World world, float x, float y) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(x, y);
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        Body body = world.createBody(bodyDef);

        FixtureDef fixtureDef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(Constants.PLAYER_RADIUS);

        fixtureDef.shape = shape;
        fixtureDef.friction = 0.5f;

        body.createFixture(fixtureDef);

        PolygonShape head = new PolygonShape();

        head.setAsBox(6f / Constants.PIXELS_PER_METER, 6.5f / Constants.PIXELS_PER_METER);
        fixtureDef.shape = head;
        fixtureDef.isSensor = true;

        body.createFixture(fixtureDef).setUserData("head");

        return body;
    }

    /**
     * <h2>createProjectile</h2>
     * <p>cria um corpo cinemático para
     * representar um projétil no jogo.<p>
     * Ele configura a definição do corpo,
     * cria a forma do corpo como um círculo e
     * adiciona a forma ao corpo usando uma
     * definição de fixação. O corpo é adicionado
     * ao mundo do jogo, e sua velocidade linear é
     * definida com base no vetor de velocidade
     * fornecido.</p>
     *
     * @param x        tipo float
     * @param y        tipo float
     * @param radius   tipo float
     * @param velocity tipo Vector2
     * @return body
     */
    public Body createProjectile(float x, float y, float radius, Vector2 velocity, String userData, short categoryBit) {
        BodyDef bodyDef = new BodyDef();

        bodyDef.position.set(x, y);
        bodyDef.type = BodyDef.BodyType.KinematicBody;

        FixtureDef fixtureDef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(radius);
        fixtureDef.filter.categoryBits = categoryBit;
        fixtureDef.filter.maskBits = (short) (Bits.PLAYER.getBitType() | Bits.ENEMY.getBitType());


        fixtureDef.shape = shape;
        fixtureDef.isSensor = true;

        Body body = world.createBody(bodyDef);
        body.createFixture(fixtureDef).setUserData(userData);
        shape.dispose();

        body.setLinearVelocity(velocity);

        return body;
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
