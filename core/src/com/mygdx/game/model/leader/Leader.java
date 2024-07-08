package mygdx.game.model.leader;

import mygdx.game.model.GameManager;

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

    public String getImageURL() {
        return "images/factions/" + getAssetName() + ".jpg";
    }

    public abstract void run(GameManager gameManager);
}
