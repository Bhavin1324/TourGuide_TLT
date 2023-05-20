/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tlt.utils;

import com.tlt.entities.SubscriptionMaster;
import com.tlt.entities.UserMaster;

/**
 *
 * @author kunal
 */
public class UserSubscriptionMapping {
    private UserMaster user;
    private SubscriptionMaster subMaster;

    public UserMaster getUser() {
        return user;
    }

    public void setUser(UserMaster user) {
        this.user = user;
    }

    public SubscriptionMaster getSubMaster() {
        return subMaster;
    }

    public void setSubMaster(SubscriptionMaster subMaster) {
        this.subMaster = subMaster;
    }
    
}
