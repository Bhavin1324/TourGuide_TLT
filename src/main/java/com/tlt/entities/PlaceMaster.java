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
 * @author kunal
 */
@Entity
@Table(name = "place_master")
@NamedQueries({
    @NamedQuery(name = "PlaceMaster.findAll", query = "SELECT p FROM PlaceMaster p"),
    @NamedQuery(name = "PlaceMaster.findById", query = "SELECT p FROM PlaceMaster p WHERE p.id = :id"),
    @NamedQuery(name = "PlaceMaster.findByOpeningTime", query = "SELECT p FROM PlaceMaster p WHERE p.openingTime = :openingTime"),
    @NamedQuery(name = "PlaceMaster.findByClosingTime", query = "SELECT p FROM PlaceMaster p WHERE p.closingTime = :closingTime")})
public class PlaceMaster implements Serializable {

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
    @Column(name = "Address")
    private String address;
    @Lob
    @Size(max = 65535)
    @Column(name = "Longitude")
    private String longitude;
    @Lob
    @Size(max = 65535)
    @Column(name = "Latitude")
    private String latitude;
    @Column(name = "OpeningTime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date openingTime;
    @Column(name = "ClosingTime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date closingTime;
    @Lob
    @Size(max = 16777215)
    @Column(name = "Description")
    private String description;
    @JoinColumn(name = "CityId", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Cities cityId;
    @JoinColumn(name = "CategoryId", referencedColumnName = "Id")
    @ManyToOne(fetch = FetchType.LAZY)
    private PlaceCategory categoryId;
    @OneToMany(mappedBy = "placeId", fetch = FetchType.LAZY)
    private Collection<BusMaster> busMasterCollection;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public Collection<BusMaster> getBusMasterCollection() {
        return busMasterCollection;
    }

    public void setBusMasterCollection(Collection<BusMaster> busMasterCollection) {
        this.busMasterCollection = busMasterCollection;
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
