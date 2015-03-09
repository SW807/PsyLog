package dk.aau.cs.psylog.psylog;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.app.Service;
import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;

public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_LEFT_ICON);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);;
   //     ServiceHelper.startService("dk.aau.cs.psylog.psylog_accelerometermodule", this);

        Manager manager = new Manager(this);
        manager.updateModules();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        getSupportActionBar().setHomeButtonEnabled(true);

        return true;
    }

    public void settingsClicked(MenuItem v)
    {/*
        getFragmentManager().beginTransaction().replace(android.R.id.content,
                new SettingsFragment()).commit();*/
        Intent intent = new Intent(this, SettingsFragment.class);

        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        ServiceHelper.stopService("dk.aau.cs.psylog.psylog_accelerometermodule", this);
        super.onDestroy();
    }
}
