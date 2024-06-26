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
import com.mygdx.game.model.card.AllCards;
import com.mygdx.game.model.faction.Faction;

import java.util.ArrayList;

public class GameMenu extends Menu {
    public static ImageButton selectedCard;

    Table table, upperSectionTable, middleSectionTable, lowerSectionTable;
    CustomTable weatherTable, myHandTable, myLeaderTable, enemyLeaderTable, myGraveyardTable;
    CustomTable[] myRowsTables, enemyRowsTables;
    ArrayList<Table> allTables;

    public GameMenu(Main game) {
        super(game);

        stage.setViewport(new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
        tableInit();

        // Load leader images using AssetLoader
        AssetLoader assetLoader = game.assetLoader;
        ArrayList<AllCards> cards = Faction.getNeutralCards();
        for (AllCards card : cards) {
            Texture texture = assetLoader.getImageFromAllCard(card);
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

            myHandTable.add(graphicalCard);
        }

    }

    private void tableInit() {
        table = new Table();
        table.setFillParent(true);
        table.setDebug(true);
        allTables = new ArrayList<>();
        weatherTable = new CustomTable("Weather", allTables);
        myHandTable = new CustomTable("My Hand");
        myLeaderTable = new CustomTable("My Leader");
        enemyLeaderTable = new CustomTable("Enemy Leader");
        myGraveyardTable = new CustomTable("My Graveyard");
        myRowsTables = new CustomTable[]{
                new CustomTable("My Melee", allTables),
                new CustomTable("My Siege", allTables),
                new CustomTable("My Range", allTables),
                new CustomTable("My Melee Special", allTables),
                new CustomTable("My Siege Special", allTables),
                new CustomTable("My Range Special", allTables)};
        enemyRowsTables = new CustomTable[]{
                new CustomTable("Enemy Melee", allTables),
                new CustomTable("Enemy Siege", allTables),
                new CustomTable("Enemy Range", allTables),
                new CustomTable("Enemy Melee Special", allTables),
                new CustomTable("Enemy Siege Special", allTables),
                new CustomTable("Enemy Range Special", allTables)};
//        myHandTable.setDebug(true);

        DragAndDrop dnd = new DragAndDrop();
        dnd.addSource(new Source(myHandTable) {
            final Payload payload = new Payload();

            @Override
            public Payload dragStart(InputEvent event, float x, float y, int pointer) {
                payload.setObject(selectedCard);
                payload.setDragActor(selectedCard);
                myHandTable.removeActor(selectedCard);

                return payload;
            }

            @Override
            public void dragStop(InputEvent event, float x, float y, int pointer, Payload payload, Target target) {
                if (target == null) {
                    myHandTable.add((ImageButton) payload.getObject());
                }
            }
        });

        for (Table table : allTables) {
            dnd.addTarget(new Target(table) {
                @Override
                public boolean drag(Source source, Payload payload, float x, float y, int pointer) {
                    return GameController.placeCardController(((GraphicalCard)payload.getObject()).getCard(), ((CustomTable)table).getId());
                }

                @Override
                public void drop(Source source, Payload payload, float x, float y, int pointer) {
                    table.add((ImageButton) payload.getObject());
                    System.out.println(((CustomTable)table).getId());
                }
            });
        }

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
}
