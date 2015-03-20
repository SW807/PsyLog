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

    public ModuleNode(CheckBoxPreference checkBoxPreference){
        this.checkBoxPreference = checkBoxPreference;
    }

    public ArrayList<ModuleNode> getParents(){
        ArrayList<ModuleNode> resultList = new ArrayList<>();
        for(DependencyNode parent : parents){
            resultList.add(parent.getParent());
        }
        return resultList;
    }

    public ModuleEnum isValid(){
        for(DependencyNode child : children){
            if(child.isValid().equals(ModuleEnum.NOT_VALID))
                return ModuleEnum.NOT_VALID;
        }
        return ModuleEnum.VALID;
    }

    public void addDependency(ArrayList<INode> dependency){
        children.add(new DependencyNode(dependency,this));
    }

    public boolean getChecked(){
        return checkBoxPreference.isChecked();
    }

    public void setChecked(boolean value){
        checkBoxPreference.setChecked(value);
    }

    public boolean getEnabled(){
        return checkBoxPreference.isEnabled();
    }

    public void setOnPreferenceChangeListener(Preference.OnPreferenceChangeListener onPreferenceChangeListener){
        checkBoxPreference.setOnPreferenceChangeListener(onPreferenceChangeListener);
    }

    public void setEnabled(boolean value){
        checkBoxPreference.setEnabled(value);
    }

    public CheckBoxPreference getCheckBoxPrefence(){
        return checkBoxPreference;
    }

    public void callOnPreferenceChange(Preference pref, boolean value){
        checkBoxPreference.getOnPreferenceChangeListener().onPreferenceChange(pref,value);
    }

    @Override
    public void addParent(DependencyNode parent) {
        parents.add(parent);
    }
}
