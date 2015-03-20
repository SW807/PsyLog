package dk.aau.cs.psylog.data_access_layer.DependencyGraph;

public class ErrorNode implements INode {

    private ModuleEnum error;

    /**
     * Constructore of ErrorNode, is used whenever a there is no matching ModuleNode to a dependency
     * @param error
     */
    public ErrorNode(ModuleEnum error){
        this.error = error;
    }

    /**
     * Checkes is ErrorNode is valid, which only returns its error, which is set in the constructor.
     * @return the error
     */
    @Override
    public ModuleEnum isValid(){
        return error;
    }
}
