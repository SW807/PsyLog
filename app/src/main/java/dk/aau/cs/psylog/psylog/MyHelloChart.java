package dk.aau.cs.psylog.psylog;

/**
 * Created by Praetorian on 06-05-2015.
 */
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lecho.lib.hellocharts.gesture.ZoomType;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.view.LineChartView;


public class MyHelloChart extends LineChartView {

    ContentResolver contentResolver;
    String xAxis;
    String yAxis;
    String tableName;

    SimpleDateFormat sdf  = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
    public MyHelloChart(Context context, String xAxis, String yAxis, String tableName) {
        super(context);
        contentResolver = context.getContentResolver();
        this.xAxis = xAxis;
        this.yAxis = yAxis;
        this.tableName = tableName;
        this.setInteractive(true);
        this.setZoomType(ZoomType.HORIZONTAL);
        this.setLineChartData(setupChart());
    }

    public LineChartData setupChart() {
        Line line = new Line(getDBData()).setColor(Color.BLUE).setCubic(false);
        List<Line> lines = new ArrayList<Line>();
        lines.add(line);

        LineChartData data = new LineChartData();
        data.setBaseValue(Float.NEGATIVE_INFINITY);
        data.setLines(lines);
        List<AxisValue> axisValues = new ArrayList<AxisValue>();
        int i = 0;
        for(PointValue p : line.getValues()) {
            if(i % 5 == 0) {
                String label = String.valueOf(p.getLabelAsChars());
                axisValues.add(new AxisValue(i).setLabel(label));
            }

            i++;
        }
        Axis axisX = new Axis(axisValues).setHasLines(true);
        axisX.setTextSize(8);

        Axis axisY = Axis.generateAxisFromRange(0.0f, 1.0f, 0.1f).setHasLines(true);
        axisX.setName("Tid");
        axisX.setMaxLabelChars(20);
        axisY.setName("Sandsynlighed");
        data.setAxisXBottom(axisX);
        data.setAxisYLeft(axisY);
        return data;
    }

    public ArrayList<PointValue> getDBData()
    {
        try {
            Date prevDate = sdf.parse("1000-10-10 10:10:10.101");
            ArrayList<PointValue> returnList = new ArrayList<>();
            Uri uri = Uri.parse("content://dk.aau.cs.psylog.data_access_layer/" + tableName);
            Cursor cursor = contentResolver.query(uri, new String[]{yAxis, xAxis}, null, null, null);
            int i = 0;
            final int blockSize = 5;
            int index = 0;
            if (cursor.moveToFirst()) {
                do {
                    float yValue = cursor.getFloat(cursor.getColumnIndex(yAxis));
                    String time = cursor.getString(cursor.getColumnIndex(xAxis));
                    if(i % blockSize == 0) {
                        if(Math.abs(sdf.parse(time).getTime()-prevDate.getTime()) > 1000*60*5) {
                            prevDate = sdf.parse(time);
                            returnList.add(new PointValue(index++, yValue).setLabel(time));
                        }
                    }
                    i++;
                }
                while (cursor.moveToNext());

            }
            return returnList;
        }
        catch (Exception e)
        {
            return new ArrayList<>();
        }
    }
}
