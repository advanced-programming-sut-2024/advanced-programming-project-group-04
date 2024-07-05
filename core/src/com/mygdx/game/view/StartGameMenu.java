package com.mygdx.game.view;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.mygdx.game.Main;
import com.mygdx.game.controller.ControllerResponse;
import com.mygdx.game.controller.StartGameController;

public class StartGameMenu extends Menu {
    private final StartGameController controller;

    public StartGameMenu(Main game) {
        super(game);
        this.controller = new StartGameController(game);

        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        Label waitingLabel = new Label("", game.assetLoader.labelStyle);
        String msg = "Waiting for the other player to accept";
        waitingLabel.addAction(new Action() {
            float timeElapsed = 0;

            @Override
            public boolean act(float delta) {
                double timeNow = timeElapsed - (Math.floor(timeElapsed) - (Math.floor(timeElapsed) % 4));
                if (timeNow < 1) waitingLabel.setText(msg);
                else if (timeNow < 2) waitingLabel.setText(msg + ".");
                else if (timeNow < 3) waitingLabel.setText(msg + "..");
                else waitingLabel.setText(msg + "...");
                timeElapsed += delta;
                return false;
            }
        });

        Label errorLabel = new Label("", game.assetLoader.labelStyle);

        TextField findPlayerField = new TextField("", game.assetLoader.textFieldStyle);
        findPlayerField.setMessageText("Username");
        findPlayerField.setAlignment(Align.center);

        TextButton findPlayerButton = new TextButton("Challenge",  game.assetLoader.textButtonStyle);
        findPlayerButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ControllerResponse response = controller.findPlayerButtonClicked(findPlayerField.getText());
                if (response.isFailed()) {
                    errorLabel.setText(response.getError());
                    errorLabel.setColor(Color.RED);
                } else {
                    table.clear();
                    table.add(waitingLabel);
                }
            }
        });

        float pad = 30;
        table.add(errorLabel).pad(pad);
        table.row().pad(20, 0, 20, 0);
        table.add(findPlayerField).width(400).pad(pad);
        table.row().pad(20, 0, 20, 0);
        table.add(findPlayerButton).width(400).pad(pad);
    }
}
