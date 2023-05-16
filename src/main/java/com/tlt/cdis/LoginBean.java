package com.tlt.cdis;

import static com.tlt.constants.UrlConstants.TO_LOGOUT;
import com.tlt.record.KeepRecord;
import java.io.IOException;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.faces.context.FacesContext;


@Named(value = "loginBean")
@RequestScoped
public class LoginBean implements Serializable{

    private String errorStatus;
    public LoginBean() {
        errorStatus = KeepRecord.getErrorStatus();
    }

    public String getErrorStatus() {
        return errorStatus;
    }

    public void setErrorStatus(String errorStatus) {
        this.errorStatus = errorStatus;
    }

    public void Logout() {
        try {
            KeepRecord.reset();
            FacesContext.getCurrentInstance().getExternalContext().redirect(TO_LOGOUT);
        } catch (IOException ex) {
            Logger.getLogger(LoginBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
