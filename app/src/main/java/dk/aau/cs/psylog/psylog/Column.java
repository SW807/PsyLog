package dk.aau.cs.psylog.psylog;

public class Column {
    public final String name;
    public final DataTypes type;

    public Column(String name, DataTypes type)
    {
        this.name = name;
        this.type = type;
    }
}
