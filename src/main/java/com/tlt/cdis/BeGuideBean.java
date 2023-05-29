package com.tlt.cdis;

import static com.tlt.constants.JwtConstants.ROLE_GUIDE;
import com.tlt.constants.UrlConstants;
import com.tlt.ejb.AdminLocal;
import com.tlt.ejb.TouristLocal;
import com.tlt.entities.GuideMaster;
import com.tlt.entities.PlaceMaster;
import com.tlt.entities.UserMaster;
import com.tlt.entities.UserRole;
import com.tlt.record.KeepRecord;
import com.tlt.utils.Utils;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;

@Named(value = "beGuideBean")
@SessionScoped
public class BeGuideBean implements Serializable {

    @EJB
    AdminLocal adminLogic;
    @EJB
    TouristLocal touristLogic;
    GuideMaster guideMaster;
    UserMaster loggedInUser;
    List<String> placesNames;

    public BeGuideBean() {
        guideMaster = new GuideMaster();
        placesNames = new ArrayList<>();
    }

    public GuideMaster getGuideMaster() {
        return guideMaster;
    }

    public void setGuideMaster(GuideMaster guideMaster) {
        this.guideMaster = guideMaster;
    }

    public UserMaster getLoggedInUser() {
        this.loggedInUser = touristLogic.findUserByUsername(KeepRecord.getUsername());
        return loggedInUser;
    }

    public void setLoggedInUser(UserMaster loggedInUser) {
        this.loggedInUser = loggedInUser;
    }

    public List<String> getPlacesNames() {
        return placesNames;
    }

    public void setPlacesNames(List<String> placesNames) {
        this.placesNames = placesNames;
    }

    public void registerGuide() {
        try {
            Collection<PlaceMaster> markedPlaces = new ArrayList<>();
            for (String placeName : placesNames) {
                ArrayList<PlaceMaster> placesByName = new ArrayList(adminLogic.getPlacesByName(placeName));
                markedPlaces.add(placesByName.get(0));
                System.out.println("places by name: " + placesByName);
                System.out.println("places by name: " + markedPlaces);
            }
            this.guideMaster.setId(Utils.getUUID());
            this.guideMaster.setName(loggedInUser.getName());
            this.guideMaster.setEmail(loggedInUser.getEmail());
            this.guideMaster.setPhoneNumber(loggedInUser.getContact());
            this.guideMaster.setIsAppointed(false);

            this.loggedInUser = getLoggedInUser();
            UserRole role = new UserRole(loggedInUser.getUsername(), ROLE_GUIDE);
            role.setUserMaster(loggedInUser);
            adminLogic.addUserRole(role);
            adminLogic.insertGuide(guideMaster);
            adminLogic.mapGuideWithPlaces(guideMaster, markedPlaces);
            this.loggedInUser = new UserMaster();
            this.guideMaster = new GuideMaster();
            placesNames = new ArrayList<>();
            FacesContext.getCurrentInstance().getExternalContext().redirect(UrlConstants.TO_TOURIST);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
