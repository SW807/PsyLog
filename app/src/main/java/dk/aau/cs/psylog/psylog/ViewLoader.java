package dk.aau.cs.psylog.psylog;

import android.content.Context;
import android.content.pm.PackageManager;
import android.util.Log;
import android.view.View;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import dalvik.system.PathClassLoader;

/**
 * Created by Praetorian on 20-03-2015.
 */
public class ViewLoader {

    Context context;
    public ViewLoader(Context context)
    {
        this.context = context;
    }
    public View getView(String packageName, String className, String viewName)
    {
        try {
            String apkName = context.getPackageManager().getApplicationInfo(packageName, 0).sourceDir;
            PathClassLoader myClassLoader = new PathClassLoader(apkName, ClassLoader.getSystemClassLoader());
            Class<?> handler = Class.forName(className, true, myClassLoader);
            Constructor[] m = handler.getConstructors();
            View returnView = null;
            for(int i = 0; i < m.length;i++)
            {
                if(m[i].getName().contains(viewName))
                {
                    Log.i("Blah", m[i].getName());
                    returnView = (View)m[i].newInstance(new Object[]{context});

                }
            }
            return returnView;
        }
        catch (PackageManager.NameNotFoundException e){
            return  null;
        }
        catch (ClassNotFoundException e){
            return null;
        }
        catch (InstantiationException e){
            return null;
        }
        catch (IllegalAccessException e){
            return null;
        }
        catch (InvocationTargetException e){
            return null;
        }
    }
}
