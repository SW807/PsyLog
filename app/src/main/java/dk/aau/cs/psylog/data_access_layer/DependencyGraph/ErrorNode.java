package dk.aau.cs.psylog.data_access_layer.DependencyGraph;

public class ErrorNode implements INode {

    ModuleEnum error;
    DependencyNode parent;

    public ErrorNode(ModuleEnum error){
        this.error = error;
    }

    public ModuleEnum isValid(){
        return error;
    }

    @Override
    public void addParent(DependencyNode parent) {
        this.parent = parent;
    }
}
