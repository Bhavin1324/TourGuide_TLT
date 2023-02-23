/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tlt.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
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
@Table(name = "states")
@NamedQueries({
    @NamedQuery(name = "States.findAll", query = "SELECT s FROM States s"),
    @NamedQuery(name = "States.findById", query = "SELECT s FROM States s WHERE s.id = :id"),
    @NamedQuery(name = "States.findByName", query = "SELECT s FROM States s WHERE s.name = :name"),
    @NamedQuery(name = "States.findByCountryCode", query = "SELECT s FROM States s WHERE s.countryCode = :countryCode"),
    @NamedQuery(name = "States.findByFipsCode", query = "SELECT s FROM States s WHERE s.fipsCode = :fipsCode"),
    @NamedQuery(name = "States.findByIso2", query = "SELECT s FROM States s WHERE s.iso2 = :iso2"),
    @NamedQuery(name = "States.findByType", query = "SELECT s FROM States s WHERE s.type = :type"),
    @NamedQuery(name = "States.findByLatitude", query = "SELECT s FROM States s WHERE s.latitude = :latitude"),
    @NamedQuery(name = "States.findByLongitude", query = "SELECT s FROM States s WHERE s.longitude = :longitude"),
    @NamedQuery(name = "States.findByCreatedAt", query = "SELECT s FROM States s WHERE s.createdAt = :createdAt"),
    @NamedQuery(name = "States.findByUpdatedAt", query = "SELECT s FROM States s WHERE s.updatedAt = :updatedAt"),
    @NamedQuery(name = "States.findByFlag", query = "SELECT s FROM States s WHERE s.flag = :flag"),
    @NamedQuery(name = "States.findByWikiDataId", query = "SELECT s FROM States s WHERE s.wikiDataId = :wikiDataId")})
public class States implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "name")
    private String name;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2)
    @Column(name = "country_code")
    private String countryCode;
    @Size(max = 255)
    @Column(name = "fips_code")
    private String fipsCode;
    @Size(max = 255)
    @Column(name = "iso2")
    private String iso2;
    @Size(max = 191)
    @Column(name = "type")
    private String type;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "latitude")
    private BigDecimal latitude;
    @Column(name = "longitude")
    private BigDecimal longitude;
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    @Basic(optional = false)
    @NotNull
    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;
    @Basic(optional = false)
    @NotNull
    @Column(name = "flag")
    private boolean flag;
    @Size(max = 255)
    @Column(name = "wikiDataId")
    private String wikiDataId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "stateId", fetch = FetchType.LAZY)
    private Collection<Cities> citiesCollection;
    @JoinColumn(name = "country_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Countries countryId;

    public States() {
    }

    public States(Integer id) {
        this.id = id;
    }

    public States(Integer id, String name, String countryCode, Date updatedAt, boolean flag) {
        this.id = id;
        this.name = name;
        this.countryCode = countryCode;
        this.updatedAt = updatedAt;
        this.flag = flag;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getFipsCode() {
        return fipsCode;
    }

    public void setFipsCode(String fipsCode) {
        this.fipsCode = fipsCode;
    }

    public String getIso2() {
        return iso2;
    }

    public void setIso2(String iso2) {
        this.iso2 = iso2;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public boolean getFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public String getWikiDataId() {
        return wikiDataId;
    }

    public void setWikiDataId(String wikiDataId) {
        this.wikiDataId = wikiDataId;
    }

    public Collection<Cities> getCitiesCollection() {
        return citiesCollection;
    }

    public void setCitiesCollection(Collection<Cities> citiesCollection) {
        this.citiesCollection = citiesCollection;
    }

    public Countries getCountryId() {
        return countryId;
    }

    public void setCountryId(Countries countryId) {
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
        if (!(object instanceof States)) {
            return false;
        }
        States other = (States) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.tlt.entities.States[ id=" + id + " ]";
    }
    
}
