/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tlt.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
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
@Table(name = "payment_master")
@NamedQueries({
    @NamedQuery(name = "PaymentMaster.findAll", query = "SELECT p FROM PaymentMaster p"),
    @NamedQuery(name = "PaymentMaster.findById", query = "SELECT p FROM PaymentMaster p WHERE p.id = :id"),
    @NamedQuery(name = "PaymentMaster.findByCreatedAt", query = "SELECT p FROM PaymentMaster p WHERE p.createdAt = :createdAt")})
public class PaymentMaster implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "id")
    private String id;
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    @Lob
    @Size(max = 16777215)
    @Column(name = "card_details")
    private String cardDetails;
    @Lob
    @Size(max = 65535)
    @Column(name = "payment_status")
    private String paymentStatus;
    @JoinColumn(name = "guide_id", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private GuideMaster guideId;
    @JoinColumn(name = "payment_method_id", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private PaymentMethod paymentMethodId;
    @JoinColumn(name = "subscription_id", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private SubscriptionMaster subscriptionId;
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private UserMaster userId;

    public PaymentMaster() {
    }

    public PaymentMaster(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getCardDetails() {
        return cardDetails;
    }

    public void setCardDetails(String cardDetails) {
        this.cardDetails = cardDetails;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public GuideMaster getGuideId() {
        return guideId;
    }

    public void setGuideId(GuideMaster guideId) {
        this.guideId = guideId;
    }

    public PaymentMethod getPaymentMethodId() {
        return paymentMethodId;
    }

    public void setPaymentMethodId(PaymentMethod paymentMethodId) {
        this.paymentMethodId = paymentMethodId;
    }

    public SubscriptionMaster getSubscriptionId() {
        return subscriptionId;
    }

    public void setSubscriptionId(SubscriptionMaster subscriptionId) {
        this.subscriptionId = subscriptionId;
    }

    public UserMaster getUserId() {
        return userId;
    }

    public void setUserId(UserMaster userId) {
        this.userId = userId;
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
        if (!(object instanceof PaymentMaster)) {
            return false;
        }
        PaymentMaster other = (PaymentMaster) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.tlt.entities.PaymentMaster[ id=" + id + " ]";
    }
    
}
