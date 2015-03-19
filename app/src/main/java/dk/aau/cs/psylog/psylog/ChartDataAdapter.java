package dk.aau.cs.psylog.psylog;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;

import java.util.ArrayList;

/**
 * Created by Praetorian on 19-03-2015.
 */

public class ChartDataAdapter extends ArrayAdapter<BarData> {

    private ArrayList<BarData> throwInList(BarData data)
    {
        ArrayList<BarData> returnList = new ArrayList<BarData>();
        returnList.add(data);
        return returnList;
    }
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