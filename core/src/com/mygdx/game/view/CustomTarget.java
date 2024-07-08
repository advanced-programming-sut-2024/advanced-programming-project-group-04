package  mygdx.game.view;

import  com.badlogic.gdx.scenes.scene2d.Actor;
import  com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;

public abstract class CustomTarget extends DragAndDrop.Target {
    private final GameMenu gameMenu;

    public CustomTarget(Actor actor, GameMenu gameMenu) {
        super(actor);
        this.gameMenu = gameMenu;
    }

    public GameMenu getGameMenu() { return this.gameMenu; }
}
