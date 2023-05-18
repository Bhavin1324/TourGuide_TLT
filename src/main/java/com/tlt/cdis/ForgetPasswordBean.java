package com.tlt.cdis;

import static com.tlt.constants.PathConstants.TEMPLATES_PATH;
import com.tlt.ejb.AdminLocal;
import com.tlt.ejb.TouristLocal;
import com.tlt.entities.UserMaster;
import com.tlt.utils.EmailUtil;
import com.tlt.utils.Utils;
import java.io.Serializable;
import java.util.HashMap;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.primefaces.PrimeFaces;

@Named(value = "forgetPasswordBean")
@SessionScoped
public class ForgetPasswordBean implements Serializable {

    @EJB
    AdminLocal adminLogic;
    @EJB
    TouristLocal userLogic;
    UserMaster userMaster, existedUser;
    String confirmPass;
    EmailUtil mailUtil;

    String enteredOtp;
    String generatedOtp;

    public ForgetPasswordBean() {
        userMaster = new UserMaster();
        existedUser = new UserMaster();
        mailUtil = new EmailUtil("properties.config");
    }

    public UserMaster getUserMaster() {
        return userMaster;
    }

    public void setUserMaster(UserMaster user) {
        this.userMaster = user;
    }

    public String getConfirmPass() {
        return confirmPass;
    }

    public void setConfirmPass(String confirmPass) {
        this.confirmPass = confirmPass;
    }

    public String getEnteredOtp() {
        return enteredOtp;
    }

    public void setEnteredOtp(String enteredOtp) {
        this.enteredOtp = enteredOtp;
    }

    public void askForOtp() {
        existedUser = userLogic.findUserByEmail(userMaster.getEmail().toString());
        if (existedUser == null) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Unable to find such user. Register yourself first.", ""));
            return;
        }
        if (!userMaster.getPassword().equals(confirmPass)) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Password: Confirm password must match with entered password", ""));
            return;
        }
        sendOTP();
        PrimeFaces.current().executeScript("PF('otp_dlg').show()");
    }

    public void verifyAndChange() {
        if (!enteredOtp.equals(generatedOtp)) {
            FacesContext.getCurrentInstance().addMessage("otp-form:otp-dialog:otp-panel:single-message", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Entered OTP is invalid", ""));
            return;
        }

        String hashedPassword = Utils.generateHash(userMaster.getPassword().toString());
        existedUser.setPassword(hashedPassword);
        userLogic.updateUser(existedUser.getId(), existedUser);

        PrimeFaces.current().executeScript("PF('otp_dlg').hide()");
        PrimeFaces.current().executeScript("PF('success_dlg').show()");

    }

    private void sendOTP() {
        mailUtil.setSubject("One time password (OTP) for resetting your Landmark tours' account");
        mailUtil.setTemplatePath(TEMPLATES_PATH);
        mailUtil.setTemplateName("EmailTemplate.html");
        HashMap<String, Object> dataModel = new HashMap<>();
        generatedOtp = Utils.generateOtp(6);
        dataModel.put("OTP", generatedOtp);
        mailUtil.setDataModel(dataModel);

        boolean mailStatus = mailUtil.sendSingleMailSync(userMaster.getEmail().toString());
        if (!mailStatus) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Unable to send OTP mail", ""));
            return;
        }
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "OTP has been sent to respected email", ""));
    }

}
