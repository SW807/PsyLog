
package dk.aau.cs.psylog.generated;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonValue;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

@JsonInclude(JsonInclude.Include.NON_NULL)

@JsonPropertyOrder({
    "name",
    "dataType",
    "_unit"
})
public class Column {

    @JsonProperty("name")
    private String name;
    @JsonProperty("dataType")
    private DataType dataType;
    @JsonProperty("_unit")
    private String Unit;

    /**
     * 
     * @return
     *     The name
     */
    @JsonProperty("name")
    public String getName() {
        return name;
    }

    /**
     * 
     * @param name
     *     The name
     */
    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 
     * @return
     *     The dataType
     */
    @JsonProperty("dataType")
    public DataType getDataType() {
        return dataType;
    }

    /**
     * 
     * @param dataType
     *     The dataType
     */
    @JsonProperty("dataType")
    public void setDataType(DataType dataType) {
        this.dataType = dataType;
    }

    /**
     * 
     * @return
     *     The Unit
     */
    @JsonProperty("_unit")
    public String getUnit() {
        return Unit;
    }

    /**
     * 
     * @param Unit
     *     The _unit
     */
    @JsonProperty("_unit")
    public void setUnit(String Unit) {
        this.Unit = Unit;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(name).append(dataType).append(Unit).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Column) == false) {
            return false;
        }
        Column rhs = ((Column) other);
        return new EqualsBuilder().append(name, rhs.name).append(dataType, rhs.dataType).append(Unit, rhs.Unit).isEquals();
    }


    public static enum DataType {

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

}
