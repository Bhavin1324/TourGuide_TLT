package com.tlt.cdis;

import com.tlt.ejb.AdminLocal;
import com.tlt.ejb.GuideLocal;
import com.tlt.ejb.TouristLocal;
import com.tlt.entities.AppointmentMaster;
import com.tlt.entities.EventMaster;
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

    public MyTripBean() {
        PrimeFaces.current().ajax().update("apt-tab-view:appointment-dt-form:dt-appointments-user");
    }

    public Collection<AppointmentMaster> getUsersAppointments() {
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

    public void selectRow(AppointmentMaster appointment) {
        session.setAttribute("selectedAppointmentRow", appointment);
    }

    public void updateAppointmentStatus(String status) {
        try {
            this.selectedAppointment = (AppointmentMaster) session.getAttribute("selectedAppointmentRow");
            guideLogic.updateAppointmentStatus(this.selectedAppointment, status);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Updated", "Marked as  " + status));
        } catch (Exception ex) {
            ex.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error Occured", "Error Updating Status"));
        }
    }
}
