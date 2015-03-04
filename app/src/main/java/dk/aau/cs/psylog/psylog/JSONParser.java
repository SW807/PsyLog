package dk.aau.cs.psylog.psylog;

import android.content.Context;
import android.util.Log;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.jsonSchema.JsonSchema;
import com.fasterxml.jackson.module.jsonSchema.factories.SchemaFactoryWrapper;

import dk.aau.cs.psylog.generated.Module;
import dk.aau.cs.psylog.generated.Test;

public class JSONParser {

    JSONParser(Context context)
    {
        ObjectMapper mapper = new ObjectMapper();

        try
        {
            Test test = mapper.readValue("{\"mikael\":1,\"bruno\":1}",Test.class);
        }
        catch(Exception alt)
        {
            Log.d("sick",alt.getMessage());
        }
    }
}
