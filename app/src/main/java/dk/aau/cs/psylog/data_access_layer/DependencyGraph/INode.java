package dk.aau.cs.psylog.data_access_layer.DependencyGraph;

/**
 * Created by Mathias on 19-03-2015.
 */
public interface INode {
    public ModuleEnum isValid();
    public void addParent(DependencyNode parent);
}
