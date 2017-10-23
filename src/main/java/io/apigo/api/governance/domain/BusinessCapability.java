package io.apigo.api.governance.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A BusinessCapability.
 */
@Entity
@Table(name = "business_capability")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class BusinessCapability implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 3)
    @Column(name = "common_name", nullable = false)
    private String commonName;

    @NotNull
    @Size(min = 3)
    @Column(name = "display_name", nullable = false)
    private String displayName;

    @Column(name = "description")
    private String description;

    @Column(name = "sort_order")
    private Integer sortOrder;

    @Column(name = "date_added")
    private ZonedDateTime dateAdded;

    @Column(name = "date_modified")
    private ZonedDateTime dateModified;

    @ManyToOne
    private BusinessCapability parent;

    @OneToMany(mappedBy = "parent")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<BusinessCapability> displayNames = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCommonName() {
        return commonName;
    }

    public BusinessCapability commonName(String commonName) {
        this.commonName = commonName;
        return this;
    }

    public void setCommonName(String commonName) {
        this.commonName = commonName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public BusinessCapability displayName(String displayName) {
        this.displayName = displayName;
        return this;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getDescription() {
        return description;
    }

    public BusinessCapability description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public BusinessCapability sortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
        return this;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    public ZonedDateTime getDateAdded() {
        return dateAdded;
    }

    public BusinessCapability dateAdded(ZonedDateTime dateAdded) {
        this.dateAdded = dateAdded;
        return this;
    }

    public void setDateAdded(ZonedDateTime dateAdded) {
        this.dateAdded = dateAdded;
    }

    public ZonedDateTime getDateModified() {
        return dateModified;
    }

    public BusinessCapability dateModified(ZonedDateTime dateModified) {
        this.dateModified = dateModified;
        return this;
    }

    public void setDateModified(ZonedDateTime dateModified) {
        this.dateModified = dateModified;
    }

    public BusinessCapability getParent() {
        return parent;
    }

    public BusinessCapability parent(BusinessCapability businessCapability) {
        this.parent = businessCapability;
        return this;
    }

    public void setParent(BusinessCapability businessCapability) {
        this.parent = businessCapability;
    }

    public Set<BusinessCapability> getDisplayNames() {
        return displayNames;
    }

    public BusinessCapability displayNames(Set<BusinessCapability> businessCapabilities) {
        this.displayNames = businessCapabilities;
        return this;
    }

    public BusinessCapability addDisplayName(BusinessCapability businessCapability) {
        this.displayNames.add(businessCapability);
        businessCapability.setParent(this);
        return this;
    }

    public BusinessCapability removeDisplayName(BusinessCapability businessCapability) {
        this.displayNames.remove(businessCapability);
        businessCapability.setParent(null);
        return this;
    }

    public void setDisplayNames(Set<BusinessCapability> businessCapabilities) {
        this.displayNames = businessCapabilities;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BusinessCapability businessCapability = (BusinessCapability) o;
        if (businessCapability.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), businessCapability.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "BusinessCapability{" +
            "id=" + getId() +
            ", commonName='" + getCommonName() + "'" +
            ", displayName='" + getDisplayName() + "'" +
            ", description='" + getDescription() + "'" +
            ", sortOrder='" + getSortOrder() + "'" +
            ", dateAdded='" + getDateAdded() + "'" +
            ", dateModified='" + getDateModified() + "'" +
            "}";
    }
}
