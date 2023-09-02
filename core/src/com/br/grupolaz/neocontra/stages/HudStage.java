package com.br.grupolaz.neocontra.stages;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.br.grupolaz.neocontra.actors.Player;
import com.br.grupolaz.neocontra.util.TextureUtils;

/**
 * <h2>HudStage</h2>
 * <p>a classe HudStage é responsável por
 * criar e gerenciar o cabeçalho do jogo,
 * exibindo a quantidade de vidas do jogador
 * e uma mensagem de "Game Over" quando o
 * jogador perde todas as vidas.</p>
 *
 * <h3>package</h3>
 * <p>stages</p>
 *
 * <h3>Variaveis</h3>
 * <p>-sprite: Sprite </p>
 * <p>-player: Player </p>
 * <p>-table: Table </p>
 *
 * <h3>Métodos</h3>
 * <p>+HudStage(SpriteBatch, Player)</p>
 * <p>-updateTable(int): void</p>
 * <p>+gameOver(): boolean</p>
 * <p>+getCamera(): Camera</p>
 * <p>+act(float): void</p>
 * <p>+draw(): void </P>
 */
public class HudStage extends Stage {

    private final Sprite sprite;
    private final Player player;
    private final Player player2;
    private final Table root;

    /**
     * <h2>HudStage</h2>
     * <p>HudStage tem a função de inicializar
     * os elementos necessários para a criação
     * do cabeçalho do jogo (HUD).</p>
     *
     * <h4>Funçoes especificas</h4>
     * <P>Inicializa a variável player com o valor recebido.</p>
     * <p>Cria uma nova tabela (table) e a configura para preencher todo o espaço disponível no estágio.</p>
     * <p>Adiciona a tabela ao estágio usando o método addActor(table).</p>
     * <p>Cria um objeto Sprite com a textura da medalha de vida (TextureUtils.getLifeMedal()) e define seu tamanho para 64x64 pixels.</p>
     *
     * @param player tipo Player
     */
    public HudStage(Player player, Player player2) {
        this.player = player;
        this.player2 = player2;
        root = new Table();
        root.top();
        root.setFillParent(true);
        root.pad(15f);
        addActor(root);

        sprite = new Sprite(TextureUtils.getLifeMedal());
        sprite.setSize(64f, 64f);

    }

    public void setUpSingleplayerHud(int player1LifeCount) {
        root.clear();
        Label player1Label = new Label("Player 1", new Label.LabelStyle(TextureUtils.getGameFont(), Color.WHITE));
        player1Label.setFontScale(0.5f);

        root.row();

        Label player1Lives;

        if(player1LifeCount <= 0) {
            gameOver();
            return;
        } else {
            player1Lives = new Label(Integer.toString(player1LifeCount), new Label.LabelStyle(TextureUtils.getGameFont(), Color.WHITE));
        }

        root.add(new Image(sprite)).size(sprite.getWidth(), sprite.getHeight());
        root.add(player1Lives).left().expandX();
    }


    public void setUpMultiplayerHud(int player1LifeCount, int player2LifeCount) {
        root.clear();
        Label player1Label = new Label("Player 1", new Label.LabelStyle(TextureUtils.getGameFont(), Color.WHITE));
        player1Label.setFontScale(0.5f);
        root.add();
        root.add(player1Label).padTop(10f).left().expandX();

        Label player2Label = new Label("Player 2", new Label.LabelStyle(TextureUtils.getGameFont(), Color.WHITE));
        player2Label.setFontScale(0.5f);
        root.add(player2Label).padTop(10f).right().expandX();
        root.add();

        root.row();

        Label player1Lives;
        Label player2Lives;

        if(player1LifeCount <= 0) {
            player1Lives = new Label("DEAD!", new Label.LabelStyle(TextureUtils.getGameFont(), Color.WHITE));
        } else {
            player1Lives = new Label(Integer.toString(player1LifeCount), new Label.LabelStyle(TextureUtils.getGameFont(), Color.WHITE));
        }

        if(player2LifeCount <= 0) {
            player2Lives = new Label("DEAD!", new Label.LabelStyle(TextureUtils.getGameFont(), Color.WHITE));
        } else {
            player2Lives = new Label(Integer.toString(player2LifeCount), new Label.LabelStyle(TextureUtils.getGameFont(), Color.WHITE));
        }

        if(player1LifeCount == 0 && player2LifeCount == 0) {
            gameOver();
            return;
        }

        root.add(new Image(sprite)).size(sprite.getWidth(), sprite.getHeight());
        root.add(player1Lives).left().expandX();

        root.add(player2Lives).right().expandX();
        root.add(new Image(sprite)).size(sprite.getWidth(), sprite.getHeight());
    }



    /**
     * <h2>gameOver</h2>
     * <p>O método gameOver é responsável
     * por exibir a tela de Game Over no HUD.
     * <p>Ele não recebe parâmetros e retorna
     * um valor booleano indicando se o jogo acabou.</p>
     *
     * <h4>Funções especificas</h4>
     * <P>Reposiciona a tabela na parte inferior da tela.</p>
     * <P>Define a textura do sprite como a textura do "Game Over".</p>
     * <p>Define o tamanho do sprite.</p>
     * <p>Cria uma imagem com o sprite do "Game Over" e adiciona à tabela.</p>
     *
     * @return true tipo boolean
     */
    public void gameOver() {
        root.clear();
        root.bottom().center();
        sprite.setTexture(TextureUtils.getGameOver());
        root.add(new Image(sprite)).size(512f, 256f).bottom();
    }

    /**
     * <h2>getCamera</h2>
     * <p>O método getCamera retorna
     * a câmera associada à instância da
     * classe HudStage</p>
     *
     * @return super.getCamera()
     */
    @Override
    public Camera getCamera() {
        return super.getCamera();
    }

    /**
     * <h2>act</h2>
     * <p>O método act é responsável por processar eventos,
     * atualizar as posições e as propriedades dos
     * atores e realizar outras operações necessárias
     * para manter o estado correto do estágio do HUD.</p>
     *
     * <h4>Funções especificas</h4>
     * <p>Atualiza o estágio.</p>
     * <p>Verifica se o número de células na tabela é diferente do número de vidas do jogador.</p>
     * Se forem diferentes, chama o método updateTable() para atualizar a tabela.</p>
     * <p>Verifica se o número de células na tabela é zero.</p>
     * <p>Se for zero, chama o método gameOver() para exibir a tela de "Game Over".</p>
     *
     * @param delta tipo float
     */
    public void act(float delta) {
        super.act(delta);

        if(player2 != null) {
            setUpMultiplayerHud(player.getLifeCount(), player2.getLifeCount());
        } else {
            setUpSingleplayerHud(player.getLifeCount());
        }
    }


    /**
     * <h2>draw</h2>
     * <p> o método draw é responsável por desenhar
     * os elementos do estágio do HUD na tela</p>
     */
    @Override
    public void draw() {
        super.draw();
    }
}
