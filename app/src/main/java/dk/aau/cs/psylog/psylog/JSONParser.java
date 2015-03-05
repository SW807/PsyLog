package dk.aau.cs.psylog.psylog;

import android.content.Context;
import android.util.Log;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import dk.aau.cs.psylog.generated.Module;
import dk.aau.cs.psylog.generated.Table;

public class JSONParser {

    private Context context;

    JSONParser(Context context)
    {
        this.context = context;
        ObjectMapper mapper = new ObjectMapper();

        try
        {
            InputStream is = ServiceHelper.getJSONForInstalledProcesses(context)
                    .get("dk.aau.cs.psylog.psylog_accelerometermodule");

            Module test = mapper.readValue(is, Module.class);
            Log.d("test", test.getName());
            test.getTables();
        }
        catch(Exception alt)
        {
            Log.d("sick",alt.getMessage());
        }

    }

    private List<Module> parse()
    {
        List<Module> modules = new ArrayList<>();
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

    private List<String> getTables(Module module)
    {
        List<String> l = new ArrayList<>();
        for(Table t : module.getTables())
            l.add(t.getName());
        return l;
    }

    

    private void createTables()
    {
        SQLiteHelper sqLiteHelper = new SQLiteHelper(context);
        sqLiteHelper.createTable();
    }

}
