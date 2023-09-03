package com.br.grupolaz.neocontra.stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Array;
import com.br.grupolaz.neocontra.NeoContra;
import com.br.grupolaz.neocontra.network.client.GameClient;
import com.br.grupolaz.neocontra.network.packets.AddPlayer;
import com.br.grupolaz.neocontra.network.server.GameServer;
import com.br.grupolaz.neocontra.screens.LoadingScreen;
import com.br.grupolaz.neocontra.screens.MainMenu;
import com.br.grupolaz.neocontra.util.Constants;
import com.br.grupolaz.neocontra.util.TextureUtils;

import java.io.IOException;

public class OnlineMenuStage extends MainMenuStage {

    private Array<TextButton> buttons;
    private TextButton thirdButton;
    private TextButton fourthButton;

    private GameServer gameServer;
    private GameClient client;
    private Label log;

    public OnlineMenuStage(NeoContra game, MainMenu mainMenu, Stage oldStage) {
        super(game, mainMenu, oldStage);
    }

    @Override
    protected void createScene() {
        setUpTable();
        setUpGrupoLAZ();
        setUpTitle();
        setUpButtons();
        setUpInputListener();
    }

    @Override
    protected void setUpButtons() {
        this.buttons = new Array<>();

        primaryButton = new TextButton("Host Game", skin);
        secondaryButton = new TextButton("Join Game", skin);
        thirdButton = new TextButton("Offline", skin);
        fourthButton = new TextButton("Back", skin);

        buttons.add(primaryButton);
        buttons.add(secondaryButton);
        buttons.add(thirdButton);
        buttons.add(fourthButton);

        for(TextButton b : buttons) {
            table.add(b).padBottom(10).width(200).height(50).row();
        }
    }

    @Override
    protected void setUpInputListener() {
        primaryButton.addListener(new InputListener() {
            public boolean touchDown(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y, int pointer, int button) {

                log = new Label("Started hosting.\nWaiting for connections to happen.", new Label.LabelStyle(TextureUtils.getGameFont(), Color.WHITE));
                log.setFontScale(0.5f);
                table.add(log).top();

                try {
                    gameServer = new GameServer();
                } catch (IOException e) {
                    log.setText("Failed to start gameServer. Getting back to Main Menu");
                    game.setScreen(new MainMenu(game,OnlineMenuStage.this));
                    return false;
                }

                //Create self-client
                client = new GameClient();

                if(!client.connect()) {
                    Gdx.app.log("Server", "Failed to connect to self. Getting back to Main Menu");
                    GameServer.shutDownServer();
                    game.setScreen(new MainMenu(game,OnlineMenuStage.this));
                    return false;
                } else {
                    System.out.println("Self-connected.");
                }

                for(TextButton b : buttons) {
                    table.removeActor(b);
                }

                return true;
            }
        });

        secondaryButton.addListener(new InputListener() {
            public boolean touchDown(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y, int pointer, int button) {
                client = new GameClient();

                for(TextButton b : buttons) {
                    table.removeActor(b);
                }

                log = new Label("Searching for server.", new Label.LabelStyle(TextureUtils.getGameFont(), Color.WHITE));
                log.setFontScale(0.25f);
                table.add(log);

                if(!client.connect()) {
                    log.setText("Timed out. Getting back to Main Menu");
                    game.setScreen(new MainMenu(game,OnlineMenuStage.this));
                    return false;
                } else {
                    log.setText("Connected. Let's play!");
                    client.getClient().sendTCP(new AddPlayer());
                }

                return true;
            }
        });

        thirdButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new LoadingScreen(game, Constants.LEVEL1_MAP, false, OnlineMenuStage.this));
                return true;
            }
        });
        fourthButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new MainMenu(game,OnlineMenuStage.this));
                return true;
            }
        });
    }
}
