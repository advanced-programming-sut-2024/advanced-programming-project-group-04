package com.mygdx.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.*;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.game.AssetLoader;
import com.mygdx.game.Main;
import com.mygdx.game.controller.GameController;
import com.mygdx.game.model.Deck;
import com.mygdx.game.model.Player;
import com.mygdx.game.model.PlayerInGame;
import com.mygdx.game.model.card.AllCards;
import com.mygdx.game.model.card.Card;
import com.mygdx.game.model.faction.*;

import java.util.ArrayList;
import java.util.HashMap;

import static com.mygdx.game.view.TableSection.*;

public class GameMenu extends Menu {
    public GraphicalCard selectedCard;
    public static float SCALE = 0.15f, offset = 30;

    GameController gameController;

    Table table, upperSectionTable, middleSectionTable, lowerSectionTable;
    CustomTable weatherTable, myHandTable, myLeaderTable, enemyLeaderTable, myGraveyardTable, enemyHandTable, enemyGraveyardTable;
    CustomTable[] myRowsTables, enemyRowsTables;
    HashMap<TableSection, CustomTable> allTables;
    Deck myDeck, enemyDeck;
    AssetLoader assetLoader;
    HashMap<Card, GraphicalCard> allCardsCreated;
    Source myHandSource, enemyHandSource;
    DragAndDrop dnd;
    Skin skin;
    TextureRegionDrawable backgroundImage;
    Label myScore, enemyScore;
    ArrayList<PlayerInGame> players;

    TextButton passButtonEnemy , passButtonSelf;
    public GameMenu(Main game) {
        super(game);
        this.gameController = new GameController(this);
        gameController.setGameMenu(this);

        stage.setViewport(new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
        assetLoader = game.assetLoader;
        this.skin = game.assetLoader.skin;
        allCardsCreated = new HashMap<>();

        // set Background
        Texture boardTexture = game.assetManager.get(AssetLoader.BOARD, Texture.class);
        backgroundImage = new TextureRegionDrawable(new TextureRegion(boardTexture));

        tableInit();

        myDeck = new Deck();
        for (AllCards allCard : Skellige.getCards()) {
            for (int i = 0; i < allCard.getNumber(); i++)
                myDeck.addCard(new Card(allCard));
        }
        myDeck.addCard(new Card(AllCards.BitingFrost));
        enemyDeck = new Deck();
        for (AllCards allCard : Nilfgaard.getCards()) {
            for (int i = 0; i < allCard.getNumber(); i++)
                enemyDeck.addCard(new Card(allCard));
        }

        Player matin = new Player("Matin", "cDnak@(#&>CAxm09218", "matin@giga.com", "GigaChad");
        Player arvin = new Player("Arvin", "1234", "arvin@gay.com", "Simp");
        arvin.loadDeck(enemyDeck);
        matin.loadDeck(myDeck);
        players = gameController.startNewGame(matin, arvin);


        loadHand(players.get(0).getHand(), myHandTable);
        loadHand(players.get(1).getHand(), enemyHandTable);

    }

    private void loadDeck(Deck deck, Table table) {
        ArrayList<Card> cards = deck.getCards();
        for (Card card : cards) {
            GraphicalCard graphicalCard = createNewGraphicalCard(card);
            table.add(graphicalCard);
        }
    }

    private void loadHand(ArrayList<Card> hand, Table table) {
        for (Card card : hand) {
            GraphicalCard graphicalCard = createNewGraphicalCard(card);
            table.add(graphicalCard);
        }
    }

    public GraphicalCard createNewGraphicalCard(Card card) {
        Texture texture = assetLoader.getImageFromAllCard(card.getAllCard());
        GraphicalCard graphicalCard = new GraphicalCard(new TextureRegionDrawable(new TextureRegion(texture)), card, allCardsCreated);

        graphicalCard.getImageCell().size(texture.getWidth() * SCALE + offset, texture.getHeight() * SCALE + offset);

        // Add hover and click effects
        graphicalCard.addListener(new ClickListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                graphicalCard.getImage().addAction(Actions.scaleTo(1.1f, 1.1f, 0.1f));
                graphicalCard.getLabelInsideCircle().addAction(Actions.scaleTo(1.1f, 1.1f, 0.1f));
                selectedCard = graphicalCard;
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                graphicalCard.getImage().addAction(Actions.scaleTo(1f, 1f, 0.1f));
                graphicalCard.getLabelInsideCircle().addAction(Actions.scaleTo(1f, 1f, 0.1f));
            }

            @Override
            public void clicked(InputEvent event, float x, float y) {
                selectedCard = graphicalCard;
                Card card = graphicalCard.getCard();
                Window window = new Window(card.getName(), skin);
                Table table = new Table();
                Drawable drawable = graphicalCard.getImage().getDrawable();
                table.add(new Image(drawable));
                table.row();
                table.add(getLabelFromString("Name " + card.getName()));
                table.row();
                table.add(getLabelFromString("Description " + card.getDescription()));
                table.row();
                table.add(getLabelFromString("CurrentHP " + Integer.toString(card.getCurrentHP())));
                table.row();
                table.setFillParent(true);
                table.setX(0);
                table.setY(0);
                window.addActor(table);
                window.setFillParent(true);
                stage.addActor(window);
            }
        });

