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
@Table(name = "country_master")
@NamedQueries({
    @NamedQuery(name = "CountryMaster.findAll", query = "SELECT c FROM CountryMaster c"),
    @NamedQuery(name = "CountryMaster.findById", query = "SELECT c FROM CountryMaster c WHERE c.id = :id"),
    @NamedQuery(name = "CountryMaster.findByName", query = "SELECT c FROM CountryMaster c WHERE c.name = :name")})
public class CountryMaster implements Serializable {

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
    @OneToMany(mappedBy = "countryId")
    private Collection<StateMaster> stateMasterCollection;

    public CountryMaster() {
    }

    public CountryMaster(String id) {
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

    public Collection<StateMaster> getStateMasterCollection() {
        return stateMasterCollection;
    }

    public void setStateMasterCollection(Collection<StateMaster> stateMasterCollection) {
        this.stateMasterCollection = stateMasterCollection;
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
        if (!(object instanceof CountryMaster)) {
            return false;
        }
        CountryMaster other = (CountryMaster) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.tlt.entities.CountryMaster[ id=" + id + " ]";
    }
    
}
