package com.tlt.cdis;

import com.google.maps.DirectionsApi;
import com.google.maps.DirectionsApiRequest;
import com.google.maps.DistanceMatrixApi;
import com.google.maps.GeoApiContext;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.DirectionsRoute;
import com.google.maps.model.DistanceMatrix;
import com.google.maps.model.TravelMode;
import com.tlt.ejb.AdminLocal;
import com.tlt.ejb.TouristLocal;
import com.tlt.entities.PlaceMaster;
import com.tlt.entities.UserMaster;
import com.tlt.record.KeepRecord;
import com.tlt.utils.GeoLocationUtil;
import com.tlt.utils.Utils;
import static com.tlt.utils.Utils.getTime12h;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import org.primefaces.PrimeFaces;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;

@Named(value = "chosenPlaceBean")
@RequestScoped
public class ChosenPlaceBean implements Serializable {

    @EJB
    AdminLocal adminLogic;
    @EJB
    TouristLocal userLogic;
    PlaceMaster selectedPlace;
    MapBean mapBean;
    UserMaster currentUser;
    int activeTabIndex;
    String openingTime;
    String closingTime;
    String distance;
    String duration;

    @Inject
    public ChosenPlaceBean(MapBean mapBean) {
        selectedPlace = new PlaceMaster();
        this.mapBean = mapBean;
    }

    public void loadMarkers() {
        MapModel<String> model = new DefaultMapModel<>();
        this.currentUser = userLogic.findUserByUsername(KeepRecord.getUsername());
        Map<String, String> query = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        this.selectedPlace = adminLogic.getPlaceById(String.valueOf(query.get("place")));
        LatLng ll = new LatLng(Double.parseDouble(selectedPlace.getLatitude()), Double.parseDouble(selectedPlace.getLongitude()));
        model.addOverlay(new Marker<>(ll, selectedPlace.getName(), selectedPlace.getImages()));
        mapBean.setSimpleModel(model);
        PrimeFaces.current().executeScript("getLocation();");
    }

    @PostConstruct
    public void init() {
        loadMarkers();
//        geoCode();
    }

    public PlaceMaster getSelectedPlace() {
//        Map<String, String> query = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
//        this.selectedPlace = adminLogic.getPlaceById(String.valueOf(query.get("place")));
//        loadMarkers();
        return selectedPlace;
    }

    public void setSelectedPlace(PlaceMaster selectedPlace) {
        this.selectedPlace = selectedPlace;
    }

    public UserMaster getCurrentUser() {
        return userLogic.findUserByUsername(String.valueOf(KeepRecord.getUsername()));
    }

    public void setCurrentUser(UserMaster currentUser) {
        this.currentUser = currentUser;
    }

    public int getActiveTabIndex() {
        return activeTabIndex;
    }

    public void setActiveTabIndex(int activeTabIndex) {
        this.activeTabIndex = activeTabIndex;
    }

    public String getOpeningTime() {
        openingTime = Utils.getTime12h(selectedPlace.getOpeningTime());
        return openingTime;
    }

    public void setOpeningTime(String openingTime) {
        this.openingTime = openingTime;
    }

    public String getClosingTime() {
        closingTime = getTime12h(selectedPlace.getClosingTime());
        return closingTime;
    }

    public void setClosingTime(String closingTime) {
        this.closingTime = closingTime;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getDistance() {
        return distance;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getDuration() {
        return duration;
    }

    public void geoCode() {
        PrimeFaces.current().ajax().update("map");
        GeoApiContext context = new GeoApiContext.Builder().apiKey(Utils.getPropertyValue("properties.config", "MAPS_API_KEY")).build();
        try {
            String originLat = GeoLocationUtil.getUserLocation().getLatitude().toString();
            String originLng = GeoLocationUtil.getUserLocation().getLongitude().toString();
            String desinationLat = selectedPlace.getLatitude();
            String desinationLng = selectedPlace.getLongitude();
            String origin = originLat + "," + originLng;
            String destination = desinationLat + "," + desinationLng;
            DirectionsResult directionsResult = DirectionsApi.newRequest(context)
                    .mode(TravelMode.DRIVING)
                    .origin(origin)
                    .destination(destination)
                    .await();
            
            if (directionsResult.routes.length > 0) {
                DirectionsRoute route = directionsResult.routes[0];
                this.distance = route.legs[0].distance.humanReadable;
                System.out.println("Distance: "+ distance);
                this.duration = route.legs[0].duration.humanReadable;
                System.out.println("Duration: "+ duration);
            } else {
                this.distance = "Unable to calculate distance";
                this.duration = "Unable to calculte duration";
            }
        } catch (Exception ex) {
            this.distance = "Error occured in calculating distance";
            this.duration = "Error occured in calculating duration";
            ex.printStackTrace();
        }
    }
}
