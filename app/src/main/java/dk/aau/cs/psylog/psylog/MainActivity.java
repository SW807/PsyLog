package dk.aau.cs.psylog.psylog;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;

import dk.aau.cs.psylog.data_access_layer.JSONParser;
import dk.aau.cs.psylog.data_access_layer.SQLiteHelper;
import dk.aau.cs.psylog.data_access_layer.generated.Module;

public class MainActivity extends ActionBarActivity {
    Intent taskRunnerIntent;
    LinearLayout layout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_LEFT_ICON);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Manager manager = new Manager(this);
        manager.updateModules();
        layout = (LinearLayout) findViewById(R.id.MainActivityLayout);
        loadAdditionalViews();

        //showView();
    }

    private void addRuler()
    {
        View ruler = new View(this); ruler.setBackgroundColor(0xFF000000);
        layout.addView(ruler,
                new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, 5));
    }
    private void addGraph(String xAxis, String yAxis, String table, String textDescription)
    {
        TextView textView = new TextView(this);
        textView.setText(textDescription);
        MyHelloChart accChart = new MyHelloChart(this,xAxis,yAxis, table);
        int height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 150, getResources().getDisplayMetrics());
        int width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 300, getResources().getDisplayMetrics());
        layout.addView(textView);
        layout.addView(accChart, getResources().getDisplayMetrics().widthPixels, height);
    }

    private void addColumnChart(String xAxis, String yAxis, String table, String textDescription){
        TextView textView = new TextView(this);
        textView.setText(textDescription);
        BarChart accChart = new BarChart(this,xAxis,yAxis, table);
        int height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 150, getResources().getDisplayMetrics());
        layout.addView(textView);
        layout.addView(accChart,getResources().getDisplayMetrics().widthPixels, height);
    }

    private void addTableForSleepAggregator()
    {
        Drawable border = getResources().getDrawable(R.drawable.cell_shape);
        TableLayout myTable = new TableLayout(this);
        TableRow myTableRowTitles = new TableRow(this);
        TextView startDateTextView = new TextView(this);
        startDateTextView.setBackground(border);

        TextView endDateTextView = new TextView(this);
        endDateTextView.setBackground(border);
        TextView probTextView = new TextView(this);
        probTextView.setBackground(border);
        startDateTextView.setText("Start Tid");
        endDateTextView.setText("Slut Tid");
        probTextView.setText("Sandsynlighed");
        myTableRowTitles.addView(startDateTextView);
        myTableRowTitles.addView(endDateTextView);
        myTableRowTitles.addView(probTextView);
        myTable.addView(myTableRowTitles);
        myTable.setStretchAllColumns(true);
        Cursor c = getContentResolver().query(Uri.parse("content://dk.aau.cs.psylog.data_access_layer/" + "SLEEPAGGREGATOR_result"), new String[]{"startdate", "enddate", "prob"}, null, null, null);

        if(c.moveToFirst()) {
            do {

                TextView startD = new TextView(this);
                startD.setText(c.getString(c.getColumnIndex("startdate")));
                startD.setBackground(border);
                TextView endD = new TextView(this);
                endD.setBackground(border);
                endD.setText(c.getString(c.getColumnIndex("enddate")));
                TextView probT = new TextView(this);
                probT.setBackground(border);
                probT.setText(c.getString(c.getColumnIndex("prob")));
                TableRow tr = new TableRow(this);
                tr.addView(startD);
                tr.addView(endD);
                tr.addView(probT);
                myTable.addView(tr);
            }while (c.moveToNext());
            myTable.setStretchAllColumns(true);
            layout.addView(myTable);
        }

    }
    private void loadAdditionalViews() {
        JSONParser parser = new JSONParser(this);
        ArrayList<Module> modules = parser.parse();
        for (Module m : modules) {
            switch (m.getName().toString()) {
                case "accelerationsleepanalysis":
                    addGraph("time", "prob", "ACCELERATIONSLEEPANALYSIS_sleepcalc","Accelerations Søvn Graf:");
                    addRuler();
                    break;
                case "sleepaggregator":
                    addTableForSleepAggregator();
                    addRuler();
                    break;
                case "sleepstationary":
                    addGraph("time", "prob", "SLEEPSTATIONARY_sleepcalc","Kombineret Søvn Graf:");
                    addRuler();
                    break;
                case "soundsleepanalysis":
                    addGraph("time", "prob", "SOUNDSLEEPANALYSIS_sleepcalc","Amplitude Søvn Graf:");
                    addRuler();
                    break;
                case "stepcountaggregator":
                    addColumnChart("date","stepcount","STEPCOUNTAGGREGATOR_result","I like turtles");
                    break;
                case "temp2":
                    break;
            }

            //   Log.e("YOYOBITCH", );
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        getSupportActionBar().setHomeButtonEnabled(true);

        return true;
    }

    public void settingsClicked(MenuItem v) {/*
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
        try {
            ViewLoader viewLoader = new ViewLoader(this);
            View textView = viewLoader.getView("dk.aau.cs.psylog.analysis.sleepstationary",
                    "dk.aau.cs.psylog.analysis.sleepstationary.SleepLineChartView",
                    "SleepLineChartView");
            LinearLayout layout = (LinearLayout) findViewById(R.id.MainActivityLayout);
            //View textView2 = viewLoader.getView("dk.aau.cs.psylog.view.stepcountview", "dk.aau.cs.psylog.view.stepcountview.HelloChartStepView2", "HelloChartStepView2");
            int height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 150, getResources().getDisplayMetrics());
            int width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 300, getResources().getDisplayMetrics());

            layout.addView(textView, width, height);
        } catch (Exception e) {
        }
        //layout.addView(textView2, width, height);
    }
}
