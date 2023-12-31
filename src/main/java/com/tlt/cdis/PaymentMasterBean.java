/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package com.tlt.cdis;

import com.tlt.ejb.AdminLocal;
import com.tlt.ejb.TouristLocal;
import com.tlt.entities.PaymentMaster;
import com.tlt.entities.SubscriptionMaster;
import com.tlt.record.KeepRecord;
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
@Named(value = "paymentMasterBean")
@SessionScoped
public class PaymentMasterBean implements Serializable {

    @EJB
    TouristLocal tb;
    
    @EJB AdminLocal ad;

    Collection<PaymentMaster> usersPayments;
    Collection<PaymentMaster> adminAllPaymentDetails;
    Collection<PaymentMaster> usersSubscriptions;
    
    
    public PaymentMasterBean() {
        usersPayments = new ArrayList<>();
        adminAllPaymentDetails= new ArrayList<>();
    }

    public Collection<PaymentMaster> getUsersPayments() {
        String uname = KeepRecord.getUsername();
        return tb.usersPaymentHistory(uname);
    }

    public void setUsersPayments(Collection<PaymentMaster> usersPayments) {
        this.usersPayments = usersPayments;
    }

    public Collection<PaymentMaster> getAdminAllPaymentDetails() {
        return ad.getAllPaymentDetails();
    }

    public void setAdminAllPaymentDetails(Collection<PaymentMaster> adminAllPaymentDetails) {
        this.adminAllPaymentDetails = adminAllPaymentDetails;
    }

    public Collection<PaymentMaster> getUsersSubscriptions() {
        return tb.getUsersSubscriptionHistory(KeepRecord.getUsername());
    }

    public void setUsersSubscriptions(Collection<PaymentMaster> usersSubscriptions) {
        this.usersSubscriptions = usersSubscriptions;
    }

    
    

}
