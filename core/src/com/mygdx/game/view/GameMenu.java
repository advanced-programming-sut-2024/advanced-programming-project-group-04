package com.mygdx.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.*;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.game.AssetLoader;
import com.mygdx.game.Main;
import com.mygdx.game.controller.GameController;
import com.mygdx.game.model.Deck;
import com.mygdx.game.model.Player;
import com.mygdx.game.model.card.AllCards;
import com.mygdx.game.model.card.Card;
import com.mygdx.game.model.faction.*;

import java.util.ArrayList;
import java.util.HashMap;

import static com.mygdx.game.view.TableSection.*;

public class GameMenu extends Menu {
    public static ImageButton selectedCard;

    Table table, upperSectionTable, middleSectionTable, lowerSectionTable;
    CustomTable weatherTable, myHandTable, myLeaderTable, enemyLeaderTable, myGraveyardTable, enemyHandTable, enemyGraveyardTable;
    CustomTable[] myRowsTables, enemyRowsTables;
    HashMap<TableSection, CustomTable> allTables;
    Deck myDeck, enemyDeck;
    AssetLoader assetLoader;

    public GameMenu(Main game) {
        super(game);

        stage.setViewport(new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
        assetLoader = game.assetLoader;

        tableInit();

        myDeck = new Deck();
        for (AllCards allCard : Nilfgaard.getCards()) {
            myDeck.addCard(new Card(allCard));
        }
        enemyDeck = new Deck();
        for (AllCards allCard : Monsters.getCards()) {
            enemyDeck.addCard(new Card(allCard));
        }

        Player matin = new Player("Matin", "cDnak@(#&>CAxm09218", "matin@giga.com", "GigaChad");
        Player arvin = new Player("Arvin", "1234", "arvin@gay.com", "Simp");
        arvin.loadDeck(enemyDeck);
        matin.loadDeck(myDeck);
        GameController.startNewGame(matin, arvin);

        loadDeck(myDeck, myHandTable);
        loadDeck(enemyDeck, enemyHandTable);

    }

    private void loadDeck(Deck deck, Table table) {
        ArrayList<Card> cards = deck.getCards();
        for (Card card : cards) {
            Texture texture = assetLoader.getImageFromAllCard(card.getAllCard());
            GraphicalCard graphicalCard = new GraphicalCard(new TextureRegionDrawable(new TextureRegion(texture)), card);

            float SCALE = 0.01f, offset = 70;
            graphicalCard.getImageCell().size(texture.getWidth() * SCALE + offset, texture.getHeight() * SCALE + offset);

            // Add hover and click effects
            graphicalCard.addListener(new ClickListener() {
                @Override
                public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                    graphicalCard.getImage().addAction(Actions.scaleTo(1.1f, 1.1f, 0.1f));
                    selectedCard = graphicalCard;
                }

                @Override
                public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                    graphicalCard.getImage().addAction(Actions.scaleTo(1f, 1f, 0.1f));
                }

                @Override
                public void clicked(InputEvent event, float x, float y) {
                    selectedCard = graphicalCard;
                }
            });

            table.add(graphicalCard);
        }
    }

    private void tableInit() {
        table = new Table();
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
                new CustomTable(MY_SIEGE, allTables),
                new CustomTable(MY_RANGE, allTables),
                new CustomTable(MY_SPELL_MELEE, allTables),
                new CustomTable(MY_SPELL_SIEGE, allTables),
                new CustomTable(MY_SPELL_RANGE, allTables)};
        enemyRowsTables = new CustomTable[]{
                new CustomTable(ENEMY_MELEE, allTables),
                new CustomTable(ENEMY_SIEGE, allTables),
                new CustomTable(ENEMY_RANGE, allTables),
                new CustomTable(ENEMY_SPELL_MELEE, allTables),
                new CustomTable(ENEMY_SPELL_SIEGE, allTables),
                new CustomTable(ENEMY_SPELL_RANGE, allTables)};

        DragAndDrop dnd = new DragAndDrop();
        addSourceToDragAndDrop(dnd, myHandTable);
        addSourceToDragAndDrop(dnd, enemyHandTable);

        for (CustomTable table : allTables.values()) {
            if (!table.getTableSection().canPlaceCard()) continue;
            dnd.addTarget(new Target(table) {
                @Override
                public boolean drag(Source source, Payload payload, float x, float y, int pointer) {
                    return GameController.canPlaceCardToPositionController(((GraphicalCard)payload.getObject()).getCard(), ((CustomTable)table).getTableSection());
                }

                @Override
                public void drop(Source source, Payload payload, float x, float y, int pointer) {
                    if (GameController.placeCardController(((GraphicalCard)payload.getObject()).getCard(), ((CustomTable)table).getTableSection())) {
                        table.add((ImageButton) payload.getObject());
                        System.out.println(((CustomTable)table).getTableSection().getTitle());
                    }
                }
            });
        }

        table.add(enemyGraveyardTable).height(100).width(100);
        table.add(enemyHandTable);
        table.row();
        table.add(enemyLeaderTable).height(200).width(100);

        upperSectionTable = new Table();
        upperSectionTable.setDebug(true);
        upperSectionTable.add(enemyRowsTables[5]).height(100).width(100);
        upperSectionTable.add(enemyRowsTables[2]).height(100).width(400);
        upperSectionTable.row();
        upperSectionTable.add(enemyRowsTables[4]).height(100).width(100);
        upperSectionTable.add(enemyRowsTables[1]).height(100).width(400);

        table.add(upperSectionTable).height(200).width(500);
        table.row();
        table.add(weatherTable).height(200).width(100);

        middleSectionTable = new Table();
        middleSectionTable.setDebug(true);
        middleSectionTable.add(enemyRowsTables[3]).height(100).width(100);
        middleSectionTable.add(enemyRowsTables[0]).height(100).width(400);
        middleSectionTable.row();
        middleSectionTable.add(myRowsTables[3]).height(100).width(100);
        middleSectionTable.add(myRowsTables[0]).height(100).width(400);

        table.add(middleSectionTable).height(200).width(500);
        table.row();
        table.add(myLeaderTable).height(200).width(100);

        lowerSectionTable = new Table();
        lowerSectionTable.setDebug(true);
        lowerSectionTable.add(myRowsTables[4]).height(100).width(100);
        lowerSectionTable.add(myRowsTables[1]).height(100).width(400);
        lowerSectionTable.row();
        lowerSectionTable.add(myRowsTables[5]).height(100).width(100);
        lowerSectionTable.add(myRowsTables[2]).height(100).width(400);

        table.add(lowerSectionTable).height(200).width(500);
        table.row();
        table.add(myGraveyardTable).height(100).width(100);
        table.add(myHandTable);

        stage.addActor(table);
    }

    private void addSourceToDragAndDrop(DragAndDrop dnd, Table table) {
        dnd.addSource(new Source(table) {
            final Payload payload = new Payload();

            @Override
            public Payload dragStart(InputEvent event, float x, float y, int pointer) {
                payload.setObject(selectedCard);
                payload.setDragActor(selectedCard);
                table.removeActor(selectedCard);

                return payload;
            }

            @Override
            public void dragStop(InputEvent event, float x, float y, int pointer, Payload payload, Target target) {
                if (target == null) {
                    table.add((ImageButton) payload.getObject());
                }
            }
        });
    }
}
