/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tlt.entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Lenovo
 */
@Entity
@Table(name = "subscription_master")
@NamedQueries({
    @NamedQuery(name = "SubscriptionMaster.findAll", query = "SELECT s FROM SubscriptionMaster s"),
    @NamedQuery(name = "SubscriptionMaster.findById", query = "SELECT s FROM SubscriptionMaster s WHERE s.id = :id"),
    @NamedQuery(name = "SubscriptionMaster.findByStartDate", query = "SELECT s FROM SubscriptionMaster s WHERE s.startDate = :startDate"),
    @NamedQuery(name = "SubscriptionMaster.findByEndDate", query = "SELECT s FROM SubscriptionMaster s WHERE s.endDate = :endDate")})
public class SubscriptionMaster implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 500)
    @Column(name = "Id")
    private String id;
    @Column(name = "StartDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date startDate;
    @Column(name = "EndDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date endDate;
    @JoinColumn(name = "PaymentMethodId", referencedColumnName = "Id")
    @ManyToOne(fetch = FetchType.LAZY)
    private PaymentMethod paymentMethodId;
    @JoinColumn(name = "SubscriptionModelId", referencedColumnName = "Id")
    @ManyToOne(fetch = FetchType.LAZY)
    private SubscriptionModel subscriptionModelId;
    @OneToMany(mappedBy = "subscriptionId", fetch = FetchType.LAZY)
    private Collection<PaymentMaster> paymentMasterCollection;

    public SubscriptionMaster() {
    }

    public SubscriptionMaster(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public PaymentMethod getPaymentMethodId() {
        return paymentMethodId;
    }

    public void setPaymentMethodId(PaymentMethod paymentMethodId) {
        this.paymentMethodId = paymentMethodId;
    }

    public SubscriptionModel getSubscriptionModelId() {
        return subscriptionModelId;
    }

    public void setSubscriptionModelId(SubscriptionModel subscriptionModelId) {
        this.subscriptionModelId = subscriptionModelId;
    }

    public Collection<PaymentMaster> getPaymentMasterCollection() {
        return paymentMasterCollection;
    }

    public void setPaymentMasterCollection(Collection<PaymentMaster> paymentMasterCollection) {
        this.paymentMasterCollection = paymentMasterCollection;
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
        if (!(object instanceof SubscriptionMaster)) {
            return false;
        }
        SubscriptionMaster other = (SubscriptionMaster) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.tlt.entities.SubscriptionMaster[ id=" + id + " ]";
    }
    
}
