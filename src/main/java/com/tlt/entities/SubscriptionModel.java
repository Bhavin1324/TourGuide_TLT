/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tlt.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Lenovo
 */
@Entity
@Table(name = "subscription_model")
@NamedQueries({
    @NamedQuery(name = "SubscriptionModel.findAll", query = "SELECT s FROM SubscriptionModel s"),
    @NamedQuery(name = "SubscriptionModel.findById", query = "SELECT s FROM SubscriptionModel s WHERE s.id = :id"),
    @NamedQuery(name = "SubscriptionModel.findByDurationInMonth", query = "SELECT s FROM SubscriptionModel s WHERE s.durationInMonth = :durationInMonth"),
    @NamedQuery(name = "SubscriptionModel.findByCost", query = "SELECT s FROM SubscriptionModel s WHERE s.cost = :cost")})
public class SubscriptionModel implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 500)
    @Column(name = "Id")
    private String id;
    @Lob
    @Size(max = 65535)
    @Column(name = "Name")
    private String name;
    @Column(name = "DurationInMonth")
    private Integer durationInMonth;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "Cost")
    private BigDecimal cost;
    @Lob
    @Size(max = 65535)
    @Column(name = "Description")
    private String description;
    @OneToMany(mappedBy = "subscriptionModelId", fetch = FetchType.LAZY)
    private Collection<SubscriptionMaster> subscriptionMasterCollection;

    public SubscriptionModel() {
    }

    public SubscriptionModel(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getDurationInMonth() {
        return durationInMonth;
    }

    public void setDurationInMonth(Integer durationInMonth) {
        this.durationInMonth = durationInMonth;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Collection<SubscriptionMaster> getSubscriptionMasterCollection() {
        return subscriptionMasterCollection;
    }

    public void setSubscriptionMasterCollection(Collection<SubscriptionMaster> subscriptionMasterCollection) {
        this.subscriptionMasterCollection = subscriptionMasterCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SubscriptionModel)) {
            return false;
        }
        SubscriptionModel other = (SubscriptionModel) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.tlt.entities.SubscriptionModel[ id=" + id + " ]";
    }
    
}
