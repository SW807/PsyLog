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
import java.util.concurrent.Callable;

import dk.aau.cs.psylog.PsyLogConstants;
import dk.aau.cs.psylog.data_access_layer.generated.AnalysisModule;
import dk.aau.cs.psylog.data_access_layer.generated.Module;
import dk.aau.cs.psylog.data_access_layer.generated.SensorModule;
import dk.aau.cs.psylog.data_access_layer.generated.ViewModule;

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
        i.putExtra("action", "cancel");
        if (isServiceRunning(processName, context)) {
            context.startService(i);
        }
    }

    public static HashMap<String, Boolean> servicesRunning(Context context) {
        return servicesRunningHelper(context, getInstalledProcessNames(context));
    }

    public static HashMap<String, Boolean> servicesSensorsRunning(Context context) {
        return servicesRunningHelper(context, getInstalledSensorsProcessNames(context));
    }

    public static HashMap<String, Boolean> servicesAnalysisRunning(Context context) {
        return servicesRunningHelper(context, getInstalledAnalysisProcessNames(context));
    }

    private static HashMap<String, Boolean> servicesRunningHelper(Context context, List<String> services) {
        HashMap<String, Boolean> resultHash = new HashMap<>();
        for (String s : services) {
            resultHash.put(s, isServiceRunning(s, context));
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

    public static HashMap<String, InputStream> getJSONForInstalledProcesses(Context context) {
        return getJSONForInstalledProcessesHelper(context, getInstalledProcessNames(context));
    }

    public static HashMap<String, InputStream> getJSONForSensorsInstalledProcesses(Context context) {
        return getJSONForInstalledProcessesHelper(context, getInstalledSensorsProcessNames(context));
    }

    public static HashMap<String, InputStream> getJSONForAnalysisInstalledProcesses(Context context) {
        return getJSONForInstalledProcessesHelper(context, getInstalledAnalysisProcessNames(context));
    }

    private static HashMap<String, InputStream> getJSONForInstalledProcessesHelper(Context context, List<String> services) {
        HashMap<String, InputStream> resultHash = new HashMap<String, InputStream>();
        for (String s : services) {
            InputStream temp = getProcessJSONDefinition(s, context);
            if (temp != null)
                resultHash.put(s, temp);
        }
        return resultHash;
    }

    public static List<String> getInstalledProcessNames(Context context) {
        return getInstalledProcessNamesHelper(context, "");
    }

    public static List<String> getInstalledSensorsProcessNames(Context context) {
        return getInstalledProcessNamesHelper(context, ".sensors");
    }

    public static List<String> getInstalledAnalysisProcessNames(Context context) {
        return getInstalledProcessNamesHelper(context, ".analysis");
    }

    public static List<String> getInstalledProcessNamesHelper(Context context, String startsWith) {
        List<String> packageNames = new ArrayList<String>();

        PackageManager pm = context.getPackageManager();
        List<ApplicationInfo> packages = pm.getInstalledApplications(PackageManager.GET_META_DATA);
        for (ApplicationInfo ai : packages) {
            if (ai.processName != null && ai.processName.startsWith("dk.aau.cs.psylog" + startsWith) && !ai.processName.equals("dk.aau.cs.psylog.psylog")) {
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
        } catch (Resources.NotFoundException e) {
            Log.e("getProcessJSONDef", e.getMessage());
        }

        return resultStream;
    }

    public static String getProcessName(Module module) throws IllegalArgumentException
    {
        if (module instanceof SensorModule)
            return PsyLogConstants.DOMAIN_NAME + "sensor." + module.getName();
        else if (module instanceof AnalysisModule)
            return PsyLogConstants.DOMAIN_NAME + "analysis." + module.getName();
        else if (module instanceof ViewModule)
            return PsyLogConstants.DOMAIN_NAME + "view." + module.getName();
        throw  new IllegalArgumentException("Unknown module type");
    }
}
