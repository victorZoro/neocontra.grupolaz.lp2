package com.br.grupolaz.neocontra.actors;


import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.br.grupolaz.neocontra.enums.ActorStates;
import com.br.grupolaz.neocontra.util.Constants;
import com.br.grupolaz.neocontra.util.TextureUtils;
import com.br.grupolaz.neocontra.util.WorldUtils;
/**
 * <h2>Enemy</h2>
 * <P> a classe Enemy representa
 *  um inimigo no jogo.
 * <p> Ele configura as animações do inimigo e define 
 * sua aparência visual usando regiões
 * de textura do atlas de texturas. 
 * Isso permite que o inimigo seja 
 * renderizado corretamente e exiba 
 * as animações apropriadas durante o jogo.</p>
 * 
 * <h3>package</h3>
 * <P>actors</p>
 * 
 * <h3>Métodos</h3>
 * <p>+Enemy(WorldUtils, Body, TextureRegion)</p>
 * <p>#setUpAnimations(): void</p>
 */
public class Enemy extends GameActor {

    private Player player;

    /**
     * <h2getFixtureList>Enemy</h2getFixtureList>
     * <p>A função do construtor Enemy
     *  é criar uma instância da classe
     *  Enemy e inicializar seus atributos.</p>
     * <p>O construtor recebe três parâmetros:
     *  uma instância da classe WorldUtils, que 
     * lida com a lógica do mundo do jogo, um 
     * objeto Body que representa o corpo físico
     *  do inimigo no mundo do Box2D, e uma região
     *  de textura que será usada para renderizar 
     * o inimigo.</p>
     * @param world tipo WorldUtils
     * @param body tipo Body
     * @param region tipo TextureRegion
     */
    
    
    public Enemy(WorldUtils world, Body body, TextureRegion region, Player player) {
        super(world, body, region);
        body.getFixtureList().get(0).setUserData(this);
        setCategoryFilter(Constants.ENEMY_BIT);
        this.player = player;
        setToDestroy = false;
        destroyed = false;
        setUpAnimations();
    }

    /**
     * <h2>setUpAnimations</h2>
     * <p>A função do método setUpAnimations
     * é responsável por configurar
     *  as animações do inimigo.</p> Ela
     *  é chamada no construtor da 
     * classe Enemy para definir as 
     * animações que serão usadas ao 
     * renderizar o inimigo.</p>
     * 
     * <h4>funçoes especificas</h4>
     * <p>diferentes regiões de textura
     *  são obtidas do atlas de texturas
     *  usando a classe {@link TextureUtils}</P>
     */
    public void facePlayer() {
        TextureRegion region = checkCurrentState();

        if(player.getBody().getPosition().x < body.getPosition().x && !(region.isFlipX())) {
            region.flip(true, false);
        } else if(player.getBody().getPosition().x > body.getPosition().x && region.isFlipX()) {
            region.flip(true, false);
        }
    }

    public void update(float delta) {
        stateTime += delta;
        if(setToDestroy && !destroyed) {
            world.getWorld().destroyBody(body);
            currentState = ActorStates.DEAD;
            destroyed = true;
            stateTime = 0;
        }
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        update(delta);
        facePlayer();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if(!destroyed | stateTime < 1f) {
            super.draw(batch, parentAlpha);
        }
    }


    @Override
    protected void setUpAnimations() {
        actorStanding = TextureUtils.getEnemyAtlas().findRegion(Constants.ENEMY_STILL_REGION);

        //Running
        TextureRegion runningRegion = TextureUtils.getEnemyAtlas().findRegion(Constants.ENEMY_RUNNING_REGION);
        for(int i = 0; i < 5; i++) {
            frames.add(new TextureRegion(runningRegion, i * 48, 0, 32, 48));
        }
        actorRunning = new Animation<TextureRegion>(0.15f, frames);
        frames.clear();
        
        TextureRegion dyingRegion = TextureUtils.getEnemyAtlas().findRegion(Constants.ENEMY_DYING_REGION);
        for(int i = 0; i < 3; i++) {
            frames.add(new TextureRegion(dyingRegion, i * 48, 0, 48, 64));
        }
        actorDying = new Animation<TextureRegion>(0.1f, frames);
        frames.clear();
        System.out.println("Created dying region");
    }

    @Override
    public void shoot() {
    }

    @Override
    public void collision() {
        setToDestroy = true;
        System.out.println("Death Enemy muahahahaha");
    }

    @Override
    protected TextureRegion checkCurrentState() {
        TextureRegion region;

        switch (currentState) {
            case RUNNING: {
                System.out.println("running in the 90s");
                resetSpriteSize(sprite);
                region = actorRunning.getKeyFrame(stateTimer, true);
                break;
            }

            case DEAD: {
                region = actorDying.getKeyFrame(stateTimer, false);
                break;
            }

            // Next 3 cases are all the same,
            // so we jump to the next one until
            // we reach the default case.
            case FALLING:
            case STANDING:
            default: {
                region = actorStanding;
                resetSpriteSize(sprite);
                break;
            }
        }

        return region;
    }
    
}
