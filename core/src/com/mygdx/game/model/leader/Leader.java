package com.mygdx.game.model.leader;

import com.mygdx.game.model.GameManager;

public abstract class Leader {
    private final String name;

    public Leader(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getImageURL() {
        String className = getClass().getSimpleName();
        return "images/factions/" + className + ".jpg";
    }

    public abstract void run(GameManager gameManager);
}
