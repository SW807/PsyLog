package dk.aau.cs.psylog.psylog;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import lecho.lib.hellocharts.gesture.ZoomType;
import lecho.lib.hellocharts.model.Column;
import lecho.lib.hellocharts.model.ColumnChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.SubcolumnValue;
import lecho.lib.hellocharts.view.ColumnChartView;


public class BarChart extends ColumnChartView{

    SimpleDateFormat sdf  = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
    ContentResolver contentResolver;
    String xAxis;
    String yAxis;
    String tableName;
    Context context;


    public BarChart(Context context,String xAxis, String yAxis, String tableName) {
        super(context);
        this.context = context;
        contentResolver = context.getContentResolver();
        this.xAxis = xAxis;
        this.yAxis = yAxis;
        this.tableName = tableName;
        this.setInteractive(true);
        this.setZoomType(ZoomType.HORIZONTAL);
        this.setColumnChartData(setupChart());
    }

    public ColumnChartData setupChart(){
        List<Column> columnList = getDBData();
        ColumnChartData columnChartData = new ColumnChartData(columnList);
        return columnChartData;
    }

    public List<Column> getDBData()
    {
        try {
            Random rnd = new Random();
            List<Column> columns = new ArrayList<>();
            Uri uri = Uri.parse("content://dk.aau.cs.psylog.data_access_layer/" + tableName);
            Cursor cursor = contentResolver.query(uri, new String[]{yAxis, xAxis}, null, null, null);
            if (cursor.moveToFirst()) {
                do {
                    float yValue = cursor.getFloat(cursor.getColumnIndex(yAxis));
                    String time = cursor.getString(cursor.getColumnIndex(xAxis));
                    Column column = new Column();
                    SubcolumnValue subcolumnValue = new SubcolumnValue(yValue, Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256)));
                    subcolumnValue.setLabel(time);
                    List<SubcolumnValue> subcolumnValues = new ArrayList<>();
                    subcolumnValues.add(subcolumnValue);
                    column.setValues(subcolumnValues);
                    columns.add(column);
                }
                while (cursor.moveToNext());
            }
            return columns;
        }
        catch (Exception e)
        {
            return new ArrayList<>();
        }
    }
}
