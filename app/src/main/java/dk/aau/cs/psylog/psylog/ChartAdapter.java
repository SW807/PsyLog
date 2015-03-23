package dk.aau.cs.psylog.psylog;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.List;

import lecho.lib.hellocharts.view.AbstractChartView;
import lecho.lib.hellocharts.view.BubbleChartView;
import lecho.lib.hellocharts.view.ColumnChartView;
import lecho.lib.hellocharts.view.LineChartView;
import lecho.lib.hellocharts.view.PieChartView;
import lecho.lib.hellocharts.view.PreviewColumnChartView;

/**
 * Created by Praetorian on 23-03-2015.
 */
public class ChartAdapter extends ArrayAdapter<ChartDescription> {

    public ChartAdapter(Context context, int resource, List<ChartDescription> objects) {
        super(context, resource, objects);
    }
    ViewHolder holder;
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        if (convertView == null) {
            convertView = View.inflate(getContext(), R.layout.list_item, null);

            holder = new ViewHolder();
            holder.chartLayout = (FrameLayout) convertView.findViewById(R.id.chart_layout);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        ChartDescription item = getItem(position);

        holder.chartLayout.setVisibility(View.VISIBLE);
        holder.chartLayout.removeAllViews();
        AbstractChartView chart;

        return convertView;
    }

    private class ViewHolder {

        TextView text1;
        TextView text2;
        FrameLayout chartLayout;
    }

    public void addView(View view)
    {
        holder.chartLayout.addView(view);
    }

}
