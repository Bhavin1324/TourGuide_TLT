/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package com.tlt.cdis;

import com.tlt.ejb.TouristLocal;
import com.tlt.entities.UserMaster;
import com.tlt.record.KeepRecord;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import javax.ejb.EJB;

/**
 *
 * @author Lenovo
 */
@Named(value = "touristBean")
@SessionScoped
public class TouristBean implements Serializable {

    @EJB
    TouristLocal userLogic;
    UserMaster userMaster;

    public TouristBean() {
        userMaster = new UserMaster();
    }

    public UserMaster getUserMaster() {
        userMaster = userLogic.findUserByUsername(KeepRecord.getUsername());
        return userMaster;
    }

    public void setUserMaster(UserMaster userMaster) {
        this.userMaster = userMaster;
    }

}