        return graphicalCard;
    }

    private void tableInit() {
        table = new Table();

        myScore = new Label("0", game.assetLoader.labelStyle);
        enemyScore = new Label("0", game.assetLoader.labelStyle);
        myScore.setPosition(200, 400);
        enemyScore.setPosition(200, 1200);
        stage.addActor(myScore);
        stage.addActor(enemyScore);


        // Pass button style
        TextButton.TextButtonStyle buttonStyle = game.assetLoader.textButtonStyle;

        // Create and position pass buttons
        passButtonEnemy = new TextButton("PASS enemy", buttonStyle);
        passButtonEnemy.setPosition(300, 1200);
        passButtonEnemy.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (!gameController.isMyTurn) {
                    players.get(1).setIsPassed(true);
                    passButtonEnemy.setText("PASSED");
                    gameController.passTurn();
                }

            }
        });

        passButtonSelf = new TextButton("PASS self", buttonStyle);
        passButtonSelf.setPosition(300, 400);
        passButtonSelf.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (gameController.isMyTurn) {
                    players.get(0).setIsPassed(true);
                    passButtonSelf.setText("PASSED");
                    gameController.passTurn();
                }
            }
        });

        // green color
        passButtonEnemy.setColor(0, 1, 0, 1f);
        passButtonSelf.setColor(0, 1, 0, 1f);

        stage.addActor(passButtonEnemy);
        stage.addActor(passButtonSelf);

        table.setFillParent(true);
        table.setDebug(true);
        allTables = new HashMap<>();
        weatherTable = new CustomTable(WEATHER, allTables);
        myHandTable = new CustomTable(MY_HAND, allTables);
        enemyHandTable = new CustomTable(ENEMY_HAND, allTables);
        myLeaderTable = new CustomTable(MY_LEADER, allTables);
        enemyLeaderTable = new CustomTable(ENEMY_LEADER, allTables);
        myGraveyardTable = new CustomTable(MY_GRAVEYARD, allTables);
        enemyGraveyardTable = new CustomTable(ENEMY_GRAVEYARD, allTables);
        myRowsTables = new CustomTable[]{
                new CustomTable(MY_MELEE, allTables),
                new CustomTable(MY_RANGE, allTables),
                new CustomTable(MY_SIEGE, allTables),
                new CustomTable(MY_SPELL_MELEE, allTables),
                new CustomTable(MY_SPELL_RANGE, allTables),
                new CustomTable(MY_SPELL_SIEGE, allTables),
        };
        enemyRowsTables = new CustomTable[]{
                new CustomTable(ENEMY_MELEE, allTables),
                new CustomTable(ENEMY_RANGE, allTables),
                new CustomTable(ENEMY_SIEGE, allTables),
                new CustomTable(ENEMY_SPELL_MELEE, allTables),
                new CustomTable(ENEMY_SPELL_RANGE, allTables),
                new CustomTable(ENEMY_SPELL_SIEGE, allTables),
        };

        dnd = new DragAndDrop();
        myHandSource = getSource(myHandTable);
        enemyHandSource = getSource(enemyHandTable);
        dnd.addSource(myHandSource);

        for (CustomTable table : allTables.values()) {
            if (!table.getTableSection().canPlaceCard()) continue;
            dnd.addTarget(new CustomTarget(table, this) {
                @Override
                public boolean drag(Source source, Payload payload, float x, float y, int pointer) {
                    return getGameMenu().getGameController().canPlaceCardToPosition(((GraphicalCard) payload.getObject()).getCard(), ((CustomTable) table).getTableSection());
                }

                @Override
                public void drop(Source source, Payload payload, float x, float y, int pointer) {
                    if (getGameMenu().getGameController().placeCard(((GraphicalCard) payload.getObject()).getCard(), ((CustomTable) table).getTableSection())) {
                        System.out.println(((CustomTable) table).getTableSection().getTitle());
                    }
                }
            });
        }

        table.add(enemyGraveyardTable).height(200).width(200);
        table.add(enemyHandTable);
        table.row();
        table.add(enemyLeaderTable).height(400).width(200);

        upperSectionTable = new Table();
        upperSectionTable.setDebug(true);
        upperSectionTable.add(enemyRowsTables[5]).height(200).width(200);
        upperSectionTable.add(enemyRowsTables[2]).height(200).width(1500);
        upperSectionTable.row();
        upperSectionTable.add(enemyRowsTables[4]).height(200).width(200);
        upperSectionTable.add(enemyRowsTables[1]).height(200).width(1500);

        table.add(upperSectionTable).height(400).width(1700);
        table.row();
        table.add(weatherTable).height(400).width(200);

        middleSectionTable = new Table();
        middleSectionTable.setDebug(true);
        middleSectionTable.add(enemyRowsTables[3]).height(200).width(200);
        middleSectionTable.add(enemyRowsTables[0]).height(200).width(1500);
        middleSectionTable.row();
        middleSectionTable.add(myRowsTables[3]).height(200).width(200);
        middleSectionTable.add(myRowsTables[0]).height(200).width(1500);

        table.add(middleSectionTable).height(400).width(1700);
        table.row();
        table.add(myLeaderTable).height(400).width(200);

        lowerSectionTable = new Table();
        lowerSectionTable.setDebug(true);
        lowerSectionTable.add(myRowsTables[4]).height(200).width(200);
        lowerSectionTable.add(myRowsTables[1]).height(200).width(1500);
        lowerSectionTable.row();
        lowerSectionTable.add(myRowsTables[5]).height(200).width(200);
        lowerSectionTable.add(myRowsTables[2]).height(200).width(1500);

        table.add(lowerSectionTable).height(400).width(1700);
        table.row();
        table.add(myGraveyardTable).height(200).width(200);
        table.add(myHandTable);

        stage.addActor(table);
    }

    private Source getSource(CustomTable table) {
        return new Source(table) {
            final Payload payload = new Payload();

            @Override
            public Payload dragStart(InputEvent event, float x, float y, int pointer) {
                payload.setObject(selectedCard);
                payload.setDragActor(selectedCard);
                getGameController().removeGraphicalCardFromTable(selectedCard, table);

                return payload;
            }

            @Override
            public void dragStop(InputEvent event, float x, float y, int pointer, Payload payload, Target target) {
                if (target == null) {
                    getGameController().addGraphicalCardToTable((GraphicalCard) payload.getObject(), table);
                }
            }
        };
    }

    public GraphicalCard showSomeCardsAndSelectOne(ArrayList<Card> cards) {
        // TODO : @Matin
        return null;
    }

    public void changeTurn(boolean isMyTurn) {
        if (isMyTurn) {
            dnd.addSource(myHandSource);
            dnd.removeSource(enemyHandSource);
        } else {
            dnd.removeSource(myHandSource);
            dnd.addSource(enemyHandSource);
        }
    }

    public Label getLabelFromString(String input) {
        Label label = new Label(input, skin);
        label.setFontScale(5);
        return label;
    }

    public HashMap<Card, GraphicalCard> getAllCardsCreated() {
        return this.allCardsCreated;
    }

    public HashMap<TableSection, CustomTable> getAllTables() {
        return this.allTables;
    }

    public GameController getGameController() {
        return this.gameController;
    }

    @Override
    public void render(float v) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            Array<Actor> actors = new Array<>(stage.getActors());
            stage.clear();
            for (Actor actor : actors) {
                if (!(actor instanceof Window)) {
                    stage.addActor(actor);
                }
            }
        }
        stage.act(v);
        stage.draw();
    }

    public void updateScores(PlayerInGame self, PlayerInGame enemy) {
        myScore.setText(self.getTotalHP());
        enemyScore.setText(enemy.getTotalHP());
    }

    public void resetPassedButtons() {
        passButtonEnemy.setText("PASS enemy");
        passButtonSelf.setText("PASS self");
    }
}
