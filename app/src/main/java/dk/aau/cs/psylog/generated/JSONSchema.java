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
    "_name",
    "_version",
    "tables",
    "dependencies"
})
public class JSONSchema {

    @JsonProperty("_name")
    private String Name;
    @JsonProperty("_version")
    private String Version;
    @JsonProperty("tables")
    private List<Table> tables = new ArrayList<Table>();
    @JsonProperty("dependencies")
    private Dependencies dependencies;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * 
     * @return
     *     The Name
     */
    @JsonProperty("_name")
    public String getName() {
        return Name;
    }

    /**
     * 
     * @param Name
     *     The _name
     */
    @JsonProperty("_name")
    public void setName(String Name) {
        this.Name = Name;
    }

    /**
     * 
     * @return
     *     The Version
     */
    @JsonProperty("_version")
    public String getVersion() {
        return Version;
    }

    /**
     * 
     * @param Version
     *     The _version
     */
    @JsonProperty("_version")
    public void setVersion(String Version) {
        this.Version = Version;
    }

    /**
     * 
     * @return
     *     The tables
     */
    @JsonProperty("tables")
    public List<Table> getTables() {
        return tables;
    }

    /**
     * 
     * @param tables
     *     The tables
     */
    @JsonProperty("tables")
    public void setTables(List<Table> tables) {
        this.tables = tables;
    }

    /**
     * 
     * @return
     *     The dependencies
     */
    @JsonProperty("dependencies")
    public Dependencies getDependencies() {
        return dependencies;
    }

    /**
     * 
     * @param dependencies
     *     The dependencies
     */
    @JsonProperty("dependencies")
    public void setDependencies(Dependencies dependencies) {
        this.dependencies = dependencies;
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
        return new HashCodeBuilder().append(Name).append(Version).append(tables).append(dependencies).append(additionalProperties).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof JSONSchema) == false) {
            return false;
        }
        JSONSchema rhs = ((JSONSchema) other);
        return new EqualsBuilder().append(Name, rhs.Name).append(Version, rhs.Version).append(tables, rhs.tables).append(dependencies, rhs.dependencies).append(additionalProperties, rhs.additionalProperties).isEquals();
    }

}
