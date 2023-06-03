/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package com.tlt.cdis;

import com.tlt.ejb.AdminLocal;
import com.tlt.ejb.GuideLocal;
import com.tlt.entities.AppointmentMaster;
import com.tlt.entities.PlaceMaster;
import com.tlt.entities.SubscriptionModel;
import com.tlt.record.KeepRecord;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.primefaces.PrimeFaces;

/**
 *
 * @author kunal
 */
@Named(value = "guidingPlaceBean")
@SessionScoped
public class GuidingPlaceBean implements Serializable {

    @EJB
    GuideLocal gd;
    @EJB
    AdminLocal ad;
    List<PlaceMaster> guidesPlaces;
    List<PlaceMaster> selectedPlaces;
    PlaceMaster selectedPlace;
    String placeName;
    AppointmentMaster eventRaised;

    public GuidingPlaceBean() {
        placeName = "";
        guidesPlaces = new ArrayList<>();
        selectedPlaces = new ArrayList<>();
        selectedPlace = new PlaceMaster();
        eventRaised = new AppointmentMaster();
    }

    public AppointmentMaster getEventRaised() {
        return eventRaised;
    }

    public void setEventRaised(AppointmentMaster eventRaised) {
        this.eventRaised = eventRaised;
    }

    public List<PlaceMaster> getGuidesPlaces() {
        guidesPlaces = (List<PlaceMaster>) gd.getAllPlacesOfGuide(KeepRecord.getUsername());
        return guidesPlaces;
    }

    public void setGuidesPlaces(List<PlaceMaster> guidesPlaces) {
        this.guidesPlaces = guidesPlaces;
    }

    public List<PlaceMaster> getSelectedPlaces() {
        return selectedPlaces;
    }

    public void setSelectedPlaces(List<PlaceMaster> selectedPlaces) {
        this.selectedPlaces = selectedPlaces;
    }

    public PlaceMaster getSelectedPlace() {
        return selectedPlace;
    }

    public void setSelectedPlace(PlaceMaster selectedPlace) {
        this.selectedPlace = selectedPlace;
    }

    public void openNew() {
        this.selectedPlace = new PlaceMaster();
        this.eventRaised = new AppointmentMaster();
    }

    public String getDeleteButtonMessage() {
        if (hasSelectedPlaces()) {
            int size = this.selectedPlaces.size();
            return size > 1 ? size + " selected" : "1 selected";
        }
        return "Delete";
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public boolean hasSelectedPlaces() {
        return this.selectedPlaces != null && !this.selectedPlaces.isEmpty();
    }

    public void deletePlace() {
        try {
            gd.removeGuidesPlace(selectedPlace, KeepRecord.getUsername());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Place Removed"));
            PrimeFaces.current().ajax().update("form:messages", "form:dt-guide-places");
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Error Removing Place"));
            PrimeFaces.current().ajax().update("form:messages", "form:dt-guide-places");
        }
    }

    public void deleteSelectedPlaces() {
        try {
            for (PlaceMaster p : selectedPlaces) {
                gd.removeGuidesPlace(p, KeepRecord.getUsername());
            }
            selectedPlaces = null;
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Places Removed"));
            PrimeFaces.current().ajax().update("form:messages", "form:dt-guide-places");
            PrimeFaces.current().executeScript("PF('dtGuidePlaces').clearFilters()");
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Error Removing Places"));
            PrimeFaces.current().executeScript("PF('dtGuidePlaces').clearFilters()");
        }
    }

    public void saveGuidePlace() {
        try {
            ArrayList<PlaceMaster> placesByName = new ArrayList(ad.getPlacesByName(placeName));
            PlaceMaster pm = placesByName.get(0);
            gd.addGuidesPlace(pm, KeepRecord.getUsername());
            placeName = "";
            selectedPlace = null;
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Place Added"));
            PrimeFaces.current().ajax().update("form:messages", "form:dt-guide-places");
            PrimeFaces.current().executeScript("PF('manageGuidePlaceDialog').hide()");
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Error Adding Place"));
            PrimeFaces.current().ajax().update("form:messages", "form:dt-guide-places");
            PrimeFaces.current().executeScript("PF('manageGuidePlaceDialog').hide()");
        }
    }
    
    public void raiseEvent(){

       try{
           gd.raiseAnEvent(selectedPlace, KeepRecord.getUsername(), eventRaised.getStartDatetime(), eventRaised.getEndDatetime());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Place Added"));
            PrimeFaces.current().ajax().update("form:messages", "form:dt-guide-places");
            PrimeFaces.current().executeScript("PF('manageEventDialog').hide()");
       }catch(Exception e){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Error Adding Place"));
            PrimeFaces.current().ajax().update("form:messages", "form:dt-guide-places");
            PrimeFaces.current().executeScript("PF('manageEventDialog').hide()");
       }
        
    }

}
