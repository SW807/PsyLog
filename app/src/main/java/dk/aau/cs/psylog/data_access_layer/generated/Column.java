
package dk.aau.cs.psylog.data_access_layer.generated;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonValue;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
    "name",
    "dataType",
    "nullable",
    "_unit"
})
public class Column {

    @JsonProperty("name")
    private String name;
    @JsonProperty("dataType")
    private Column.DataType dataType;
    @JsonProperty("nullable")
    private Boolean nullable;
    @JsonProperty("_unit")
    private String _unit;

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
    public Column.DataType getDataType() {
        return dataType;
    }

    /**
     * 
     * @param dataType
     *     The dataType
     */
    @JsonProperty("dataType")
    public void setDataType(Column.DataType dataType) {
        this.dataType = dataType;
    }

    /**
     * 
     * @return
     *     The nullable
     */
    @JsonProperty("nullable")
    public Boolean getNullable() {
        return nullable;
    }

    /**
     * 
     * @param nullable
     *     The nullable
     */
    @JsonProperty("nullable")
    public void setNullable(Boolean nullable) {
        this.nullable = nullable;
    }

    /**
     * 
     * @return
     *     The _unit
     */
    @JsonProperty("_unit")
    public String get_unit() {
        return _unit;
    }

    /**
     * 
     * @param _unit
     *     The _unit
     */
    @JsonProperty("_unit")
    public void set_unit(String _unit) {
        this._unit = _unit;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(name).append(dataType).append(nullable).append(_unit).toHashCode();
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
        return new EqualsBuilder().append(name, rhs.name).append(dataType, rhs.dataType).append(nullable, rhs.nullable).append(_unit, rhs._unit).isEquals();
    }

    @Generated("org.jsonschema2pojo")
    public static enum DataType {

        INTEGER("INTEGER"),
        REAL("REAL"),
        TEXT("TEXT"),
        BLOB("BLOB");
        private final String value;
        private static Map<String, Column.DataType> constants = new HashMap<String, Column.DataType>();

        static {
            for (Column.DataType c: values()) {
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
        public static Column.DataType fromValue(String value) {
            Column.DataType constant = constants.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }

}
