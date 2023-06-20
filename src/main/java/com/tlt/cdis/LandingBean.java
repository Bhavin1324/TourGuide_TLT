package com.tlt.cdis;

import com.maxmind.geoip2.model.CityResponse;
import static com.tlt.constants.UrlConstants.TO_CHOSEN_PLACES_FORWARD;
import com.tlt.ejb.AdminLocal;
import com.tlt.ejb.TouristLocal;
import com.tlt.entities.PlaceCategory;
import com.tlt.entities.PlaceMaster;
import com.tlt.entities.UserMaster;
import com.tlt.record.KeepRecord;
import static com.tlt.utils.GeoLocationUtil.getUserLocation;
import com.tlt.utils.Utils;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import org.primefaces.PrimeFaces;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;

@Named(value = "landingBean")
@SessionScoped
public class LandingBean implements Serializable {

    @EJB
    AdminLocal adminLogic;
    @EJB
    TouristLocal userLogic;

    String placeName;
    Collection<PlaceMaster> recommandedPlaces;
    UserMaster currentUser;
    ArrayList<PlaceCategory> placeCategories;
    PlaceCategory selectedCategory;
    Collection<PlaceMaster> lstPlacesByCategory;

    MapBean mapBean;
    String currentLat;
    String currentLng;
    CityResponse userLocation;

    @Inject
    public LandingBean(MapBean mapBean) {
        recommandedPlaces = new ArrayList<>();
        currentUser = new UserMaster();
        placeCategories = new ArrayList<>();
        lstPlacesByCategory = new ArrayList<>();
        this.mapBean = mapBean;
    }

    public void loadMarkers() {
        userLocation = getUserLocation();
        String currentUserCity = userLocation.getCity().getName();
        String countryCode = userLocation.getCountry().getIsoCode();

        Collection<PlaceMaster> places = adminLogic.getPlacesByCity(currentUserCity, countryCode);
        MapModel<String> advancedModel = new DefaultMapModel<>();
        for (PlaceMaster p : places) {
            LatLng ll = new LatLng(Double.parseDouble(p.getLatitude()), Double.parseDouble(p.getLongitude()));
            advancedModel.addOverlay(new Marker<>(ll, p.getName(), p.getImages()));
        }
        mapBean.setAdvancedModel(advancedModel);
        PrimeFaces.current().executeScript("getLocation();");
        this.currentLat = userLocation.getLocation().getLatitude().toString();
        this.currentLng = userLocation.getLocation().getLongitude().toString();
    }

    public void loadMarkerByCategory() {
        MapModel<String> categoryModel = new DefaultMapModel<>();
        for (PlaceMaster p : lstPlacesByCategory) {
            LatLng ll = new LatLng(Double.parseDouble(p.getLatitude()), Double.parseDouble(p.getLongitude()));
            categoryModel.addOverlay(new Marker<>(ll, p.getName(), p.getImages()));
        }
        mapBean.setCategoryModel(categoryModel);
        PrimeFaces.current().executeScript("getLocation();");
    }

    @PostConstruct
    public void init() {
        loadMarkers();
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public Collection<PlaceMaster> getRecommandedPlaces() {
        recommandedPlaces = adminLogic.getPlacesByCity(userLocation.getCity().getName(), userLocation.getCountry().getIsoCode().toString());
        ArrayList<PlaceMaster> recPlace = new ArrayList<>(recommandedPlaces);
        return recPlace.subList(recPlace.size() - 4, recPlace.size());
    }

    public void setRecommandedPlaces(Collection<PlaceMaster> recommandedPlaces) {
        this.recommandedPlaces = recommandedPlaces;
    }

    public UserMaster getCurrentUser() {
        return userLogic.findUserByUsername(String.valueOf(KeepRecord.getUsername()));
    }

    public void setCurrentUser(UserMaster currentUser) {
        this.currentUser = currentUser;
    }

    public String getCurrentLat() {
        return currentLat;
    }

    public void setCurrentLat(String currentLat) {
        this.currentLat = currentLat;
    }

    public String getCurrentLng() {
        return currentLng;
    }

    public void setCurrentLng(String currentLng) {
        this.currentLng = currentLng;
    }

    public Collection<PlaceMaster> getLstPlacesByCategory() {
        return lstPlacesByCategory;
    }

    public void setLstPlacesByCategory(Collection<PlaceMaster> lstPlacesByCategory) {
        this.lstPlacesByCategory = lstPlacesByCategory;
    }

    public PlaceCategory getSelectedCategory() {
        return selectedCategory;
    }

    public void setSelectedCategory(PlaceCategory selectedCategory) {
        this.selectedCategory = selectedCategory;
    }

    public ArrayList<PlaceCategory> getPlaceCategories() {
        if (placeCategories.size() > 0) {
            return this.placeCategories;
        }
        String currentUserCity = userLocation.getCity().getName();
        String countryCode = userLocation.getCountry().getIsoCode();
        ArrayList<PlaceCategory> placeCategoryList = new ArrayList(adminLogic.getAllPlaceCategories());

        for (PlaceCategory pc : placeCategoryList) {
            ArrayList<PlaceMaster> pm = new ArrayList<>(adminLogic.getCityPlacesByCategory(pc, currentUserCity, countryCode));
            if ((pm.size() == 0 || pm == null)) {
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
    public List<String> placesNameList(String query) {
        Collection<PlaceMaster> places = adminLogic.getAllPlaces();
        List<String> namesOfPlaces = new ArrayList<>();
        for (PlaceMaster place : places) {
            namesOfPlaces.add(place.getName());
        }
        return namesOfPlaces.stream().filter(place -> place.toLowerCase().contains(query.toLowerCase())).collect(Collectors.toList());
    }

    public void redirectToPlace() {
        try {
            Collection<PlaceMaster> places = adminLogic.getPlacesByName(placeName);
            for (PlaceMaster p : places) {
                if (p.getName().equals(placeName)) {
                    FacesContext.getCurrentInstance().getExternalContext().redirect(TO_CHOSEN_PLACES_FORWARD + "?place=" + p.getId());
                    return;
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void onCatCardSelect(PlaceCategory category) {
        String currentUserCity = userLocation.getCity().getName();
        String countryCode = userLocation.getCountry().getIsoCode();
        lstPlacesByCategory = adminLogic.getCityPlacesByCategory(category, currentUserCity, countryCode);
        selectedCategory = category;
        loadMarkerByCategory();
        PrimeFaces.current().executeScript("PF('c_dialog').show()");
        PrimeFaces.current().ajax().update(":dialog-form:category-dialog");
    }

    public String getTime(Date date) {
        return Utils.getTime12h(date);
    }

    public String getCurrentYear() {
        Calendar cal = Calendar.getInstance();
        return String.valueOf(cal.get(Calendar.YEAR));
    }
}
