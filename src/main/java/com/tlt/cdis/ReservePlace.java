package com.tlt.cdis;

import static com.tlt.constants.UrlConstants.TO_LOGIN;
import static com.tlt.constants.UrlConstants.TO_MY_TRIP;
import com.tlt.ejb.AdminLocal;
import com.tlt.ejb.TouristLocal;
import com.tlt.entities.AppointmentMaster;
import com.tlt.entities.GuideMaster;
import com.tlt.entities.PaymentMaster;
import com.tlt.entities.PlaceMaster;
import com.tlt.entities.TransportMaster;
import com.tlt.entities.TransporterMaster;
import com.tlt.entities.UserMaster;
import com.tlt.record.KeepRecord;
import com.tlt.utils.Utils;
import java.io.IOException;
import java.io.Serializable;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    @EJB
    TouristLocal touristLogic;
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

    String cardNumber;

    public ReservePlace() {
        numberOfPeople = 0;
        pack = "T";
        currentPlace = new PlaceMaster();
        selectedGuide = null;
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
        if (selectedGuide == null) {
            List<GuideMaster> agl = new ArrayList<GuideMaster>(this.availableGuideOfPlace);
            selectedGuide = agl.get(0);
        }
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

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public void step1Action() {
        PrimeFaces.current().executeScript("PF('booking_dialog').hide()");
        PrimeFaces.current().executeScript("PF('guide_select_dialog').show()");
    }

    public void step2Action() {
        PrimeFaces.current().executeScript("PF('payment_dialog').show()");
        PrimeFaces.current().executeScript("PF('guide_select_dialog').hide()");
    }

    public void paymentAction() {
        try {
            currentPlace = adminLogic.getPlaceById(getCurrentPlaceIdFromSession());
            transport.setId(Utils.getUUID());
            transport.setTransporterId(transporter);
            UserMaster currentUser = touristLogic.findUserByUsername(KeepRecord.getUsername());
            transport.setUserId(currentUser);

            touristAppointment.setId(Utils.getUUID());
            touristAppointment.setUserId(currentUser);
            touristAppointment.setPlaceId(currentPlace);
            touristAppointment.setAppointmentStatus("pending");
            touristAppointment.setPackType(pack);
            touristAppointment.setNumberOfPeople(numberOfPeople);

            PaymentMaster payment = new PaymentMaster();
            payment.setId(Utils.getUUID());
            payment.setCreatedAt(new Date());
            payment.setCardDetails(cardNumber);
            payment.setPaymentStatus("Success");
            payment.setUserId(currentUser);
            payment.setPaymentMethodId(adminLogic.getCardPayment());
            switch (pack) {
                case "TG":
                    int grossAmount = selectedGuide.getAmount() + transporter.getAmount();
                    touristAppointment.setGuideId(selectedGuide);
                    touristAppointment.setTransportId(transport);

                    payment.setGuideId(selectedGuide);
                    payment.setTransportId(transport);
                    payment.setAmount(grossAmount);

                    touristLogic.insertTransport(transport);
                    adminLogic.insertIntoAppointment(touristAppointment);
                    touristLogic.reserveYourPlace(payment);
                    reset();
                    PrimeFaces.current().executeScript("PF('success_dialog').show()");
                    return;
                case "G":
                    touristAppointment.setGuideId(selectedGuide);

                    payment.setGuideId(selectedGuide);
                    payment.setAmount(selectedGuide.getAmount());

                    adminLogic.insertIntoAppointment(touristAppointment);
                    touristLogic.reserveYourPlace(payment);
                    reset();
                    PrimeFaces.current().executeScript("PF('success_dialog').show()");
                    return;
                case "T":
                    touristAppointment.setTransportId(transport);

                    payment.setTransportId(transport);
                    payment.setAmount(transporter.getAmount());

                    touristLogic.insertTransport(transport);
                    adminLogic.insertIntoAppointment(touristAppointment);
                    touristLogic.reserveYourPlace(payment);
                    reset();
                    PrimeFaces.current().executeScript("PF('success_dialog').show()");
                    return;
                default:
                    PrimeFaces.current().executeScript("PF('error_dialog').show()");
                    return;
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            PrimeFaces.current().executeScript("PF('error_dialog').show()");
        }
    }

    public void goToMyTrip() {
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect(TO_MY_TRIP);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void reset() {
        numberOfPeople = 0;
        pack = "T";
        currentPlace = new PlaceMaster();
        selectedGuide = null;
        guidesOfPlace = new ArrayList<>();
        touristAppointment = new AppointmentMaster();
        transport = new TransportMaster();
        cardNumber = null;
        transporter = null;
    }

    public void guideCardSelectOption(GuideMaster guide) {
        selectedGuide = guide;
        PrimeFaces.current().executeScript("PF('booking_dialog').show()");
    }
}
