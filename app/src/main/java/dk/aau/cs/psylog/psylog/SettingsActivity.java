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

import dk.aau.cs.psylog.generated.Dependency;
import dk.aau.cs.psylog.generated.Module;

public class SettingsActivity extends PreferenceActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initSettings();
    }

    @Override
    protected void onPause() {
        super.onPause();
        for (Map.Entry<String, CheckBoxPreference> cbf : dicModules.entrySet()) {
            if (cbf.getValue().isChecked()) {
                ServiceHelper.startService("dk.aau.cs.psylog." + cbf.getKey(), this);
            } else {
                ServiceHelper.stopService("dk.aau.cs.psylog." + cbf.getKey(), this);
            }
        }
    }

    final HashMap<String, CheckBoxPreference> dicModules = new HashMap<String, CheckBoxPreference>();

    private void initSettings() {
        PreferenceScreen root = getPreferenceManager().createPreferenceScreen(this);

        PreferenceCategory preferenceCategory = new PreferenceCategory(this);
        preferenceCategory.setTitle("Analyse Moduler");
        root.addPreference(preferenceCategory);

        JSONParser parser = new JSONParser(this);
        ArrayList<Module> modules = parser.parse();


        for (Module module : modules) {
            dicModules.put(module.getName(), makePreference(module.getName(), module.getName(), "some summa", true, false));
        }

        for (Module module : modules) {
            final List<Pair<Module, Set<Dependency>>> dependencySet = new ArrayList<>();
            for (Module module2 : modules) {
                if (!module.getName().equals(module2.getName())) {
                    Set<Dependency> res = containsString(module2.getDependencies(), module.getName());
                    if (res != null) {
                        dependencySet.add(new Pair<>(module2, res));
                    }
                }
            }

            dicModules.get(module.getName()).setOnPreferenceChangeListener(getOnPreferenceChangeListener(dependencySet));
        }
        for (Map.Entry<String, Boolean> entry : ServiceHelper.servicesRunning(this).entrySet()) {
            Preference pref = new Preference(this);
            String modName = entry.getKey().substring(entry.getKey().lastIndexOf('.') + 1);
            pref.setKey(modName);
            dicModules.get(modName).getOnPreferenceChangeListener().onPreferenceChange(pref, entry.getValue());
        }

        for (CheckBoxPreference value : dicModules.values()) {
            preferenceCategory.addPreference(value);
        }
        this.setPreferenceScreen(root);

    }

    private Preference.OnPreferenceChangeListener getOnPreferenceChangeListener(final List<Pair<Module, Set<Dependency>>> dependencySet) {
        return new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                if (newValue.toString().equals("true")) {
                    for (Pair<Module, Set<Dependency>> setDP : dependencySet) {
                        dicModules.get(setDP.first.getName()).setEnabled(true);
                    }
                } else {
                    for (Pair<Module, Set<Dependency>> setDP : dependencySet) {
                        boolean checked = false;
                        for (Dependency dp : setDP.second) {
                            if (dicModules.get(dp.getName()).isChecked() && !dp.getName().equals(preference.getKey())) {
                                checked = true;
                                break;
                            }
                        }

                        dicModules.get(setDP.first.getName()).setEnabled(checked);
                        dicModules.get(setDP.first.getName()).setChecked(checked);
                        Preference pref = new Preference(getCon());
                        pref.setKey(setDP.first.getName());
                        dicModules.get(setDP.first.getName()).getOnPreferenceChangeListener().onPreferenceChange(pref, checked);
                    }
                }
                return true;
            }
        };
    }

    public Context getCon() {
        return this;
    }

    private Set<Dependency> containsString(List<Set<Dependency>> dependencies, String name) {
        for (Set<Dependency> dpSet : dependencies) {
            for (Dependency dp : dpSet) {
                if (dp.getName().equals(name))
                    return dpSet;
            }
        }
        return null;
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
