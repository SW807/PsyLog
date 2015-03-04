package dk.aau.cs.psylog.psylog;


import android.content.Context;
import android.util.Log;

import com.fasterxml.aalto.stax.InputFactoryImpl;
import com.fasterxml.aalto.stax.OutputFactoryImpl;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule;
import com.fasterxml.jackson.dataformat.xml.XmlFactory;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import dk.aau.cs.psylog.generated.Module;

public class XMLParser {

    public XMLParser(Context context)
    {
        try {
            //Use Aalto StAX implementation explicitly for XML parsing
            XmlFactory f = new XmlFactory(new InputFactoryImpl(), new OutputFactoryImpl());

            JacksonXmlModule module = new JacksonXmlModule();

    /*
     * Tell Jackson that Lists are using "unwrapped" style (i.e.,
     * there is no wrapper element for list).
     * NOTE - This requires Jackson 2.1 or higher
     */
            module.setDefaultUseWrapper(false);

            XmlMapper xmlMapper = new XmlMapper(f, module);

            xmlMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
            xmlMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT,true);
            xmlMapper.configure(DeserializationFeature.USE_JAVA_ARRAY_FOR_JSON_ARRAY, true);
            xmlMapper.configure(DeserializationFeature.READ_ENUMS_USING_TO_STRING,true);

            //Tell Jackson to expect the XML in PascalCase, instead of camelCase
            xmlMapper.setPropertyNamingStrategy(new PropertyNamingStrategy.PascalCaseStrategy());

            //Make the HTTP request, and deserialize the XML response into the Siri object
            Module m = xmlMapper.readValue(ServiceHelper.getXMLForInstalledProcesses(context).get("dk.aau.cs.psylog.psylog_accelerometermodule"),  Module.class);
        } catch(Exception e){
            Log.e("HEJ STUFFE!", "Error fetching or parsing XML: " + e);
        }
    }

}
