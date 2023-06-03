/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package com.tlt.cdis;

import com.tlt.ejb.GuideLocal;
import com.tlt.entities.AppointmentMaster;
import com.tlt.record.KeepRecord;
import com.tlt.utils.GraphUtils;
import com.tlt.utils.Utils;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.primefaces.PrimeFaces;
import org.primefaces.model.charts.bar.BarChartModel;
import org.primefaces.model.charts.pie.PieChartModel;

/**
 *
 * @author kunal
 */
@Named(value = "guideHomeBean")
@SessionScoped
public class GuideHomeBean implements Serializable {

    @EJB
    GuideLocal gd;
    private List<GraphUtils> monthlyAppointmentsCountData;
    private List<GraphUtils> monthlyRevenueOfPersonalAppointmentsData;
    private List<GraphUtils> monthlyEventsCountData;
    private List<GraphUtils> monthlyRevenueOfEventsData;
    private BarChartModel monthlyApptCountModel;
    private BarChartModel monthlyEventCountModel;
    private PieChartModel monthlyRevenueOfPersonalAppointmentsModel;
    private PieChartModel monthlyRevenueOfEventsModel;
    long apptPendingCount, apptCompleteCount;
    long personalAptrevenue, eventRevenue;
    private Collection<AppointmentMaster> appointments;
    private AppointmentMaster selectedAppointment;
    GraphUtils gUtils;

    public GuideHomeBean() {
        monthlyAppointmentsCountData = new ArrayList<>();
        monthlyEventsCountData = new ArrayList<>();
        monthlyRevenueOfPersonalAppointmentsData = new ArrayList<>();
        monthlyRevenueOfEventsData = new ArrayList<>();
        personalAptrevenue = 0;
        eventRevenue = 0;
        gUtils = new GraphUtils();
    }

    public void init() {

        monthlyAppointmentsCountData = gd.getMonthlyPersonalAppointmentsCount(KeepRecord.getUsername());
        this.monthlyApptCountModel = gUtils.createBarModel("Personal Appointments", monthlyAppointmentsCountData);

        monthlyEventsCountData = gd.getMonthlyEventsCount(KeepRecord.getUsername());
        this.monthlyEventCountModel = gUtils.createBarModel("Events", monthlyEventsCountData);

        monthlyRevenueOfPersonalAppointmentsData = gd.getMonthlyRevenueOfPersonalAppointments(KeepRecord.getUsername());
        this.monthlyRevenueOfPersonalAppointmentsModel = gUtils.createPirChart(monthlyRevenueOfPersonalAppointmentsData);

        monthlyRevenueOfEventsData = gd.getMonthlyRevenueOfEvents(KeepRecord.getUsername());
        this.monthlyRevenueOfEventsModel = gUtils.createPirChart(monthlyRevenueOfEventsData);
    }

    public String getFormatedDate(Date date) {
        return Utils.getDateTimeFormat(date);
    }

    public String getFormatedTime(Date date) {
        return Utils.getTime12h(date);
    }

    public long getPersonalAptrevenue() {
        this.personalAptrevenue = gd.getTotalRevenueOfPersonalAppointments(KeepRecord.getUsername());
        if (personalAptrevenue == 0) {
            return 0;
        }
        return personalAptrevenue;
    }

    public void setPersonalAptrevenue(long personalAptrevenue) {
        this.personalAptrevenue = personalAptrevenue;
    }

    public long getEventRevenue() {
        eventRevenue =  gd.getTotalRevenueOfEvents(KeepRecord.getUsername());
        if(eventRevenue == 0){
            return 0;
        }
        return eventRevenue;
    }

    public void setEventRevenue(long eventRevenue) {
        this.eventRevenue = eventRevenue;
    }

    public Collection<AppointmentMaster> getAppointments() {
        return gd.getAllAppointmentsByGuide(KeepRecord.getUsername());
    }

