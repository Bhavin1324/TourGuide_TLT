/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tlt.entities;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
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
@Table(name = "user_master")
@NamedQueries({
    @NamedQuery(name = "UserMaster.findAll", query = "SELECT u FROM UserMaster u"),
    @NamedQuery(name = "UserMaster.findById", query = "SELECT u FROM UserMaster u WHERE u.id = :id"),
    @NamedQuery(name = "UserMaster.findByUsername", query = "SELECT u FROM UserMaster u WHERE u.username = :username"),
    @NamedQuery(name = "UserMaster.findByContact", query = "SELECT u FROM UserMaster u WHERE u.contact = :contact")})
public class UserMaster implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "id")
    private String id;
    @Size(max = 100)
    @Column(name = "username")
    private String username;
    @Lob
    @Size(max = 65535)
    @Column(name = "name")
    private String name;
    @Lob
    @Size(max = 65535)
    @Column(name = "password")
    private String password;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Lob
    @Size(max = 65535)
    @Column(name = "email")
    private String email;
    @Basic(optional = false)
    @NotNull
    @Column(name = "contact")
    private long contact;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 65535)
    @Column(name = "address")
    private String address;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 65535)
    @Column(name = "profile_image")
    private String profileImage;
    @JoinTable(name = "guide_user_mapping", joinColumns = {
        @JoinColumn(name = "user_id", referencedColumnName = "id")}, inverseJoinColumns = {
        @JoinColumn(name = "guide_id", referencedColumnName = "id")})
    @ManyToMany(fetch = FetchType.LAZY)
    private Collection<GuideMaster> guideMasterCollection;
    @JoinTable(name = "user_subscription_mapping", joinColumns = {
        @JoinColumn(name = "user_id", referencedColumnName = "id")}, inverseJoinColumns = {
        @JoinColumn(name = "subscription_id", referencedColumnName = "id")})
    @ManyToMany(fetch = FetchType.LAZY)
    private Collection<SubscriptionMaster> subscriptionMasterCollection;
    @OneToMany(mappedBy = "userId", fetch = FetchType.LAZY)
    private Collection<PaymentMaster> paymentMasterCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userMaster", fetch = FetchType.LAZY)
    private Collection<UserRole> userRoleCollection;
    @OneToMany(mappedBy = "userId", fetch = FetchType.LAZY)
    private Collection<AppointmentMaster> appointmentMasterCollection;

    public UserMaster() {
    }

    public UserMaster(String id) {
        this.id = id;
    }

    public UserMaster(String id, long contact, String address, String profileImage) {
        this.id = id;
        this.contact = contact;
        this.address = address;
        this.profileImage = profileImage;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getContact() {
        return contact;
    }

    public void setContact(long contact) {
        System.out.println("Contact: " + contact);
        this.contact = contact;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public Collection<GuideMaster> getGuideMasterCollection() {
        return guideMasterCollection;
    }

    public void setGuideMasterCollection(Collection<GuideMaster> guideMasterCollection) {
        this.guideMasterCollection = guideMasterCollection;
    }

    public Collection<SubscriptionMaster> getSubscriptionMasterCollection() {
        return subscriptionMasterCollection;
    }

    public void setSubscriptionMasterCollection(Collection<SubscriptionMaster> subscriptionMasterCollection) {
        this.subscriptionMasterCollection = subscriptionMasterCollection;
    }

    public Collection<PaymentMaster> getPaymentMasterCollection() {
        return paymentMasterCollection;
    }

    public void setPaymentMasterCollection(Collection<PaymentMaster> paymentMasterCollection) {
        this.paymentMasterCollection = paymentMasterCollection;
    }

    public Collection<UserRole> getUserRoleCollection() {
        return userRoleCollection;
    }

    public void setUserRoleCollection(Collection<UserRole> userRoleCollection) {
        this.userRoleCollection = userRoleCollection;
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
        if (!(object instanceof UserMaster)) {
            return false;
        }
        UserMaster other = (UserMaster) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.tlt.entities.UserMaster[ id=" + id + " ]";
    }
    
}
