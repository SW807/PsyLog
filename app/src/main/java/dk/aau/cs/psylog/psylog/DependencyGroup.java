package dk.aau.cs.psylog.psylog;

import java.util.ArrayList;
import java.util.List;

public class DependencyGroup {

    private List<Dependency> dependencies;
    public DependencyGroup(Dependency dependency)
    {
        this.dependencies = new ArrayList<>();
        this.dependencies.add(dependency);
    }

    public DependencyGroup()
    {
        this.dependencies = new ArrayList<>();
    }

    public void Add(Dependency dependency)
    {
        this.dependencies.add(dependency);
    }
}
