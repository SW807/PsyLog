package dk.aau.cs.psylog.psylog;

import android.content.Context;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceCategory;
import android.preference.PreferenceScreen;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import dk.aau.cs.psylog.PsyLogConstants;
import dk.aau.cs.psylog.data_access_layer.DependencyGraph.ErrorNode;
import dk.aau.cs.psylog.data_access_layer.DependencyGraph.INode;
import dk.aau.cs.psylog.data_access_layer.DependencyGraph.ModuleEnum;
import dk.aau.cs.psylog.data_access_layer.DependencyGraph.ModuleNode;
import dk.aau.cs.psylog.data_access_layer.JSONParser;
import dk.aau.cs.psylog.data_access_layer.generated.AnalysisModule;
import dk.aau.cs.psylog.data_access_layer.generated.Dependency;
import dk.aau.cs.psylog.data_access_layer.generated.Module;
import dk.aau.cs.psylog.data_access_layer.generated.SensorModule;

public class SettingsActivity extends PreferenceActivity {
    private ArrayList<Module> modules;
    private HashMap<String, Module> stringModuleHashMap = new HashMap<>();
    private HashMap<Module, ModuleNode> moduleModuleNodeHashMap = new HashMap<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        modules = loadModules();

        FillHashMaps();
        ConstructTreeForModules();
        SetOnPreferenceChangeListenerForModules();
        SetModulesStatus();
        CreatePreferenceScreen();
    }

    /**
     * Starts the modules that are checked and stops the modules that are unchecked.
     */
    @Override
    protected void onPause() {
        super.onPause();
        for (Map.Entry<Module, ModuleNode> entry : moduleModuleNodeHashMap.entrySet()) {
            String type = "";
            if (entry.getKey() instanceof AnalysisModule)
                type = "analysis.";
            else if (entry.getKey() instanceof SensorModule)
                type = "sensor.";
            if (entry.getValue().getChecked())
                ServiceHelper.startService(PsyLogConstants.DOMAIN_NAME + type + entry.getKey().getName(), this);
            else
                ServiceHelper.stopService(PsyLogConstants.DOMAIN_NAME + type + entry.getKey().getName(), this);
        }
    }

    /**
     * Fills stringModuleHashMap and moduleModuleNodeHashMap such it can be used in the constructTreeForModule
     */
    private void FillHashMaps() {
        for (Module m : modules) {
            stringModuleHashMap.put(m.getName(), m);
            moduleModuleNodeHashMap.put(m, new ModuleNode(makePreference(m.getName(), m.getName(), m.get_description(), false, false)));
        }
    }

    /**
     * Construct the tree for the modules, such that each ModuleNode are connected to its dependency.
     */
    private void ConstructTreeForModules() {
        for (Module m : modules) {
            if (m instanceof AnalysisModule) {
                for (Set<Dependency> dependencies : ((AnalysisModule) m).getDependencies()) {
                    ArrayList<INode> nodesToBeAdded = new ArrayList<>();
                    for (Dependency dependency : dependencies) {
                        if (stringModuleHashMap.containsKey(dependency.getName())) {
                            Module moduleToAdd = stringModuleHashMap.get(dependency.getName());
                            if (moduleToAdd.get_version().equals(dependency.getVersion()))
                                nodesToBeAdded.add(moduleModuleNodeHashMap.get(moduleToAdd));
                            else
                                nodesToBeAdded.add(new ErrorNode(ModuleEnum.WRONG_VERSION));
                        } else
                            nodesToBeAdded.add(new ErrorNode(ModuleEnum.NOT_INSTALLED));
                    }
                    moduleModuleNodeHashMap.get(m).addDependency(nodesToBeAdded);
                }
            }
        }
    }

    /**
     * Sets the OnPreferenceChangeListener for modules
     */
    private void SetOnPreferenceChangeListenerForModules() {
        for (final Map.Entry<Module, ModuleNode> entry : moduleModuleNodeHashMap.entrySet()) {
            entry.getValue().setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object newValue) {
                    entry.getValue().setChecked((boolean) newValue);
                    for (INode iNode : entry.getValue().getParents()) {
                        if (iNode instanceof ModuleNode) {
                            ModuleNode moduleNode = (ModuleNode) iNode;
                            moduleNode.setEnabled(moduleNode.isValid().equals(ModuleEnum.VALID));
                            Preference pref = new Preference(getSettingsContext());
                            pref.setKey(entry.getKey().getName());
                            moduleNode.callOnPreferenceChange(pref, false);
                        }
                    }
                    return false;
                }
            });
        }
    }

    /**
     * Sets the status of every Module, first checks if it is running, and afterwards disable the modules that are not valid.
     */
    private void SetModulesStatus() {
        for (Map.Entry<String, Boolean> entry : ServiceHelper.servicesRunning(this).entrySet()) {
            Preference pref = new Preference(this);
            String modName = entry.getKey().substring(entry.getKey().lastIndexOf('.') + 1);
            pref.setKey(modName);

            moduleModuleNodeHashMap.get(stringModuleHashMap.get(modName)).setChecked(entry.getValue());
        }

        for (ModuleNode m : moduleModuleNodeHashMap.values()) {
            boolean enabled = m.isValid().equals(ModuleEnum.VALID);
            m.setEnabled(enabled);
            if (!enabled)
                m.setChecked(false);
        }
    }

    /**
     * Creates the preference screen, depending on what the preferenceCheckBox is in the modulenodes.
     */
    private void CreatePreferenceScreen() {
        PreferenceScreen root = getPreferenceManager().createPreferenceScreen(this);

        PreferenceCategory preferenceCategory = new PreferenceCategory(this);
        preferenceCategory.setTitle("Analyse Moduler");
        root.addPreference(preferenceCategory);
        preferenceCategory.setOrderingAsAdded(true);

        HashMap <Integer, ArrayList<ModuleNode>> sortedModuleNodes = new HashMap<>();
        int maxLevel = 0;
        for(ModuleNode value : moduleModuleNodeHashMap.values()){
            int curMaxLevel = value.getMaxLevel();
            if(!sortedModuleNodes.containsKey(curMaxLevel))
                sortedModuleNodes.put(curMaxLevel,new ArrayList<ModuleNode>());
            sortedModuleNodes.get(curMaxLevel).add(value);
        }

        for(int i = 1; i <= sortedModuleNodes.values().size(); i++){
            for(ModuleNode moduleNode : sortedModuleNodes.get(i)){
                preferenceCategory.addPreference(moduleNode.getCheckBoxPrefence());
            }
        }
        this.setPreferenceScreen(root);
    }

    /**
     * Loads installed modules.
     *
     * @return an arraylist of all modules
     */
    private ArrayList<Module> loadModules() {
        JSONParser parser = new JSONParser(this);
        ArrayList<Module> modules = parser.parse();
        return modules;
    }

    /**
     * Method to get the context within a scope that are not able to get the context
     *
     * @return Context
     */
    public Context getSettingsContext() {
        return this;
    }

    /**
     * Method to construct a checkboxpreference.
     *
     * @param key     to identify the checkboxpreference
     * @param title   the title of the checkboxpreference
     * @param summary the summary of the checkboxpreference
     * @param enabled the enabled status of the cehckboxpreference
     * @param checked the checked status of the checkboxpreference
     * @return the checkboxpreference constructed from the parameters
     */
    private CheckBoxPreference makePreference(String key, String title, String summary, boolean enabled, boolean checked) {
        CheckBoxPreference checkBoxPreference = new CheckBoxPreference(this);
        checkBoxPreference.setKey(key);
        checkBoxPreference.setTitle(title);
        checkBoxPreference.setSummary(summary);
        checkBoxPreference.setEnabled(enabled);
        checkBoxPreference.setChecked(checked);
        return checkBoxPreference;
    }
}
