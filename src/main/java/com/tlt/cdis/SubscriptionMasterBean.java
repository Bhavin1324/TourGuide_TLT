/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package com.tlt.cdis;

import com.tlt.ejb.TouristLocal;
import com.tlt.entities.SubscriptionMaster;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author kunal
 */
@Named(value = "subscriptionMasterBean")
@SessionScoped
public class SubscriptionMasterBean implements Serializable {

    @EJB TouristLocal tb;
    private Collection<SubscriptionMaster> usersSubscriptions;
    public SubscriptionMasterBean() {
        usersSubscriptions = new ArrayList<>();
    }

    public Collection<SubscriptionMaster> getUsersSubscriptions() {
      
        HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        String username = req.getSession().getAttribute("username").toString();

        return tb.getUsersSubscriptions(username);
    }

    public void setUsersSubscriptions(Collection<SubscriptionMaster> usersSubscriptions) {
        this.usersSubscriptions = usersSubscriptions;
    }
    
    
}
