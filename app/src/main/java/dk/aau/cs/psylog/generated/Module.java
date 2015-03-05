
package dk.aau.cs.psylog.generated;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;


/**
 * Module
 * <p>
 * 
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)

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
    private Double Version;
    @JsonProperty("tables")
    @JsonDeserialize(as = LinkedHashSet.class)
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
     *     The Version
     */
    @JsonProperty("_version")
    public Double getVersion() {
        return Version;
    }

    /**
     * 
     * @param Version
     *     The _version
     */
    @JsonProperty("_version")
    public void setVersion(Double Version) {
        this.Version = Version;
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
        return new HashCodeBuilder().append(name).append(Version).append(tables).append(dependencies).toHashCode();
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
        return new EqualsBuilder().append(name, rhs.name).append(Version, rhs.Version).append(tables, rhs.tables).append(dependencies, rhs.dependencies).isEquals();
    }

}
