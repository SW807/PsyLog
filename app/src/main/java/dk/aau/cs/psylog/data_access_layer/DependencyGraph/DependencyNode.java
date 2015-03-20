package dk.aau.cs.psylog.data_access_layer.DependencyGraph;


import java.util.ArrayList;

public class DependencyNode {

    private ArrayList<INode> children = new ArrayList<>();
    private ModuleNode parent;

    /**
     * Constructor for Dependency node, set up the link between ModuleNodes.
     * @param children The children of the dependency node
     * @param parent The parent of the dependency node
     */
    public DependencyNode(ArrayList<INode> children, ModuleNode parent){
        this.parent = parent;

        for(INode iNode : children){
            this.children.add(iNode);
            if(iNode instanceof ModuleNode)
                ((ModuleNode)iNode).addParent(this);
        }
    }

    /**
     * Gets the parent of a ModuleNode
     * @return the parent
     */
    public ModuleNode getParent(){
        return parent;
    }

    /**
     * Checks if a DependencyNode is valid, by checking all it's children, if one of them is valid, it is valid
     * @return State of dependencyNode
     */
    public ModuleEnum isValid(){
        if(children.isEmpty())
            return ModuleEnum.VALID;
        for(INode iNode : children){
            if(iNode instanceof ModuleNode){
                if(((ModuleNode)iNode).getChecked())
                    return ModuleEnum.VALID;
            }
        }
        return ModuleEnum.NOT_VALID;
    }

    public int getMaxLevel(){
        int level = 0;
        for(INode iNode : children)
        {
            int dependencyMaxLevel = iNode.getMaxLevel();
            if(dependencyMaxLevel > level)
                level = dependencyMaxLevel;
        }
        return level;
    }
}
