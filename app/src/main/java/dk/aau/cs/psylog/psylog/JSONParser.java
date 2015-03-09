package dk.aau.cs.psylog.psylog;

import android.content.Context;
import android.util.Log;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jackson.JsonLoader;
import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import com.github.fge.jsonschema.main.JsonSchema;
import com.github.fge.jsonschema.main.JsonSchemaFactory;

import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import dk.aau.cs.psylog.generated.Module;

public class JSONParser {

    private Context context;

    JSONParser(Context context)
    {
        this.context = context;
    }

    public ArrayList<Module> parse()
    {
        ArrayList<Module> modules = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
        HashMap<String, InputStream> processes = ServiceHelper.getJSONForInstalledProcesses(context);
        for(InputStream is : processes.values())
        {
            try {
                modules.add(mapper.readValue(is, Module.class));
            }
            catch (IOException e) {
                Log.e("JSONParser", e.getMessage());
            }
        }
        return  modules;
    }

    public boolean validate(InputStream jsonData, InputStream jsonSchema) {
        InputStreamReader jsonDataReader = new InputStreamReader(jsonData);
        InputStreamReader jsonSchemaReader = new InputStreamReader(jsonSchema);

        JsonNode schemaNode = null;
        JsonNode data = null;

        try {
            schemaNode = JsonLoader.fromReader(jsonSchemaReader);
            data = JsonLoader.fromReader(jsonDataReader);

            JsonSchemaFactory factory = JsonSchemaFactory.byDefault();
            // load the schema and validate
            JsonSchema schema = factory.getJsonSchema(schemaNode);
            ProcessingReport report = schema.validate(data);


            return report.isSuccess();
        } catch (IOException e) {
            Log.e("JSONParser", "Could not parse json file when validating: " + e.getMessage());
        } catch (ProcessingException e) {
            Log.e("JSONParser", "Json could not be parsed, schema may contain MissingNode " + e.getMessage());
        }

        return false;
    }


}
