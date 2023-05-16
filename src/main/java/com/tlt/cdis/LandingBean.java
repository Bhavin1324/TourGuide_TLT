/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package com.tlt.cdis;

import com.tlt.ejb.AdminLocal;
import com.tlt.entities.PlaceMaster;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import javax.ejb.EJB;


@Named(value = "landingBean")
@SessionScoped
public class LandingBean implements Serializable {

    @EJB AdminLocal adminLogic;
    // Landing page search place variable
    String placeName;
    Collection<PlaceMaster> recommandedPlaces;
    public LandingBean() {
        
    }

    public String getPlaceName() {
        return placeName;
    }

    public Collection<PlaceMaster> getRecommandedPlaces() {
        return adminLogic.getAllPlaces();
    }

    public void setRecommandedPlaces(Collection<PlaceMaster> recommandedPlaces) {
        this.recommandedPlaces = recommandedPlaces;
    }
    
    
    // This method would use in landing page search method as auto complete 
    public List<String> placesNameList(String query){
        Collection<PlaceMaster> places = adminLogic.getAllPlaces();
        List<String> namesOfPlaces = new ArrayList<>();
        for(PlaceMaster place : places){
            namesOfPlaces.add(place.getName());
        }
        return namesOfPlaces.stream().filter(place -> place.toLowerCase().contains(query.toLowerCase())).collect(Collectors.toList()).subList(namesOfPlaces.size() - 4, namesOfPlaces.size());
    }
}
