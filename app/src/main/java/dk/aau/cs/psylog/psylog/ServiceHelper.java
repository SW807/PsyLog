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

    public static void startService(String processName, Context context) {
        Intent i = new Intent();
        i.setComponent(new ComponentName(processName, processName + "Service"));
        if (!isServiceRunning(processName + "Service", context)) {
            context.startService(i);
        }
    }

    public static void stopService(String processName, Context context) {
        Intent i = new Intent();
        i.setComponent(new ComponentName(processName, processName + "Service"));
        if (isServiceRunning(processName + "Service", context)) {
            context.stopService(i);
        }
    }

    public static boolean isServiceRunning(String serviceName, Context context) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceName.equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    public static HashMap<String,String> getXMLForInstalledProcesses(Context context){
        HashMap<String,String> resultHash = new HashMap<String,String>();
        for(String s : getInstalledProcessNames(context)){
            String temp = getProcessXMLDefinition(s,context);
            if(temp != "")
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

    public static String getProcessXMLDefinition(String processName, Context context) {
        String s = "";
        try {
            Resources r = context.getPackageManager().getResourcesForApplication(processName);
            int id = r.getIdentifier(processName + ":raw/module", null, null);
            InputStream in_s = r.openRawResource(id);
            byte[] b = new byte[in_s.available()];
            in_s.read(b);
            s = new String(b);
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("getProcessXMLDefinition", e.getMessage());
        }
        catch (IOException e) {
            Log.e("getProcessXMLDefinition", e.getMessage());
        }
        catch(Resources.NotFoundException e){
            Log.e("getProcessXMLDefinition", e.getMessage());
        }

        return s;
    }
}
