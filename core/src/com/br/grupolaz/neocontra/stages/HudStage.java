package com.br.grupolaz.neocontra.stages;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.br.grupolaz.neocontra.actors.Player;
import com.br.grupolaz.neocontra.util.TextureUtils;
/**
 * <h2>HudStage</h2>
 * <p>a classe HudStage é responsável por 
 * criar e gerenciar o cabeçalho do jogo, 
 * exibindo a quantidade de vidas do jogador
 *  e uma mensagem de "Game Over" quando o 
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
    private final Table table;

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
     * @param player tipo Player
     */
    public HudStage(Player player) {
        this.player = player;
        table = new Table();
        table.left().top();
        table.setFillParent(true);
        addActor(table);

        sprite = new Sprite(TextureUtils.getLifeMedal());
        sprite.setSize(64f, 64f);
    }

    /**
     * <h2>updateTable</h2>
     * <P>O método updateTable(int lifeCount) é 
     * responsável por atualizar a tabela do HUD 
     * com base na contagem de vidas do jogador. </P>
     * <h4>Funções especificas</h4>
     * <P>Limpa a tabela para remover elementos antigos.</p>
     * <p>Altera sobre o número de vidas do jogador.</p>
     * <p>Para cada vida, cria uma imagem usando o sprite da medalha de vida e adiciona à tabela.</p>
     * @param lifeCount tipo int
     */
    private void updateTable(int lifeCount) {
        table.clear();

        for (int i = 0; i < lifeCount; i++) {
            Image spriteImage = new Image(sprite);
            table.add(spriteImage).size(sprite.getWidth(), sprite.getHeight());
        }
    }

    /**
     * <h2>gameOver</h2>
     * <p>O método gameOver é responsável
     * por exibir a tela de Game Over no HUD.
     * <p>Ele não recebe parâmetros e retorna
     *  um valor booleano indicando se o jogo acabou.</p>
     *
     * <h4>Funções especificas</h4>
     * <P>Reposiciona a tabela na parte inferior da tela.</p>
     * <P>Define a textura do sprite como a textura do "Game Over".</p>
     * <p>Define o tamanho do sprite.</p>
     * <p>Cria uma imagem com o sprite do "Game Over" e adiciona à tabela.</p>
     * @return true tipo boolean
     */
    public boolean gameOver() {
            table.bottom();
            sprite.setTexture(TextureUtils.getGameOver());
            sprite.setSize(256f, 128f);
            
            Image spriteImage = new Image(sprite);
            table.add(spriteImage).size(sprite.getWidth(), sprite.getHeight());
            return true;
    }

   
    /**
     * <h2>getCamera</h2>
     * <p>O método getCamera retorna
     *  a câmera associada à instância da 
     * classe HudStage</p>
     * @return super.getCamera()
     */
    @Override
    public Camera getCamera() {
        return super.getCamera();
    }

    /**
     * <h2>act</h2>
     * <p>O método act é responsável por processar eventos,
     *  atualizar as posições e as propriedades dos 
     * atores e realizar outras operações necessárias 
     * para manter o estado correto do estágio do HUD.</p>
     * 
     * <h4>Funções especificas</h4>
     * <p>Atualiza o estágio.</p>
     * <p>Verifica se o número de células na tabela é diferente do número de vidas do jogador.</p>
     * Se forem diferentes, chama o método updateTable() para atualizar a tabela.</p>
     * <p>Verifica se o número de células na tabela é zero.</p>
     * <p>Se for zero, chama o método gameOver() para exibir a tela de "Game Over".</p>
     * @param delta tipo float
     */
    public void act(float delta) {
        super.act(delta);
        if(table.getCells().size != player.getLifeCount()) {
            updateTable(player.getLifeCount());
        }

        if(table.getCells().size == 0) {
            gameOver();
        }
    }

    
    /**
     * <h2>draw</h2>
     * <p> o método draw é responsável por desenhar
     *  os elementos do estágio do HUD na tela</p>
     */
    @Override
    public void draw() {
        super.draw();
    }
}
