package dk.aau.cs.psylog.psylog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Praetorian on 23-03-2015.
 */
public class CustomAdapter extends ArrayAdapter {
    private Context context;
    public CustomAdapter(Context context, int resource) {
        super(context, resource);
    }

    public CustomAdapter(Context context, int resource, int textViewResourceId) {
        super(context, resource, textViewResourceId);
    }

    public CustomAdapter(Context context, int resource, Object[] objects) {
        super(context, resource, objects);
        this.context = context;
    }

    public CustomAdapter(Context context, int resource, Object[] objects, ArrayList<View> views)
    {
        super(context, resource, objects);
        theViews = views;
    }

    public CustomAdapter(Context context, int resource, int textViewResourceId, Object[] objects) {
        super(context, resource, textViewResourceId, objects);
    }

    public CustomAdapter(Context context, int resource, List objects) {
        super(context, resource, objects);
    }

    public CustomAdapter(Context context, int resource, int textViewResourceId, List objects) {
        super(context, resource, textViewResourceId, objects);
    }

    ArrayList<View> theViews = new ArrayList<>();
    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        LayoutInflater infalter = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //View rowView = infalter.inflate()
        try {
            return theViews.get(position);
        }
        catch (Exception e)
        {
            return null;
        }
    }
}
