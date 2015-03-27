package dk.aau.cs.psylog.psylog;

import android.content.Context;
import android.content.SharedPreferences;

public class SettingsHelper {
    public static class Modules{
        private static final String PREFS_NAME = "modulesPreferences";
        private SharedPreferences settings;

        public Modules(Context context){
            settings = context.getSharedPreferences(PREFS_NAME,context.MODE_PRIVATE);
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
}
