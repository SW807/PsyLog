package dk.aau.cs.psylog.psylog;

import android.content.Context;
import android.util.Log;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.InputStream;

import dk.aau.cs.psylog.generated.Module;

public class JSONParser {

    JSONParser(Context context)
    {
        ObjectMapper mapper = new ObjectMapper();

        try
        {
            InputStream is = ServiceHelper.getXMLForInstalledProcesses(context)
                    .get("dk.aau.cs.psylog.psylog_acceloremetermodule");

            Module test = mapper.readValue(is, Module.class);
        }
        catch(Exception alt)
        {
            Log.d("sick",alt.getMessage());
        }
    }
}
