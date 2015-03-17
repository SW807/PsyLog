
package dk.aau.cs.psylog.data_access_layer.generated;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

@Generated("org.jsonschema2pojo")
public enum DataType {

    INTEGER("INTEGER"),
    REAL("REAL"),
    TEXT("TEXT"),
    BLOB("BLOB");
    private final String value;
    private static Map<String, DataType> constants = new HashMap<String, DataType>();

    static {
        for (DataType c: values()) {
            constants.put(c.value, c);
        }
    }

    private DataType(String value) {
        this.value = value;
    }

    @JsonValue
    @Override
    public String toString() {
        return this.value;
    }

    @JsonCreator
    public static DataType fromValue(String value) {
        DataType constant = constants.get(value);
        if (constant == null) {
            throw new IllegalArgumentException(value);
        } else {
            return constant;
        }
    }

}
