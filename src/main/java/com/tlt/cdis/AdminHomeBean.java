/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package com.tlt.cdis;

import com.tlt.ejb.AdminLocal;
import com.tlt.utils.GraphUtils;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import org.primefaces.model.charts.bar.BarChartModel;
import org.primefaces.model.charts.pie.PieChartModel;

@Named(value = "adminHomeBean")
@SessionScoped
public class AdminHomeBean implements Serializable {

    @EJB
    AdminLocal ad;
    private List<GraphUtils> barGraphData;
    private List<GraphUtils> pieGraphData;
    private BarChartModel barModel;
    private PieChartModel pieModel;
    GraphUtils gUtils;
    long userCount, placeCount, subsCount, todaysApptCount, todaysEventsCount, todaysSubsCount, eventCancelCount, apptCancelCount;
    long revenue;

    public AdminHomeBean() {
        barGraphData = new ArrayList<>();
        pieGraphData = new ArrayList<>();
        gUtils = new GraphUtils();
    }

    public void init() {
        barGraphData = ad.getMonthlySubscriptionData();
        this.barModel = gUtils.createBarModel("Subscriptions", barGraphData);
        pieGraphData = ad.getMonthlyRevenueData();
        this.pieModel = gUtils.createPirChart(pieGraphData);
        this.todaysApptCount = ad.getTodaysAppointmentsCount();
        this.todaysEventsCount = ad.getTodaysEventsCount();
        this.todaysSubsCount = ad.getTodaysSubscriptionsCount();
        this.eventCancelCount = ad.getEventCancelCount();
        this.apptCancelCount = ad.getAppointmentCancelCount();
    }

    public List<GraphUtils> getGraphData() {
        return barGraphData;
    }

    public void setGraphData(List<GraphUtils> graphData) {
        this.barGraphData = graphData;
    }

    public long getTodaysEventsCount() {
        return todaysEventsCount;
    }

    public void setTodaysEventsCount(long todaysEventsCount) {
        this.todaysEventsCount = todaysEventsCount;
    }

    public long getTodaysSubsCount() {
        return todaysSubsCount;
    }

    public void setTodaysSubsCount(long todaysSubsCount) {
        this.todaysSubsCount = todaysSubsCount;
    }

    public long getEventCancelCount() {
        return eventCancelCount;
    }

    public void setEventCancelCount(long eventCancelCount) {
        this.eventCancelCount = eventCancelCount;
    }

    public long getApptCancelCount() {
        return apptCancelCount;
    }

    public void setApptCancelCount(long apptCancelCount) {
        this.apptCancelCount = apptCancelCount;
    }

    public long getTodaysApptCount() {
        return todaysApptCount;
    }

    public void setTodaysApptCount(long todaysApptCount) {
        this.todaysApptCount = todaysApptCount;
    }

    public long getUserCount() {
        return ad.getUserCount();
    }

    public void setUserCount(long userCount) {
        this.userCount = userCount;
    }

    public long getPlaceCount() {
        return ad.getPlacesCount();
    }

    public void setPlaceCount(long placeCount) {
        this.placeCount = placeCount;
    }

    public long getRevenue() {
        return ad.getTotalIncome();
    }

    public BarChartModel getBarModel() {
        return barModel;
    }

    public long getSubsCount() {
        return ad.getActiveSubsCount();
    }

    public void setSubsCount(long subsCount) {
        this.subsCount = subsCount;
    }

    public void setBarModel(BarChartModel barModel) {
        this.barModel = barModel;
    }

    public PieChartModel getPieModel() {
        return pieModel;
    }

    public void setPieModel(PieChartModel pieModel) {
        this.pieModel = pieModel;
    }
}
