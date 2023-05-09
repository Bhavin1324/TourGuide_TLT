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
@Table(name = "appointment_master")
@NamedQueries({
    @NamedQuery(name = "AppointmentMaster.findAll", query = "SELECT a FROM AppointmentMaster a"),
    @NamedQuery(name = "AppointmentMaster.findById", query = "SELECT a FROM AppointmentMaster a WHERE a.id = :id"),
    @NamedQuery(name = "AppointmentMaster.findByStartDatetime", query = "SELECT a FROM AppointmentMaster a WHERE a.startDatetime = :startDatetime"),
    @NamedQuery(name = "AppointmentMaster.findByEndDatetime", query = "SELECT a FROM AppointmentMaster a WHERE a.endDatetime = :endDatetime")})
public class AppointmentMaster implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "id")
    private String id;
    @Column(name = "start_datetime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date startDatetime;
    @Column(name = "end_datetime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date endDatetime;
    @Lob
    @Size(max = 65535)
    @Column(name = "appointment_status")
    private String appointmentStatus;
    @JoinColumn(name = "guide_id", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private GuideMaster guideId;
    @JoinColumn(name = "place_id", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private PlaceMaster placeId;
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private UserMaster userId;

    public AppointmentMaster() {
    }

    public AppointmentMaster(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getStartDatetime() {
        return startDatetime;
    }

    public void setStartDatetime(Date startDatetime) {
        this.startDatetime = startDatetime;
    }

    public Date getEndDatetime() {
        return endDatetime;
    }

    public void setEndDatetime(Date endDatetime) {
        this.endDatetime = endDatetime;
    }

    public String getAppointmentStatus() {
        return appointmentStatus;
    }

    public void setAppointmentStatus(String appointmentStatus) {
        this.appointmentStatus = appointmentStatus;
    }

    public GuideMaster getGuideId() {
        return guideId;
    }

    public void setGuideId(GuideMaster guideId) {
        this.guideId = guideId;
    }

    public PlaceMaster getPlaceId() {
        return placeId;
    }

    public void setPlaceId(PlaceMaster placeId) {
        this.placeId = placeId;
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
        if (!(object instanceof AppointmentMaster)) {
            return false;
        }
        AppointmentMaster other = (AppointmentMaster) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.tlt.entities.AppointmentMaster[ id=" + id + " ]";
    }
    
}
