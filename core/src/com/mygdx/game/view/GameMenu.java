package com.mygdx.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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

public class GameMenu extends Menu {
    public static ImageButton selectedLeader;

    Table myCards, table, ground, graveyard;

    public GameMenu(Main game) {
        super(game);

        stage.setViewport(new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
        Skin skin = game.assetManager.get(AssetLoader.SKIN, Skin.class);
        table = new Table();
        myCards = new Table();

        // Load leader images using AssetLoader
        String selectedFaction = "realms";
        AssetLoader assetLoader = game.assetLoader;
        for (String leaderPath : assetLoader.getLeaders(selectedFaction)) {
            Texture leaderTexture = assetLoader.getAssetManager().get(leaderPath, Texture.class);
            ImageButton leaderButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(leaderTexture)));

            float SCALE = 0.5f, offset = 70;
            leaderButton.getImageCell().size(leaderTexture.getWidth() * SCALE + offset, leaderTexture.getHeight() * SCALE + offset);

            // Add hover and click effects
            leaderButton.addListener(new ClickListener() {
                @Override
                public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                    leaderButton.getImage().addAction(Actions.scaleTo(1.1f, 1.1f, 0.1f));
                    selectedLeader = leaderButton;
                }

                @Override
                public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                    leaderButton.getImage().addAction(Actions.scaleTo(1f, 1f, 0.1f));
                }

                @Override
                public void clicked(InputEvent event, float x, float y) {
                    selectedLeader = leaderButton;
                }
            });

            myCards.add(leaderButton);
        }

        ground = new Table();
        graveyard = new Table();

        table.setFillParent(true);
        table.setDebug(true);

        table.add(myCards).expand().fill();
        table.add(ground).expand().fill();
        table.add(graveyard).expand().fill();

        DragAndDrop d = new DragAndDrop();
        d.addSource(new Source(myCards) {
            final Payload payload = new Payload();

            @Override
            public Payload dragStart(InputEvent event, float x, float y, int pointer) {
                payload.setObject(selectedLeader);
                payload.setDragActor(selectedLeader);
                myCards.removeActor(selectedLeader);

                return payload;
            }

            @Override
            public void dragStop(InputEvent event, float x, float y, int pointer, Payload payload, Target target) {
                System.out.println(target);
                if (target == null) {
                    myCards.add((ImageButton) payload.getObject());
                }
            }
        });
        d.addTarget(new Target(ground) {
            @Override
            public boolean drag(Source source, Payload payload, float v, float v1, int i) {
                return true;
            }

            @Override
            public void drop(Source source, Payload payload, float v, float v1, int i) {
                ground.add((ImageButton) payload.getObject());
            }
        });
        d.addTarget(new Target(graveyard) {
            @Override
            public boolean drag(Source source, Payload payload, float x, float y, int pointer) {
                return true;
            }

            @Override
            public void drop(Source source, Payload payload, float x, float y, int pointer) {
                graveyard.add((ImageButton) payload.getObject());
            }
        });

        stage.addActor(table);
    }
}
