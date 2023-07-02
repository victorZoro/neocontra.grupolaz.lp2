package com.br.grupolaz.neocontra.util;


import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.br.grupolaz.neocontra.util.TextureUtils;
/**
 * <h2>WorldUtils</h2>
 * <p>A classe WorldUtils fornece utilitários para 
 * criar e manipular corpos físicos no mundo do jogo, 
 * com base nas informações do mapa. Isso permite a 
 * simulação de física e interações realistas entre 
 * os objetos no jogo.</p>
 * 
 *  <h3>package</h3>
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

    private MapLoader mapLoader;
    private World world;

    /**
     * <h2>WorldUtils</h2>
     * <p>Contrutor da classe WorldUtils, que tema função
     * de criar uma nova instância do mundo do jogo usando
     *  a gravidade definida em Constants.WORLD_GRAVITY</p>
     * @param mapLoader tipo MapLoader
     */
    public WorldUtils(MapLoader mapLoader) {
        this.mapLoader = mapLoader;
        this.world = new World(Constants.WORLD_GRAVITY, true);
        createWorldBodies();
    }

    /**
     * <h2>getWorld</h2>
     * <p>retorna o objeto do mundo</p>
     * @return world tipo World(get)
     */
    public World getWorld() {
        return world;
    }

    /**
     * <h2>createWorldBodies</h2>
     * <p>Cria corpos estáticos no mundo
     *  do jogo com base nas camadas do mapa, 
     * como camada de solo, escadas, plataformas, 
     * paredes, nível do mar e teto. </p>
     * <p>Isso é feito chamando o método createObject() para cada camada específica.</p>
     */
    public void createWorldBodies() {
        createObject(Constants.GROUND_LAYER);
        createObject(Constants.STAIRS_LAYER);
        createObject(Constants.PLATFORMS_LAYER);
        createObject(Constants.WALLS_LAYER);
        createObject(Constants.SEALEVEL_LAYER);
        createObject(Constants.CEILING_LAYER);
    }
    /**
     * <h2>createObject</h2>
     * <p>cria corpos estáticos no mundo do jogo com base em
     *  objetos retangulares de uma camada específica do mapa.</p>
     * @param layer tipo int
     */
    public void createObject(int layer) {
        for(MapObject object : mapLoader.getMap().getLayers().get(layer).getObjects().getByType(RectangleMapObject.class)){
            Rectangle r = ((RectangleMapObject)object).getRectangle();
            createStaticBody(world, r);
        }
    }

    /**
     * <h2> createStaticBody</h2>
     * <p>cria um corpo estático com base em um 
     * retângulo fornecido. <p>Ele configura a definição 
     * do corpo, cria a forma do corpo como um polígono
     *  retangular e adiciona a forma ao corpo usando 
     * uma definição de fixação. Em seguida, o corpo é 
     * adicionado ao mundo do jogo.</p>
     * @param world tipo World
     * @param r tipo Rectangle
     */
    public void createStaticBody(World world, Rectangle r) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set((r.getX() + r.getWidth() / 2) / Constants.PIXELS_PER_METER, (r.getY() + r.getHeight() / 2) / Constants.PIXELS_PER_METER);

        Body body;
        body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox((r.getWidth() / 2) / Constants.PIXELS_PER_METER, (r.getHeight() / 2) / Constants.PIXELS_PER_METER);
        
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        
        body.createFixture(fixtureDef);

        shape.dispose();
    }

    /**
     * <h2>createPerson</h2>
     * <p>Cria um corpo dinâmico para representar 
     * o personagem do jogador. <p>Ele configura a 
     * definição do corpo, cria a forma do corpo 
     * como um círculo e adiciona a forma ao corpo 
     * usando uma definição de fixação. O corpo é 
     * então adicionado ao mundo do jogo e retornado.</p>
     * @param world tipo World
     * @param x tipo float
     * @param y tipo float
     * @return body 
     */

    public Body createPerson(World world, float x, float y) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(x, y);
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        
        FixtureDef fixtureDef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(Constants.PLAYER_RADIUS);

        fixtureDef.shape = shape;
        fixtureDef.friction = 0.5f;

        Body body = world.createBody(bodyDef);
        body.createFixture(fixtureDef);
        shape.dispose();

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
     *  definida com base no vetor de velocidade 
     * fornecido.</p>
     * @param x tipo float
     * @param y tipo float
     * @param radius tipo float
     * @param velocity tipo Vector2
     * @return body
     */
    public Body createProjectile(float x, float y, float radius, Vector2 velocity) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(x, y);
        bodyDef.type = BodyDef.BodyType.KinematicBody;

        FixtureDef fixtureDef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(radius);

        fixtureDef.shape = shape;
        
        Body body = world.createBody(bodyDef);
        body.createFixture(fixtureDef);
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
