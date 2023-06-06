/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package com.tlt.cdis;

import com.tlt.ejb.AdminLocal;
import com.tlt.entities.Cities;
import com.tlt.entities.EventMaster;
import com.tlt.entities.UserMaster;
import com.tlt.record.KeepRecord;
import com.tlt.utils.GeoLocationUtil;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import org.primefaces.PrimeFaces;

/**
 *
 * @author kunal
 */
@Named(value = "joinGroupBean")
@SessionScoped
public class JoinGroupBean implements Serializable {

    @EJB
    AdminLocal adminLogic;
    Collection<Cities> cities;
    Collection<EventMaster> events;
    EventMaster selectedEvent;
    String cityText;
    TouristBean tb;
    Integer people;

    public JoinGroupBean() {
        cities = new ArrayList<>();
        events = new ArrayList<>();
        cityText = "";
        tb = new TouristBean();
        people = 0;
    }

    public Integer getPeople() {
        return people;
    }

    public void setPeople(Integer people) {
        this.people = people;
    }

    public Collection<Cities> getCities() {
        cities = adminLogic.getCityByStateId(4030);
        return cities;
    }

    public void setCities(Collection<Cities> cities) {
        this.cities = cities;
    }

    public Collection<EventMaster> getEvents() {
        if (cityText.equals("none") || cityText.equals("")) {
            this.events = adminLogic.getEventsOfAllGuides();
        }
        return events;
    }

    public EventMaster getSelectedEvent() {
        return selectedEvent;
    }

    public void setSelectedEvent(EventMaster selectedEvent) {
        this.selectedEvent = selectedEvent;
    }

    public void setEvents(Collection<EventMaster> events) {
        this.events = events;
    }

    public String getCityText() {
        if (cityText.equals("")) {
            cityText = GeoLocationUtil.getUserCurrentCity();
            this.events = adminLogic.getEventsByCity(cityText);
        }
        return cityText;
    }

    public void setCityText(String cityText) {
        this.cityText = cityText;
    }

    public void cityValuChangeListener() {
        System.out.println(cityText);
        if (!this.cityText.equals("none")) {
            this.events = new ArrayList<>();
            this.events = adminLogic.getEventsByCity(cityText);
            System.out.println("Inside true : " + this.events);
            PrimeFaces.current().ajax().update(":dataTableForm:dt-join-event");

        } else {
            this.events = adminLogic.getEventsOfAllGuides();
            System.out.println("Inside false: " + this.events);
            PrimeFaces.current().ajax().update(":dataTableForm:dt-join-event");
        }
    }

    public void joinEvent() {
        try {

            System.out.println(KeepRecord.getUsername());
            System.out.println(selectedEvent.getPlaceId().getName());
            System.out.println(people);
            adminLogic.joinEvent(people, selectedEvent, KeepRecord.getUsername());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Event Joined"));
            PrimeFaces.current().ajax().update(":dataTableForm:dt-join-event");

        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("You Already Joined this event!"));
        }
        PrimeFaces.current().ajax().update(":dataTableForm:messages");
        PrimeFaces.current().executeScript("PF('manageJoinDialog').hide()");
    }
}
