package com.br.grupolaz.neocontra.stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Array;
import com.br.grupolaz.neocontra.NeoContra;
import com.br.grupolaz.neocontra.screens.LoadingScreen;
import com.br.grupolaz.neocontra.screens.MainMenu;
import com.br.grupolaz.neocontra.util.Constants;
import com.br.grupolaz.neocontra.util.SoundsUtils;
import com.br.grupolaz.neocontra.util.TextureUtils;

public class MainMenuStage extends Stage {

    protected final NeoContra game;
    protected final MainMenu mainMenu;
    protected final Table table;
    protected final Skin skin;
    private final Label teamLabel;
    private final Sprite titleSprite;
    protected TextButton primaryButton;
    protected TextButton secondaryButton;


    public MainMenuStage(NeoContra game, MainMenu mainMenu) {
        this.game = game;
        this.mainMenu = mainMenu;
        this.table = new Table();
        this.teamLabel = new Label("Grupo LAZ Presents", new Label.LabelStyle(TextureUtils.getGameFont(), Color.WHITE));
        this.titleSprite = new Sprite(TextureUtils.getGameTitleLogo());
        this.skin = new Skin(Gdx.files.internal("uiSkin.json"));

        createScene();

        game.alignCameraToWorldCenter();


        Gdx.input.setInputProcessor(this);
    }

    public MainMenuStage(NeoContra game, MainMenu mainMenu, Stage oldStage) {
        this.game = game;
        this.mainMenu = mainMenu;
        this.table = new Table();
        this.teamLabel = new Label("Grupo LAZ Presents", new Label.LabelStyle(TextureUtils.getGameFont(), Color.WHITE));
        this.titleSprite = new Sprite(TextureUtils.getGameTitleLogo());
        this.skin = new Skin(Gdx.files.internal("uiSkin.json"));
        oldStage.dispose();

        game.alignCameraToWorldCenter();

        createScene();

        Gdx.input.setInputProcessor(this);
    }

    protected void createScene() {
        setUpTable();
        setUpGrupoLAZ();
        setUpTitle();
        setUpButtons();
        setUpCopyrightText();
        setUpInputListener();
        setUpMusic();
    }

    protected void setUpTable() {
        this.table.setFillParent(true);
        this.table.center().pad(50f);
        addActor(table);
    }

    protected void setUpGrupoLAZ() {
        teamLabel.setFontScale(0.25f);
        table.add(teamLabel).minHeight(25f);
        table.row();
    }

    protected void setUpTitle() {
        this.titleSprite.setSize(512f, 256f);
        Image titleImage = new Image(titleSprite);
        table.add(titleImage).size(titleSprite.getWidth(), titleSprite.getHeight());
        table.row();
    }

    protected void setUpButtons() {
        primaryButton = new TextButton("1 Player", skin);
        secondaryButton = new TextButton("2 Players", skin);

        table.add(primaryButton).width(10).minHeight(70f);
        table.row();
        table.add(secondaryButton).width(10).minHeight(70f);
        table.row();
    }

    private void setUpCopyrightText() {
        Array<Label> labels = new Array<>();
        labels.add(new Label("TM AND COPYRIGHTED 2023", new Label.LabelStyle(TextureUtils.getGameFont(), Color.WHITE)));
        labels.add(new Label("GRUPO LAZ - INFO3", new Label.LabelStyle(TextureUtils.getGameFont(), Color.WHITE)));
        labels.add(new Label("LICENSED BY ALISSON R. S.", new Label.LabelStyle(TextureUtils.getGameFont(), Color.WHITE)));
        labels.add(new Label("Meant for studying. Non profitable.", new Label.LabelStyle(TextureUtils.getGameFont(), Color.WHITE)));

        for(Label label : labels) {
            label.setFontScale(0.35f);
            table.add(label).minHeight(25f);
            table.row();
        }
    }

    protected void setUpInputListener() {
        primaryButton.addListener(new InputListener() {
            public boolean touchDown(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new LoadingScreen(game, Constants.LEVEL1_MAP, true, MainMenuStage.this));
                return true;
            }
        });

        secondaryButton.addListener(new InputListener() {
            public boolean touchDown(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y, int pointer, int button) {
                mainMenu.setStage(new OnlineMenuStage(game, mainMenu, MainMenuStage.this));
                return true;
            }
        });
    }

    private void setUpMusic() {
        SoundsUtils.getIntroSong().play();
    }

    @Override
    public void act() {
        super.act();
    }

    @Override
    public void draw() {
        super.draw();
    }

    @Override
    public void dispose() {
        super.dispose();
        skin.dispose();
        SoundsUtils.getIntroSong().dispose();
    }
}
