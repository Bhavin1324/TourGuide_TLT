/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package com.tlt.cdis;

import static com.tlt.constants.UrlConstants.TO_SUBS_CARDS;
import com.tlt.ejb.AdminLocal;
import com.tlt.ejb.TouristLocal;
import com.tlt.entities.SubscriptionMaster;
import com.tlt.entities.SubscriptionModel;
import com.tlt.record.KeepRecord;
import com.tlt.utils.UserSubscriptionMapping;
import java.io.IOException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import org.primefaces.PrimeFaces;

/**
 *
 * @author kunal
 */
@Named(value = "subMasterBean")
@SessionScoped
public class SubscriptionMasterBean implements Serializable {

    @EJB
    TouristLocal tb;
    @EJB AdminLocal ad;
    private Collection<SubscriptionMaster> usersSubscriptions;
    Collection<UserSubscriptionMapping> adminAllSubscriptions;

    public SubscriptionMasterBean() {
        usersSubscriptions = new ArrayList<>();
        adminAllSubscriptions = new ArrayList<>();
    }

    public Collection<SubscriptionMaster> getUsersSubscriptions() {
        String username = KeepRecord.getUsername();
        usersSubscriptions = tb.getUsersSubscriptions(username);
        if (!usersSubscriptions.isEmpty()) {
            return usersSubscriptions;
        } else {
            PrimeFaces.current().executeScript("PF('noSubsDlg').show()");
            return usersSubscriptions;
        }
    }

    public Collection<UserSubscriptionMapping> getAdminAllSubscriptions() {
        return ad.getAllSubscriptions();
    }

    public void setAdminAllSubscriptions(Collection<UserSubscriptionMapping> adminAllSubscriptions) {
        this.adminAllSubscriptions = adminAllSubscriptions;
    }

    public void redirectToSubscriptionPlans() {
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect(TO_SUBS_CARDS);
        } catch (IOException ex) {
            Logger.getLogger(SubscriptionMasterBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setUsersSubscriptions(Collection<SubscriptionMaster> usersSubscriptions) {
        this.usersSubscriptions = usersSubscriptions;
    }

}
