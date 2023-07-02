package com.br.grupolaz.neocontra.util;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
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
    /**
     * <h2>fixTimeStep</h2>
     * <p>Esse metodo é responsável por avançar a 
     * simulação do mundo de física em um passo fixo</p>
     * @param world tipo World
     * @param delta tipo float
     */
    public static void fixTimeStep(World world, float delta) {
        //simulação é avançada em um passo de 1/60 segundos com 6 iterações de velocidade e 2 iterações de posição.
        world.step(1/60f, 6, 2);
    }

    /**
     * <h2>createInputHandler</h2>
     * <p>É responsavel por lidar com a manipulação 
     * das entradas do usuário para controlar o jogador no jogo.</p>
     * <h3>Função</h3>
     * <p>Detecta se o ususario precionou: Espaço,Seta esquesda,
     *  seta direita, R ou E </P>
     * @param player tipo Player
     * @param delta tipo float
     */
    public static void createInputHandler(Player player, float delta) {
        if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            player.jump();
        }
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            player.walk(true);
        } else if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            player.walk(false);
        } else if(Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            player.changeLinearVelocity(new Vector2(0, 0));
        }

        
        if(Gdx.input.isKeyJustPressed(Input.Keys.R)) {
            player.getBody().setTransform(Constants.PLAYER_X, Constants.PLAYER_Y, player.getBody().getAngle());
            player.spawn();
        }

        if(Gdx.input.isKeyJustPressed(Input.Keys.E)) {
            player.shoot();
        }
    }
}
