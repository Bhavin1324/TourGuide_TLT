/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tlt.entities;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author kunal
 */
@Entity
@Table(name = "bus_master")
@NamedQueries({
    @NamedQuery(name = "BusMaster.findAll", query = "SELECT b FROM BusMaster b"),
    @NamedQuery(name = "BusMaster.findById", query = "SELECT b FROM BusMaster b WHERE b.id = :id"),
    @NamedQuery(name = "BusMaster.findByBusNumber", query = "SELECT b FROM BusMaster b WHERE b.busNumber = :busNumber")})
public class BusMaster implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 500)
    @Column(name = "Id")
    private String id;
    @Lob
    @Size(max = 65535)
    @Column(name = "BusType")
    private String busType;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 500)
    @Column(name = "BusNumber")
    private String busNumber;
    @JoinColumn(name = "CityId", referencedColumnName = "Id")
    @ManyToOne
    private CityMaster cityId;
    @JoinColumn(name = "PlaceId", referencedColumnName = "Id")
    @ManyToOne
    private PlaceMaster placeId;
    @OneToMany(mappedBy = "busNumber")
    private Collection<BusStop> busStopCollection;

    public BusMaster() {
    }

    public BusMaster(String id) {
        this.id = id;
    }

    public BusMaster(String id, String busNumber) {
        this.id = id;
        this.busNumber = busNumber;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBusType() {
        return busType;
    }

    public void setBusType(String busType) {
        this.busType = busType;
    }

    public String getBusNumber() {
        return busNumber;
    }

    public void setBusNumber(String busNumber) {
        this.busNumber = busNumber;
    }

    public CityMaster getCityId() {
        return cityId;
    }

    public void setCityId(CityMaster cityId) {
        this.cityId = cityId;
    }

    public PlaceMaster getPlaceId() {
        return placeId;
    }

    public void setPlaceId(PlaceMaster placeId) {
        this.placeId = placeId;
    }

    public Collection<BusStop> getBusStopCollection() {
        return busStopCollection;
    }

    public void setBusStopCollection(Collection<BusStop> busStopCollection) {
        this.busStopCollection = busStopCollection;
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
        if (!(object instanceof BusMaster)) {
            return false;
        }
        BusMaster other = (BusMaster) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.tlt.entities.BusMaster[ id=" + id + " ]";
    }
    
}
