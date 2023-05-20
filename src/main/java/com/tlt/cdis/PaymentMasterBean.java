/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package com.tlt.cdis;

import com.tlt.ejb.TouristLocal;
import com.tlt.entities.PaymentMaster;
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

    Collection<PaymentMaster> usersPayments;

    public PaymentMasterBean() {
        usersPayments = new ArrayList<>();
    }

    public Collection<PaymentMaster> getUsersPayments() {
        HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        String uname = req.getSession().getAttribute("username").toString();
        return tb.usersPaymentHistory(uname);
    }

    public void setUsersPayments(Collection<PaymentMaster> usersPayments) {
        this.usersPayments = usersPayments;
    }

}
