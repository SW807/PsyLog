package dk.aau.cs.psylog.psylog;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Set;

public class SettingsHelper {
    public static class Modules{
        private static final String PREFS_NAME = "modulesPreferences";
        private SharedPreferences settings;

        public Modules(Context context){
            settings = context.getSharedPreferences(PREFS_NAME,context.MODE_PRIVATE | Context.MODE_MULTI_PROCESS);
        }

        public void setSettings(String moduleName, boolean value){
            SharedPreferences.Editor editor = settings.edit();
            editor.putBoolean(moduleName,value);
            editor.commit();
        }

        public boolean getSettings(String moduleName){
            return settings.getBoolean(moduleName,false);
        }
    }

    public static class Tasks{
        private static final String PREFS_NAME = "tasksPreferences";
        private static final String KEY = "key";
        private SharedPreferences settings;

        public Tasks(Context context){
            settings = context.getSharedPreferences(PREFS_NAME,context.MODE_PRIVATE | Context.MODE_MULTI_PROCESS);
        }

        public void setSettings(Set<String> stringSet){
            SharedPreferences.Editor editor = settings.edit();
            editor.putStringSet(KEY, stringSet);
            editor.commit();
        }

        public Set<String> getSettings(){
            return settings.getStringSet(KEY,null);
        }
    }
}
