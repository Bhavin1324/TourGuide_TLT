/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tlt.entities;

import java.io.Serializable;
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author kunal
 */
@Entity
@Table(name = "bus_stop")
@NamedQueries({
    @NamedQuery(name = "BusStop.findAll", query = "SELECT b FROM BusStop b"),
    @NamedQuery(name = "BusStop.findById", query = "SELECT b FROM BusStop b WHERE b.id = :id")})
public class BusStop implements Serializable {

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
    @JoinColumn(name = "BusNumber", referencedColumnName = "BusNumber")
    @ManyToOne(fetch = FetchType.LAZY)
    private BusMaster busNumber;

    public BusStop() {
    }

    public BusStop(String id) {
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

    public BusMaster getBusNumber() {
        return busNumber;
    }

    public void setBusNumber(BusMaster busNumber) {
        this.busNumber = busNumber;
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
        if (!(object instanceof BusStop)) {
            return false;
        }
        BusStop other = (BusStop) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.tlt.entities.BusStop[ id=" + id + " ]";
    }
    
}
