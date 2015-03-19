package dk.aau.cs.psylog.psylog;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.XAxis.XAxisPosition;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

import dk.aau.cs.psylog.data_access_layer.SQLiteHelper;
import dk.aau.cs.psylog.psylog.R;

/**
 * Demonstrates the use of charts inside a ListView. IMPORTANT: provide a
 * specific height attribute for the chart inside your listview-item
 *
 * @author Philipp Jahoda
 */
public class ListViewBarChartActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_listview_chart);

        ListView lv = (ListView) findViewById(R.id.listView1);

        ArrayList<BarData> list = new ArrayList<BarData>();

        // 20 items
        for (int i = 0; i < 20; i++) {
            list.add(generateData(i + 1));
        }

        ChartDataAdapter cda = new ChartDataAdapter(getApplicationContext(), list);
        lv.setAdapter(cda);
    }

    private class ChartDataAdapter extends ArrayAdapter<BarData> {

        private Typeface mTf;

        public ChartDataAdapter(Context context, List<BarData> objects) {
            super(context, 0, objects);

            mTf = Typeface.DEFAULT;//createFromAsset(getAssets(), "OpenSans-Regular.ttf");
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            BarData data = getItem(position);

            ViewHolder holder = null;

            if (convertView == null) {

                holder = new ViewHolder();

                convertView = LayoutInflater.from(getContext()).inflate(
                        R.layout.list_item_barchart, null);
                holder.chart = (BarChart) convertView.findViewById(R.id.chart);

                convertView.setTag(holder);

            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            // apply styling
            data.setValueTypeface(mTf);
            holder.chart.setDescription("");
            holder.chart.setDrawGridBackground(false);
            data.setValueTextColor(Color.WHITE);

            XAxis xAxis = holder.chart.getXAxis();
            xAxis.setPosition(XAxisPosition.BOTTOM);
            xAxis.setTypeface(mTf);
            xAxis.setDrawGridLines(false);

            YAxis leftAxis = holder.chart.getAxisLeft();
            leftAxis.setTypeface(mTf);
            leftAxis.setLabelCount(5);
            leftAxis.setSpaceTop(15f);

            YAxis rightAxis = holder.chart.getAxisRight();
            rightAxis.setTypeface(mTf);
            rightAxis.setLabelCount(5);
            rightAxis.setSpaceTop(15f);

            // set data
            holder.chart.setData(data);

            // do not forget to refresh the chart
//            holder.chart.invalidate();
            holder.chart.animateY(700);

            return convertView;
        }

        private class ViewHolder {

            BarChart chart;
        }
    }

    /**
     * generates a random ChartData object with just one DataSet
     *
     * @return
     */
    private BarData generateData(int cnt) {
        if(cnt == 1)
        {
            ArrayList<String> xaxis = new ArrayList<>();
            ArrayList<BarEntry> yaxis = new ArrayList<>();

            SQLiteHelper helper = new SQLiteHelper(this);
            Cursor cursor = helper.readFromDB("dk_aau_cs_psylog_STEPCOUNTER_steps",new String[]{"stepcount", "time"}, null, null, null, null, null, null);
           int i = 0;
            if(cursor.moveToFirst()) {
                do {
                    int stepCount = cursor.getInt(cursor.getColumnIndex("stepcount"));
                    String time = cursor.getString(cursor.getColumnIndex("time"));
                    xaxis.add(time);
                    yaxis.add(new BarEntry(stepCount, i));
                    i++;
                }
                while (cursor.moveToNext());

            }
            ContentResolver cr = this.getContentResolver();
            BarDataSet d = new BarDataSet(yaxis, "stepcount dataset");
            d.setBarSpacePercent(20f);
            d.setColors(ColorTemplate.VORDIPLOM_COLORS);
            d.setBarShadowColor(Color.rgb(203, 203, 203));
            ArrayList<BarDataSet> sets = new ArrayList<BarDataSet>();
            sets.add(d);
            return new BarData(xaxis, sets);
        }
        else {
            ArrayList<BarEntry> entries = new ArrayList<BarEntry>();

            for (int i = 0; i < 12; i++) {
                entries.add(new BarEntry((int) (Math.random() * 70) + 30, i));
            }

            BarDataSet d = new BarDataSet(entries, "New DataSet " + cnt);
            d.setBarSpacePercent(20f);
            d.setColors(ColorTemplate.VORDIPLOM_COLORS);
            d.setBarShadowColor(Color.rgb(203, 203, 203));

            ArrayList<BarDataSet> sets = new ArrayList<BarDataSet>();
            sets.add(d);

            BarData cd = new BarData(getMonths(cnt), sets);
            return cd;
        }
    }

    private ArrayList<String> getMonths(int cnt) {
        ArrayList<String> m = new ArrayList<String>();
        if(cnt == 1)
        {

        }
        else {

            m.add("Jan");
            m.add("Feb");
            m.add("Mar");
            m.add("Apr");
            m.add("May");
            m.add("Jun");
            m.add("Jul");
            m.add("Aug");
            m.add("Sep");
            m.add("Okt");
            m.add("Nov");
            m.add("Dec");
        }
        return m;
    }
}