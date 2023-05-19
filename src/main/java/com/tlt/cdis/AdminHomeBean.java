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
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import org.primefaces.model.charts.ChartData;
import org.primefaces.model.charts.axes.cartesian.CartesianScales;
import org.primefaces.model.charts.axes.cartesian.linear.CartesianLinearAxes;
import org.primefaces.model.charts.axes.cartesian.linear.CartesianLinearTicks;
import org.primefaces.model.charts.bar.BarChartDataSet;
import org.primefaces.model.charts.bar.BarChartModel;
import org.primefaces.model.charts.bar.BarChartOptions;
import org.primefaces.model.charts.optionconfig.animation.Animation;
import org.primefaces.model.charts.optionconfig.legend.Legend;
import org.primefaces.model.charts.optionconfig.legend.LegendLabel;
import org.primefaces.model.charts.optionconfig.title.Title;
import org.primefaces.model.charts.pie.PieChartDataSet;
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
    long userCount, placeCount, subsCount;
    long revenue;

    public AdminHomeBean() {
        barGraphData = new ArrayList<>();
        pieGraphData = new ArrayList<>();
    }

    @PostConstruct
    public void init() {
        barGraphData = ad.getMonthlySubscriptionData();
        pieGraphData = ad.getMonthlyRevenueData();
        createBarModel();
        createPirChart();
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

    public void createBarModel() {
        barModel = new BarChartModel();
        ChartData data = new ChartData();

        BarChartDataSet barDataSet = new BarChartDataSet();
        barDataSet.setLabel("Subscriptions");

        List<Number> values = new ArrayList<>();
        
//        values.add(65);
//        values.add(59);
//        values.add(80);
//        values.add(81);
//        values.add(56);
//        values.add(55);
//        values.add(40);
//        values.add(40);
//        values.add(40);
//        values.add(40);
//        values.add(40);
//        values.add(40);

        List<String> bgColor = new ArrayList<>();
        bgColor.add("rgb(255, 99, 132)");
        bgColor.add("rgb(255, 159, 64)");
        bgColor.add("rgb(255, 205, 86)");
        bgColor.add("rgb(75, 192, 192)");
        bgColor.add("rgb(54, 162, 235)");
        bgColor.add("rgb(153, 102, 255)");
        bgColor.add("rgb(71, 99, 255)");
        bgColor.add("rgb(249, 78, 78)");
        bgColor.add("rgb(42, 152, 42)");
        bgColor.add("rgb(237, 119, 204)");
        bgColor.add("rgb(203, 204, 37)");
        bgColor.add("rgb(255, 217, 82)");
        barDataSet.setBackgroundColor(bgColor);

        data.addChartDataSet(barDataSet);

        List<String> labels = new ArrayList<>();
        for(GraphUtils g : barGraphData){
            values.add(g.getCount());
            labels.add(g.getMonth());
        }
//        labels.add("January");
//        labels.add("February");
//        labels.add("March");
//        labels.add("April");
//        labels.add("May");
//        labels.add("June");
//        labels.add("July");
//        labels.add("August");
//        labels.add("September");
//        labels.add("October");
//        labels.add("November");
//        labels.add("December");
        barDataSet.setData(values);
        data.setLabels(labels);
        barModel.setData(data);

        //Options
        BarChartOptions options = new BarChartOptions();
        CartesianScales cScales = new CartesianScales();
        CartesianLinearAxes linearAxes = new CartesianLinearAxes();
        linearAxes.setOffset(true);
        linearAxes.setBeginAtZero(true);
        CartesianLinearTicks ticks = new CartesianLinearTicks();
        linearAxes.setTicks(ticks);
        cScales.addYAxesData(linearAxes);
        options.setScales(cScales);

//        Title title = new Title();
//        title.setDisplay(true);
//        title.setText("Bar Chart");
//        options.setTitle(title);

        Legend legend = new Legend();
        legend.setDisplay(true);
        legend.setPosition("top");
        LegendLabel legendLabels = new LegendLabel();
        legendLabels.setFontStyle("italic");
        legendLabels.setFontColor("#2980B9");
        legendLabels.setFontSize(24);
        legend.setLabels(legendLabels);
        options.setLegend(legend);

        // disable animation
        Animation animation = new Animation();
        animation.setDuration(0);
        options.setAnimation(animation);

        barModel.setOptions(options);
    }

    public void createPirChart() {
        pieModel = new PieChartModel();
        ChartData data = new ChartData();

        PieChartDataSet dataSet = new PieChartDataSet();
        List<Number> values = new ArrayList<>();
//        values.add(300);
//        values.add(50);
//        values.add(13);
//        values.add(45);
//        values.add(98);
//        values.add(11);
//        values.add(14);
//        values.add(1);
//        values.add(40);
//        values.add(30);
//        values.add(30);
//        values.add(10);

        List<String> bgColors = new ArrayList<>();
        bgColors.add("rgb(255, 99, 132)");
        bgColors.add("rgb(255, 159, 64)");
        bgColors.add("rgb(255, 205, 86)");
        bgColors.add("rgb(75, 192, 192)");
        bgColors.add("rgb(54, 162, 235)");
        bgColors.add("rgb(153, 102, 255)");
        bgColors.add("rgb(71, 99, 255)");
        bgColors.add("rgb(249, 78, 78)");
        bgColors.add("rgb(42, 152, 42)");
        bgColors.add("rgb(237, 119, 204)");
        bgColors.add("rgb(203, 204, 37)");
        bgColors.add("rgb(255, 217, 82)");
        dataSet.setBackgroundColor(bgColors);

        data.addChartDataSet(dataSet);
        List<String> labels = new ArrayList<>();
         for(GraphUtils g : pieGraphData){
            values.add(g.getCount());
            labels.add(g.getMonth());
        }
//        labels.add("January");
//        labels.add("February");
//        labels.add("March");
//        labels.add("April");
//        labels.add("May");
//        labels.add("June");
//        labels.add("July");
//        labels.add("August");
//        labels.add("September");
//        labels.add("October");
//        labels.add("November");
//        labels.add("December");

        dataSet.setData(values);
        data.setLabels(labels);
        pieModel.setData(data);
    }

}
