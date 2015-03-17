
package dk.aau.cs.psylog.data_access_layer.generated;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;


/**
 * Module
 * <p>
 * 
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
    "name",
    "_version",
    "tables",
    "dependencies"
})
public class Module {

    @JsonProperty("name")
    private String name;
    @JsonProperty("_version")
    private Double _version;
    @JsonProperty("tables")
    @JsonDeserialize(as = java.util.LinkedHashSet.class)
    private Set<Table> tables = new LinkedHashSet<Table>();
    @JsonProperty("dependencies")
    private List<Set<Dependency>> dependencies = new ArrayList<Set<Dependency>>();

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
     *     The _version
     */
    @JsonProperty("_version")
    public Double get_version() {
        return _version;
    }

    /**
     * 
     * @param _version
     *     The _version
     */
    @JsonProperty("_version")
    public void set_version(Double _version) {
        this._version = _version;
    }

    /**
     * 
     * @return
     *     The tables
     */
    @JsonProperty("tables")
    public Set<Table> getTables() {
        return tables;
    }

    /**
     * 
     * @param tables
     *     The tables
     */
    @JsonProperty("tables")
    public void setTables(Set<Table> tables) {
        this.tables = tables;
    }

    /**
     * 
     * @return
     *     The dependencies
     */
    @JsonProperty("dependencies")
    public List<Set<Dependency>> getDependencies() {
        return dependencies;
    }

    /**
     * 
     * @param dependencies
     *     The dependencies
     */
    @JsonProperty("dependencies")
    public void setDependencies(List<Set<Dependency>> dependencies) {
        this.dependencies = dependencies;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(name).append(_version).append(tables).append(dependencies).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Module) == false) {
            return false;
        }
        Module rhs = ((Module) other);
        return new EqualsBuilder().append(name, rhs.name).append(_version, rhs._version).append(tables, rhs.tables).append(dependencies, rhs.dependencies).isEquals();
    }

}
