
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
    "columns"
})
public class Table {

    @JsonProperty("name")
    private String name;
    @JsonProperty("columns")
    @JsonDeserialize(as = java.util.LinkedHashSet.class)
    private Set<Column> columns = new LinkedHashSet<Column>();

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
     *     The columns
     */
    @JsonProperty("columns")
    public Set<Column> getColumns() {
        return columns;
    }

    /**
     * 
     * @param columns
     *     The columns
     */
    @JsonProperty("columns")
    public void setColumns(Set<Column> columns) {
        this.columns = columns;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(name).append(columns).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Table) == false) {
            return false;
        }
        Table rhs = ((Table) other);
        return new EqualsBuilder().append(name, rhs.name).append(columns, rhs.columns).isEquals();
    }

}
