package com.tlt.cdis;

import com.google.maps.DirectionsApi;
import com.google.maps.GeoApiContext;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.DirectionsRoute;
import com.google.maps.model.TravelMode;
import com.maxmind.geoip2.model.CityResponse;
import com.tlt.ejb.AdminLocal;
import com.tlt.ejb.TouristLocal;
import com.tlt.entities.GuideMaster;
import com.tlt.entities.PlaceMaster;
import com.tlt.entities.UserMaster;
import com.tlt.record.KeepRecord;
import com.tlt.utils.GeoLocationUtil;
import com.tlt.utils.Utils;
import static com.tlt.utils.Utils.getTime12h;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpSession;
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
    @Inject
    HttpSession session;
    PlaceMaster selectedPlace;
    MapBean mapBean;
    UserMaster currentUser;
    int activeTabIndex;
    String openingTime;
    String closingTime;
    String distance;
    String duration;

    Collection<GuideMaster> guidsOfPlaces;

    @Inject
    public ChosenPlaceBean(MapBean mapBean) {
        selectedPlace = new PlaceMaster();
        this.mapBean = mapBean;
        guidsOfPlaces = new ArrayList<>();
    }

    public void loadMarkers() {
        MapModel<String> model = new DefaultMapModel<>();
        this.currentUser = userLogic.findUserByUsername(KeepRecord.getUsername());
        Map<String, String> query = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        String placeId = String.valueOf(query.get("place"));
        session.setAttribute("pid", placeId);
        this.selectedPlace = adminLogic.getPlaceById(placeId);
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

    public Collection<GuideMaster> getGuidsOfPlaces() {
        this.guidsOfPlaces = adminLogic.getAllGuidesOfPlaces(selectedPlace.getId());
        return guidsOfPlaces;
    }

    public void setGuidsOfPlaces(Collection<GuideMaster> guidsOfPlaces) {
        this.guidsOfPlaces = guidsOfPlaces;
    }

    public void geoCode() {
        PrimeFaces.current().ajax().update("map");
        GeoApiContext context = new GeoApiContext.Builder().apiKey(Utils.getPropertyValue("properties.config", "MAPS_API_KEY")).build();
        try {
            CityResponse userLocation = GeoLocationUtil.getUserLocation();
            String originLat = userLocation.getLocation().getLatitude().toString();
            String originLng = userLocation.getLocation().getLongitude().toString();
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
                this.duration = route.legs[0].duration.humanReadable;
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
