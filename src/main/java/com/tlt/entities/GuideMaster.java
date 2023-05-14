/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tlt.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.eclipse.persistence.annotations.CascadeOnDelete;

/**
 *
 * @author Lenovo
 */
@Entity
@Table(name = "guide_master")
@NamedQueries({
    @NamedQuery(name = "GuideMaster.findAll", query = "SELECT g FROM GuideMaster g"),
    @NamedQuery(name = "GuideMaster.findById", query = "SELECT g FROM GuideMaster g WHERE g.id = :id"),
    @NamedQuery(name = "GuideMaster.findByAmount", query = "SELECT g FROM GuideMaster g WHERE g.amount = :amount"),
    @NamedQuery(name = "GuideMaster.findByPhoneNumber", query = "SELECT g FROM GuideMaster g WHERE g.phoneNumber = :phoneNumber"),
    @NamedQuery(name = "GuideMaster.findByIsAppointed", query = "SELECT g FROM GuideMaster g WHERE g.isAppointed = :isAppointed")})
public class GuideMaster implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "id")
    private String id;
    @Lob
    @Size(max = 65535)
    @Column(name = "name")
    private String name;
    @Lob
    @Size(max = 65535)
    @Column(name = "gender")
    private String gender;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Lob
    @Size(max = 65535)
    @Column(name = "email")
    private String email;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "amount")
    private BigDecimal amount;
    @Column(name = "phone_number")
    private BigInteger phoneNumber;
    @Column(name = "is_appointed")
    private Boolean isAppointed;
    @CascadeOnDelete
    @ManyToMany(mappedBy = "guideMasterCollection", fetch = FetchType.LAZY)
    private Collection<UserMaster> userMasterCollection;
    @ManyToMany(mappedBy = "guideMasterCollection", fetch = FetchType.LAZY)
    private Collection<PlaceMaster> placeMasterCollection;
    @OneToMany(mappedBy = "guideId", fetch = FetchType.LAZY)
    private Collection<PaymentMaster> paymentMasterCollection;
    @OneToMany(mappedBy = "guideId", fetch = FetchType.LAZY)
    private Collection<AppointmentMaster> appointmentMasterCollection;

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

    public BigInteger getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(BigInteger phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Boolean getIsAppointed() {
        return isAppointed;
    }

    public void setIsAppointed(Boolean isAppointed) {
        this.isAppointed = isAppointed;
    }

    public Collection<UserMaster> getUserMasterCollection() {
        return userMasterCollection;
    }

    public void setUserMasterCollection(Collection<UserMaster> userMasterCollection) {
        this.userMasterCollection = userMasterCollection;
    }

    public Collection<PlaceMaster> getPlaceMasterCollection() {
        return placeMasterCollection;
    }

    public void setPlaceMasterCollection(Collection<PlaceMaster> placeMasterCollection) {
        this.placeMasterCollection = placeMasterCollection;
    }

    public Collection<PaymentMaster> getPaymentMasterCollection() {
        return paymentMasterCollection;
    }

    public void setPaymentMasterCollection(Collection<PaymentMaster> paymentMasterCollection) {
        this.paymentMasterCollection = paymentMasterCollection;
    }

    public Collection<AppointmentMaster> getAppointmentMasterCollection() {
        return appointmentMasterCollection;
    }

    public void setAppointmentMasterCollection(Collection<AppointmentMaster> appointmentMasterCollection) {
        this.appointmentMasterCollection = appointmentMasterCollection;
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
