package dk.aau.cs.psylog.psylog;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;

public class MainActivity extends ActionBarActivity {
    Intent taskRunnerIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_LEFT_ICON);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
        Intent intent = new Intent(this, SettingsActivity.class);

        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void showView() {
        ViewLoader viewLoader = new ViewLoader(this);
        View textView = viewLoader.getView("dk.aau.cs.psylog.view.stepcountview", "dk.aau.cs.psylog.view.stepcountview.HelloChartStepView", "HelloChartStepView");
        LinearLayout layout = (LinearLayout)findViewById(R.id.MainActivityLayout);
        View textView2 = viewLoader.getView("dk.aau.cs.psylog.view.stepcountview", "dk.aau.cs.psylog.view.stepcountview.HelloChartStepView2", "HelloChartStepView2");
        int height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 150, getResources().getDisplayMetrics());
        int width = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 300, getResources().getDisplayMetrics());
        layout.addView(textView, width, height);
        layout.addView(textView2, width, height);
    }
}
