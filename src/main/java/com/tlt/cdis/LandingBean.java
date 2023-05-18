/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package com.tlt.cdis;

import com.tlt.ejb.AdminLocal;
import com.tlt.ejb.TouristLocal;
import com.tlt.entities.PlaceCategory;
import com.tlt.entities.PlaceMaster;
import com.tlt.entities.UserMaster;
import com.tlt.record.KeepRecord;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import org.primefaces.PrimeFaces;
import org.primefaces.event.map.OverlaySelectEvent;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;



@Named(value = "landingBean")
@SessionScoped
public class LandingBean implements Serializable {

    @EJB AdminLocal adminLogic;
    @EJB TouristLocal userLogic;
    private MapModel<String> advancedModel;
    private Marker<String> marker;
    
    String placeName;
    Collection<PlaceMaster> recommandedPlaces;
    UserMaster currentUser;
    ArrayList<PlaceCategory> placeCategories;
    public LandingBean() {
        recommandedPlaces = new ArrayList<>();
        currentUser = new UserMaster();
        placeCategories = new ArrayList<>();
    }
    
    @PostConstruct
    public void init() {
        Collection<PlaceMaster> places = adminLogic.getAllPlaces();
        advancedModel = new DefaultMapModel<>();
        for (PlaceMaster p : places) {
            LatLng ll = new LatLng(Double.parseDouble(p.getLatitude()), Double.parseDouble(p.getLongitude()));
            advancedModel.addOverlay(new Marker<>(ll,p.getName(),p.getImages()));
        }
        PrimeFaces.current().executeScript("getLocation();");
    }

    public void onMarkerSelect(OverlaySelectEvent<String> event) {
        marker = (Marker) event.getOverlay();
    }
    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public Collection<PlaceMaster> getRecommandedPlaces() {
        recommandedPlaces = adminLogic.getAllPlaces();
        ArrayList<PlaceMaster> recPlace = new ArrayList<>(recommandedPlaces);
        return recPlace.subList(recPlace.size() - 4, recPlace.size());
    }

    public void setRecommandedPlaces(Collection<PlaceMaster> recommandedPlaces) {
        this.recommandedPlaces = recommandedPlaces;
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

    public UserMaster getCurrentUser() {
        return userLogic.findUserByUsername(String.valueOf(KeepRecord.getUsername()));
    }

    public void setCurrentUser(UserMaster currentUser) {
        this.currentUser = currentUser;
    }

    public ArrayList<PlaceCategory> getPlaceCategories() {
        if(placeCategories.size() > 0){
            return this.placeCategories;
        }
        ArrayList<PlaceCategory> placeCategoryList = new ArrayList( adminLogic.getAllPlaceCategories());
       
        for(PlaceCategory pc : placeCategoryList){
            ArrayList<PlaceMaster> pm = new ArrayList<>(pc.getPlaceMasterCollection());
            if(pm.size() == 0 || pm == null){
                continue;
            }
            placeCategories.add(pc);
        }   
        return this.placeCategories;
    }

    public void setPlaceCategories(ArrayList<PlaceCategory> placeCategories) {
        this.placeCategories = placeCategories;
    }
    
    // This method would use in landing page search method as auto complete 
    public List<String> placesNameList(String query){
        Collection<PlaceMaster> places = adminLogic.getAllPlaces();
        List<String> namesOfPlaces = new ArrayList<>();
        for(PlaceMaster place : places){
            namesOfPlaces.add(place.getName());
        }
        return namesOfPlaces.stream().filter(place -> place.toLowerCase().contains(query.toLowerCase())).collect(Collectors.toList());
    }
}
