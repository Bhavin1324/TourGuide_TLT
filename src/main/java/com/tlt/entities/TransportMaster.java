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
import javax.persistence.Lob;
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
@Table(name = "transport_master")
@NamedQueries({
    @NamedQuery(name = "TransportMaster.findAll", query = "SELECT t FROM TransportMaster t"),
    @NamedQuery(name = "TransportMaster.findById", query = "SELECT t FROM TransportMaster t WHERE t.id = :id"),
    @NamedQuery(name = "TransportMaster.findByPickupDateTime", query = "SELECT t FROM TransportMaster t WHERE t.pickupDateTime = :pickupDateTime")})
public class TransportMaster implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "id")
    private String id;
    @Column(name = "pickup_date_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date pickupDateTime;
    @Lob
    @Size(max = 65535)
    @Column(name = "pickup_location")
    private String pickupLocation;
    @JoinColumn(name = "transporter_id", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private TransporterMaster transporterId;
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private UserMaster userId;
    @OneToMany(mappedBy = "transportId", fetch = FetchType.LAZY)
    private Collection<PaymentMaster> paymentMasterCollection;
    @OneToMany(mappedBy = "transportId", fetch = FetchType.LAZY)
    private Collection<AppointmentMaster> appointmentMasterCollection;

    public TransportMaster() {
    }

    public TransportMaster(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getPickupDateTime() {
        return pickupDateTime;
    }

    public void setPickupDateTime(Date pickupDateTime) {
        this.pickupDateTime = pickupDateTime;
    }

    public String getPickupLocation() {
        return pickupLocation;
    }

    public void setPickupLocation(String pickupLocation) {
        this.pickupLocation = pickupLocation;
    }

    public TransporterMaster getTransporterId() {
        return transporterId;
    }

    public void setTransporterId(TransporterMaster transporterId) {
        this.transporterId = transporterId;
    }

    public UserMaster getUserId() {
        return userId;
    }

    public void setUserId(UserMaster userId) {
        this.userId = userId;
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
        if (!(object instanceof TransportMaster)) {
            return false;
        }
        TransportMaster other = (TransportMaster) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.tlt.entities.TransportMaster[ id=" + id + " ]";
    }
    
}
