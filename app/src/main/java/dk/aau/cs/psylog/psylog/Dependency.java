package dk.aau.cs.psylog.psylog;

public class Dependency {
    public final String name;
    public final Boolean optional;

    public Dependency(String name, Boolean optional)
    {
        this.name = name;
        this.optional = optional;
    }
}
