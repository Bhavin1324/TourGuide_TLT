/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package com.tlt.cdis;

import static com.tlt.constants.UrlConstants.TO_MY_TRIP;
import com.tlt.ejb.AdminLocal;
import com.tlt.ejb.TouristLocal;
import com.tlt.entities.Cities;
import com.tlt.entities.EventMaster;
import com.tlt.entities.PaymentMaster;
import com.tlt.entities.SubscriptionMaster;
import com.tlt.entities.UserMaster;
import com.tlt.record.KeepRecord;
import com.tlt.utils.GeoLocationUtil;
import com.tlt.utils.Utils;
import java.io.IOException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.primefaces.PrimeFaces;

/**
 *
 * @author Lenovo
 */
@Named(value = "joinGroupBean")
@SessionScoped
public class JoinGroupBean implements Serializable {

    @EJB
    AdminLocal adminLogic;
    
    @EJB
    TouristLocal touristLogic;
    Collection<Cities> cities;
    Collection<EventMaster> events;
    EventMaster selectedEvent;
    String cityText;
    TouristBean tb;
    Integer people, currentCost;
    String cardNumber;

    public JoinGroupBean() {
        cities = new ArrayList<>();
        events = new ArrayList<>();
        cityText = "";
        currentCost = 0;
        cardNumber = "";
        tb = new TouristBean();
        people = 0;
    }

    public Integer getCurrentCost() {
        return currentCost;
    }

    public void setCurrentCost(Integer currentCost) {
        this.currentCost = currentCost;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
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
        getCityText();
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
            this.cityText = GeoLocationUtil.getUserLocation().getCity().getName();
            this.events = adminLogic.getEventsByCity(cityText);
            PrimeFaces.current().ajax().update(":data-table-form:dt-join-event");
        }
        return cityText;
    }

    public void setCityText(String cityText) {
        this.cityText = cityText;
    }

    public void cityValuChangeListener() {
        if (!this.cityText.equals("none")) {
            this.events = new ArrayList<>();
            this.events = adminLogic.getEventsByCity(cityText);
            PrimeFaces.current().ajax().update(":data-table-form:dt-join-event");

        } else {
            this.events = adminLogic.getEventsOfAllGuides();
            PrimeFaces.current().ajax().update(":data-table-form:dt-join-event");
        }
    }

    public void goToMyTrip() {
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect(TO_MY_TRIP);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void proceedToPayment() {

        this.currentCost = 0;
        this.currentCost = selectedEvent.getGuideId().getAmount();
        PrimeFaces.current().executeScript("PF('manageJoinDialog').hide()");
        PrimeFaces.current().executeScript("PF('payment_dialog').show()");

    }

    public void joinEvent() {
        try {
            UserMaster currentUser = touristLogic.findUserByUsername(KeepRecord.getUsername());

            PaymentMaster payment = new PaymentMaster();
            payment.setId(Utils.getUUID());
            payment.setCreatedAt(new Date());
            payment.setCardDetails(cardNumber);
            payment.setPaymentStatus("Success");
            payment.setUserId(currentUser);
            payment.setPaymentMethodId(adminLogic.getCardPayment());
            payment.setAmount(this.currentCost);
            payment.setEventId(selectedEvent);
            selectedEvent.setNumberOfPeople(selectedEvent.getNumberOfPeople() + people);
            boolean status = touristLogic.joinEvent(selectedEvent, KeepRecord.getUsername(), payment);
            if (!status) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("You Already Joined this event!"));
                this.currentCost = 0;
                this.cardNumber = "";
                this.selectedEvent = null;
                return;
            }
            this.currentCost = 0;
            this.cardNumber = "";
            this.selectedEvent = null;
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Event Joined"));

        } catch (Exception e) {
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("You Already Joined this event!"));
        }
        PrimeFaces.current().executeScript("PF('payment_dialog').hide()");
        PrimeFaces.current().ajax().update("data-table-form:messages", "data-table-form:dt-join-event");
    }
    
    public boolean hasAnySubscription () {
        Collection<SubscriptionMaster> usersSubscriptions = touristLogic.getUsersSubscriptions(String.valueOf(KeepRecord.getUsername()));
        if (!usersSubscriptions.isEmpty()) {
            return true;
        } else {
            return false;
        }
    }
}
