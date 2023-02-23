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
@Table(name = "state_master")
@NamedQueries({
    @NamedQuery(name = "StateMaster.findAll", query = "SELECT s FROM StateMaster s"),
    @NamedQuery(name = "StateMaster.findById", query = "SELECT s FROM StateMaster s WHERE s.id = :id"),
    @NamedQuery(name = "StateMaster.findByName", query = "SELECT s FROM StateMaster s WHERE s.name = :name")})
public class StateMaster implements Serializable {

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
    @OneToMany(mappedBy = "stateId")
    private Collection<CityMaster> cityMasterCollection;
    @JoinColumn(name = "CountryId", referencedColumnName = "Id")
    @ManyToOne
    private CountryMaster countryId;

    public StateMaster() {
    }

    public StateMaster(String id) {
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

    public Collection<CityMaster> getCityMasterCollection() {
        return cityMasterCollection;
    }

    public void setCityMasterCollection(Collection<CityMaster> cityMasterCollection) {
        this.cityMasterCollection = cityMasterCollection;
    }

    public CountryMaster getCountryId() {
        return countryId;
    }

    public void setCountryId(CountryMaster countryId) {
        this.countryId = countryId;
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
        if (!(object instanceof StateMaster)) {
            return false;
        }
        StateMaster other = (StateMaster) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.tlt.entities.StateMaster[ id=" + id + " ]";
    }
    
}
