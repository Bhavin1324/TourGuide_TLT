package com.tlt.cdis;

import static com.tlt.constants.JwtConstants.ROLE_GUIDE;
import static com.tlt.constants.PathConstants.TEMPLATES_PATH;
import com.tlt.constants.UrlConstants;
import static com.tlt.constants.UrlConstants.TO_LOGOUT;
import com.tlt.ejb.AdminLocal;
import com.tlt.ejb.TouristLocal;
import com.tlt.entities.GuideMaster;
import com.tlt.entities.PlaceMaster;
import com.tlt.entities.UserMaster;
import com.tlt.entities.UserRole;
import com.tlt.record.KeepRecord;
import com.tlt.utils.EmailUtil;
import com.tlt.utils.Utils;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.primefaces.PrimeFaces;

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
    EmailUtil mailUtil;

    String enteredOtp;
    String generatedOtp;

    public BeGuideBean() {
        guideMaster = new GuideMaster();
        placesNames = new ArrayList<>();
        mailUtil = new EmailUtil("properties.config");
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

    public String getEnteredOtp() {
        return enteredOtp;
    }

    public void setEnteredOtp(String enteredOtp) {
        this.enteredOtp = enteredOtp;
    }

    public void verify() {
        sendOTP();
        PrimeFaces.current().executeScript("PF('otp_dlg').show()");
    }

    public void registerGuide() {
        if (!enteredOtp.equals(generatedOtp)) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Entered OTP is invalid", ""));
            return;
        }
        try {
            Collection<PlaceMaster> markedPlaces = new ArrayList<>();
            for (String placeName : placesNames) {
                ArrayList<PlaceMaster> placesByName = new ArrayList(adminLogic.getPlacesByName(placeName));
                markedPlaces.add(placesByName.get(0));
            }
            this.guideMaster.setId(Utils.getUUID());
            this.guideMaster.setName(loggedInUser.getName());
            this.guideMaster.setEmail(loggedInUser.getEmail());
            this.guideMaster.setPhoneNumber(loggedInUser.getContact());
            this.guideMaster.setUsername(loggedInUser.getUsername());
            this.guideMaster.setProfileImage(loggedInUser.getProfileImage());
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
            PrimeFaces.current().executeScript("PF('success_dialog').show()");
            PrimeFaces.current().executeScript("PF('otp_dlg').hide()");

        } catch (Exception ex) {
            ex.printStackTrace();
            PrimeFaces.current().executeScript("PF('error_dialog').show()");
        }
    }

    private void sendOTP() {
        mailUtil.setSubject("One time password (OTP) for verifying tourist role");
        mailUtil.setTemplatePath(TEMPLATES_PATH);
        mailUtil.setTemplateName("GuideVerification.html");
        HashMap<String, Object> dataModel = new HashMap<>();
        generatedOtp = Utils.generateOtp(6);
        dataModel.put("OTP", generatedOtp);
        mailUtil.setDataModel(dataModel);

        boolean mailStatus = mailUtil.sendSingleMailSync(loggedInUser.getEmail().toString());
        if (!mailStatus) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Unable to send OTP mail", ""));
            return;
        }
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "OTP has been sent to respected email", ""));
    }

    public void loginToGuide() throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect(TO_LOGOUT);
    }
}
