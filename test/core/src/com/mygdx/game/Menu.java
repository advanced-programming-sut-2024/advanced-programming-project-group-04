package com.mygdx.game;

import com.mygdx.game.Main;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;

import static com.badlogic.gdx.Gdx.*;

public abstract class Menu implements Screen {
    protected Stage stage;
    protected Main game;

    public Menu(Main game) {
        this.game = game;

        stage = new Stage();
        input.setInputProcessor(stage);
        stage.setViewport(new FitViewport(graphics.getWidth(), graphics.getHeight()));
    }

    protected void setScreen(Menu menu) {
        game.setScreen(menu);
    }

    @Override
    public void render(float v) {
        stage.act(v);
        stage.draw();
    }

    @Override
    public void resize(int i, int i1) {
        stage.getViewport().update(i, i1, true);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    public void show() {

    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
