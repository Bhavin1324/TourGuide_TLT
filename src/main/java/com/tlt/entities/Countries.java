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
import javax.persistence.Lob;
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
@Table(name = "countries")
@NamedQueries({
    @NamedQuery(name = "Countries.findAll", query = "SELECT c FROM Countries c"),
    @NamedQuery(name = "Countries.findById", query = "SELECT c FROM Countries c WHERE c.id = :id"),
    @NamedQuery(name = "Countries.findByName", query = "SELECT c FROM Countries c WHERE c.name = :name"),
    @NamedQuery(name = "Countries.findByIso3", query = "SELECT c FROM Countries c WHERE c.iso3 = :iso3"),
    @NamedQuery(name = "Countries.findByNumericCode", query = "SELECT c FROM Countries c WHERE c.numericCode = :numericCode"),
    @NamedQuery(name = "Countries.findByIso2", query = "SELECT c FROM Countries c WHERE c.iso2 = :iso2"),
    @NamedQuery(name = "Countries.findByPhonecode", query = "SELECT c FROM Countries c WHERE c.phonecode = :phonecode"),
    @NamedQuery(name = "Countries.findByCapital", query = "SELECT c FROM Countries c WHERE c.capital = :capital"),
    @NamedQuery(name = "Countries.findByCurrency", query = "SELECT c FROM Countries c WHERE c.currency = :currency"),
    @NamedQuery(name = "Countries.findByCurrencyName", query = "SELECT c FROM Countries c WHERE c.currencyName = :currencyName"),
    @NamedQuery(name = "Countries.findByCurrencySymbol", query = "SELECT c FROM Countries c WHERE c.currencySymbol = :currencySymbol"),
    @NamedQuery(name = "Countries.findByTld", query = "SELECT c FROM Countries c WHERE c.tld = :tld"),
    @NamedQuery(name = "Countries.findByNative1", query = "SELECT c FROM Countries c WHERE c.native1 = :native1"),
    @NamedQuery(name = "Countries.findByRegion", query = "SELECT c FROM Countries c WHERE c.region = :region"),
    @NamedQuery(name = "Countries.findBySubregion", query = "SELECT c FROM Countries c WHERE c.subregion = :subregion"),
    @NamedQuery(name = "Countries.findByLatitude", query = "SELECT c FROM Countries c WHERE c.latitude = :latitude"),
    @NamedQuery(name = "Countries.findByLongitude", query = "SELECT c FROM Countries c WHERE c.longitude = :longitude"),
    @NamedQuery(name = "Countries.findByEmoji", query = "SELECT c FROM Countries c WHERE c.emoji = :emoji"),
    @NamedQuery(name = "Countries.findByEmojiU", query = "SELECT c FROM Countries c WHERE c.emojiU = :emojiU"),
    @NamedQuery(name = "Countries.findByCreatedAt", query = "SELECT c FROM Countries c WHERE c.createdAt = :createdAt"),
    @NamedQuery(name = "Countries.findByUpdatedAt", query = "SELECT c FROM Countries c WHERE c.updatedAt = :updatedAt"),
    @NamedQuery(name = "Countries.findByFlag", query = "SELECT c FROM Countries c WHERE c.flag = :flag"),
    @NamedQuery(name = "Countries.findByWikiDataId", query = "SELECT c FROM Countries c WHERE c.wikiDataId = :wikiDataId")})
public class Countries implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "name")
    private String name;
    @Size(max = 3)
    @Column(name = "iso3")
    private String iso3;
    @Size(max = 3)
    @Column(name = "numeric_code")
    private String numericCode;
    @Size(max = 2)
    @Column(name = "iso2")
    private String iso2;
    @Size(max = 255)
    @Column(name = "phonecode")
    private String phonecode;
    @Size(max = 255)
    @Column(name = "capital")
    private String capital;
    @Size(max = 255)
    @Column(name = "currency")
    private String currency;
    @Size(max = 255)
    @Column(name = "currency_name")
    private String currencyName;
    @Size(max = 255)
    @Column(name = "currency_symbol")
    private String currencySymbol;
    @Size(max = 255)
    @Column(name = "tld")
    private String tld;
    @Size(max = 255)
    @Column(name = "native")
    private String native1;
    @Size(max = 255)
    @Column(name = "region")
    private String region;
    @Size(max = 255)
    @Column(name = "subregion")
    private String subregion;
    @Lob
    @Size(max = 65535)
    @Column(name = "timezones")
    private String timezones;
    @Lob
    @Size(max = 65535)
    @Column(name = "translations")
    private String translations;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "latitude")
    private BigDecimal latitude;
    @Column(name = "longitude")
    private BigDecimal longitude;
    @Size(max = 191)
    @Column(name = "emoji")
    private String emoji;
    @Size(max = 191)
    @Column(name = "emojiU")
    private String emojiU;
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
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "countryId", fetch = FetchType.LAZY)
    private Collection<Cities> citiesCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "countryId", fetch = FetchType.LAZY)
    private Collection<States> statesCollection;

    public Countries() {
    }

    public Countries(Integer id) {
        this.id = id;
    }

    public Countries(Integer id, String name, Date updatedAt, boolean flag) {
        this.id = id;
        this.name = name;
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

    public String getIso3() {
        return iso3;
    }

    public void setIso3(String iso3) {
        this.iso3 = iso3;
    }

    public String getNumericCode() {
        return numericCode;
    }

    public void setNumericCode(String numericCode) {
        this.numericCode = numericCode;
    }

    public String getIso2() {
        return iso2;
    }

    public void setIso2(String iso2) {
        this.iso2 = iso2;
    }

    public String getPhonecode() {
        return phonecode;
    }

    public void setPhonecode(String phonecode) {
        this.phonecode = phonecode;
    }

    public String getCapital() {
        return capital;
    }

    public void setCapital(String capital) {
        this.capital = capital;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getCurrencyName() {
        return currencyName;
    }

    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }

    public String getCurrencySymbol() {
        return currencySymbol;
    }

    public void setCurrencySymbol(String currencySymbol) {
        this.currencySymbol = currencySymbol;
    }

    public String getTld() {
        return tld;
    }

    public void setTld(String tld) {
        this.tld = tld;
    }

    public String getNative1() {
        return native1;
    }

    public void setNative1(String native1) {
        this.native1 = native1;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getSubregion() {
        return subregion;
    }

    public void setSubregion(String subregion) {
        this.subregion = subregion;
    }

    public String getTimezones() {
        return timezones;
    }

    public void setTimezones(String timezones) {
        this.timezones = timezones;
    }

    public String getTranslations() {
        return translations;
    }

    public void setTranslations(String translations) {
        this.translations = translations;
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

    public String getEmoji() {
        return emoji;
    }

    public void setEmoji(String emoji) {
        this.emoji = emoji;
    }

    public String getEmojiU() {
        return emojiU;
    }

    public void setEmojiU(String emojiU) {
        this.emojiU = emojiU;
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

    public Collection<States> getStatesCollection() {
        return statesCollection;
    }

    public void setStatesCollection(Collection<States> statesCollection) {
        this.statesCollection = statesCollection;
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
        if (!(object instanceof Countries)) {
            return false;
        }
        Countries other = (Countries) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.tlt.entities.Countries[ id=" + id + " ]";
    }
    
}
