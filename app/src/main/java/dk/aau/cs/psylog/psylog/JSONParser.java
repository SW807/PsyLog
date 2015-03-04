package dk.aau.cs.psylog.psylog;

import android.content.Context;
import android.util.Log;

import com.fasterxml.jackson.databind.ObjectMapper;

import dk.aau.cs.psylog.generated.Test;

/**
 * Created by - on 04-03-2015.
 */
public class JSONParser {

    JSONParser(Context context)
    {
        ObjectMapper mapper = new ObjectMapper();

        try
        {
            Test test = mapper.readValue("{\"mikael\":1,\"bruno\":1}",Test.class);

            test.getBruno();
        }
        catch(Exception alt)
        {
            Log.d("sick",alt.getMessage());
        }


    }
}
