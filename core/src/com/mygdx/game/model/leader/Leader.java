package com.mygdx.game.model.leader;

import com.mygdx.game.model.GameManager;

import java.io.Serializable;

public abstract class Leader implements Serializable {
    private final String name;

    public Leader(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getAssetName() {
        return getClass().getSimpleName();
    }

    abstract public String getImageURL();

    public abstract void run(GameManager gameManager);
}
