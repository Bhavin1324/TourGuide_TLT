/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package com.tlt.cdis;

import com.tlt.constants.UrlConstants;
import static com.tlt.constants.UrlConstants.TO_ADMIN;
import static com.tlt.constants.UrlConstants.TO_GUIDE;
import static com.tlt.constants.UrlConstants.TO_PLACE;
import static com.tlt.constants.UrlConstants.TO_PLACE_CATEGORY;
import static com.tlt.constants.UrlConstants.TO_TOURIST;
import com.tlt.record.KeepRecord;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.Set;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

@Named(value = "navigationBean")
@SessionScoped
public class NavigationBean implements Serializable {

    Set<String> roles;

    public NavigationBean() {
        roles = KeepRecord.getRoles();
    }

    public void toHome() {
        try {

            if (roles.contains("admin")) {
                FacesContext.getCurrentInstance().getExternalContext().redirect(TO_ADMIN);
            }
            if (roles.contains("tourist")) {
                FacesContext.getCurrentInstance().getExternalContext().redirect(TO_TOURIST);
            }
            if (roles.contains("guide")) {
                FacesContext.getCurrentInstance().getExternalContext().redirect(TO_GUIDE);
            }
        } catch (Exception ex) {
            System.out.println("Exception in redirection from context");
            ex.printStackTrace();
        }
    }

    public void redirectTo(String destination) {
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect(destination);
        } catch (Exception ex) {
            System.out.println("Exception in redirection from context");
            ex.printStackTrace();
        }
    }

    public void confirm(String navigation) {
        addMessage("Confirmed", "You have accepted");
    }

    public void delete() {
        addMessage("Confirmed", "Record deleted");
    }

    public void logout(String navigation) {
        addMessage("Confirmed", "Logged out");
        redirectTo(navigation);
    }

    public void addMessage(String summary, String detail) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, detail);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
}
