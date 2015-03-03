package dk.aau.cs.psylog.psylog;

import android.provider.SyncStateContract;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class XMLParser {
    private static final String NAMESPACE = null;

    private Module readModule(XmlPullParser parser) throws XmlPullParserException, IOException
    {
        parser.require(XmlPullParser.START_TAG, NAMESPACE, "module");
        Module module = null;
        ArrayList<Table> tables = new ArrayList<>();
        ArrayList<Column> columns = new ArrayList<>();

        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String currentTag = parser.getName();

            switch (currentTag) {
                case "tables" : module.SetTables(readTables(parser)); break;
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

    private ArrayList<Table> readTables(XmlPullParser parser) throws XmlPullParserException, IOException
    {
        parser.require(XmlPullParser.START_TAG, NAMESPACE, "tables");
        ArrayList<Table> tables = new ArrayList<>();

        while (parser.next() != XmlPullParser.END_TAG && parser.getName() != "tables") {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            if (parser.getName() == "table")
                tables.add(readTable(parser));
        }
        return tables;
    }

    private Table readTable(XmlPullParser parser) throws XmlPullParserException, IOException
    {
        parser.require(XmlPullParser.START_TAG, NAMESPACE, "table");
        Table table = new Table(readStringElement(parser, "name"));
        while (parser.next() != XmlPullParser.END_TAG && parser.getName() != "table") {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }

        }
        return table;
    }

    private ArrayList<Column> readColumns(XmlPullParser parser) throws XmlPullParserException, IOException
    {
        parser.require(XmlPullParser.START_TAG, NAMESPACE, "columns");
        ArrayList<Column> columns = new ArrayList<>();
        while (parser.next() != XmlPullParser.END_TAG && parser.getName() != "columns") {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }

        }
        return columns;
    }

    private Column readColumn(XmlPullParser parser) throws XmlPullParserException, IOException
    {
        parser.require(XmlPullParser.START_TAG, NAMESPACE, "column");

        String name = readStringElement(parser,"name");
        String type = readStringElement(parser,"type");

        Column column =readColumn(parser);
        return new Column(name,type,column);
    }


    public Module parse(InputStream stream) throws XmlPullParserException, IOException
    {
        try {
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(stream, null);
            parser.nextTag();
            return readModule(parser);
        } finally {
            stream.close();
        }
    }


    private Boolean readColumnNullable(XmlPullParser parser) throws IOException, XmlPullParserException
    {
        if( parser.getText() == "nullable") {
            parser.nextTag();
            return true;
        }
        else return false;

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
        String text = readText(parser);
        parser.require(XmlPullParser.END_TAG, NAMESPACE, element);
        return text;
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
                case "integer": parser.nextTag(); return DataType.integer;
                case "real" : parser.nextTag(); return DataType.real;
                case "text" : parser.nextTag(); return DataType.text;
                case "blob" : parser.nextTag(); return DataType.blob;
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
