package dk.aau.cs.psylog.psylog;

import android.view.View;

import com.commonsware.cwac.sacklist.SackOfViewsAdapter;

import java.util.List;


/**
 * Created by Praetorian on 23-03-2015.
 */
public class MySackAdapt extends SackOfViewsAdapter {
    public MySackAdapt(int count)
    {
        super(count);
    }

    public MySackAdapt(List<View> views)
    {
        super(views);
    }
}
