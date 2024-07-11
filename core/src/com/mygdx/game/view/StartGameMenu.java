package mygdx.game.view;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import mygdx.game.Main;
import mygdx.game.controller.ControllerResponse;
import mygdx.game.controller.StartGameController;

public class StartGameMenu extends Menu {
    private final StartGameController controller;

    public StartGameMenu(Main game) {
        super(game);
        this.controller = new StartGameController(game);
        stage.addActor(game.assetLoader.backgroundImage);

        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        Label waitingLabel = createWaitingLabel(game, "Waiting for the other player to accept");
        Label joinTournamentWaitingLabel = createWaitingLabel(game, "Waiting to join tournament");
        Label findRandomOpponentWaitingLabel = createWaitingLabel(game, "Waiting to find random opponent");

        Label errorLabel = new Label("", game.assetLoader.labelStyle);

        TextField findPlayerField = new TextField("", game.assetLoader.textFieldStyle);
        findPlayerField.setMessageText("Username");
        findPlayerField.setAlignment(Align.center);

        TextButton findPlayerButton = new TextButton("Challenge", game.assetLoader.textButtonStyle);
        findPlayerButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ControllerResponse response = controller.findPlayerButtonClicked(findPlayerField.getText());
                if (response.isFailed()) {
                    errorLabel.setText(response.getError());
                    errorLabel.setColor(Color.RED);
                } else {
                    table.clear();
                    table.add(waitingLabel).expandX().pad(30);
                }
            }
        });

        TextButton joinTournamentButton = new TextButton("Join tournament", game.assetLoader.textButtonStyle);
        joinTournamentButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                table.clear();
                table.add(joinTournamentWaitingLabel).expandX().pad(30);
            }
        });

        TextButton findRandomOpponentButton = new TextButton("Find random opponent", game.assetLoader.textButtonStyle);
        findRandomOpponentButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                table.clear();
                table.add(findRandomOpponentWaitingLabel).expandX().pad(30);
            }
        });

        TextButton publicityButton = new TextButton("Public game", game.assetLoader.textButtonStyle);
        findRandomOpponentButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (publicityButton.getText().equals("Public game")) publicityButton.setText("Private game");
                if (publicityButton.getText().equals("Private game")) publicityButton.setText("Public game");
                // TODO This is 100 % Kos sher
            }
        });

        TextButton backButton = new TextButton("Back", game.assetLoader.textButtonStyle);
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MainMenu(game));
                // TODO make game requests cancel
            }
        });

        stage.addActor(backButton);
        backButton.setPosition(10, stage.getHeight() - backButton.getHeight() - 10);

        float pad = 20;
        float buttonWidth = 500;
        float buttonHeight = findPlayerButton.getHeight() + 20;
        table.add(errorLabel).pad(pad);
        table.row().pad(20, 0, 20, 0);
        table.add(findPlayerField).width(buttonWidth).pad(pad);
        table.row().pad(20, 0, 20, 0);
        table.add(findPlayerButton).width(buttonWidth).height(buttonHeight).pad(pad);
        table.row().pad(20, 0, 20, 0);
        table.add(joinTournamentButton).width(buttonWidth).height(buttonHeight).pad(pad);
        table.row().pad(20, 0, 20, 0);
        table.add(findRandomOpponentButton).width(buttonWidth).height(buttonHeight).pad(pad);
        table.row().pad(20, 0, 20, 0);
        table.add(publicityButton).width(buttonWidth).height(buttonHeight).pad(pad);
    }

    private Label createWaitingLabel(Main game, String message) {
        Label waitingLabel = new Label("", game.assetLoader.labelStyle);
        waitingLabel.addAction(new Action() {
            float timeElapsed = 0;
            String msg = message;

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
        return waitingLabel;
    }
}
