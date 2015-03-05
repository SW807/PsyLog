package dk.aau.cs.psylog.psylog;

import android.content.Context;
import android.util.Log;

import com.fasterxml.jackson.databind.ObjectMapper;

import dk.aau.cs.psylog.generated.Module;

public class JSONParser {

    JSONParser(Context context)
    {
        ObjectMapper mapper = new ObjectMapper();

        try
        {
            //InputStream is = ServiceHelper.getXMLForInstalledProcesses(context)
            //        .get("dk.aau.cs.psylog.psylog_acceloremetermodule");

            Module test = mapper.readValue("{\n" +
                    "  \"name\": \"accelerometer\",\n" +
                    "  \"_version\": 1.0,\n" +
                    "  \"tables\": [\n" +
                    "    {\n" +
                    "      \"name\": \"data\",\n" +
                    "      \"columns\": [\n" +
                    "        { \"name\": \"accx\", \"dataType\": \"REAL\", \"_unit\": \"m/s\"},\n" +
                    "        { \"name\": \"accy\", \"dataType\": \"REAL\", \"_unit\": \"m/s\"},\n" +
                    "        { \"name\": \"accz\", \"dataType\": \"REAL\", \"_unit\": \"m/s\"}\n" +
                    "      ]\n" +
                    "    }\n" +
                    "  ],\n" +
                    "  \"dependencies\": [\n" +
                    "    [{ \"name\": \"hardConstraint\" }],\n" +
                    "    [{ \"name\": \"option1\" }, { \"name\": \"option2\" }]\n" +
                    "  ]\n" +
                    "}", Module.class);
            Log.d("test", test.getName());
        }
        catch(Exception alt)
        {
            Log.d("sick",alt.getMessage());
        }
    }
}
