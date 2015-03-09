package dk.aau.cs.psylog.psylog;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceCategory;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.PreferenceScreen;
import android.preference.RingtonePreference;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;


import java.net.DatagramPacket;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.prefs.PreferenceChangeListener;

import dk.aau.cs.psylog.generated.Dependency;
import dk.aau.cs.psylog.generated.Module;

/**
 * A {@link PreferenceActivity} that presents a set of application settings. On
 * handset devices, settings are presented as a single list. On tablets,
 * settings are split by category, with category headers shown to the left of
 * the list of settings.
 * <p/>
 * See <a href="http://developer.android.com/design/patterns/settings.html">
 * Android Design: Settings</a> for design guidelines and the <a
 * href="http://developer.android.com/guide/topics/ui/settings.html">Settings
 * API Guide</a> for more information on developing a Settings UI.
 */
public class SettingsFragment extends PreferenceActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initSettings();
    }

    private void initSettings() {
        PreferenceScreen root = getPreferenceManager().createPreferenceScreen(this);

        PreferenceCategory preferenceCategory = new PreferenceCategory(this);
        preferenceCategory.setTitle("Analyse Moduler");
        root.addPreference(preferenceCategory);

        JSONParser parser = new JSONParser(this);
        ArrayList<Module> modules = parser.parse();

        final HashMap<String, CheckBoxPreference> dicModules = new HashMap<String, CheckBoxPreference>();

        for (Module module : modules) {
            dicModules.put(module.getName(), makePreference(module.getName(), module.getName(), "some summa", true, false));
        }

        for (Module module : modules) {
            ArrayList<Module> dependencies = new ArrayList<Module>();
            final List<Pair<Module, Set<Dependency>>> dependencySet = new ArrayList<Pair<Module, Set<Dependency>>>();
            for (Module module2 : modules) {
                if (!module.getName().equals(module2.getName())) {
                    Set<Dependency> res = containsString(module2.getDependencies(), module.getName());
                    if (res != null) {
                        dependencySet.add(new Pair<Module, Set<Dependency>>(module2, res));
                    }
                }
            }
            Preference.OnPreferenceChangeListener listener = new Preference.OnPreferenceChangeListener() {
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
            dicModules.get(module.getName()).setOnPreferenceChangeListener(listener);

        }

        for (CheckBoxPreference value : dicModules.values()) {
            preferenceCategory.addPreference(value);
        }
        this.setPreferenceScreen(root);

    }

    public Context getCon()
    {
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
