/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tlt.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author kunal
 */
@Entity
@Table(name = "guide_master")
@NamedQueries({
    @NamedQuery(name = "GuideMaster.findAll", query = "SELECT g FROM GuideMaster g"),
    @NamedQuery(name = "GuideMaster.findById", query = "SELECT g FROM GuideMaster g WHERE g.id = :id"),
    @NamedQuery(name = "GuideMaster.findByAmount", query = "SELECT g FROM GuideMaster g WHERE g.amount = :amount"),
    @NamedQuery(name = "GuideMaster.findByPlaceId", query = "SELECT g FROM GuideMaster g WHERE g.placeId = :placeId"),
    @NamedQuery(name = "GuideMaster.findByUserId", query = "SELECT g FROM GuideMaster g WHERE g.userId = :userId"),
    @NamedQuery(name = "GuideMaster.findByIsAppointed", query = "SELECT g FROM GuideMaster g WHERE g.isAppointed = :isAppointed"),
    @NamedQuery(name = "GuideMaster.findByAppiontmentStartedAt", query = "SELECT g FROM GuideMaster g WHERE g.appiontmentStartedAt = :appiontmentStartedAt"),
    @NamedQuery(name = "GuideMaster.findByAppiontmentEndedAt", query = "SELECT g FROM GuideMaster g WHERE g.appiontmentEndedAt = :appiontmentEndedAt"),
    @NamedQuery(name = "GuideMaster.findByPaymentMethodId", query = "SELECT g FROM GuideMaster g WHERE g.paymentMethodId = :paymentMethodId")})
public class GuideMaster implements Serializable {

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
    @Lob
    @Size(max = 65535)
    @Column(name = "Gender")
    private String gender;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Lob
    @Size(max = 65535)
    @Column(name = "Email")
    private String email;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "Amount")
    private BigDecimal amount;
    @Lob
    @Size(max = 65535)
    @Column(name = "ContactNo")
    private String contactNo;
    @Size(max = 500)
    @Column(name = "PlaceId")
    private String placeId;
    @Size(max = 500)
    @Column(name = "UserId")
    private String userId;
    @Lob
    @Size(max = 65535)
    @Column(name = "VisitStatus")
    private String visitStatus;
    @Column(name = "IsAppointed")
    private Boolean isAppointed;
    @Column(name = "AppiontmentStartedAt")
    @Temporal(TemporalType.TIMESTAMP)
    private Date appiontmentStartedAt;
    @Column(name = "AppiontmentEndedAt")
    @Temporal(TemporalType.TIMESTAMP)
    private Date appiontmentEndedAt;
    @Size(max = 500)
    @Column(name = "PaymentMethodId")
    private String paymentMethodId;
    @OneToMany(mappedBy = "guideId", fetch = FetchType.LAZY)
    private Collection<PaymentMaster> paymentMasterCollection;

    public GuideMaster() {
    }

    public GuideMaster(String id) {
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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getVisitStatus() {
        return visitStatus;
    }

    public void setVisitStatus(String visitStatus) {
        this.visitStatus = visitStatus;
    }

    public Boolean getIsAppointed() {
        return isAppointed;
    }

    public void setIsAppointed(Boolean isAppointed) {
        this.isAppointed = isAppointed;
    }

    public Date getAppiontmentStartedAt() {
        return appiontmentStartedAt;
    }

    public void setAppiontmentStartedAt(Date appiontmentStartedAt) {
        this.appiontmentStartedAt = appiontmentStartedAt;
    }

    public Date getAppiontmentEndedAt() {
        return appiontmentEndedAt;
    }

    public void setAppiontmentEndedAt(Date appiontmentEndedAt) {
        this.appiontmentEndedAt = appiontmentEndedAt;
    }

    public String getPaymentMethodId() {
        return paymentMethodId;
    }

    public void setPaymentMethodId(String paymentMethodId) {
        this.paymentMethodId = paymentMethodId;
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
        if (!(object instanceof GuideMaster)) {
            return false;
        }
        GuideMaster other = (GuideMaster) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.tlt.entities.GuideMaster[ id=" + id + " ]";
    }
    
}
