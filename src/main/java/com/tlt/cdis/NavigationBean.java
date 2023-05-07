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
import javax.faces.context.ExternalContext;
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

}
