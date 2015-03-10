package dk.aau.cs.psylog.psylog;

import android.content.Context;
import android.util.Log;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
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
                if(isValid(is)) modules.add(mapper.readValue(is, Module.class));
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
