package com.br.grupolaz.neocontra.actors;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.br.grupolaz.neocontra.enums.ActorStates;
import com.br.grupolaz.neocontra.enums.Bits;
import com.br.grupolaz.neocontra.util.Constants;
import com.br.grupolaz.neocontra.util.SoundsUtils;
import com.br.grupolaz.neocontra.util.TextureUtils;

/**
 * <h2>Enemy</h2>
 * <P>
 * a classe Enemy representa
 * um inimigo no jogo.
 * <p>
 * Ele configura as animações do inimigo e define
 * sua aparência visual usando regiões
 * de textura do atlas de texturas.
 * Isso permite que o inimigo seja
 * renderizado corretamente e exiba
 * as animações apropriadas durante o jogo.
 * </p>
 * 
 * <h3>package</h3>
 * <P>
 * actors
 * </p>
 * 
 * <h3>Métodos</h3>
 * <p>
 * +Enemy(WorldUtils, Body, TextureRegion)
 * </p>
 * <p>
 * #setUpAnimations(): void
 * </p>
 */
public class Enemy extends GameActor {

    private final Player player;
    // variaveis de tiro
    private float timeSinceAttack;
    private float attackDistance;
    private boolean canAttack;
    ArrayList<Enemy> enemies = new ArrayList<>();

    // variaveis para movimento do inimigo
    private Vector2 playerPos;
    private Vector2 enemyPos;
    private Vector2 direction;
    private float distanceToPlayer;
    private float desiredDistance;
    public float runSpeed = 2.0f;

    /**
     * <h2>Enemy</h2>
     * <p>
     * A função do construtor Enemy
     * é criar uma instância da classe
     * Enemy e inicializar seus atributos.
     * </p>
     * <p>
     * O construtor recebe três parâmetros:
     * uma instância da classe WorldUtils, que
     * lida com a lógica do mundo do jogo, um
     * objeto Body que representa o corpo físico
     * do inimigo no mundo do Box2D, e uma região
     * de textura que será usada para renderizar
     * o inimigo.
     * </p>
     * 
     * @param world  tipo WorldUtils
     * @param region tipo TextureRegion
     */

    public Enemy(World world, TextureRegion region, Player player, float x, float y) {
        super(world, region, x, y);
        body.getFixtureList().get(0).setUserData(this);
        setCategoryFilter(Bits.ENEMY.getBitType());
        this.player = player;
        setToDestroy = false;
        destroyed = false;
        setUpAnimations();
        this.timeSinceAttack = 0;
        this.canAttack = true;
        this.attackDistance = 1.5f;
        this.enemyPos = new Vector2(body.getPosition());
        this.playerPos = new Vector2(player.body.getPosition());
        this.distanceToPlayer = 0;
        this.direction = new Vector2();
    }

    public Enemy(World world, TextureRegion region, Player player, Vector2 position) {
        super(world, region, position);
        body.getFixtureList().get(0).setUserData(this);
        setCategoryFilter(Bits.ENEMY.getBitType());
        this.player = player;
        setToDestroy = false;
        destroyed = false;
        setUpAnimations();
    }

    /**
     * <h2>setUpAnimations</h2>
     * <p>
     * A função do método setUpAnimations
     * é responsável por configurar
     * as animações do inimigo.
     * </p>
     * Ela
     * é chamada no construtor da
     * classe Enemy para definir as
     * animações que serão usadas ao
     * renderizar o inimigo.
     * </p>
     * 
     * <h4>funçoes especificas</h4>
     * <p>
     * diferentes regiões de textura
     * são obtidas do atlas de texturas
     * usando a classe {@link TextureUtils}
     * </P>
     */
    public void facePlayer() {
        TextureRegion region = checkCurrentState();

        if (player.getBody().getPosition().x < body.getPosition().x && !(region.isFlipX())) {
            region.flip(true, false);
        } else if (player.getBody().getPosition().x > body.getPosition().x && region.isFlipX()) {
            region.flip(true, false);
        }
    }

