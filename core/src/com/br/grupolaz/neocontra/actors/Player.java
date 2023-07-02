package com.br.grupolaz.neocontra.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.br.grupolaz.neocontra.enums.ActorStates;
import com.br.grupolaz.neocontra.util.Constants;
import com.br.grupolaz.neocontra.util.TextureUtils;
import com.br.grupolaz.neocontra.util.WorldUtils;
/**
 * <h2>Player</h2>
 * <p>a classe Player controla o personagem
 *  do jogador no jogo, gerenciando suas animações,
 *  estado, contagem de vidas e interações com o 
 *  ambiente de jogo.</p>
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

    private Animation<TextureRegion> actorJumping;

    /**
     * <h2>player</h2>
     * A função do construtor Player é criar 
     * uma instância da classe Player e inicializar
     *  seus atributos. </p>
     * <p>O construtor recebe três parâmetros:
     *  um objeto {@link WorldUtils}(responsável pelo mundo do jogo), 
     * um objeto Body (corpo físico do jogador)e uma região de 
     * textura que representa a aparência do jogador.</p>
     * @param world tipo WorldUtils
     * @param body tipo Body
     * @param region tipo TextureRegion
     */
    public Player(WorldUtils world, Body body, TextureRegion region) {
        super(world, body, region);
        spawn();
        lifeCount = 3;
        setUpAnimations();
    }

    /**
     * <h2>hit</h2>
     * <p>A função do método hit() é lidar 
     * com o evento de o jogador ser atingido 
     * por algum objeto ou inimigo. 
     * <p>Quando o jogador é atingido, 
     * esse método é chamado para realizar
     *  as ações necessárias.</P>
     */
    public void spawn() {
        jump();
        body.setLinearVelocity(2f, 0);
    }
    
    public void stayInBounds() {
        if(body.getPosition().x < 0){
            body.setTransform(0, body.getPosition().y, body.getAngle());
        } else if(body.getPosition().x >= 25.5f) {
            body.setTransform(25.5f, body.getPosition().y, body.getAngle());
        }
    }

    public void hit() {
        hit = true;
        lifeCount--;
        System.out.println(lifeCount);
    }

    /**
     * <h2>setHit</h2>
     * <p>O método setHit(boolean)
     *  permite definir o estado de 
     * "atingido" do jogador. </p>
     * <p> Ele recebe um valor booleano 
     * hit como parâmetro e define esse 
     * valor para a variável hit do jogador.</P>
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
     *  de "atingido" ou não.</p> 
     * @return hit tipo boolean (get)
     */
    public boolean isHit() {
        return hit;
    }

    /**
     * <h2>getLifeCount</h2>
     * <p>O método getLifeCount()
     *  retorna a contagem atual 
     * de vidas do jogador. Ele 
     * retorna o valor da variável 
     * lifeCount, que representa o 
     * número de vidas restantes do jogador.</p>
     * @return lifeCount int (get)
     */
    public int getLifeCount() {
        return lifeCount;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        stayInBounds();
    }
    

    /**
     * <h2>getState</h2>
     * <P>O método getState() 
     * retorna o estado atual 
     * do jogador com base em 
     * sua velocidade linear e
     *  nas teclas pressionadas pelo jogador</p>
     * <p> Os possíveis estados são: "JUMPING" (pulando),
     *  "FALLING" (caindo), "RUNNING" (correndo), 
     * "CROUCHING" (agachado) e "STANDING" (parado).</p>
     * @return retorna o esta atual do jogador
     */
    @Override
    public ActorStates getState() {
        if(body.getLinearVelocity().y != 0) {
            return ActorStates.JUMPING;
        } else if(body.getLinearVelocity().x != 0 ) {
            return ActorStates.RUNNING;
        } else if(Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            return ActorStates.CROUCHING;
        } else {
            return ActorStates.STANDING;
        }
    }
    
    /**
     * <h2>checkCurrentState</h2>
     * <p>O método checkCurrentState() 
     * verifica o estado atual do jogador
     *  e retorna a região de textura correspondente
     *  para renderização. <p>Ele também realiza ajustes 
     * no tamanho e na posição do sprite do jogador 
     * com base no estado atual.</p>
     * @return etorna a região de textura correspondente para renderização
     */
    @Override
    protected TextureRegion checkCurrentState() {
        TextureRegion region;

        switch (currentState) {
            case JUMPING: {
                region = actorJumping.getKeyFrame(stateTimer, true);
                sprite.setPosition(sprite.getX(), sprite.getY() - (2f / Constants.PIXELS_PER_METER));
                break;
            }

            case RUNNING: {
                resetSpriteSize(sprite);
                region = actorRunning.getKeyFrame(stateTimer, true);
                break;
            }

            case CROUCHING: {
                region = actorCrouching;
                sprite.setSize(25f / Constants.PIXELS_PER_METER, 16f / Constants.PIXELS_PER_METER);
                sprite.setPosition(sprite.getX(), sprite.getY() - (2f / Constants.PIXELS_PER_METER));
                break;
            }
            
            // Next 3 cases are all the same,
            // so we jump to the next one until
            // we reach the default case.
            case FALLING: case STANDING: default: {
                region = actorStanding;
                resetSpriteSize(sprite);
                break;
            }
        }

        return region;
    }

    
    /**
     * <h2>SetUpAnimations</h2>
     * <P>O método setUpAnimations() 
     * configura as animações do jogador.</p>
     *  Ele define as regiões de textura 
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
        for(int i = 0; i < 6; i++) {
            frames.add(new TextureRegion(runningRegion, i * 48, 0, 32, 48));
        }
        actorRunning = new Animation<TextureRegion>(0.15f, frames);
        frames.clear();


        //Jumping
        TextureRegion jumpingRegion = TextureUtils.getPlayerAtlas().findRegion(Constants.PLAYER_JUMPING_REGION);
        for(int i = 0; i < 4; i++) {
            frames.add(new TextureRegion(jumpingRegion, i * 48, 0, 48, 48));
        }
        actorJumping = new Animation<TextureRegion>(0.15f, frames);
        frames.clear();
    }



}
