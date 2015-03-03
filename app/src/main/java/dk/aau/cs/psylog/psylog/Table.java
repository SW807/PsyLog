package dk.aau.cs.psylog.psylog;

import java.util.ArrayList;
import java.util.List;

public class Table {
    public final String name;
    public final String frequency;
    private List<Column> columns;

    public Table(String name, String frequency)
    {
        this.name = name;
        this.frequency = frequency;
        this.columns = new ArrayList<Column>();
    }

    public Table(String name)
    {
        this.name = name;
        this.frequency = null;
        this.columns = new ArrayList<Column>();
    }

    public void AddColumn(Column column)
    {
        this.columns.add(column);
    }
}
