
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
    "_type",
    "_version",
    "tables",
    "dependencies",
    "view"
})
public class Module_v2 {

    @JsonProperty("name")
    private String name;
    @JsonProperty("_type")
    private String _type;
    @JsonProperty("_version")
    private Double _version;
    @JsonProperty("tables")
    @JsonDeserialize(as = java.util.LinkedHashSet.class)
    private Set<Table_> tables = new LinkedHashSet<Table_>();
    @JsonProperty("dependencies")
    private List<Set<Dependency_>> dependencies = new ArrayList<Set<Dependency_>>();
    /**
     * 
     */
    @JsonProperty("view")
    private View view;

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
     *     The _type
     */
    @JsonProperty("_type")
    public String get_type() {
        return _type;
    }

    /**
     * 
     * @param _type
     *     The _type
     */
    @JsonProperty("_type")
    public void set_type(String _type) {
        this._type = _type;
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
    public Set<Table_> getTables() {
        return tables;
    }

    /**
     * 
     * @param tables
     *     The tables
     */
    @JsonProperty("tables")
    public void setTables(Set<Table_> tables) {
        this.tables = tables;
    }

    /**
     * 
     * @return
     *     The dependencies
     */
    @JsonProperty("dependencies")
    public List<Set<Dependency_>> getDependencies() {
        return dependencies;
    }

    /**
     * 
     * @param dependencies
     *     The dependencies
     */
    @JsonProperty("dependencies")
    public void setDependencies(List<Set<Dependency_>> dependencies) {
        this.dependencies = dependencies;
    }

    /**
     * 
     * @return
     *     The view
     */
    @JsonProperty("view")
    public View getView() {
        return view;
    }

    /**
     * 
     * @param view
     *     The view
     */
    @JsonProperty("view")
    public void setView(View view) {
        this.view = view;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(name).append(_type).append(_version).append(tables).append(dependencies).append(view).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Module_v2) == false) {
            return false;
        }
        Module_v2 rhs = ((Module_v2) other);
        return new EqualsBuilder().append(name, rhs.name).append(_type, rhs._type).append(_version, rhs._version).append(tables, rhs.tables).append(dependencies, rhs.dependencies).append(view, rhs.view).isEquals();
    }

}
