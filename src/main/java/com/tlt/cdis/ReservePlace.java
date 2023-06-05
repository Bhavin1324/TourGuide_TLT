package com.tlt.cdis;

import static com.tlt.constants.UrlConstants.TO_LOGIN;
import com.tlt.ejb.AdminLocal;
import com.tlt.entities.AppointmentMaster;
import com.tlt.entities.GuideMaster;
import com.tlt.entities.PlaceMaster;
import com.tlt.entities.TransportMaster;
import com.tlt.entities.TransporterMaster;
import java.io.Serializable;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.Collection;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpSession;
import org.primefaces.PrimeFaces;

@Named(value = "reservePlace")
@SessionScoped
public class ReservePlace implements Serializable {

    @EJB
    AdminLocal adminLogic;
    @Inject
    HttpSession session;
    AppointmentMaster touristAppointment;
    PlaceMaster currentPlace;
    int numberOfPeople;

    Collection<GuideMaster> guidesOfPlace;
    Collection<GuideMaster> availableGuideOfPlace;
    GuideMaster selectedGuide;

    TransporterMaster transporter;
    TransportMaster transport;

    String pack;

    public ReservePlace() {
        numberOfPeople = 0;
        pack = "T";
        currentPlace = new PlaceMaster();
        selectedGuide = new GuideMaster();
        guidesOfPlace = new ArrayList<>();
        touristAppointment = new AppointmentMaster();
        transport = new TransportMaster();
    }

    public String getCurrentPlaceIdFromSession() {
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

    public Collection<GuideMaster> getAvailableGuideOfPlace() {
        this.availableGuideOfPlace = adminLogic.getAvailableGuidesOfPlace(getCurrentPlaceIdFromSession());
        return availableGuideOfPlace;
    }

    public void setAvailableGuideOfPlace(Collection<GuideMaster> availableGuideOfPlace) {
        this.availableGuideOfPlace = availableGuideOfPlace;
    }

    public TransporterMaster getTransporter() {
        if (transporter == null) {
            transporter = adminLogic.getRandomTranporter();
        }
        return transporter;
    }

    public void setTransporter(TransporterMaster transporter) {
        this.transporter = transporter;
    }

    public TransportMaster getTransport() {
        return transport;
    }

    public void setTransport(TransportMaster transport) {
        this.transport = transport;
    }

    public void step1Action() {
        System.out.println("Transporter : " + transporter);
        PrimeFaces.current().executeScript("PF('booking_dialog').hide()");
        PrimeFaces.current().executeScript("PF('guide_select_dialog').show()");
    }

    public void step2Action() {
        System.out.println("guide information : " + selectedGuide);
        System.out.println("Number of people : " + numberOfPeople);
        System.out.println("Selected pack : " + pack);
        System.out.println("Toruist appointment : " + touristAppointment.getStartDatetime());
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect(TO_LOGIN);
        } catch (Exception e) {
        }
    }
}
