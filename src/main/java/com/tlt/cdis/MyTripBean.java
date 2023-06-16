package com.tlt.cdis;

import com.tlt.ejb.AdminLocal;
import com.tlt.ejb.GuideLocal;
import com.tlt.ejb.TouristLocal;
import com.tlt.entities.AppointmentMaster;
import com.tlt.entities.EventMaster;
import com.tlt.entities.GuideMaster;
import com.tlt.entities.PaymentMaster;
import com.tlt.entities.UserMaster;
import com.tlt.record.KeepRecord;
import com.tlt.utils.Utils;
import javax.inject.Named;
import java.util.Collection;
import java.util.Date;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpSession;
import org.primefaces.PrimeFaces;

@Named(value = "myTripBean")
@RequestScoped
public class MyTripBean {

    @EJB
    AdminLocal adminLogic;
    @EJB
    TouristLocal touristLogic;
    @EJB
    GuideLocal guideLogic;
    Collection<AppointmentMaster> usersAppointments;
    Collection<EventMaster> userEvents;

    AppointmentMaster selectedAppointment;
    EventMaster selectedEvent;

    UserMaster loggedInUser;

    int activeTabIndex;
    @Inject
    HttpSession session;

    int appointmentCost;

    public MyTripBean() {
        PrimeFaces.current().ajax().update("apt-tab-view:appointment-dt-form:dt-appointments-user");
    }

    public Collection<AppointmentMaster> getUsersAppointments() {
        touristLogic.markMissedAppointment();
        loggedInUser = touristLogic.findUserByUsername(KeepRecord.getUsername());
        usersAppointments = adminLogic.getUserAppointments(loggedInUser.getId());
        return usersAppointments;
    }

    public void setUsersAppointments(Collection<AppointmentMaster> usersAppointments) {
        this.usersAppointments = usersAppointments;
    }

    public Collection<EventMaster> getUserEvents() {
        loggedInUser = touristLogic.findUserByUsername(KeepRecord.getUsername());
        userEvents = adminLogic.getUserEvents(loggedInUser.getId());
        return userEvents;
    }

    public void setUserEvents(Collection<EventMaster> userEvents) {
        this.userEvents = userEvents;
    }

    public AppointmentMaster getSelectedAppointment() {
        return selectedAppointment;
    }

    public void setSelectedAppointment(AppointmentMaster selectedAppointment) {
        this.selectedAppointment = selectedAppointment;
    }

    public EventMaster getSelectedEvent() {
        return selectedEvent;
    }

    public void setSelectedEvent(EventMaster selectedEvent) {
        this.selectedEvent = selectedEvent;
    }

    public int getActiveTabIndex() {
        return activeTabIndex;
    }

    public void setActiveTabIndex(int activeTabIndex) {
        this.activeTabIndex = activeTabIndex;
    }

    public String getFormatedDate(Date date) {
        return Utils.getDateTimeFormat(date);
    }

    public String getFormatedTime(Date date) {
        return Utils.getTime12h(date);
    }

    public int getAppointmentCost() {
        return appointmentCost;
    }

    public void setAppointmentCost(int appointmentCost) {
        this.appointmentCost = appointmentCost;
    }

    public void selectRow(AppointmentMaster appointment) {
        session.setAttribute("selectedAppointmentRow", appointment);
        PaymentMaster paymentMaster = guideLogic.getPaymentByAppointmentId(appointment.getId());
        this.appointmentCost = paymentMaster.getAmount();
    }

    public void updateAppointmentStatus(String status) {
        try {
            this.selectedAppointment = (AppointmentMaster) session.getAttribute("selectedAppointmentRow");
            if (status.equalsIgnoreCase("Complete")) {
                if (selectedAppointment.getPackType().equals("TG") || selectedAppointment.getPackType().equals("G")) {
                    GuideMaster guide = selectedAppointment.getGuideId();
                    guide.setIsAppointed(false);
                    adminLogic.updateGuide(guide.getId(), guide);
                }
                guideLogic.updateAppointmentStatus(this.selectedAppointment, status);
            }
            if (status.equalsIgnoreCase("Cancelled")) {
                GuideMaster guideOfAppointment = selectedAppointment.getGuideId();

                PaymentMaster paymentMaster = guideLogic.getPaymentByAppointmentId(selectedAppointment.getId());
                paymentMaster.setPaymentStatus("Success Refund");
                String tranportToDelete = selectedAppointment.getTransportId() != null ? selectedAppointment.getTransportId().getId() : null;
                selectedAppointment.setAppointmentStatus("Cancelled");
                switch (selectedAppointment.getPackType()) {
                    case "TG":
                        paymentMaster.setTransportId(null);
                        selectedAppointment.setTransportId(null);
                        touristLogic.updatePayment(paymentMaster);
                        adminLogic.updateAppointment(selectedAppointment.getId(), selectedAppointment);
                        touristLogic.removeTransportInfo(tranportToDelete);
                        guideOfAppointment.setIsAppointed(false);
                        adminLogic.updateGuide(guideOfAppointment.getId(), guideOfAppointment);
                        break;
                    case "T":
                        paymentMaster.setTransportId(null);
                        selectedAppointment.setTransportId(null);
                        adminLogic.updateAppointment(selectedAppointment.getId(), selectedAppointment);
                        touristLogic.updatePayment(paymentMaster);
                        touristLogic.removeTransportInfo(tranportToDelete);
                        break;
                    case "G":
                        guideOfAppointment.setIsAppointed(false);
                        adminLogic.updateAppointment(selectedAppointment.getId(), selectedAppointment);
                        adminLogic.updateGuide(guideOfAppointment.getId(), guideOfAppointment);
                        touristLogic.updatePayment(paymentMaster);
                        break;
                }
            }
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Updated", "Marked as  " + status));
        } catch (Exception ex) {
            ex.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error Occured", "Error Updating Status"));
        }
    }
}
