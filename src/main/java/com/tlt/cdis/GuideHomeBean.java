/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package com.tlt.cdis;

import com.tlt.ejb.GuideLocal;
import com.tlt.entities.AppointmentMaster;
import com.tlt.record.KeepRecord;
import com.tlt.utils.GraphUtils;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.ejb.EJB;
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
    private List<GraphUtils> barGraphData;
    private List<GraphUtils> pieGraphData;
    private BarChartModel barModel;
    private PieChartModel pieModel;
    long apptPendingCount, apptCompleteCount;
    long revenue;
    GraphUtils gUtils;

    public GuideHomeBean() {
        barGraphData = new ArrayList<>();
        pieGraphData = new ArrayList<>();
        gUtils = new GraphUtils();
    }

    public void init() {
        barGraphData = gd.getMonthlyAppointmentsCount(KeepRecord.getUsername());
        this.barModel = gUtils.createBarModel("Appointments", barGraphData);
        pieGraphData  = gd.getMonthlyRevenueOfGuide(KeepRecord.getUsername());
        this.pieModel = gUtils.createPirChart(pieGraphData);
    }

    public List<GraphUtils> getBarGraphData() {
        return barGraphData;
    }

    public void setBarGraphData(List<GraphUtils> barGraphData) {
        this.barGraphData = barGraphData;
    }

    public List<GraphUtils> getPieGraphData() {
        return pieGraphData;
    }

    public void setPieGraphData(List<GraphUtils> pieGraphData) {
        this.pieGraphData = pieGraphData;
    }

    public BarChartModel getBarModel() {
        return barModel;
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

    public long getRevenue() {
        this.revenue = 0;
        Collection<AppointmentMaster> appointments = gd.getAllAppointmentsByGuide(KeepRecord.getUsername());
        for (AppointmentMaster ap : appointments) {
            this.revenue += ap.getGuideId().getAmount();
        }
        return revenue;
    }

    public void setRevenue(long revenue) {
        this.revenue = revenue;
    }

}
