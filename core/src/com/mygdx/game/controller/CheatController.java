package mygdx.game.controller;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import mygdx.game.view.GameMenu;

public class CheatController implements InputProcessor {
    private GameMenu gameMenu;

    public CheatController(GameMenu gameMenu) {
        this.gameMenu = gameMenu;
    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.TAB) {
            gameMenu.toggleCheatConsole();
            return true;
        } else if (keycode == Input.Keys.ESCAPE) {
            if (gameMenu.isCheatConsoleVisible()) {
                gameMenu.hideCheatConsole();
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }

    @Override
    public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
        return false;
    }
}
