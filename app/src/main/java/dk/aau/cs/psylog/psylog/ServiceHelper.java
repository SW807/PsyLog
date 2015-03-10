package dk.aau.cs.psylog.psylog;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.graphics.drawable.Drawable;
import android.util.Log;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;

public class ServiceHelper {

    private static String serviceName = ".PsyLogService";

    public static void startService(String processName, Context context) {
        Intent i = new Intent();
        i.setComponent(new ComponentName(processName, processName + serviceName));
        if (!isServiceRunning(processName, context)) {
            context.startService(i);
        }
    }

    public static void stopService(String processName, Context context) {
        Intent i = new Intent();
        i.setComponent(new ComponentName(processName, processName + serviceName));
        if (isServiceRunning(processName, context)) {
            context.stopService(i);
        }
    }

    public static HashMap<String,Boolean> servicesRunning(Context context){
        HashMap<String, Boolean> resultHash = new HashMap<String,Boolean>();
        for(String s : getInstalledProcessNames(context)){
            resultHash.put(s,isServiceRunning(s,context));
        }

        return resultHash;
    }

    public static boolean isServiceRunning(String processName, Context context) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if ((processName + serviceName).equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    public static HashMap<String,InputStream> getJSONForInstalledProcesses(Context context){
        HashMap<String,InputStream> resultHash = new HashMap<String,InputStream>();
        for(String s : getInstalledProcessNames(context)){
            InputStream temp = getProcessJSONDefinition(s,context);
            if(temp != null)
                resultHash.put(s,temp);
        }

        return resultHash;
    }

    public static List<String> getInstalledProcessNames(Context context) {
        List<String> packageNames = new ArrayList<String>();

        PackageManager pm = context.getPackageManager();
        List<ApplicationInfo> packages = pm.getInstalledApplications(PackageManager.GET_META_DATA);
        for (ApplicationInfo ai : packages) {
            if (ai.processName != null && ai.processName.startsWith("dk.aau.cs.psylog") && !ai.processName.equals("dk.aau.cs.psylog.psylog")) {
                packageNames.add(ai.processName);
            }
        }
        return packageNames;
    }

    public static InputStream getProcessJSONDefinition(String processName, Context context) {
        InputStream resultStream = null;
        try {
            Resources r = context.getPackageManager().getResourcesForApplication(processName);
            int id = r.getIdentifier(processName + ":raw/module", null, null);
            resultStream = r.openRawResource(id);
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("getProcessJSONDef", e.getMessage());
        }
        catch(Resources.NotFoundException e){
            Log.e("getProcessJSONDef", e.getMessage());
        }

        return resultStream;
    }
}
