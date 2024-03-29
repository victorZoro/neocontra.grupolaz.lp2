package com.br.grupolaz.neocontra.actors;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import com.br.grupolaz.neocontra.enums.ActorStates;
import com.br.grupolaz.neocontra.util.Constants;
import com.br.grupolaz.neocontra.util.SoundsUtils;
import com.br.grupolaz.neocontra.util.TextureUtils;
import com.br.grupolaz.neocontra.util.WorldUtils;

/**
 * <h2>Player</h2>
 * <p>a classe Player controla o personagem
 * do jogador no jogo, gerenciando suas animações,
 * estado, contagem de vidas e interações com o
 * ambiente de jogo.</p>
 *
 * <h3>package</h3>
 * <p>actors</p>
 *
 * <h3>Variaveis</h3>
 * <p>-hit: boolean</p>
 * <p>-lifeCount: int</p>
 * <p>-<TextureRegion> actorJumping: Animation</p>
 *
 * <h3>Métodos</h3>
 * <p>+Player(WorldUtils, Body, TextureRegion)</p>
 * <p>+hit(): void</p>
 * <p>+setHit(boolean): void</p>
 * <p>+isHit(): boolean</p>
 * <p>+getLifeCount(): int</p>
 * <p>+getState(): ActorStates</p>
 * <p>#checkCurrentState(): TextureRegion</p>
 * <p>#setUpAnimations(): void</p>
 */
public class Player extends GameActor {

    private boolean hit;
    private int lifeCount;

    /**
     * <h2>player</h2>
     * A função do construtor Player é criar
     * uma instância da classe Player e inicializar
     * seus atributos. </p>
     * <p>O construtor recebe três parâmetros:
     * um objeto {@link WorldUtils}(responsável pelo mundo do jogo),
     * um objeto Body (corpo físico do jogador)e uma região de
     * textura que representa a aparência do jogador.</p>
     *
     * @param world  tipo WorldUtils
     * @param region tipo TextureRegion
     */
    public Player(World world, TextureRegion region, float x, float y) {
        super(world, region, x, y);
//        body.getFixtureList().get(0).setUserData(this);
//        setCategoryFilter(Bits.PLAYER.getBitType());
        spawn();
        lifeCount = 3;
        setUpAnimations();
    }

    public Player(World world, TextureRegion region, Vector2 position) {
        super(world, region, position);
//        body.getFixtureList().get(0).setUserData(this);
//        setCategoryFilter(Bits.PLAYER.getBitType());
        spawn();
        lifeCount = 3;
        setUpAnimations();
    }

    // Inspired by Brent Aureli Code

    /**
     * <h2>hit</h2>
     * <p>A função do método hit() é lidar
     * com o evento de o jogador ser atingido
     * por algum objeto ou inimigo.
     * <p>Quando o jogador é atingido,
     * esse método é chamado para realizar
     * as ações necessárias.</P>
     */
    public void spawn() {
        jump();
        body.setLinearVelocity(2f, 0);
    }

    private boolean collisionWithBulletEnemy() {
    for (Fixture fixture : body.getFixtureList()) {
        Object userData = fixture.getUserData();
        if (userData instanceof BulletEnemy) {
            return true;
        }
    }
    return false;
}
    public void hit() {
        hit = true;
        lifeCount--;
        System.out.println("Voce so tem:" + lifeCount + "vidas");
    }

    public void die() {
        setToDestroy = true;
    }

    /**
     * <h2>setHit</h2>
     * <p>O método setHit(boolean)
     * permite definir o estado de
     * "atingido" do jogador. </p>
     * <p> Ele recebe um valor booleano
     * hit como parâmetro e define esse
     * valor para a variável hit do jogador.</P>
     *
     * @param hit tipo boolean (set)
     */
    public void setHit(boolean hit) {
        this.hit = hit;
    }

    /**
     * <h2>isHit</h2>
     * <p>O método isHit() retorna
     * um valor booleano indicando
     * se o jogador está no estado
     * de "atingido" ou não.</p>
     *
     * @return hit tipo boolean (get)
     */
    public boolean isHit() {
        return hit;
    }

