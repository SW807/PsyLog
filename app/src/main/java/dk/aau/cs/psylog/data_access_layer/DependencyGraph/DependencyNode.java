package dk.aau.cs.psylog.data_access_layer.DependencyGraph;

import java.util.ArrayList;

public class DependencyNode {

    private ArrayList<INode> children = new ArrayList<>();
    private ModuleNode parent;

    public DependencyNode(ArrayList<INode> children, ModuleNode parent){
        this.parent = parent;

        for(INode iNode : children){
            this.children.add(iNode);
            iNode.addParent(this);
        }
    }

    public ModuleNode getParent(){
        return parent;
    }

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
}
