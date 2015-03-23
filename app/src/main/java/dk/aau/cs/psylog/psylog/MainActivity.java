package dk.aau.cs.psylog.psylog;

import android.app.ActionBar;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.mikephil.charting.data.BarData;

import java.util.ArrayList;

public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_LEFT_ICON);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Manager manager = new Manager(this);
        manager.updateModules();

        ViewLoader viewLoader = new ViewLoader(this);
        View textView = viewLoader.getView("dk.aau.cs.psylog.view.stepcountview", "dk.aau.cs.psylog.view.stepcountview.HelloChartStepView", "HelloChartStepView");
        LinearLayout layout = (LinearLayout)findViewById(R.id.linlay);
        float pixels = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 150, getResources().getDisplayMetrics());
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) layout.getLayoutParams();
        params.height = (int)pixels;
        layout.setLayoutParams(params);

        layout.addView(textView);

        View textView2 = viewLoader.getView("dk.aau.cs.psylog.view.stepcountview", "dk.aau.cs.psylog.view.stepcountview.HelloChartStepView2", "HelloChartStepView2");
        LinearLayout.LayoutParams params2 = (LinearLayout.LayoutParams) layout.getLayoutParams();
        params.height = (int)pixels;
        layout.setLayoutParams(params2);
        layout.addView(textView2);

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
        super.onDestroy();
    }
}