    /**
     * <h2>getLifeCount</h2>
     * <p>O método getLifeCount()
     * retorna a contagem atual
     * de vidas do jogador. Ele
     * retorna o valor da variável
     * lifeCount, que representa o
     * número de vidas restantes do jogador.</p>
     *
     * @return lifeCount int (get)
     */
    public int getLifeCount() {
        return lifeCount;
    }

    public void setLifeCount(int lifeCount) {
        this.lifeCount = lifeCount;
    }

    public void update(float delta) {
        stateTime += delta; 
        

        if (lifeCount <= 0) {
            die();
        }

        if (setToDestroy && !destroyed && !world.isLocked()) {
            world.destroyBody(body);
            currentState = ActorStates.DEAD;
            destroyed = true;
            stateTime = 0;
        }
    }


    @Override
    public void act(float delta) {
        super.act(delta);
        update(delta);
        collision();
        //stayInBounds();
        
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if (!destroyed || stateTime < 1) {
            super.draw(batch, parentAlpha);
        }
    }



    /**
     * <h2>SetUpAnimations</h2>
     * <P>O método setUpAnimations()
     * configura as animações do jogador.</p>
     * Ele define as regiões de textura
     * correspondentes aos diferentes estados do
     * jogador, como parado, correndo, agachado e pulando.</p>
     */
    @Override
    //Inspired by Brent Aureli Codes
    protected void setUpAnimations() {
        //Standing
        actorStanding = TextureUtils.getPlayerAtlas().findRegion(Constants.PLAYER_STILL_REGION);

        //Crouching
        actorCrouching = TextureUtils.getPlayerAtlas().findRegion(Constants.PLAYER_CROUCHING_REGION);

        //Running
        TextureRegion runningRegion = TextureUtils.getPlayerAtlas().findRegion(Constants.PLAYER_RUNNING_REGION);
        for (int i = 0; i < 6; i++) {
            frames.add(new TextureRegion(runningRegion, i * 48, 0, 32, 48));
        }
        actorRunning = new Animation<>(0.15f, frames);
        frames.clear();


        //Jumping
        TextureRegion jumpingRegion = TextureUtils.getPlayerAtlas().findRegion(Constants.PLAYER_JUMPING_REGION);
        for (int i = 0; i < 4; i++) {
            frames.add(new TextureRegion(jumpingRegion, i * 48, 0, 48, 48));
        }
        actorJumping = new Animation<>(0.15f, frames);
        frames.clear();

        //Dying
        TextureRegion dyingRegion = TextureUtils.getPlayerAtlas().findRegion(Constants.PLAYER_DYING_REGION);
        for (int i = 0; i < 5; i++) {
            frames.add(new TextureRegion(dyingRegion, i * 48, 0, 32, 48));
        }
        actorDying = new Animation<>(0.25f, frames);
        frames.clear();
    }

    @Override
    public void shoot() {
        if (!runningRight) {
            if (currentState == ActorStates.CROUCHING) {
                projectiles.add(new Bullet(world,body.getPosition().x - Constants.PLAYER_RADIUS - 1f / Constants.PIXELS_PER_METER, body.getPosition().y - 1.5f / Constants.PIXELS_PER_METER, new Vector2(-3f, 0)));
            } else {
                projectiles.add(new Bullet(world, body.getPosition().x - Constants.PLAYER_RADIUS - 1f / Constants.PIXELS_PER_METER, body.getPosition().y + 2f / Constants.PIXELS_PER_METER, new Vector2(-3f, 0)));
            }
        } else {
            if (currentState == ActorStates.CROUCHING) {
                projectiles.add(new Bullet(world, body.getPosition().x + Constants.PLAYER_RADIUS + 1f / Constants.PIXELS_PER_METER, body.getPosition().y - 1.5f / Constants.PIXELS_PER_METER, new Vector2(3f, 0)));
            } else {
                projectiles.add(new Bullet(world, body.getPosition().x + Constants.PLAYER_RADIUS + 1f / Constants.PIXELS_PER_METER, body.getPosition().y + 2f / Constants.PIXELS_PER_METER, new Vector2(3f, 0)));
            }
        }
        SoundsUtils.getShotSound().play();
    }

    @Override
    public void collision() {
        if(isHit()){  
            if(collisionWithBulletEnemy()){
                hit();          
            }
            setHit(hit);
            getLifeCount(); //Essa linha ainda não tem utilidade...
            update(stateTime);
        }
    }

}
