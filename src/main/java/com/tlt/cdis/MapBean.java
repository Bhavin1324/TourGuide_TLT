package com.tlt.cdis;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import org.primefaces.event.map.OverlaySelectEvent;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;

@Named(value = "mapBean")
@SessionScoped
public class MapBean implements Serializable {

    private MapModel<String> advancedModel;
    private MapModel<String> simpleModel;
    private MapModel<String> categoryModel;
    private Marker<String> marker;

    public MapBean() {
    }

    public MapModel<String> getAdvancedModel() {
        return advancedModel;
    }

    public void setAdvancedModel(MapModel<String> advancedModel) {
        this.advancedModel = advancedModel;
    }

    public Marker<String> getMarker() {
        return marker;
    }

    public void setMarker(Marker<String> marker) {
        this.marker = marker;
    }

    public MapModel<String> getSimpleModel() {
        return simpleModel;
    }

    public void setSimpleModel(MapModel<String> simpleModel) {
        this.simpleModel = simpleModel;
    }

    public MapModel<String> getCategoryModel() {
        return categoryModel;
    }

    public void setCategoryModel(MapModel<String> categoryModel) {
        this.categoryModel = categoryModel;
    }

    public void onMarkerSelect(OverlaySelectEvent<String> event) {
        marker = (Marker) event.getOverlay();
    }
}
