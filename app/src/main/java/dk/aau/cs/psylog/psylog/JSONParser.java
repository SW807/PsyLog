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
            //InputStream is = ServiceHelper.getXMLForInstalledProcesses(context)
            //        .get("dk.aau.cs.psylog.psylog_acceloremetermodule");

            Module test = mapper.readValue("{\n" +
                    "   \"_name\": \"accelerometer\",\n" +
                    "   \"_version\": \"1.0\",\n" +
                    "   \"tables\": [\n" +
                    "      {\n" +
                    "         \"name\": \"accelerations\",\n" +
                    "         \"columns\": [\n" +
                    "            {\n" +
                    "               \"_unit\": \"g\",\n" +
                    "               \"name\": \"accX\",\n" +
                    "               \"type\": \"real\"\n" +
                    "            },\n" +
                    "            {\n" +
                    "               \"_unit\": \"g\",\n" +
                    "               \"name\": \"accY\",\n" +
                    "               \"type\": \"real\"\n" +
                    "            },\n" +
                    "            {\n" +
                    "               \"_unit\": \"g\",\n" +
                    "               \"name\": \"accZ\",\n" +
                    "               \"type\": \"real\"\n" +
                    "            }\n" +
                    "         ]\n" +
                    "      }\n" +
                    "   ]\n" +
                    "}", Module.class);
            Log.d("test", test.getName());
            test.getName();
        }
        catch(Exception alt)
        {
            Log.d("sick",alt.getMessage());
        }
    }
}
