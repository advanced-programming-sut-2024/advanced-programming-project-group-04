package com.mygdx.game.view;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.FocusListener;
import com.mygdx.game.AssetLoader;
import com.mygdx.game.controller.CheatProcessor;

public class CheatConsoleWindow extends Window {
    private TextField cheatInputField;
    private BitmapFont font = AssetLoader.getFontWithCustomSize(48);
    public CheatConsoleWindow(String title, Skin skin, CheatProcessor cheatProcessor) {
        super(title, skin);
        cheatInputField = new TextField("", skin);
        cheatInputField.setName("cheatInputField");
        cheatInputField.getStyle().font = font;
        add(cheatInputField).expand().fill();
        row();
        TextButton submitButton = new TextButton("Submit", skin);
        submitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                cheatProcessor.processCheat(cheatInputField.getText());
                cheatInputField.setText("");
            }
        });
        add(submitButton).colspan(2);
        pack();
    }
}

