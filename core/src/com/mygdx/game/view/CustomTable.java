package mygdx.game.view;

import com.badlogic.gdx.scenes.scene2d.ui.Table;

import java.util.HashMap;

public class CustomTable extends Table {
    private final TableSection tableSection;

    public CustomTable(TableSection tableSection, HashMap<TableSection, CustomTable> allTables) {
        super();
        this.tableSection = tableSection;
        allTables.put(tableSection, this);
    }

    public CustomTable(TableSection tableSection) {
        super();
        this.tableSection = tableSection;
    }

    public TableSection getTableSection() {
        return this.tableSection;
    }
}
