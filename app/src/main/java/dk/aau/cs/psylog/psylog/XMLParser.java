package dk.aau.cs.psylog.psylog;

import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class XMLParser {
    private final String tag = "module";

    private Module readModule(XmlPullParser parser) throws XmlPullParserException, IOException
    {
        parser.require(XmlPullParser.START_TAG, ns, tag);

        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String currentTag = parser.getName();

            switch (currentTag) {
                case "tables" : break;
                case "table" : readTableName(parser); break;
                case "columns" : break;
                case "column" : readColumnSource(parser); break;

            }
            }
        }
    }

    private static final String ns = null;


    public List parse(InputStream stream) throws XmlPullParserException, IOException
    {
        try {
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(stream, null);
            parser.nextTag();
            return readFeed(parser);
        } finally {
            stream.close();
        }
    }

    private List readFeed(XmlPullParser parser) throws XmlPullParserException, IOException {
        List entries = new ArrayList();

        parser.require(XmlPullParser.START_TAG, ns, "feed");
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            // Starts by looking for the entry tag
            if (name.equals(tag)) {
                entries.add(readModule(parser));
            }
            /*else {
                skip(parser);
            }*/
        }
        return entries;
    }




    private String readColumnSource(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "source");
        String summary = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, "source");
        return summary;
    }

    private String readColumnText(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "source");
        String summary = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, "source");
        return summary;
    }



    private String readTableName(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "name");
        String summary = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, "name");
        return summary;
    }

    private String readText(XmlPullParser parser) throws IOException, XmlPullParserException {
        String result = "";
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.getText();
            parser.nextTag();
        }
        return result;
    }

}
