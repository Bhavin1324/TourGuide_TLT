/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package com.tlt.cdis;

import com.tlt.ejb.TouristLocal;
import com.tlt.entities.PaymentMaster;
import com.tlt.record.KeepRecord;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.Collection;
import javax.ejb.EJB;

/**
 *
 * @author kunal
 */
@Named(value = "userTransactionBean")
@SessionScoped
public class UserTransactionBean implements Serializable {

    @EJB
    TouristLocal tb;
    Collection<PaymentMaster> usersSubscriptions;
    Collection<PaymentMaster> usersAppointmentHistory;
    Collection<PaymentMaster> usersEventHistory;
    public UserTransactionBean() {
    }

    public Collection<PaymentMaster> getUsersSubscriptions() {
        return tb.getUsersSubscriptionHistory(KeepRecord.getUsername());
    }

    public void setUsersSubscriptions(Collection<PaymentMaster> usersSubscriptions) {
        this.usersSubscriptions = usersSubscriptions;
    }
    public Collection<PaymentMaster> getUsersAppointmentHistory() {
        return tb.getUsersAppointmentHistory(KeepRecord.getUsername());
    }

    public void setUsersAppointmentHistory(Collection<PaymentMaster> usersAppointmentHistory) {
        this.usersAppointmentHistory = usersAppointmentHistory;
    }

    public Collection<PaymentMaster> getUsersEventHistory() {
        return tb.getUsersEventsHistory(KeepRecord.getUsername());
    }

    public void setUsersEventHistory(Collection<PaymentMaster> usersEventHistory) {
        this.usersEventHistory = usersEventHistory;
    }
}
