/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package com.tlt.cdis;

import com.tlt.ejb.AdminLocal;
import com.tlt.entities.Cities;
import com.tlt.entities.PlaceCategory;
import com.tlt.entities.PlaceMaster;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.primefaces.PrimeFaces;

/**
 *
 * @author kunal
 */
@Named(value = "placesBean")
@SessionScoped
public class PlacesBean implements Serializable {

    @EJB
    AdminLocal ad;
    List<PlaceMaster> pc;
    PlaceMaster selectedPlace;
    String catid, cityid;
    List<PlaceMaster> selectedPlaces;
    Collection<PlaceCategory> placeCategories;
    Collection<Cities> cities;

    /**
     * Creates a new instance of AdminBean
     */
    public PlacesBean() {
        pc = new ArrayList<>();
        selectedPlaces = new ArrayList<>();
        selectedPlace = new PlaceMaster();
        placeCategories = new ArrayList<>();
        cities = new ArrayList<>();
        catid = "";
        cityid = "";
    }

    public Collection<Cities> getCities() {
        return ad.getCityByStateId(4030);
    }

    public void setCities(Collection<Cities> cities) {
        this.cities = cities;
    }

    public Collection<PlaceCategory> getPlaceCategories() {
        return ad.getAllPlaceCategories();
    }

    public void setPlaceCategories(Collection<PlaceCategory> placeCategories) {
        this.placeCategories = placeCategories;
    }

    public String getCatid() {
        return catid;
    }

    public void setCatid(String catid) {
        this.catid = catid;
    }

    public String getCityid() {
        return cityid;
    }

    public void setCityid(String cityid) {
        this.cityid = cityid;
    }

    public PlaceMaster getSelectedPlace() {
        return selectedPlace;
    }

    public void setSelectedPlace(PlaceMaster selectedPlace) {
        System.out.println(selectedPlace);
        this.selectedPlace = selectedPlace;
    }

    public List<PlaceMaster> getPc() {
        return (List<PlaceMaster>) ad.getAllPlaces();
    }

    public void setPc(List<PlaceMaster> pc) {
        this.pc = pc;
    }

    public List<PlaceMaster> getSelectedPlaces() {
        return selectedPlaces;
    }

    public void setSelectedPlaces(List<PlaceMaster> selectedPlaces) {
        this.selectedPlaces = selectedPlaces;
    }

    public void openNew() {
        this.selectedPlace = new PlaceMaster();
    }

    public void savePlace() {
        PlaceCategory p = ad.getPlaceCategoryById(catid);
        Cities city = ad.getCityById(Integer.parseInt(cityid));
        selectedPlace.setCityId(city);
        selectedPlace.setCategoryId(p);
        if (this.selectedPlace.getId() == null) {
            this.selectedPlace.setId(UUID.randomUUID().toString().replaceAll("-", "").substring(0, 20));
            this.pc.add(this.selectedPlace);
            ad.insertPlace(selectedPlace);
            this.selectedPlace = null;
            cityid = "";
            catid = "";
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Place Added"));
        } else {
            cityid = "";
            catid = "";
//            this.pc.add(this.selectedPlace);
            ad.updatePlace(selectedPlace.getId(), selectedPlace);
            this.selectedPlace = null;
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Place Updated"));
        }
        PrimeFaces.current().executeScript("PF('managePlaceDialog').hide()");
        PrimeFaces.current().ajax().update("form:messages", "form:dt-places");
    }

    public String getDeleteButtonMessage() {
        if (hasSelectedPlaces()) {
            int size = this.selectedPlaces.size();
            return size > 1 ? size + " selected" : "1 selected";
        }
        return "Delete";
    }

    public boolean hasSelectedPlaces() {
        return this.selectedPlaces != null && !this.selectedPlaces.isEmpty();
    }

    public void deletePlace() {
        ad.deletePlace(catid);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Place Removed"));
        PrimeFaces.current().ajax().update("form:messages", "form:dt-places");
    }

    public void deleteSelectedPlaces() {
        try {
            for (PlaceMaster p : selectedPlaces) {
                ad.deletePlace(p.getId());
            }
            selectedPlaces = null;
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Places Removed"));
            PrimeFaces.current().ajax().update("form:messages", "form:dt-places");
            PrimeFaces.current().executeScript("PF('dtPlaces').clearFilters()");
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Error Deleting Place"));
            PrimeFaces.current().executeScript("PF('dtPlaces').clearFilters()");
        }
    }

    public void selectPlace(PlaceMaster place) {
        catid = place.getCategoryId().getId();
        cityid = place.getCityId().getId().toString();
    }

}