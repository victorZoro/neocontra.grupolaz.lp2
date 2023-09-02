package com.br.grupolaz.neocontra.util;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.br.grupolaz.neocontra.actors.Player;

/**
 * <h2>GameUtils</h2>
 * <p>A classe GameUtils contém utilitários que são usados para controlar o tempo de simulação do jogo e
 * manipular as entradas do usuário para controlar o jogador.</p>
 * <p>Os metodos presentes nessa classe são projetados para serem
 * chamados em momentos específicos no loop principal do jogo, garantindo um controle preciso e uma simulação suave.</p>
 *
 * <h3>package</h3>
 * <p>util</p>
 *
 * <h3>Métodos</h3>
 * <p>+Static fixTimeStep(World, float): void </p>
 * <p>+Static createInputHandler(Player,float): void</p>
 */
//Inspired by Martian Run
public class GameUtils {

    private static final int[] konamiCode = {Input.Keys.UP, Input.Keys.UP, Input.Keys.DOWN, Input.Keys.DOWN, Input.Keys.LEFT, Input.Keys.RIGHT, Input.Keys.LEFT, Input.Keys.RIGHT, Input.Keys.B, Input.Keys.A};
    private static final Array<Integer> lastKeys = new Array<>();

    /**
     * <h2>fixTimeStep</h2>
     * <p>Esse metodo é responsável por avançar a
     * simulação do mundo de física em um passo fixo</p>
     *
     * @param world tipo World
     */
    public static void fixTimeStep(World world) {
        //simulação é avançada em um passo de 1/60 segundos com 6 iterações de velocidade e 2 iterações de posição.
        world.step(1 / 60f, 6, 2);
    }

    /**
     * <h2>createInputHandler</h2>
     * <p>É responsavel por lidar com a manipulação
     * das entradas do usuário para controlar o jogador no jogo.</p>
     * <h3>Função</h3>
     * <p>Detecta se o ususario precionou: Espaço,Seta esquesda,
     * seta direita, R ou E </P>
     */
    public static void createInputHandler(Player player1, Player player2, String level) {

        konamiCodeListener();

        player1Inputs(player1, level);

        player2Inputs(player2, level);

    }

    private static void player1Inputs(Player player1, String level) {
        if(Gdx.input.isKeyJustPressed(Input.Keys.W)) {
            player1.jump();
        }else if(Gdx.input.isKeyPressed(Input.Keys.D)) {
            player1.walk(true);
        } else if(Gdx.input.isKeyPressed(Input.Keys.A)) {
            player1.walk(false);
        } else if(Gdx.input.isKeyPressed(Input.Keys.S)) {
            player1.changeLinearVelocity(new Vector2(0, 0));
            player1.crouch();
        }

        if(Gdx.input.isKeyJustPressed(Input.Keys.X)) {
            player1.hit();
        }

        if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            player1.shoot();
        }
    }

    private static void player2Inputs(Player player2, String level) {
        if(Gdx.input.isKeyJustPressed(Input.Keys.NUMPAD_8)) {
            player2.jump();
        }else if(Gdx.input.isKeyPressed(Input.Keys.NUMPAD_6)) {
            player2.walk(true);
        } else if(Gdx.input.isKeyPressed(Input.Keys.NUMPAD_4)) {
            player2.walk(false);
        } else if(Gdx.input.isKeyPressed(Input.Keys.NUMPAD_5)) {
            player2.changeLinearVelocity(new Vector2(0, 0));
        }

        if(Gdx.input.isKeyJustPressed(Input.Keys.C)) {
            player2.hit();
        }

        if(Gdx.input.isKeyJustPressed(Input.Keys.NUMPAD_ENTER)) {
            player2.shoot();
        }
    }

    /*
    * spawnPlayers - Método responsável por spawnar os jogadores no mapa
    * @deprecated
    *
    */
    private static void spawnPlayers(Player player1, Player player2, String level) {
        switch (level) {
            case Constants.LEVEL1_MAP:
                player1.getBody().setTransform(-10f / Constants.PIXELS_PER_METER, 100f / Constants.PIXELS_PER_METER, player1.getBody().getAngle());
                player2.getBody().setTransform(-12f / Constants.PIXELS_PER_METER, 100f / Constants.PIXELS_PER_METER, player2.getBody().getAngle());
                break;
            case Constants.LEVEL2_MAP:
                player1.getBody().setTransform(-20f / Constants.PIXELS_PER_METER, 150f / Constants.PIXELS_PER_METER, player1.getBody().getAngle());
                player2.getBody().setTransform(-22f / Constants.PIXELS_PER_METER, 150f / Constants.PIXELS_PER_METER, player2.getBody().getAngle());
                break;
            case Constants.LEVEL3_MAP:
                player1.getBody().setTransform(1f / Constants.PIXELS_PER_METER, 10f / Constants.PIXELS_PER_METER, player1.getBody().getAngle());
                player2.getBody().setTransform(-1f / Constants.PIXELS_PER_METER, 10f / Constants.PIXELS_PER_METER, player2.getBody().getAngle());
                break;
            case Constants.LEVEL4_MAP:
                player1.getBody().setTransform(-10f / Constants.PIXELS_PER_METER, 80f / Constants.PIXELS_PER_METER, player1.getBody().getAngle());
                player2.getBody().setTransform(-12f / Constants.PIXELS_PER_METER, 80f / Constants.PIXELS_PER_METER, player2.getBody().getAngle());
                break;
        }

        player1.spawn();
        player2.spawn();
    }


    private static void konamiCodeListener() {
        Gdx.input.setInputProcessor(new InputProcessor() {
            @Override
            public boolean keyDown(int keycode) {
                lastKeys.add(keycode);
                return false;
            }

            @Override
            public boolean keyUp(int keycode) {
                return false;
            }

            @Override
            public boolean keyTyped(char character) {
                return false;
            }

            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                return false;
            }

            @Override
            public boolean touchUp(int screenX, int screenY, int pointer, int button) {
                return false;
            }

            @Override
            public boolean touchDragged(int screenX, int screenY, int pointer) {
                return false;
            }

            @Override
            public boolean mouseMoved(int screenX, int screenY) {
                return false;
            }

            @Override
            public boolean scrolled(float amountX, float amountY) {
                return false;
            }
        });
    }


    public static boolean isKonamiCode() {
        if (lastKeys.size > 10) {
            lastKeys.removeIndex(0);
        }

        if (lastKeys.size == 10) {
            for (int i = 0; i < 10; i++) {
                if (lastKeys.get(i) != konamiCode[i]) {
                    return false;
                }
            }

            return true;
        }

        return false;
    }
}
