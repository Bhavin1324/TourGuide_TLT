package com.tlt.cdis;

import com.tlt.ejb.AdminLocal;
import com.tlt.entities.AppointmentMaster;
import com.tlt.entities.GuideMaster;
import com.tlt.entities.PlaceMaster;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.servlet.http.HttpSession;
import org.primefaces.PrimeFaces;

@Named(value = "reservePlace")
@RequestScoped
public class ReservePlace implements Serializable {

    @EJB
    AdminLocal adminLogic;
    @Inject
    HttpSession session;
    AppointmentMaster touristAppointment;
    PlaceMaster currentPlace;
    int numberOfPeople;
    Collection<GuideMaster> guidesOfPlace;
    GuideMaster selectedGuide;
    String pack;

    public ReservePlace() {
        numberOfPeople = 0;
        pack = "T";
        currentPlace = new PlaceMaster();
        selectedGuide = new GuideMaster();
        guidesOfPlace = new ArrayList<>();
    }
    public String getCurrentPlaceIdFromSession(){
        return String.valueOf(session.getAttribute("pid"));
    }
    public AppointmentMaster getTouristAppointment() {
        return touristAppointment;
    }

    public void setTouristAppointment(AppointmentMaster touristAppointment) {
        this.touristAppointment = touristAppointment;
    }

    public int getNumberOfPeople() {
        return numberOfPeople;
    }

    public void setNumberOfPeople(int numberOfPeople) {
        this.numberOfPeople = numberOfPeople;
    }

    public String getPack() {
        return pack;
    }

    public void setPack(String pack) {
        this.pack = pack;
    }

    public PlaceMaster getCurrentPlace() {
        this.currentPlace = adminLogic.getPlaceById(getCurrentPlaceIdFromSession());
        return currentPlace;
    }

    public void setCurrentPlace(PlaceMaster currentPlace) {
        this.currentPlace = currentPlace;
    }

    public Collection<GuideMaster> getGuidesOfPlace() {
        this.guidesOfPlace = adminLogic.getAllGuidesOfPlaces(getCurrentPlaceIdFromSession());
        return this.guidesOfPlace;
    }

    public void setGuidesOfPlace(Collection<GuideMaster> guidesOfPlace) {
        this.guidesOfPlace = guidesOfPlace;
    }

    public GuideMaster getSelectedGuide() {
        return selectedGuide;
    }

    public void setSelectedGuide(GuideMaster selectedGuide) {
        this.selectedGuide = selectedGuide;
    }
    
    public void openReservationModal(String placeId){
        PrimeFaces.current().executeScript("PF('booking_dialog').show()");
    }
}
