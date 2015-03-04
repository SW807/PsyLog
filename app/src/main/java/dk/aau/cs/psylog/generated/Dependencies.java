import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
    "dependency",
    "dependencyGroup"
})
public class Dependencies {

    @JsonProperty("dependency")
    private List<String> dependency = new ArrayList<String>();
    @JsonProperty("dependencyGroup")
    private List<List<String>> dependencyGroup = new ArrayList<List<String>>();
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * 
     * @return
     *     The dependency
     */
    @JsonProperty("dependency")
    public List<String> getDependency() {
        return dependency;
    }

    /**
     * 
     * @param dependency
     *     The dependency
     */
    @JsonProperty("dependency")
    public void setDependency(List<String> dependency) {
        this.dependency = dependency;
    }

    /**
     * 
     * @return
     *     The dependencyGroup
     */
    @JsonProperty("dependencyGroup")
    public List<List<String>> getDependencyGroup() {
        return dependencyGroup;
    }

    /**
     * 
     * @param dependencyGroup
     *     The dependencyGroup
     */
    @JsonProperty("dependencyGroup")
    public void setDependencyGroup(List<List<String>> dependencyGroup) {
        this.dependencyGroup = dependencyGroup;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(dependency).append(dependencyGroup).append(additionalProperties).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Dependencies) == false) {
            return false;
        }
        Dependencies rhs = ((Dependencies) other);
        return new EqualsBuilder().append(dependency, rhs.dependency).append(dependencyGroup, rhs.dependencyGroup).append(additionalProperties, rhs.additionalProperties).isEquals();
    }

}
