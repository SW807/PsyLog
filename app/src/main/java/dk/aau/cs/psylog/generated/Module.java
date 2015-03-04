
package dk.aau.cs.psylog.generated;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Generated;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

@Generated("org.jsonschema2pojo")
public class Module {

    private String Name;
    private String Version;
    private List<Table> tables = new ArrayList<Table>();
    private Dependencies dependencies;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * 
     * @return
     *     The Name
     */
    public String getName() {
        return Name;
    }

    /**
     * 
     * @param Name
     *     The _name
     */
    public void setName(String Name) {
        this.Name = Name;
    }

    /**
     * 
     * @return
     *     The Version
     */
    public String getVersion() {
        return Version;
    }

    /**
     * 
     * @param Version
     *     The _version
     */
    public void setVersion(String Version) {
        this.Version = Version;
    }

    /**
     * 
     * @return
     *     The tables
     */
    public List<Table> getTables() {
        return tables;
    }

    /**
     * 
     * @param tables
     *     The tables
     */
    public void setTables(List<Table> tables) {
        this.tables = tables;
    }

    /**
     * 
     * @return
     *     The dependencies
     */
    public Dependencies getDependencies() {
        return dependencies;
    }

    /**
     * 
     * @param dependencies
     *     The dependencies
     */
    public void setDependencies(Dependencies dependencies) {
        this.dependencies = dependencies;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

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
        if ((other instanceof Module) == false) {
            return false;
        }
        Module rhs = ((Module) other);
        return new EqualsBuilder().append(Name, rhs.Name).append(Version, rhs.Version).append(tables, rhs.tables).append(dependencies, rhs.dependencies).append(additionalProperties, rhs.additionalProperties).isEquals();
    }

}
