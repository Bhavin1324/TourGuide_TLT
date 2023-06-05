/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tlt.entities;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Collection;
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Lenovo
 */
@Entity
@Table(name = "transporter_master")
@NamedQueries({
    @NamedQuery(name = "TransporterMaster.findAll", query = "SELECT t FROM TransporterMaster t"),
    @NamedQuery(name = "TransporterMaster.findById", query = "SELECT t FROM TransporterMaster t WHERE t.id = :id"),
    @NamedQuery(name = "TransporterMaster.randomOrder", query = "SELECT t FROM TransporterMaster t ORDER BY FUNCTION('RAND')"),
    @NamedQuery(name = "TransporterMaster.findByContactNo", query = "SELECT t FROM TransporterMaster t WHERE t.contactNo = :contactNo"),
    @NamedQuery(name = "TransporterMaster.findByAmount", query = "SELECT t FROM TransporterMaster t WHERE t.amount = :amount")})
public class TransporterMaster implements Serializable {

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
    @Column(name = "contact_no")
    private BigInteger contactNo;
    @Lob
    @Size(max = 65535)
    @Column(name = "plate_no")
    private String plateNo;
    @Column(name = "amount")
    private Integer amount;
    @JoinColumn(name = "city_id", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Cities cityId;
    @OneToMany(mappedBy = "transporterId", fetch = FetchType.LAZY)
    private Collection<TransportMaster> transportMasterCollection;

    public TransporterMaster() {
    }

    public TransporterMaster(String id) {
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

    public BigInteger getContactNo() {
        return contactNo;
    }

    public void setContactNo(BigInteger contactNo) {
        this.contactNo = contactNo;
    }

    public String getPlateNo() {
        return plateNo;
    }

    public void setPlateNo(String plateNo) {
        this.plateNo = plateNo;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Cities getCityId() {
        return cityId;
    }

    public void setCityId(Cities cityId) {
        this.cityId = cityId;
    }

    public Collection<TransportMaster> getTransportMasterCollection() {
        return transportMasterCollection;
    }

    public void setTransportMasterCollection(Collection<TransportMaster> transportMasterCollection) {
        this.transportMasterCollection = transportMasterCollection;
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
        if (!(object instanceof TransporterMaster)) {
            return false;
        }
        TransporterMaster other = (TransporterMaster) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.tlt.entities.TransporterMaster[ id=" + id + " ]";
    }
    
}
