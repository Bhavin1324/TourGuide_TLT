package com.tlt.cdis;

import com.tlt.constants.UrlConstants;
import static com.tlt.constants.UrlConstants.TO_SUBS_CARDS;
import com.tlt.ejb.AdminLocal;
import com.tlt.ejb.TouristLocal;
import com.tlt.entities.SubscriptionMaster;
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
import org.primefaces.PrimeFaces;

@Named(value = "subMasterBean")
@SessionScoped
public class SubscriptionMasterBean implements Serializable {

    @EJB
    TouristLocal tb;
    @EJB
    AdminLocal ad;
    private Collection<SubscriptionMaster> usersSubscriptions;
    Collection<UserSubscriptionMapping> adminAllSubscriptions;
    boolean isPrimeUser;

    public SubscriptionMasterBean() {
        usersSubscriptions = new ArrayList<>();
        adminAllSubscriptions = new ArrayList<>();
    }

    public Collection<SubscriptionMaster> getUsersSubscriptions() {
        String username = KeepRecord.getUsername();
        usersSubscriptions = tb.getUsersSubscriptions(username);
        return usersSubscriptions;
    }

    public Collection<UserSubscriptionMapping> getAdminAllSubscriptions() {
        return ad.getAllSubscriptions();
    }

    public void setAdminAllSubscriptions(Collection<UserSubscriptionMapping> adminAllSubscriptions) {
        this.adminAllSubscriptions = adminAllSubscriptions;
    }

    public void setUsersSubscriptions(Collection<SubscriptionMaster> usersSubscriptions) {
        this.usersSubscriptions = usersSubscriptions;
    }

    public boolean isIsPrimeUser() {
        usersSubscriptions = tb.getUsersSubscriptions(KeepRecord.getUsername());
        for (SubscriptionMaster sm : usersSubscriptions) {
            if (sm.getSubscriptionModelId().getName().equalsIgnoreCase("prime")) {
                isPrimeUser = true;
                return isPrimeUser;
            }
        }
        isPrimeUser = false;
        return isPrimeUser;
    }

    public void setIsPrimeUser(boolean isPrimeUser) {
        this.isPrimeUser = isPrimeUser;
    }

    public void redirectIfNotPrime() {
        try {
            if (!isIsPrimeUser()) {
                FacesContext.getCurrentInstance().getExternalContext().redirect(UrlConstants.TO_TOURIST);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
