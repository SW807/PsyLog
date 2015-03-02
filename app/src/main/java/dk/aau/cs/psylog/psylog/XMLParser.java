package dk.aau.cs.psylog.psylog;

import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class XMLParser {

    private Module readModules(XmlPullParser parser) throws XmlPullParserException, IOException
    {

    }


    public List parse(InputStream stream) throws XmlPullParserException, IOException
    {
        try {
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(stream, null);
            parser.nextTag();
            return readModules(parser);
        } finally {
            stream.close();
        }
    }

}
