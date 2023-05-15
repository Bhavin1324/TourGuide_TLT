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
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
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
@Table(name = "place_master")
@NamedQueries({
    @NamedQuery(name = "PlaceMaster.findAll", query = "SELECT p FROM PlaceMaster p"),
    @NamedQuery(name = "PlaceMaster.findById", query = "SELECT p FROM PlaceMaster p WHERE p.id = :id"),
    @NamedQuery(name = "PlaceMaster.getPlaceCount", query = "SELECT Count(p) FROM PlaceMaster p"),
    @NamedQuery(name = "PlaceMaster.findByOpeningTime", query = "SELECT p FROM PlaceMaster p WHERE p.openingTime = :openingTime"),
    @NamedQuery(name = "PlaceMaster.findByClosingTime", query = "SELECT p FROM PlaceMaster p WHERE p.closingTime = :closingTime")})
public class PlaceMaster implements Serializable {

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
    @Column(name = "address")
    private String address;
    @Lob
    @Size(max = 65535)
    @Column(name = "longitude")
    private String longitude;
    @Lob
    @Size(max = 65535)
    @Column(name = "latitude")
    private String latitude;
    @Column(name = "opening_time")
    @Temporal(TemporalType.TIME)
    private Date openingTime;
    @Column(name = "closing_time")
    @Temporal(TemporalType.TIME)
    private Date closingTime;
    @Lob
    @Size(max = 2147483647)
    @Column(name = "images")
    private String images;
    @Lob
    @Size(max = 16777215)
    @Column(name = "description")
    private String description;
    @JoinTable(name = "place_guide_mapping", joinColumns = {
        @JoinColumn(name = "place_id", referencedColumnName = "id")}, inverseJoinColumns = {
        @JoinColumn(name = "guide_id", referencedColumnName = "id")})
    @ManyToMany(fetch = FetchType.LAZY)
    private Collection<GuideMaster> guideMasterCollection;
    @JoinColumn(name = "city_id", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Cities cityId;
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private PlaceCategory categoryId;
    @OneToMany(mappedBy = "placeId", fetch = FetchType.LAZY)
    private Collection<AppointmentMaster> appointmentMasterCollection;

    public PlaceMaster() {
    }

    public PlaceMaster(String id) {
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public Date getOpeningTime() {
        return openingTime;
    }

    public void setOpeningTime(Date openingTime) {
        this.openingTime = openingTime;
    }

    public Date getClosingTime() {
        return closingTime;
    }

    public void setClosingTime(Date closingTime) {
        this.closingTime = closingTime;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Collection<GuideMaster> getGuideMasterCollection() {
        return guideMasterCollection;
    }

    public void setGuideMasterCollection(Collection<GuideMaster> guideMasterCollection) {
        this.guideMasterCollection = guideMasterCollection;
    }

    public Cities getCityId() {
        return cityId;
    }

    public void setCityId(Cities cityId) {
        this.cityId = cityId;
    }

    public PlaceCategory getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(PlaceCategory categoryId) {
        this.categoryId = categoryId;
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
        if (!(object instanceof PlaceMaster)) {
            return false;
        }
        PlaceMaster other = (PlaceMaster) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.tlt.entities.PlaceMaster[ id=" + id + " ]";
    }
    
}
