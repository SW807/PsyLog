package dk.aau.cs.psylog.psylog;


import java.util.ArrayList;
import java.util.List;

public class Module {
    public final String name;
    public final String version;
    private List<Table> tables;

    public Module(String name, String version)
    {
        this.name = name;
        this.version = version;
        tables = new ArrayList<Table>();
    }

    public void AddTable(Table table)
    {
        this.tables.add(table);
    }
}
