package dk.aau.cs.psylog.psylog;

public class Column {
    public final String name;
    public final DataType type;
    public final Boolean nullable;

    public Column(String name, DataType type, Boolean nullable)
    {
        this.name = name;
        this.type = type;
        this.nullable = nullable;
    }
}
