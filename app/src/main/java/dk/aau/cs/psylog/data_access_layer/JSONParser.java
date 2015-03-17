package dk.aau.cs.psylog.data_access_layer;

import android.content.Context;
import android.util.Log;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import dk.aau.cs.psylog.data_access_layer.generated.AnalysisModule;
import dk.aau.cs.psylog.data_access_layer.generated.Module;
import dk.aau.cs.psylog.data_access_layer.generated.SensorModule;
import dk.aau.cs.psylog.psylog.ServiceHelper;

public class JSONParser {

    private Context context;

    public JSONParser(Context context)
    {
        this.context = context;
    }

    public ArrayList<Module> parse()
    {
        ArrayList<Module> modules = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
        HashMap<String, InputStream> processes = ServiceHelper.getJSONForInstalledProcesses(context);
        for(Map.Entry<String, InputStream> is : processes.entrySet())
        {
            try {
                if(isValid(is.getValue())) {
                    if (is.getKey().startsWith("dk.aau.cs.psylog.sensor"))
                        modules.add(mapper.readValue(is.getValue(), SensorModule.class));
                    else if (is.getKey().startsWith("dk.aau.cs.psylog.analysis"))
                        modules.add(mapper.readValue(is.getValue(), AnalysisModule.class));
                }
            }
            catch (IOException e) {
                Log.e("JSONParser", e.getMessage());
            }
        }
        return  modules;
    }


    private boolean isValid(InputStream json){
        InputStream schema;
        return true;
    }



}