    public void setAppointments(Collection<AppointmentMaster> appointments) {
        this.appointments = appointments;
    }

    public AppointmentMaster getSelectedAppointment() {
        return selectedAppointment;
    }

    public void setSelectedAppointment(AppointmentMaster selectedAppointment) {
        this.selectedAppointment = selectedAppointment;
    }

    public BarChartModel getMonthlyEventCountModel() {
        return monthlyEventCountModel;
    }

    public void setMonthlyEventCountModel(BarChartModel monthlyEventCountModel) {
        this.monthlyEventCountModel = monthlyEventCountModel;
    }

    public PieChartModel getMonthlyRevenueOfEventsModel() {
        return monthlyRevenueOfEventsModel;
    }

    public void setMonthlyRevenueOfEventsModel(PieChartModel monthlyRevenueOfEventsModel) {
        this.monthlyRevenueOfEventsModel = monthlyRevenueOfEventsModel;
    }

    public List<GraphUtils> getBarGraphData() {
        return monthlyAppointmentsCountData;
    }

    public void setBarGraphData(List<GraphUtils> monthlyAppointmentsCountData) {
        this.monthlyAppointmentsCountData = monthlyAppointmentsCountData;
    }

    public List<GraphUtils> getPieGraphData() {
        return monthlyRevenueOfPersonalAppointmentsData;
    }

    public void setPieGraphData(List<GraphUtils> monthlyRevenueOfPersonalAppointmentsData) {
        this.monthlyRevenueOfPersonalAppointmentsData = monthlyRevenueOfPersonalAppointmentsData;
    }

    public List<GraphUtils> getMonthlyAppointmentsCountData() {
        return monthlyAppointmentsCountData;
    }

    public void setMonthlyAppointmentsCountData(List<GraphUtils> monthlyAppointmentsCountData) {
        this.monthlyAppointmentsCountData = monthlyAppointmentsCountData;
    }

    public List<GraphUtils> getMonthlyRevenueOfPersonalAppointmentsData() {
        return monthlyRevenueOfPersonalAppointmentsData;
    }

    public void setMonthlyRevenueOfPersonalAppointmentsData(List<GraphUtils> monthlyRevenueOfPersonalAppointmentsData) {
        this.monthlyRevenueOfPersonalAppointmentsData = monthlyRevenueOfPersonalAppointmentsData;
    }

    public BarChartModel getMonthlyApptCountModel() {
        return monthlyApptCountModel;
    }

    public void setMonthlyApptCountModel(BarChartModel monthlyApptCountModel) {
        this.monthlyApptCountModel = monthlyApptCountModel;
    }

    public PieChartModel getMonthlyRevenueOfPersonalAppointmentsModel() {
        return monthlyRevenueOfPersonalAppointmentsModel;
    }

    public void setMonthlyRevenueOfPersonalAppointmentsModel(PieChartModel monthlyRevenueOfPersonalAppointmentsModel) {
        this.monthlyRevenueOfPersonalAppointmentsModel = monthlyRevenueOfPersonalAppointmentsModel;
    }

    public long getApptPendingCount() {
        apptPendingCount = gd.getAppointmentsOfGuide(KeepRecord.getUsername(), "Pending").size();
        return apptPendingCount;
    }

    public void setApptPendingCount(long apptPendingCount) {
        this.apptPendingCount = apptPendingCount;
    }

    public long getApptCompleteCount() {
        apptCompleteCount = gd.getAppointmentsOfGuide(KeepRecord.getUsername(), "Complete").size();
        return apptCompleteCount;
    }

    public void setApptCompleteCount(long apptCompleteCount) {
        this.apptCompleteCount = apptCompleteCount;
    }

    public void updateStatus(String status) {
        try {
            gd.updateAppointmentStatus(this.selectedAppointment, status);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Updated", "Marked as Complete"));
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error Occured", "Error Updating Status"));
        }
    }
}
