package dk.aau.cs.psylog.data_access_layer.DependencyGraph;

import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.widget.CheckBox;

import java.util.ArrayList;
import java.util.List;

public class ModuleNode implements INode{

    private List<DependencyNode> parents = new ArrayList<>();
    private List<DependencyNode> children = new ArrayList<>();
    private CheckBoxPreference checkBoxPreference;

    /**
     * Constructor for ModuleNode, only needs its checkBoxPreference
     * @param checkBoxPreference that are connected to this node, an
     */
    public ModuleNode(CheckBoxPreference checkBoxPreference){
        this.checkBoxPreference = checkBoxPreference;
    }

    /**
     * Get the parents of this ModuleNode, parents can only be ModuleNode
     * @return ArrayList of parents
     */
    public ArrayList<ModuleNode> getParents(){
        ArrayList<ModuleNode> resultList = new ArrayList<>();
        for(DependencyNode parent : parents){
            resultList.add(parent.getParent());
        }
        return resultList;
    }

    /**
     * Add parent to ModuleNode, should only be called though DependencyNode
     * @param parent
     */
    public void addParent(DependencyNode parent) {
        parents.add(parent);
    }
    /**
     * isValid checks if it children are valid.
     * @return Return if this ModuleNode is valid.
     */
    @Override
    public ModuleEnum isValid(){
        for(DependencyNode child : children){
            if(child.isValid().equals(ModuleEnum.NOT_VALID))
                return ModuleEnum.NOT_VALID;
        }
        return ModuleEnum.VALID;
    }

    /**
     * Add dependecy to this ModuleNode, must be INodes that are added
     * @param dependency the dependencies that this ModuleNode have.
     */
    public void addDependency(ArrayList<INode> dependency){
        children.add(new DependencyNode(dependency,this));
    }

    /**
     * Get the checked value of the checkBoxPreference
     * @return the check value
     */
    public boolean getChecked(){
        return checkBoxPreference.isChecked();
    }

    /**
     * Set the check value of the checkBoxPreference
     * @param value the boolean value of the check
     */
    public void setChecked(boolean value){
        checkBoxPreference.setChecked(value);
    }

    /**
     * Get the enabled value of the checkBoxPreference
     * @return the enabled value
     */
    public boolean getEnabled(){
        return checkBoxPreference.isEnabled();
    }

    /**
     * Set the enabled value of the checkBoxPreference
     * @param value the the boolean value of the enabled
     */
    public void setEnabled(boolean value){
        checkBoxPreference.setEnabled(value);
    }

    /**
     * Set the OnPreferenceChangeListener
     * @param onPreferenceChangeListener the OnPreferenceChangeListener
     */
    public void setOnPreferenceChangeListener(Preference.OnPreferenceChangeListener onPreferenceChangeListener){
        checkBoxPreference.setOnPreferenceChangeListener(onPreferenceChangeListener);
    }

    /**
     * Get the checkBoxPreference
     * @return the checkBoxPreference
     */
    public CheckBoxPreference getCheckBoxPrefence(){
        return checkBoxPreference;
    }

    /**
     * Perform a OnPreferenceChange call
     * @param pref The Preference of the checkBoxPreference
     * @param value The value of the performed action
     */
    public void callOnPreferenceChange(Preference pref, boolean value){
        checkBoxPreference.getOnPreferenceChangeListener().onPreferenceChange(pref,value);
    }


}
