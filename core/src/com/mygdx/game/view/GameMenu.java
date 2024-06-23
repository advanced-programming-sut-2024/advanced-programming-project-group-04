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

    Table myCards, table, ground;
    SpriteBatch batch;
    Label myCardsLabel, groundLabel;

    public GameMenu(Main game) {
        super(game);

        stage.setViewport(new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
        Skin skin = game.assetManager.get(AssetLoader.SKIN, Skin.class);
        table = new Table();
        myCards = new Table();
        batch = new SpriteBatch();

        // Load leader images using AssetLoader
        String selectedFaction = "realms";
        AssetLoader assetLoader = game.assetLoader;
        for (String leaderPath : assetLoader.getLeaders(selectedFaction)) {
            Texture leaderTexture = assetLoader.getAssetManager().get(leaderPath, Texture.class);
            ImageButton leaderButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(leaderTexture)));
            String leaderName = leaderPath.substring(leaderPath.lastIndexOf("/") + 1, leaderPath.lastIndexOf("."));

            float SCALE = 0.5f, offset = 40;
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
//                    selectedLeader = null;
                }

                @Override
                public void clicked(InputEvent event, float x, float y) {
                    selectedLeader = leaderButton;
                }
            });

            myCards.add(leaderButton);
        }

        ground = new Table();

        table.setFillParent(true);
        table.setDebug(true);
//        myCards.setDebug(true);

        myCardsLabel = new Label("My Cards", skin);
        groundLabel = new Label("Ground", skin);

        table.add(myCardsLabel);
        table.add(groundLabel);
        table.row();
        table.add(myCards).expand().fill();
        table.add(ground).expand().fill();

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
//                    myCards.add(new Label("Yo", skin)).pad(10);
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

        stage.addActor(table);
    }

    @Override
    public void render(float deltaTime) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
//        stage.clear();
//        table.clear();
//        table.add(myCardsLabel);
//        table.add(groundLabel);
//        table.row();
//        table.add(myCards).expand().fill();
//        table.add(ground).expand().fill();
//        stage.addActor(table);
        stage.act(deltaTime);
        stage.draw();
    }
}
