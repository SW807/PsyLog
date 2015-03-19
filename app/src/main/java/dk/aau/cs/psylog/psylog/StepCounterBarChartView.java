package dk.aau.cs.psylog.psylog;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import dk.aau.cs.psylog.data_access_layer.SQLiteHelper;
import dk.aau.cs.psylog.data_access_layer.generated.View;

/**
 * Created by Praetorian on 19-03-2015.
 */
public class StepCounterBarChartView extends LinearLayout{
    Context context;

    ChartDataAdapter cda;
    public StepCounterBarChartView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.context = context;
        ArrayList<BarData> list = new ArrayList<BarData>();
        try {
            BarData data = getData();
            if (data.getXValCount() > 0 && data.getYValCount() > 0 && data.getXValCount() == data.getYValCount())
                list.add(data);
        }
        catch (Exception e){}
        cda = new ChartDataAdapter(this.context, list);
    }

    private BarData getData()
    {
        ArrayList<String> xaxis = new ArrayList<>();
        ArrayList<BarEntry> yaxis = new ArrayList<>();

        SQLiteHelper helper = new SQLiteHelper(context);
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
        ContentResolver cr = context.getContentResolver();
        BarDataSet d = new BarDataSet(yaxis, "stepcount dataset");
        d.setBarSpacePercent(20f);
        d.setColors(ColorTemplate.VORDIPLOM_COLORS);
        d.setBarShadowColor(Color.rgb(203, 203, 203));
        ArrayList<BarDataSet> sets = new ArrayList<BarDataSet>();
        sets.add(d);
        return new BarData(xaxis, sets);
    }

    public class ChartDataAdapter extends ArrayAdapter<BarData> {

        private Typeface mTf;
        public ChartDataAdapter(Context context, ArrayList<BarData> objects) {
            super(context, 0,  objects);
            mTf = Typeface.DEFAULT;//createFromAsset(getAssets(), "OpenSans-Regular.ttf");
        }

        @Override
        public android.view.View getView(int position, android.view.View convertView, ViewGroup parent) {
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
            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
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
}

