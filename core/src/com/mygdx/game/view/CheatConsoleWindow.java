package mygdx.game.view;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import mygdx.game.AssetLoader;
import mygdx.game.controller.cheat.CheatProcessor;

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

