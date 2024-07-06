package com.mygdx.game.model.leader;

import com.mygdx.game.model.GameManager;
import com.mygdx.game.model.faction.Faction;

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
        return "images/leaders/" + getAssetName() + ".jpg";
    } // TODO WTF

    public abstract void run(GameManager gameManager);
}
