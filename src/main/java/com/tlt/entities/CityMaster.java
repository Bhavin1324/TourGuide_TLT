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
@Table(name = "city_master")
@NamedQueries({
    @NamedQuery(name = "CityMaster.findAll", query = "SELECT c FROM CityMaster c"),
    @NamedQuery(name = "CityMaster.findById", query = "SELECT c FROM CityMaster c WHERE c.id = :id"),
    @NamedQuery(name = "CityMaster.findByName", query = "SELECT c FROM CityMaster c WHERE c.name = :name")})
public class CityMaster implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 500)
    @Column(name = "Id")
    private String id;
    @Size(max = 500)
    @Column(name = "Name")
    private String name;
    @JoinColumn(name = "StateId", referencedColumnName = "Id")
    @ManyToOne
    private StateMaster stateId;
    @OneToMany(mappedBy = "cityId")
    private Collection<PlaceMaster> placeMasterCollection;
    @OneToMany(mappedBy = "cityId")
    private Collection<BusMaster> busMasterCollection;

    public CityMaster() {
    }

    public CityMaster(String id) {
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

    public StateMaster getStateId() {
        return stateId;
    }

    public void setStateId(StateMaster stateId) {
        this.stateId = stateId;
    }

    public Collection<PlaceMaster> getPlaceMasterCollection() {
        return placeMasterCollection;
    }

    public void setPlaceMasterCollection(Collection<PlaceMaster> placeMasterCollection) {
        this.placeMasterCollection = placeMasterCollection;
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
        if (!(object instanceof CityMaster)) {
            return false;
        }
        CityMaster other = (CityMaster) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.tlt.entities.CityMaster[ id=" + id + " ]";
    }
    
}
