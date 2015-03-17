
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
    "layout",
    "data"
})
public class View {

    @JsonProperty("layout")
    private String layout;
    @JsonProperty("data")
    @JsonDeserialize(as = java.util.LinkedHashSet.class)
    private Set<Datum> data = new LinkedHashSet<Datum>();

    /**
     * 
     * @return
     *     The layout
     */
    @JsonProperty("layout")
    public String getLayout() {
        return layout;
    }

    /**
     * 
     * @param layout
     *     The layout
     */
    @JsonProperty("layout")
    public void setLayout(String layout) {
        this.layout = layout;
    }

    /**
     * 
     * @return
     *     The data
     */
    @JsonProperty("data")
    public Set<Datum> getData() {
        return data;
    }

    /**
     * 
     * @param data
     *     The data
     */
    @JsonProperty("data")
    public void setData(Set<Datum> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(layout).append(data).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof View) == false) {
            return false;
        }
        View rhs = ((View) other);
        return new EqualsBuilder().append(layout, rhs.layout).append(data, rhs.data).isEquals();
    }

}
