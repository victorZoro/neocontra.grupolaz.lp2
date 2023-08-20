package com.br.grupolaz.neocontra.actors;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.br.grupolaz.neocontra.enums.ActorStates;
import com.br.grupolaz.neocontra.util.Constants;
import com.br.grupolaz.neocontra.util.SoundsUtils;
import com.br.grupolaz.neocontra.util.TextureUtils;
import com.br.grupolaz.neocontra.util.WorldUtils;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;

public class Enemy_2 extends GameActor {

    // A variavel health pode ser usada caso haja inimigos mais fortes.
    // public int helth;
    private Player player;

    // Velocidade de corrida do inimigo
    public float runSpeed = 2.0f;

    // variaveis para movimento do inimigo
    private Vector2 playerPos;
    private Vector2 enemyPos;
    private Vector2 direction;
    private float distanceToPlayer;
    private float desiredDistance;

    protected boolean face = true;
    protected Matrix4 targent;
    protected float targentDistance;
    protected Body body;
    protected Body emeny;

    // Variaveis de salto do inimigo
    private boolean isJumping = false;
    private float junpForce = 8.0f;

    // Variaveis para o tiro
    private float timeSinceAttack;
    private float attackDistance;
    private boolean canAttack;
    ArrayList<Enemy_2> enemies = new ArrayList<>();
    
    public Enemy_2(WorldUtils world, Body body, TextureRegion region, Player player) {
        super(world, body, region);
        this.body = body;
        body.getFixtureList().get(0).setUserData(this);
        setCategoryFilter(Constants.ENEMY_BIT);
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

    public void facePlayer() {
        TextureRegion region = checkCurrentState();
        if (runningRight) {
            if (player.getBody().getPosition().x > body.getPosition().x && !region.isFlipX()) {
                if (region.isFlipX()) {
                    region.flip(true, false);
                    System.out.println("player na direita");
                }
            }
        } else if (!runningRight) {
            if (player.getBody().getPosition().x < body.getPosition().x && region.isFlipX()) {
                region.flip(true, false);
                System.out.println("player na esquerda");
            }
        }
    }

    @Override
    protected TextureRegion checkCurrentState() {
        TextureRegion region;

        switch (currentState) {
            case RUNNING: {
                // System.out.println("running in the 90s");
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
                projectiles.add(new Bullet(world.createProjectile(
                        body.getPosition().x - Constants.PLAYER_RADIUS - 1f / Constants.PIXELS_PER_METER,
                        body.getPosition().y + 4f / Constants.PIXELS_PER_METER, Constants.PLAYER_BULLET_RADIUS,
                        new Vector2(-3f, 0), "bullet", Constants.BULLET_BIT)));
                SoundsUtils.getShotSound().play();
                System.out.println("cra para direita, mas tiro para a esquerda");
                canAttack = false;
            }

        } else if (!runningRight) {
            if (!destroyed) {
                projectiles.add(new Bullet(world.createProjectile(
                        body.getPosition().x + Constants.PLAYER_RADIUS + 1f / Constants.PIXELS_PER_METER,
                        body.getPosition().y + 4f / Constants.PIXELS_PER_METER, Constants.PLAYER_BULLET_RADIUS,
                        new Vector2(3f, 0), "bullet", Constants.BULLET_BIT)));
                SoundsUtils.getShotSound().play();
                System.out.println("cara para a esquerda mas dtiro direita");
                canAttack = false;
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
        for (Enemy_2 enemy : enemies) {
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

        // Lógica de salto se houver obstáculo
        if (shouldEnemyJump(delta)) {
            body.applyLinearImpulse(0, junpForce, body.getWorldCenter().x, body.getWorldCenter().y, true);
            isJumping = true;
        }

    }

    private boolean shouldEnemyJump(float delta) {
        Vector2 currentPosition = body.getPosition();
        Vector2 rayStart = new Vector2(currentPosition.x + (face ? 1.0f : -1.0f), currentPosition.y + 0.5f);
        Vector2 rayEnd = new Vector2(rayStart.x + (face ? 2.0f : -2.0f), rayStart.y);
        float raycastLength = 2.0f; // Ajuste conforme necessário

        return world.rayCastObstacle(rayStart, rayEnd, raycastLength);
    }

    public void enemyDed(float delta) {
        stateTime += delta;
        if (setToDestroy && !destroyed) {
            world.getWorld().destroyBody(body);
            currentState = ActorStates.DEAD;
            destroyed = true;
            stateTime = 0;
            runSpeed = 0;

        }
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        enemyChasesPlayer(delta);
        enemyDed(delta);
        facePlayer();
        update(delta, playerPos, null);
    }

    public Object getUserData() {
        return null;
    }
}
