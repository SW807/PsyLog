//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2015.03.03 at 02:38:51 PM CET 
//


package dk.aau.cs.psylog.generated;



/**
 * <p>Java class for dataTypes.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="dataTypes"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="integer"/&gt;
 *     &lt;enumeration value="real"/&gt;
 *     &lt;enumeration value="text"/&gt;
 *     &lt;enumeration value="blob"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */


public enum DataTypes {


    INTEGER("integer"),

    REAL("real"),

    TEXT("text"),

    BLOB("blob");
    private final String value;

    DataTypes(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static DataTypes fromValue(String v) {
        for (DataTypes c: DataTypes.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
