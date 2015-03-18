package dk.aau.cs.psylog.psylog;

import android.content.Context;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceCategory;
import android.preference.PreferenceScreen;
import android.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import dk.aau.cs.psylog.PsyLogConstants;
import dk.aau.cs.psylog.data_access_layer.JSONParser;
import dk.aau.cs.psylog.data_access_layer.generated.AnalysisModule;
import dk.aau.cs.psylog.data_access_layer.generated.Dependency;
import dk.aau.cs.psylog.data_access_layer.generated.Module;
import dk.aau.cs.psylog.data_access_layer.generated.SensorModule;

public class SettingsActivity extends PreferenceActivity {
    ArrayList<Module> modules;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        modules = loadModules();
        initSettings();
    }

    @Override
    protected void onPause() {
        super.onPause();
        for (Map.Entry<String, CheckBoxPreference> cbf : dicModules.entrySet()) {
            String type = "";
            for (Module m : modules) {
                if (m.getName().equals(cbf.getKey())) {
                    if (m instanceof AnalysisModule)
                        type = "analysis.";
                    else if (m instanceof SensorModule)
                        type = "sensor.";
                }
            }
            if (type.equals(""))
                continue;
            if (cbf.getValue().isChecked()) {
                ServiceHelper.startService(PsyLogConstants.DOMAIN_NAME + type + cbf.getKey(), this);
            } else {
                ServiceHelper.stopService(PsyLogConstants.DOMAIN_NAME + type + cbf.getKey(), this);
            }
        }
    }

    final HashMap<String, CheckBoxPreference> dicModules = new HashMap<>();

    private void initSettings() {

        PreferenceScreen root = getPreferenceManager().createPreferenceScreen(this);

        PreferenceCategory preferenceCategory = new PreferenceCategory(this);
        preferenceCategory.setTitle("Analyse Moduler");
        root.addPreference(preferenceCategory);

        resolveDependencies(modules);

        setModulesRunning();

        for (CheckBoxPreference value : dicModules.values()) {
            preferenceCategory.addPreference(value);
        }
        this.setPreferenceScreen(root);

    }

    /**
     * sets the status of all modules to its state (running / not running) in the dictionary
     */
    private void setModulesRunning() {
        for (Map.Entry<String, Boolean> entry : ServiceHelper.servicesRunning(this).entrySet()) {
            Preference pref = new Preference(this);
            String modName = entry.getKey().substring(entry.getKey().lastIndexOf('.') + 1);
            pref.setKey(modName);
            if (!dicModules.isEmpty())
                dicModules.get(modName).getOnPreferenceChangeListener().onPreferenceChange(pref, entry.getValue());
        }
    }

    private void resolveDependencies(ArrayList<Module> modules) {
        for (Module module : modules) {
            final List<Pair<AnalysisModule, Set<Dependency>>> dependencySet = new ArrayList<>();
            for (Module module2 : modules) {
                if (module2 instanceof AnalysisModule)
                    if (!module.getName().equals(module2.getName())) {
                        insertDependency((AnalysisModule)module2, module, dependencySet);
                    }
            }

            dicModules.get(module.getName()).setOnPreferenceChangeListener(getOnPreferenceChangeListener(dependencySet));
        }
    }

    /**
     * inserts dependency to the set of dependencies if module is dependent on it
     *
     * @param module
     * @param dependency
     * @param dependencySet
     */
    private void insertDependency(AnalysisModule module, Module dependency, List<Pair<AnalysisModule, Set<Dependency>>> dependencySet) {
        for (Set<Dependency> dpSet : module.getDependencies()) {
            for (Dependency dp : dpSet) {
                if (dp.getName().equals(dependency.getName()))
                    dependencySet.add(new Pair<>(module, dpSet));
            }
        }
    }

    /**
     * Loads installed modules and creates an enabled unchecked checkbox for each.
     *
     * @return an arraylist of all modules
     */
    private ArrayList<Module> loadModules() {
        JSONParser parser = new JSONParser(this);
        ArrayList<Module> modules = parser.parse();

        for (Module module : modules) {
            dicModules.put(module.getName(), makePreference(module.getName(), module.getName(), module.get_description(), true, false));
        }
        return modules;
    }

    private Preference.OnPreferenceChangeListener getOnPreferenceChangeListener(final List<Pair<AnalysisModule, Set<Dependency>>> dependencySet) {
        return new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                if (newValue.toString().equals("true")) {
                    for (Pair<AnalysisModule, Set<Dependency>> setDP : dependencySet) {
                        dicModules.get(setDP.first.getName()).setEnabled(true);
                        dicModules.get(setDP.first.getName()).setSummary(setDP.first.get_description());
                    }
                } else {
                    for (Pair<AnalysisModule, Set<Dependency>> setDP : dependencySet) {
                        CheckBoxPreference dependencyCheckBox = dicModules.get(setDP.first.getName());
                        boolean checked = false;
                        for (Dependency dp : setDP.second) {
                            if (dependencyCheckBox.isChecked() && !dp.getName().equals(preference.getKey())) {
                                checked = true;
                                break;
                            }
                        }


                        dependencyCheckBox.setEnabled(checked);
                        dependencyCheckBox.setChecked(checked);

                        if(checked){
                            dependencyCheckBox.setSummary(setDP.first.get_description());
                        }
                        else{
                            String summary = "Du skal aktiver ";
                            for(Dependency dp : setDP.second){
                                if (!(dependencyCheckBox.isChecked() && !dp.getName().equals(preference.getKey()))) {
                                    summary += dp.getName() + ", ";
                                }
                            }
                            summary = summary.substring(0,summary.length() -2);
                            dependencyCheckBox.setSummary(summary);
                        }

                        Preference pref = new Preference(getSettingsContext());
                        pref.setKey(setDP.first.getName());
                        dependencyCheckBox.getOnPreferenceChangeListener().onPreferenceChange(pref, checked);
                    }
                }
                return true;
            }
        };
    }

    public Context getSettingsContext() {
        return this;
    }


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
