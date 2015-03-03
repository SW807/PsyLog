package dk.aau.cs.psylog.psylog;


import java.util.ArrayList;
import java.util.List;

public class Module {
    public final String name;
    public final String version;
    private List<Table> tables;
    private List<DependencyGroup> dependencies;

    public Module(String name, String version)
    {
        this.name = name;
        this.version = version;
        tables = new ArrayList<Table>();
        this.dependencies = new ArrayList<>();
    }

    public void SetTables(ArrayList<Table> tables)
    {
        this.tables = tables;
    }

    public void AddDependencyGroup(DependencyGroup dependencyGroup)
    {
        this.dependencies.add(dependencyGroup);
    }
}
