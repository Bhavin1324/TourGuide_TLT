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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "event_master")
@NamedQueries({
    @NamedQuery(name = "EventMaster.findAll", query = "SELECT e FROM EventMaster e"),
    @NamedQuery(name = "EventMaster.findById", query = "SELECT e FROM EventMaster e WHERE e.id = :id"),
    @NamedQuery(name = "EventMaster.findByStartTime", query = "SELECT e FROM EventMaster e WHERE e.startTime = :startTime"),
    @NamedQuery(name = "EventMaster.findByEndTime", query = "SELECT e FROM EventMaster e WHERE e.endTime = :endTime"),
    @NamedQuery(name = "EventMaster.findByNumberOfPeople", query = "SELECT e FROM EventMaster e WHERE e.numberOfPeople = :numberOfPeople")})
public class EventMaster implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "id")
    private String id;
    @Column(name = "start_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date startTime;
    @Column(name = "end_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date endTime;
    @Lob
    @Size(max = 65535)
    @Column(name = "event_status")
    private String eventStatus;
    @Column(name = "number_of_people")
    private Integer numberOfPeople;
    @JoinTable(name = "event_user_mapping", joinColumns = {
        @JoinColumn(name = "event_id", referencedColumnName = "id")}, inverseJoinColumns = {
        @JoinColumn(name = "user_id", referencedColumnName = "id")})
    @ManyToMany(fetch = FetchType.LAZY)
    private Collection<UserMaster> userMasterCollection;
    @JoinColumn(name = "guide_id", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private GuideMaster guideId;
    @JoinColumn(name = "place_id", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private PlaceMaster placeId;

    public EventMaster() {
    }

    public EventMaster(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getEventStatus() {
        return eventStatus;
    }

    public void setEventStatus(String eventStatus) {
        this.eventStatus = eventStatus;
    }

    public Integer getNumberOfPeople() {
        return numberOfPeople;
    }

    public void setNumberOfPeople(Integer numberOfPeople) {
        this.numberOfPeople = numberOfPeople;
    }

    public Collection<UserMaster> getUserMasterCollection() {
        return userMasterCollection;
    }

    public void setUserMasterCollection(Collection<UserMaster> userMasterCollection) {
        this.userMasterCollection = userMasterCollection;
    }

    public GuideMaster getGuideId() {
        return guideId;
    }

    public void setGuideId(GuideMaster guideId) {
        this.guideId = guideId;
    }

    public PlaceMaster getPlaceId() {
        return placeId;
    }

    public void setPlaceId(PlaceMaster placeId) {
        this.placeId = placeId;
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
        if (!(object instanceof EventMaster)) {
            return false;
        }
        EventMaster other = (EventMaster) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.tlt.entities.EventMaster[ id=" + id + " ]";
    }
    
}
