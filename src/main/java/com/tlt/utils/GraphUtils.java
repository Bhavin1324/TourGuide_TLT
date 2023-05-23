/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tlt.utils;

import com.tlt.ejb.AdminLocal;
import java.util.ArrayList;
import java.util.List;
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
import org.primefaces.model.charts.pie.PieChartModel;

/**
 *
 * @author kunal
 */
public class GraphUtils {

    @EJB
    AdminLocal ad;
    private BarChartModel barModel;
    private PieChartModel pieModel;
    String month;
    Long count;

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
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

    public BarChartModel createBarModel(String setLabel, List<GraphUtils> barGraphData) {
        barModel = new BarChartModel();
        ChartData data = new ChartData();

        BarChartDataSet barDataSet = new BarChartDataSet();
        barDataSet.setLabel(setLabel);

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

        List<Number> values = new ArrayList<>();
        List<String> labels = new ArrayList<>();
        for (GraphUtils g : barGraphData) {
            values.add(g.getCount());
            labels.add(g.getMonth());
        }

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
        animation.setDuration(2);
        options.setAnimation(animation);

        barModel.setOptions(options);
        return barModel;
    }

}
