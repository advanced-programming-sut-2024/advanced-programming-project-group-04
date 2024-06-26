package com.mygdx.game.view;

import com.badlogic.gdx.scenes.scene2d.ui.Table;

import java.util.ArrayList;

public class CustomTable extends Table {
    private final String id;

    public CustomTable(String id, ArrayList<Table> allTables) {
        super();
        this.id = id;
        allTables.add(this);
    }

    public CustomTable(String id) {
        super();
        this.id = id;
    }

    public String getId() {
        return this.id;
    }
}
