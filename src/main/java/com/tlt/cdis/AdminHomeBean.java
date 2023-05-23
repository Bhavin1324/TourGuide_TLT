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
    long userCount, placeCount, subsCount;
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
    }

    public List<GraphUtils> getGraphData() {
        return barGraphData;
    }

    public void setGraphData(List<GraphUtils> graphData) {
        this.barGraphData = graphData;
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
