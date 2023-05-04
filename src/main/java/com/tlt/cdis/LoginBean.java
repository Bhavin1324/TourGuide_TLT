/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package com.tlt.cdis;

import com.tlt.record.KeepRecord;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author kunal
 */
@Named(value = "loginBean")
@RequestScoped
public class LoginBean {

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

    public void Logout(boolean status) {
        try {
            KeepRecord.reset();
            FacesContext.getCurrentInstance().getExternalContext().redirect("/TheLandmarkTour/Logout.jsf");
        } catch (IOException ex) {
            Logger.getLogger(LoginBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