    @Override
    protected void flipSprite(TextureRegion region) {
        if ((body.getLinearVelocity().x > 0 || !runningRight) && !region.isFlipX()) {
            region.flip(true, false);
            runningRight = false;
        } else if ((body.getLinearVelocity().x < 0 || runningRight) && region.isFlipX()) {
            region.flip(true, false);
            runningRight = true;
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if (!destroyed | stateTime < 1f) {
            super.draw(batch, parentAlpha);
        }
    }

    @Override
    protected void setUpAnimations() {
        actorStanding = TextureUtils.getEnemyAtlas().findRegion(Constants.ENEMY_STILL_REGION);

        // Running
        TextureRegion runningRegion = TextureUtils.getEnemyAtlas().findRegion(Constants.ENEMY_RUNNING_REGION);
        for (int i = 0; i < 5; i++) {
            frames.add(new TextureRegion(runningRegion, i * 48, 0, 32, 48));
        }
        actorRunning = new Animation<TextureRegion>(0.15f, frames);
        frames.clear();

        TextureRegion dyingRegion = TextureUtils.getEnemyAtlas().findRegion(Constants.ENEMY_DYING_REGION);
        for (int i = 0; i < 3; i++) {
            frames.add(new TextureRegion(dyingRegion, i * 48, 0, 48, 64));
        }
        actorDying = new Animation<TextureRegion>(0.1f, frames);
        frames.clear();
        System.out.println("Created dying region");
    }

    @Override
    public void shoot() {
        if (runningRight) {
            if (!destroyed) {
                projectiles.add(new Bullet(world,
                        body.getPosition().x - Constants.PLAYER_RADIUS - 1f / Constants.PIXELS_PER_METER,
                        body.getPosition().y + 2f / Constants.PIXELS_PER_METER, new Vector2(-3f, 0)));
                SoundsUtils.getShotSound().play();
                canAttack = false;
                System.out.println();
            } else if (!runningRight) {
                if (!destroyed) {
                    projectiles.add(new Bullet(world,
                            body.getPosition().x + Constants.PLAYER_RADIUS + 1f / Constants.PIXELS_PER_METER,
                            body.getPosition().y + 2f / Constants.PIXELS_PER_METER, new Vector2(3f, 0)));
                    SoundsUtils.getShotSound().play();
                    canAttack = false;
                }
            }
        }
    }

    public void timeAttack(float delta, Vector2 posPlayer, List<Projectile> projectiles) {
        if ((posPlayer.len() * (-1)) <= attackDistance && canAttack) {
            shoot();
            timeSinceAttack = 0;
            System.out.println("tiro");
        }
    }

    public void updateAttackTimer(float delta) {
        if (!canAttack) {
            timeSinceAttack += delta;

            if (timeSinceAttack >= 3) {
                canAttack = true;
            }
        }
    }

    public void update(float delta, Vector2 playerpos, List<Projectile> projectiles) {

        if (destroyed) {
            canAttack = false;
        }
        // temporizador
        updateAttackTimer(delta);

        // verificação de ataque
        timeAttack(delta, playerpos, projectiles);
        for (Enemy enemy : enemies) {
            enemy.act(delta);
        }
    }

    @Override
    public void collision() {
        setToDestroy = true;
        System.out.println("Death Enemy muahahahaha");
    }

    // Método para fazer o inimigo seguir o jogador
    public void enemyChasesPlayer(float delta) {
        playerPos = new Vector2(player.body.getPosition().x, delta);
        enemyPos = new Vector2(body.getPosition());
        direction.x = (playerPos.x + 40) - (enemyPos.x + 40);
        direction.y = (playerPos.y + 40) - (enemyPos.y + 40);

        distanceToPlayer = direction.len();
        desiredDistance = 1.5f;

        if (distanceToPlayer > desiredDistance) {
            direction.nor();
            body.setLinearVelocity(direction.scl(runSpeed));
        } else {
            body.setLinearVelocity(0, 0);
        }

    }

    public void enemyDed(float delta) {
        stateTime += delta;
        if (setToDestroy && !destroyed) {
            world.destroyBody(body);
            body.setUserData(null);
            currentState = ActorStates.DEAD;
            destroyed = true;
            stateTime = 0;
            runSpeed = 0;
        }
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

    @Override
    public void act(float delta) {
        super.act(delta);
        enemyChasesPlayer(delta);
        enemyDed(delta);
        facePlayer();
        update(delta, playerPos, null);
    }
}
