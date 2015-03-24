package dk.aau.cs.psylog.psylog;

import android.app.ActionBar;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
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

      //  ListView listView = (ListView)findViewById(R.id.lstview);
        adapter = new CustomAdapter(this, android.R.layout.simple_list_item_1, listItems);

        //adapter = new ArrayAdapter<View>(this, android.R.layout.simple_list_item_1, listItems);
        //listView.setAdapter(adapter);

        ViewLoader viewLoader = new ViewLoader(this);
        View textView = viewLoader.getView("dk.aau.cs.psylog.view.stepcountview", "dk.aau.cs.psylog.view.stepcountview.HelloChartStepView", "HelloChartStepView");
        LinearLayout layout = (LinearLayout)findViewById(R.id.MainActivityLayout);
        View textView2 = viewLoader.getView("dk.aau.cs.psylog.view.stepcountview", "dk.aau.cs.psylog.view.stepcountview.HelloChartStepView2", "HelloChartStepView2");
        View textView3 = viewLoader.getView("dk.aau.cs.psylog.view.stepcountview", "dk.aau.cs.psylog.view.stepcountview.HelloChartStepView3", "HelloChartStepView3");
        int height = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 150, getResources().getDisplayMetrics());
        int width = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 300, getResources().getDisplayMetrics());
        layout.addView(textView, width, height);
        layout.addView(textView2, width, height);
        layout.addView(textView3, width, height);
    }
    ArrayAdapter<View> adapter;
    ListAdapter adapter2;
    ArrayList<View> listItems = new ArrayList<>();


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
