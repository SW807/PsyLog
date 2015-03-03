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
    private static final String NAMESPACE = null;

    private Module readModule(XmlPullParser parser) throws XmlPullParserException, IOException
    {
        parser.require(XmlPullParser.START_TAG, NAMESPACE, tag);
        Module module = null;

        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String currentTag = parser.getName();

            ArrayList<Table> tables = new ArrayList<>();
            ArrayList<Column> columns = new ArrayList<>();

            switch (currentTag) {
                case "tables" : break;
                case "table" : tables.add(new Table(readStringElement(parser, "name"))); break;
                case "columns" : break;
                case "column" : columns.add(new Column(readStringElement(parser, "name"), readColumnDataType(parser), readColumnNullable(parser))); break;
                case "dependencies" : break;
                case "dependency" : module.AddDependencyGroup(new DependencyGroup(new Dependency(readStringElement(parser, "name"), readDependencyOptional(parser))));
                case "dependencyGroup" : module.AddDependencyGroup(readDependencyGroup(parser));
            }
        }
        return module;
    }


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
        List modules = new ArrayList();

        parser.require(XmlPullParser.START_TAG, NAMESPACE, "feed");
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            // Starts by looking for the entry tag
            if (name.equals(tag)) {
                modules.add(readModule(parser));
            }
            /*else {
                skip(parser);
            }*/
        }
        return modules;
    }



    private Boolean readColumnNullable(XmlPullParser parser) throws IOException, XmlPullParserException
    {
        return parser.getText() == "nullable";
    }

    private Boolean readDependencyOptional(XmlPullParser parser) throws IOException, XmlPullParserException
    {
        if (parser.next() == XmlPullParser.TEXT) {
            return parser.getText() == "true";
        }
        return false;
    }


    private DependencyGroup readDependencyGroup(XmlPullParser parser) throws IOException, XmlPullParserException
    {
        DependencyGroup dependencies = new DependencyGroup();
        while (parser.getName() == "dependency") {
            dependencies.Add(new Dependency(readStringElement(parser, "name"), readDependencyOptional(parser)));
            parser.nextTag();
        }
        return dependencies;
    }


    private String readStringElement(XmlPullParser parser, String element) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, NAMESPACE, element);
        String summary = readText(parser);
        parser.require(XmlPullParser.END_TAG, NAMESPACE, element);
        return summary;
    }


    private DataType readColumnDataType(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, NAMESPACE, "type");
        DataType type = readDataType(parser);
        parser.require(XmlPullParser.END_TAG, NAMESPACE, "type");
        return type;
    }


    private DataType readDataType(XmlPullParser parser) throws IOException, XmlPullParserException
    {
        if (parser.next() == XmlPullParser.TEXT) {
            switch (parser.getText()) {
                case "integer": return DataType.integer;
                case "real" : return DataType.real;
                case "text" : return DataType.text;
                case "blob" : return DataType.blob;
                default: throw new ClassCastException("Wrong datattype in xml document");
            }
        }
        return null;
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
