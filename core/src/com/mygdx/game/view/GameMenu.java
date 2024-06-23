package com.mygdx.game.view;

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
import com.mygdx.game.AssetLoader;
import com.mygdx.game.Main;


public class GameMenu extends Menu {
    public static ImageButton selectedLeader;
    public GameMenu(Main game) {
        super(game);

        Skin skin = game.assetManager.get(AssetLoader.SKIN, Skin.class);

        // Load leader images using AssetLoader
        String selectedFaction = "realms";
        AssetLoader assetLoader = game.assetLoader;
        Table myCards = new Table();
        for (String leaderPath : assetLoader.getLeaders(selectedFaction)) {
            Texture leaderTexture = assetLoader.getAssetManager().get(leaderPath, Texture.class);
            ImageButton leaderButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(leaderTexture)));
            String leaderName = leaderPath.substring(leaderPath.lastIndexOf("/") + 1, leaderPath.lastIndexOf("."));

            float buttonSize = 450;
            leaderButton.getImageCell().size(buttonSize, buttonSize);

            // Add hover and click effects
            leaderButton.addListener(new ClickListener(leaderButton) {
                @Override
                public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                    leaderButton.getImage().addAction(Actions.scaleTo(1.1f, 1.1f, 0.1f));
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

            myCards.add(leaderButton).pad(20);
        }

        Table ground = new Table();
        myCards.setFillParent(true);
        ground.setFillParent(true);

        Table table = new Table();
        table.setFillParent(true);
        table.setDebug(true);

        Label myCardsLabel = new Label("My Cards", skin);
        Label groundLabel = new Label("Ground", skin);

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
                payload.setDragActor(selectedLeader.getImage());
                myCards.removeActor(selectedLeader);

                return payload;
            }

            @Override
            public void dragStop(InputEvent event, float x, float y, int pointer, Payload payload, Target target) {
                if (target == null) {
                    myCards.add((Image) payload.getObject());
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
                ground.add((Image) payload.getObject());
            }
        });

        stage.addActor(table);
    }
}
