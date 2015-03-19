package dk.aau.cs.psylog.psylog;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.github.mikephil.charting.data.BarData;

import java.util.ArrayList;

public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_LEFT_ICON);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);;
   //     ServiceHelper.startService("dk.aau.cs.psylog.psylog_accelerometermodule", this);

        Manager manager = new Manager(this);
        manager.updateModules();

        ListView listView = (ListView)findViewById(R.id.list_view);

        StepCounterBarChartView stepCounterBarChartView = new StepCounterBarChartView(this, null);
        listView.setAdapter(stepCounterBarChartView.cda);

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
        Intent intent = new Intent(this, SettingsActivity.class);

        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        ServiceHelper.stopService("dk.aau.cs.psylog.psylog_accelerometermodule", this);
        super.onDestroy();
    }
}
