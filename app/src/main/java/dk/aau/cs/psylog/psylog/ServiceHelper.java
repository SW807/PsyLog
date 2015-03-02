package dk.aau.cs.psylog.psylog;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

import java.util.ArrayList;
import java.util.List;

public class ServiceHelper {

    public void startService(String processName){
        Intent i = new Intent();
        i.setComponent(new ComponentName(processName, processName + "Service"));
    }

    private boolean isMyServiceRunning(String serviceName, Context context) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceName.equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    public List<String> getAllProcessNames(Context context){
        List<String> packageNames = new ArrayList<String>();

        PackageManager pm = context.getPackageManager();
        List<ApplicationInfo> packages = pm.getInstalledApplications(PackageManager.GET_META_DATA);
        for(ApplicationInfo ai : packages) {
            if(ai.processName != null && ai.processName.startsWith("dk.aau.cs.psylog") && !ai.processName.equals("dk.aau.cs.psylog.psylog"))
            {
                packageNames.add(ai.processName);
            }
        }
        return packageNames;
    }
}
