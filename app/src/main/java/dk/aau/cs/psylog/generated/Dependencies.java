
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
public class Dependencies {

    private List<Dependency> dependencies = new ArrayList<Dependency>();
    private List<DependencyGroup> dependencyGroups = new ArrayList<DependencyGroup>();
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * 
     * @return
     *     The dependencies
     */
    public List<Dependency> getDependencies() {
        return dependencies;
    }

    /**
     * 
     * @param dependencies
     *     The dependencies
     */
    public void setDependencies(List<Dependency> dependencies) {
        this.dependencies = dependencies;
    }

    /**
     * 
     * @return
     *     The dependencyGroups
     */
    public List<DependencyGroup> getDependencyGroups() {
        return dependencyGroups;
    }

    /**
     * 
     * @param dependencyGroups
     *     The dependencyGroups
     */
    public void setDependencyGroups(List<DependencyGroup> dependencyGroups) {
        this.dependencyGroups = dependencyGroups;
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
        return new HashCodeBuilder().append(dependencies).append(dependencyGroups).append(additionalProperties).toHashCode();
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
        return new EqualsBuilder().append(dependencies, rhs.dependencies).append(dependencyGroups, rhs.dependencyGroups).append(additionalProperties, rhs.additionalProperties).isEquals();
    }

}
