
package dk.aau.cs.psylog.data_access_layer.generated;

import java.util.LinkedHashSet;
import java.util.Set;
import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
    "name",
    "dataTypes",
    "fromTimestamp"
})
public class Datum {

    @JsonProperty("name")
    private String name;
    @JsonProperty("dataTypes")
    @JsonDeserialize(as = java.util.LinkedHashSet.class)
    private Set<DataType> dataTypes = new LinkedHashSet<DataType>();
    @JsonProperty("fromTimestamp")
    private Boolean fromTimestamp;

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
     *     The dataTypes
     */
    @JsonProperty("dataTypes")
    public Set<DataType> getDataTypes() {
        return dataTypes;
    }

    /**
     * 
     * @param dataTypes
     *     The dataTypes
     */
    @JsonProperty("dataTypes")
    public void setDataTypes(Set<DataType> dataTypes) {
        this.dataTypes = dataTypes;
    }

    /**
     * 
     * @return
     *     The fromTimestamp
     */
    @JsonProperty("fromTimestamp")
    public Boolean getFromTimestamp() {
        return fromTimestamp;
    }

    /**
     * 
     * @param fromTimestamp
     *     The fromTimestamp
     */
    @JsonProperty("fromTimestamp")
    public void setFromTimestamp(Boolean fromTimestamp) {
        this.fromTimestamp = fromTimestamp;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(name).append(dataTypes).append(fromTimestamp).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Datum) == false) {
            return false;
        }
        Datum rhs = ((Datum) other);
        return new EqualsBuilder().append(name, rhs.name).append(dataTypes, rhs.dataTypes).append(fromTimestamp, rhs.fromTimestamp).isEquals();
    }

}
