package dk.aau.cs.psylog.data_access_layer.DependencyGraph;

/**
 * INode is a interface to make similarities between ErrorNode and ModuleNode, makes DepedencyNode more beautiful.
 */
public interface INode {
    public ModuleEnum isValid();
}
